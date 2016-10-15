package com.cydeer.task.core.api;

import org.quartz.SchedulerException;

/**
 * Created by zhangsong on 2016/10/13.
 */
public interface TaskSchedulerService {
	/**
	 * 暂停任务调度执行器
	 *
	 * @throws SchedulerException
	 */
	void stopSchedule() throws SchedulerException;

	/**
	 * 启动任务调度执行器
	 *
	 * @throws SchedulerException
	 */
	void startSchedule() throws SchedulerException;

	/**
	 * 添加任务到任务调度执行器中
	 * @param taskId
	 * @throws SchedulerException
	 */
	void scheduleTask(Integer taskId) throws SchedulerException;

	/**
	 * 从任务调度执行器中移除某个定时任务
	 * @param taskId
	 */
	void unScheduleTask(Integer taskId);
}

