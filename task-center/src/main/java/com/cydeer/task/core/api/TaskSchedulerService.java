package com.cydeer.task.core.api;

import org.quartz.SchedulerException;

/**
 * Created by zhangsong on 2016/10/13.
 */
public interface TaskSchedulerService {
	void stopSchedule() throws SchedulerException;

	void startSchedule() throws SchedulerException;

	void scheduleTask(Integer taskId) throws SchedulerException;

	void unScheduleTask(Integer taskId);
}

