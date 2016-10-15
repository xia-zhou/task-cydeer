package com.cydeer.task.core.api;

import com.cydeer.task.result.AjaxResult;

/**
 * Created by zhangsong on 2016/10/13.
 */
public interface TaskConsumerService {
	AjaxResult<Boolean> validate(Integer taskId);

	void dispatch(Integer taskId);
}
