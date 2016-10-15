package com.cydeer.task.core.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhangsong on 2016/10/13.
 * 定时任务信息
 */
public class TaskInfo implements Serializable {
	/**
	 * id:
	 */
	private Integer id;

	/**
	 * app_name:
	 */
	private String appName;

	private String taskName;

	private String description;

	private String cronExpression;

	private Date nextRunTime;

	private Date lastRunTime;

	private String lastRunResult;

	private Date createDateTime;

	private Integer isForceDo;

	private String alarmPerson;

	private String beanName;

	private String methodName;

	private Integer status;

	private Integer isDelete;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public Date getNextRunTime() {
		return nextRunTime;
	}

	public void setNextRunTime(Date nextRunTime) {
		this.nextRunTime = nextRunTime;
	}

	public Date getLastRunTime() {
		return lastRunTime;
	}

	public void setLastRunTime(Date lastRunTime) {
		this.lastRunTime = lastRunTime;
	}

	public String getLastRunResult() {
		return lastRunResult;
	}

	public void setLastRunResult(String lastRunResult) {
		this.lastRunResult = lastRunResult;
	}

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public Integer getIsForceDo() {
		return isForceDo;
	}

	public void setIsForceDo(Integer isForceDo) {
		this.isForceDo = isForceDo;
	}

	public String getAlarmPerson() {
		return alarmPerson;
	}

	public void setAlarmPerson(String alarmPerson) {
		this.alarmPerson = alarmPerson;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	@Override public String toString() {
		final StringBuilder sb = new StringBuilder("{");
		sb.append("  \"id\":").append(id);
		sb.append(", \"appName\":\"").append(appName).append('\"');
		sb.append(", \"taskName\":\"").append(taskName).append('\"');
		sb.append(", \"description\":\"").append(description).append('\"');
		sb.append(", \"cronExpression\":\"").append(cronExpression).append('\"');
		sb.append(", \"nextRunTime\":\"").append(nextRunTime).append('\"');
		sb.append(", \"lastRunTime\":\"").append(lastRunTime).append('\"');
		sb.append(", \"lastRunResult\":\"").append(lastRunResult).append('\"');
		sb.append(", \"createDateTime\":\"").append(createDateTime).append('\"');
		sb.append(", \"isForceDo\":").append(isForceDo);
		sb.append(", \"alarmPerson\":\"").append(alarmPerson).append('\"');
		sb.append(", \"beanName\":\"").append(beanName).append('\"');
		sb.append(", \"methodName\":\"").append(methodName).append('\"');
		sb.append(", \"status\":").append(status);
		sb.append(", \"isDelete\":").append(isDelete);
		sb.append('}');
		return sb.toString();
	}
}
