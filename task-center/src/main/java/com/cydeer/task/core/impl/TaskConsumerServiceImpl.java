package com.cydeer.task.core.impl;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.MethodConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.cydeer.task.api.TaskDispatcher;
import com.cydeer.task.core.api.TaskConsumerService;
import com.cydeer.task.core.api.TaskInfoRepo;
import com.cydeer.task.core.api.TaskStatus;
import com.cydeer.task.core.entity.TaskInfo;
import com.cydeer.task.dto.TaskInvokerInfo;
import com.cydeer.task.result.AjaxException;
import com.cydeer.task.result.AjaxResult;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ConcurrentReferenceHashMap;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangsong on 2016/10/15.
 */
@Service("taskConsumerService")
public class TaskConsumerServiceImpl implements TaskConsumerService {

	@Resource
	private TaskInfoRepo taskInfoRepo;

	@Resource
	private ApplicationConfig applicationConfig;

	@Resource
	private RegistryConfig registryConfig;

	/**
	 * 缓存各个系统的task消费者
	 */
	private Map<String, ReferenceConfig<TaskDispatcher>> taskConsumerCache = new ConcurrentReferenceHashMap<>();

	private Object lock = new Object();

	@Override public AjaxResult<Boolean> validate(Integer taskId) {
		TaskInfo taskInfo = taskInfoRepo.findById(taskId);
		if (taskInfo == null || Integer.valueOf(1).equals(taskInfo.getIsDelete())) {
			throw new AjaxException("-1", "任务不存在或者任务已经删除");
		}
		TaskInvokerInfo taskInvokerInfo = buildTaskInvokerInfo(taskInfo);
		// TODO: 2016/10/15 异常处理
		return getTaskConsumer(taskInfo.getAppName()).get().validateTask(taskInvokerInfo);
	}

	private TaskInvokerInfo buildTaskInvokerInfo(TaskInfo taskInfo) {
		TaskInvokerInfo taskInvokerInfo = new TaskInvokerInfo();
		taskInvokerInfo.setAppName(taskInfo.getAppName());
		taskInvokerInfo.setBeanName(taskInfo.getBeanName());
		taskInvokerInfo.setJobId(taskInfo.getId());
		taskInvokerInfo.setMethodName(taskInfo.getMethodName());
		taskInvokerInfo.setTaskName(taskInfo.getTaskName());
		return taskInvokerInfo;
	}

	@Override public void dispatch(Integer taskId) {
		TaskInfo taskInfo = taskInfoRepo.findById(taskId);
		if (taskInfo == null || !TaskStatus.READY.getCode().equals(taskInfo.getStatus())) {
			// 不存在或者没有启用的状态不处理
			return;
		}
		taskInfoRepo.updateTaskStatus(taskId, TaskStatus.RUNNING.getCode());
		TaskInvokerInfo taskInvokerInfo = buildTaskInvokerInfo(taskInfo);
		taskInvokerInfo.setStartTime(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		// TODO: 2016/10/15 异常处理 
		getTaskConsumer(taskInfo.getAppName()).get().dispatch(taskInvokerInfo);
	}

	private ReferenceConfig<TaskDispatcher> getTaskConsumer(String appName) {
		synchronized (lock) {
			if (taskConsumerCache.containsKey(appName)) {
				return taskConsumerCache.get(appName);
			}
			ReferenceConfig<TaskDispatcher> reference = new ReferenceConfig<>(); // 该实例很重量，里面封装了所有与注册中心及服务提供方连接，请缓存
			reference.setInterface(TaskDispatcher.class); // 弱类型接口名
			reference.setApplication(applicationConfig);
			reference.setRegistry(registryConfig);
			reference.setGroup(appName);
			List<MethodConfig> methodConfigs = new ArrayList<MethodConfig>();
			MethodConfig methodConfig = new MethodConfig();
			methodConfig.setName("validateTask");
			methodConfigs.add(methodConfig);
			methodConfig = new MethodConfig();
			methodConfig.setName("dispatch");
			methodConfig.setAsync(true);
			methodConfig.setReturn(false);// 异步调用
			methodConfigs.add(methodConfig);

			reference.setMethods(methodConfigs);
			reference.get();
			taskConsumerCache.put(appName, reference);
			return reference;
		}
	}
}
