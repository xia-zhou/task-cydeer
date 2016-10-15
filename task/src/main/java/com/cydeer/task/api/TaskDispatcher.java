package com.cydeer.task.api;

import com.cydeer.task.dto.TaskInvokerInfo;
import com.cydeer.task.result.AjaxResult;

import java.util.List;

/**
 * Created by zhangsong on 16/9/22.
 * 业务系统要暴漏的soa接口,task系统提供实现。
 */
public interface TaskDispatcher {
	/**
	 * 验证任务是否存在,对应的方法,是否存在
	 *
	 * @param taskInvokerInfo 任务描述信息 系统名,任务bean名称,method名称
	 * @return 存在返回 true,不存在 返回 false, message是错误信息
	 */
	AjaxResult<Boolean> validateTask(TaskInvokerInfo taskInvokerInfo);

	/**
	 * @param taskInvokerInfo 任务描述信息 系统名,任务bean名称,method名称
	 */
	void dispatch(TaskInvokerInfo taskInvokerInfo);

	/**
	 * task系统用来手动获取某个系统的所有 定时任务信息,方便task系统的操作人员,不用每次都手动添加
	 *
	 * @param appName 系统名称
	 * @return 该系统下的所有定时任务信息
	 */
	List<TaskInvokerInfo> listAllTask(String appName);
}
