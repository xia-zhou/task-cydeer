package com.cydeer.task.impl;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.MethodConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.rpc.RpcContext;
import com.cydeer.task.api.TaskDispatcher;
import com.cydeer.task.api.TaskFeedBack;
import com.cydeer.task.dto.TaskInvokerInfo;
import com.cydeer.task.dto.TaskInvokerResult;
import com.cydeer.task.jackson.Jackson;
import com.cydeer.task.result.AjaxResult;
import com.cydeer.task.utils.InetUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.net.UnknownHostException;
import java.util.*;

/**
 * Created by zhangsong on 16/9/22.
 */
public class TaskDispatcherImpl implements TaskDispatcher {

	private final static Logger LOGGER = LoggerFactory.getLogger(TaskDispatcherImpl.class);
	/**
	 * 该系统的所有任务bean
	 */
	private Map<String, Object> taskBeanMap = new HashMap<String, Object>();
	/**
	 * dubbo注册信息,用来获取taskFeedBack服务,
	 */
	private RegistryConfig registryConfig;

	/**
	 * dubbo应用信息
	 */
	private ApplicationConfig applicationConfig;
	/**
	 * 定时任务执行结果回调
	 */
	private volatile TaskFeedBack taskFeedBack;
	private Object lock = new Object();

	@Override public AjaxResult<Boolean> validateTask(TaskInvokerInfo taskInvokerInfo) {
		AjaxResult<Boolean> result = new AjaxResult<>();
		result.setData(Boolean.FALSE);
		if (!taskBeanMap.containsKey(taskInvokerInfo.getBeanName())) {
			result.setMessage("Bean:" + taskInvokerInfo.getBeanName() + " is not fund!");
			return result;
		}
		Method method = findMethod(taskInvokerInfo);
		if (method == null) {
			result.setMessage("method is not fund,method Name：" + taskInvokerInfo.getMethodName() + ";args type："
					+ taskInvokerInfo.getParameterTypes());
			return result;
		}
		result.setData(Boolean.TRUE);
		return result;
	}

	/**
	 * @param taskInvokeInfo 根据方法名,方法参数,获取对应的方法
	 * @return 返回找到的方法 找不到返回null
	 */
	private Method findMethod(TaskInvokerInfo taskInvokeInfo) {
		Object bean = taskBeanMap.get(taskInvokeInfo.getBeanName());
		Method method = null;
		if (ArrayUtils.isEmpty(taskInvokeInfo.getParameterTypes())) {
			method = ReflectionUtils.findMethod(bean.getClass(), taskInvokeInfo.getMethodName());
		} else {
			final int paramCount = taskInvokeInfo.getParameterTypes().length;
			Class<?>[] clazzArray = new Class<?>[paramCount];
			for (int i = 0; i < paramCount; i++) {
				try {
					clazzArray[i] = ClassUtils.getClass(taskInvokeInfo.getParameterTypes()[i]);
				} catch (ClassNotFoundException e) {
					LOGGER.info("根据参数类型的字符串创建class对象时失败", e);
					return null;
				}
			}
			method = ReflectionUtils.findMethod(bean.getClass(), taskInvokeInfo.getMethodName(), clazzArray);
		}
		return method;
	}

	@Override public void dispatch(TaskInvokerInfo taskInvokerInfo) {
		// 异步执行
		RpcContext.getContext().getAttachments().remove(Constants.ASYNC_KEY);
		String currentHost = "";
		try {
			currentHost = InetUtil.getLocalHostLANAddress().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		TaskInvokerResult result = new TaskInvokerResult();
		result.setAppName(taskInvokerInfo.getAppName());
		result.setJobId(taskInvokerInfo.getJobId());
		result.setTaskName(taskInvokerInfo.getTaskName());
		result.setTriggerTime(taskInvokerInfo.getStartTime());

		if (RpcContext.getContext().isConsumerSide()) {
			result.setAppHost(currentHost);
			result.setTaskHost(currentHost);
		} else if (RpcContext.getContext().isProviderSide()) {
			// 定时任务系统的host
			result.setTaskHost(RpcContext.getContext().getRemoteHost());
			result.setAppHost(currentHost);
		}
		result.setExecuteStartTime(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		try {
			// 寻找方法:
			Method method = findMethod(taskInvokerInfo);
			Class<?>[] paramClasses = method.getParameterTypes();
			// 使用反射调用。
			if (taskInvokerInfo.getParameterTypes() != null && taskInvokerInfo.getParameterTypes().length > 0) {
				Object[] params = new Object[taskInvokerInfo.getParameterTypes().length];
				for (int i = 0; i < paramClasses.length; i++) {
					params[i] = Jackson.base().readValue(taskInvokerInfo.getArgs()[i], paramClasses[i]);
				}
				ReflectionUtils.invokeMethod(method, taskBeanMap.get(taskInvokerInfo.getBeanName()), params);
			} else {
				ReflectionUtils.invokeMethod(method, taskBeanMap.get(taskInvokerInfo.getBeanName()));
			}
			result.setExecuteEndTime(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			result.setExecuteResult("执行成功");
		} catch (Exception e) {
			result.setExecuteResult("执行失败");
			result.setErrorMsg(ExceptionUtils.getStackTrace(e));
		}
		// 查找task系统soa服务
		// 通知执行结果
		getFeedBackConsumer().feedBack(result);
	}

	private TaskFeedBack getFeedBackConsumer() {
		synchronized (lock) {
			if (taskFeedBack != null) {
				return taskFeedBack;
			}
			ReferenceConfig<TaskFeedBack> reference = new ReferenceConfig<>();
			reference.setApplication(applicationConfig);
			reference.setRegistry(registryConfig);
			reference.setInterface(TaskFeedBack.class);
			List<MethodConfig> methodConfigs = new ArrayList<MethodConfig>();
			MethodConfig methodConfig = new MethodConfig();
			methodConfig.setName("feedback");
			methodConfig.setAsync(true);
			methodConfig.setReturn(false);
			methodConfigs.add(methodConfig);
			reference.setMethods(methodConfigs);
			taskFeedBack = reference.get();
			return taskFeedBack;
		}
	}

	@Override public List<TaskInvokerInfo> listAllTask(String appName) {
		List<TaskInvokerInfo> tasks = new ArrayList<>();
		for (String key : taskBeanMap.keySet()) {
			TaskInvokerInfo info = new TaskInvokerInfo();
			info.setAppName(appName);
			info.setBeanName(key);
			tasks.add(info);
		}
		return tasks;
	}

	public Map<String, Object> getTaskBeanMap() {
		return taskBeanMap;
	}

	public void setTaskBeanMap(Map<String, Object> taskBeanMap) {
		this.taskBeanMap = taskBeanMap;
	}

	public RegistryConfig getRegistryConfig() {
		return registryConfig;
	}

	public void setRegistryConfig(RegistryConfig registryConfig) {
		this.registryConfig = registryConfig;
	}

	public ApplicationConfig getApplicationConfig() {
		return applicationConfig;
	}

	public void setApplicationConfig(ApplicationConfig applicationConfig) {
		this.applicationConfig = applicationConfig;
	}

}
