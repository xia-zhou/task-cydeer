package com.cydeer.task.core.api;

import com.cydeer.task.result.AjaxResult;

/**
 * Created by zhangsong on 2016/10/13.
 */
public interface TaskConsumerService {
	/**
	 * @param taskId 定时任务唯一标识
	 * @return 定时任务验证结果
	 */
	AjaxResult<Boolean> validate(Integer taskId);

	/**
	 * 获取定时任务信息，调用soa接口，执行定时任务。
	 *
	 * @param taskId 定时任务唯一标识
	 */
	void dispatch(Integer taskId);
}
