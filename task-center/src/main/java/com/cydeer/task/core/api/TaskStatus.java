package com.cydeer.task.core.api;

public enum TaskStatus {

	INACTIVE(1, "禁用"),
	READY(2, "启用"),
	RUNNING(3, "运行中");

	private final Integer code;

	private final String name;

	TaskStatus(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public static TaskStatus parseCode(Integer code) {
		for (TaskStatus status : values()) {
			if (status.code.equals(code)) {
				return status;
			}
		}
		return null;
	}

}
