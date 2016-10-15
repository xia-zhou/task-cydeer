package com.cydeer.task;

import com.cydeer.task.core.api.TaskInfoRepo;
import com.cydeer.task.core.api.TaskSchedulerService;
import com.cydeer.task.core.entity.TaskInfo;
import org.quartz.SchedulerException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhangsong on 2016/10/13.
 * 有xml配置到spring 容器中
 */
public class TaskCenterApplicationListener
		implements ApplicationContextAware, ApplicationListener<ContextRefreshedEvent> {

	private ApplicationContext applicationContext;

	@Resource
	private TaskSchedulerService taskSchedulerService;

	@Resource
	private TaskInfoRepo taskInfoRepo;

	@Override public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext() != applicationContext) {
			return;
		}
		try {
			// 启动任务调度执行器
			taskSchedulerService.startSchedule();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		registerTaskListener();
	}

	private void registerTaskListener() {
		List<TaskInfo> taskInfos = taskInfoRepo.listAllTaskInfo();
		if (CollectionUtils.isEmpty(taskInfos)) {
			return;
		}
		for (TaskInfo taskInfo : taskInfos) {
			// TODO: 2016/10/13 判断定时任务的状态 准备就绪，或者正在运行中的都启动
			try {
				// 添加任务到任务调度执行器中
				taskSchedulerService.scheduleTask(taskInfo.getId());
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
		}
	}
}
