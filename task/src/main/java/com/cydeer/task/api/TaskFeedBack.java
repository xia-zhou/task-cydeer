package com.cydeer.task.api;

import com.cydeer.task.dto.TaskInvokerResult;

/**
 * Created by zhangsong on 16/9/22.
 * task系统暴漏的接口信息
 */
public interface TaskFeedBack {
	/**
	 * @param taskInvokerResult 定时任务执行的结果,task系统要暴漏的soa接口,业务系统执行完定时任务会回调该接口,记录定时任务的执行信息
	 */
	void feedBack(TaskInvokerResult taskInvokerResult);
}
