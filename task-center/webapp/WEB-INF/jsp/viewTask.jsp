<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<ul class="nav nav-tabs" role="tablist" id="taskTab">
	<li role="presentation" class="active"><a href="#taskInfo" role="tab" data-toggle="tab">任务详情</a></li>
	<li role="presentation"><a href="#executeTraces" role="tab" data-toggle="tab">执行历史</a></li>
</ul>
<div class="tab-content pt20">
	<div role="tabpanel" class="tab-pane active" id="taskInfo">
		<div class="form-group row">
			<label class="col-sm-2">系统名称</label>
			<div class="col-sm-8">
				<span class="control-label">${taskInfoDto.appName}</span>
			</div>
		</div>
		<div class="form-group row">
			<label class="col-sm-2 control-label">任务名称</label>
			<div class="col-sm-8">
				<span class="control-label">${taskInfoDto.taskName}</span>
			</div>
		</div>
		<div class="form-group row">
			<label class="col-sm-2 control-label">任务描述</label>
			<div class="col-sm-8">
				<span class="control-label">${taskInfoDto.description}</span>
			</div>
		</div>
		<div class="form-group row">
			<label class="col-sm-2 control-label">调度策略</label>
			<div class="col-sm-8">
				<span class="control-label">${taskInfoDto.cronExpression}</span>
			</div>
		</div>
		<div class="form-group row">
			<label class="col-sm-2 control-label">Bean名称</label>
			<div class="col-sm-8">
				<span class="control-label">${taskInfoDto.beanName}</span>
			</div>
		</div>
		<div class="form-group row">
			<label class="col-sm-2 control-label">方法名</label>
			<div class="col-sm-8">
				<span class="control-label">${taskInfoDto.methodName}</span>
			</div>
		</div>
		<div class="form-group row">
			<label class="col-sm-2 control-label">执行计划</label>
			<c:forEach items="${plans}" var="executePlan">
				<div class="col-sm-8">
					<span class="control-label">${executePlan}</span>
				</div>
			</c:forEach>
		</div>
	</div>
	<div role="tabpanel" class="tab-pane" id="executeTraces">
		<table class="table table-striped table-bordered table-hover" id="taskTraceTable">
			<thead>
				<tr>
					<th>调度触发时间</th>
					<th>任务开始时间</th>
					<th>任务结束时间</th>
					<th>Task IP</th>
					<th>${taskInfoDto.appName} IP</th>
					<th>运行结果</th>
					<th>错误信息</th>
					<th>执行日志</th>
				</tr>
			</thead>
		</table>
	</div>
</div>