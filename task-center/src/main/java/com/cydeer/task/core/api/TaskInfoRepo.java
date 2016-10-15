package com.cydeer.task.core.api;

import com.cydeer.task.core.entity.TaskInfo;

import java.util.List;

/**
 * Created by zhangsong on 2016/10/13.
 */
public interface TaskInfoRepo {
	
	List<TaskInfo> listAllTaskInfo();

	TaskInfo findById(Integer taskId);
}
