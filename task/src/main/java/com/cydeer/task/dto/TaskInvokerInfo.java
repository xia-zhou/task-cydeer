package com.cydeer.task.dto;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by zhangsong on 16/9/22.
 */
public class TaskInvokerInfo implements Serializable {
	/**
	 * 系统名称
	 */
	private String appName;
	/**
	 * 任务名称
	 */
	private String taskName;

	/**
	 * 任务对应的bean名称
	 */
	private String beanName;

	/**
	 * 任务的方法名称
	 */
	private String methodName;
	/**
	 * 任务的方法参数类型列表
	 */
	private String[] parameterTypes;

	/**
	 * 任务的参数值 和 上面的参数类型一一对应
	 * 如果是对象类型，则为json串
	 */
	private String[] args;
	/**
	 *
	 */
	private String startTime;

	/**
	 * 任务ID
	 */
	private Integer jobId;

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String[] getParameterTypes() {
		return parameterTypes;
	}

	public void setParameterTypes(String[] parameterTypes) {
		this.parameterTypes = parameterTypes;
	}

	public String[] getArgs() {
		return args;
	}

	public void setArgs(String[] args) {
		this.args = args;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public Integer getJobId() {
		return jobId;
	}

	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}

	@Override public String toString() {
		final StringBuilder sb = new StringBuilder("{");
		sb.append("  \"appName\":\"").append(appName).append('\"');
		sb.append(", \"taskName\":\"").append(taskName).append('\"');
		sb.append(", \"beanName\":\"").append(beanName).append('\"');
		sb.append(", \"methodName\":\"").append(methodName).append('\"');
		sb.append(", \"parameterTypes\":").append(Arrays.toString(parameterTypes));
		sb.append(", \"args\":").append(Arrays.toString(args));
		sb.append(", \"startTime\":\"").append(startTime).append('\"');
		sb.append(", \"jobId\":").append(jobId);
		sb.append('}');
		return sb.toString();
	}
}
