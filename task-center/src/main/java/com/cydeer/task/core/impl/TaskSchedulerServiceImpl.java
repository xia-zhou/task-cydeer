package com.cydeer.task.core.impl;

import com.cydeer.task.core.api.TaskInfoRepo;
import com.cydeer.task.core.api.TaskSchedulerService;
import com.cydeer.task.core.entity.TaskInfo;
import com.cydeer.task.core.quartz.TaskQuartzJob;
import org.quartz.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zhangsong on 2016/10/13.
 */
@Service
public class TaskSchedulerServiceImpl implements TaskSchedulerService {

	@Resource
	private Scheduler scheduler;

	@Resource
	private TaskInfoRepo taskInfoRepo;

	@Override public void stopSchedule() throws SchedulerException {
		scheduler.standby();
	}

	@Override public void startSchedule() throws SchedulerException {
		scheduler.start();
	}

	@Override public void scheduleTask(Integer taskId) throws SchedulerException {
		if (!scheduler.checkExists(new JobKey(String.valueOf(taskId)))) {
			TaskInfo taskInfo = taskInfoRepo.findById(taskId);
			scheduler.scheduleJob(createJobDetail(taskId), createTrigger(taskId, taskInfo.getCronExpression()));
		}

	}

	/**
	 * @param taskId         定时任务唯一标识
	 * @param cronExpression quartz表达式
	 * @return 触发器（quartz表达式的触发时间）
	 */
	private Trigger createTrigger(Integer taskId, String cronExpression) {
		return TriggerBuilder.newTrigger().withIdentity(String.valueOf(taskId))
		                     .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)).build();
	}

	/**
	 *
	 * @param taskId 定时任务唯一标识
	 * @return 具体的job对象，当触发时，调用指定job的execute方法。
	 */
	private JobDetail createJobDetail(Integer taskId) {
		return JobBuilder.newJob(TaskQuartzJob.class).withIdentity(String.valueOf(taskId)).build();
	}

	@Override public void unScheduleTask(Integer taskId) {

	}
}
