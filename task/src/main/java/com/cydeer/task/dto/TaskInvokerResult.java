package com.cydeer.task.dto;

import java.io.Serializable;

/**
 * Created by zhangsong on 16/9/22.
 */
public class TaskInvokerResult implements Serializable {
	/**
	 * 系统名称
	 */
	private String appName;

	/**
	 * 任务名称
	 */
	private String taskName;

	/**
	 * Task调度触发的时间
	 */
	private String triggerTime;

	/**
	 * 业务系统SOA服务调用的开始时间
	 */
	private String executeStartTime;

	/**
	 * 业务系统SOA服务调用的结束时间
	 */
	private String executeEndTime;

	private String executeResult;

	/**
	 * Task系统的host
	 */
	private String taskHost;

	/**
	 * 业务系统的host
	 */
	private String appHost;

	/**
	 * 错误信息
	 */
	private String errorMsg;

	/**
	 * 任务id,task系统中每个任务的唯一id
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

	public String getTriggerTime() {
		return triggerTime;
	}

	public void setTriggerTime(String triggerTime) {
		this.triggerTime = triggerTime;
	}

	public String getExecuteStartTime() {
		return executeStartTime;
	}

	public void setExecuteStartTime(String executeStartTime) {
		this.executeStartTime = executeStartTime;
	}

	public String getExecuteEndTime() {
		return executeEndTime;
	}

	public void setExecuteEndTime(String executeEndTime) {
		this.executeEndTime = executeEndTime;
	}

	public String getExecuteResult() {
		return executeResult;
	}

	public void setExecuteResult(String executeResult) {
		this.executeResult = executeResult;
	}

	public String getTaskHost() {
		return taskHost;
	}

	public void setTaskHost(String taskHost) {
		this.taskHost = taskHost;
	}

	public String getAppHost() {
		return appHost;
	}

	public void setAppHost(String appHost) {
		this.appHost = appHost;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
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
		sb.append(", \"triggerTime\":\"").append(triggerTime).append('\"');
		sb.append(", \"executeStartTime\":\"").append(executeStartTime).append('\"');
		sb.append(", \"executeEndTime\":\"").append(executeEndTime).append('\"');
		sb.append(", \"executeResult\":\"").append(executeResult).append('\"');
		sb.append(", \"taskHost\":\"").append(taskHost).append('\"');
		sb.append(", \"appHost\":\"").append(appHost).append('\"');
		sb.append(", \"errorMsg\":\"").append(errorMsg).append('\"');
		sb.append(", \"jobId\":").append(jobId);
		sb.append('}');
		return sb.toString();
	}
}
