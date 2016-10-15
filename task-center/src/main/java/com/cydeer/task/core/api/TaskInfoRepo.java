package com.cydeer.task.core.api;

import com.cydeer.task.core.entity.TaskInfo;

import java.util.List;

/**
 * Created by zhangsong on 2016/10/13.
 */
public interface TaskInfoRepo {

	/**
	 * @return 所有的定时任务
	 */
	List<TaskInfo> listAllTaskInfo();

	/**
	 *
	 * @param taskId 定时任务唯一标识
	 * @return 该定时任务的详细信息
	 */
	TaskInfo findById(Integer taskId);

	/**
	 * @param taskId 定时任务唯一标识
	 * @param status 定时任务的状态
	 */
	void updateTaskStatus(Integer taskId, Integer status);
}
