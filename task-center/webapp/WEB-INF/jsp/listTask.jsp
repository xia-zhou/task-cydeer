<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="common/header.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="INACTIVE" value="<%=com.xiaoka.mid.task.center.service.dto.enums.TaskStatus.INACTIVE%>" />
<c:set var="READY" value="<%=com.xiaoka.mid.task.center.service.dto.enums.TaskStatus.READY%>" />
<c:set var="RUNNING" value="<%=com.xiaoka.mid.task.center.service.dto.enums.TaskStatus.RUNNING%>" />

<c:set var="SUCCESS" value="<%=com.xiaoka.mid.task.soa.api.dto.ExecuteResultEnum.SUCCESS%>" />
<c:set var="FAILURE" value="<%=com.xiaoka.mid.task.soa.api.dto.ExecuteResultEnum.FAILURE%>" />


<div class="container-fluid">
	<c:if test="${empty app }">
		<div class="jumbotron">
			<h1>Hello, Admin!</h1>
			<p>现在还没有业务系统纳入任务调度系统，请先添加业务系统,再对业务系统添加定时任务。</p>
			<p>
				<a href="admin/addApp" class="btn btn-primary btn-lg" role="button">添加业务系统</a>
			</p>
		</div>
	</c:if>
	<c:if test="${not empty app }">
		<div class="row">
			<div class="col-sm-3 col-md-2 sidebar">
				<div class="row center-block btn-group-sm">
					<!-- Standard button -->
					<a href="admin/addApp" class="btn btn-default" role="button">添加系统</a> <a href="#" class="btn btn-default" role="button" id='deleteAppBtn'>删除系统</a>
				</div>
				<div class="row">
					<ul class="nav nav-sidebar nav-pills nav-stacked">
						<c:forEach items="${appInfos}" var="appInfo">
							<li <c:if test="${app eq appInfo.name}">class="active"</c:if>><a href="listTask?app=${appInfo.name}">${appInfo.name} <span
									class="badge pull-right">${appInfo.taskNumber}</span></a></li>
						</c:forEach>
					</ul>
				</div>
			</div>

			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<div class="page-header btn-group-sm">
					<h2>
						任务列表
						<c:if test="${not empty param.app}">
							<span class="btn btn-default" id="addTaskSpan">添加任务</span>
						</c:if>
					</h2>
				</div>
				<div class="row mb20">
					<table class="table table-striped table-bordered table-hover" id="tasks-table">
						<thead>
							<tr>
								<th>名称</th>
								<th>ID</th>
								<th>状态</th>
								<th>触发器</th>
								<th>下次运行时间</th>
								<th>上次运行时间</th>
								<th>上次运行结果</th>
								<th>创建时间</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${taskInfoDtos}" var="taskInfoDto">
								<tr class="task-row">
									<td>${taskInfoDto.taskName}</td>
									<td>${taskInfoDto.id}</td>
									<td><c:choose>
											<c:when test="${taskInfoDto.status eq INACTIVE.code}">${INACTIVE.name}</c:when>
											<c:when test="${taskInfoDto.status eq READY.code}">${READY.name}</c:when>
											<c:when test="${taskInfoDto.status eq RUNNING.code}">${RUNNING.name}</c:when>
										</c:choose></td>
									<td>${taskInfoDto.cronExpression}</td>
									<td><fmt:formatDate value="${taskInfoDto.nextRunTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
									<td><fmt:formatDate value="${taskInfoDto.lastRunTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
									<td><c:choose>
											<c:when test="${taskInfoDto.lastRunResult eq SUCCESS.code}">${SUCCESS.name}</c:when>
											<c:when test="${taskInfoDto.lastRunResult eq FAILURE.code}">${FAILURE.name}</c:when>
										</c:choose></td>
									<td><fmt:formatDate value="${taskInfoDto.createDateTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
									<td class="task-oper"><input type="hidden" value="${taskInfoDto.taskName}" id="taskName"/><input id="taskId" type="hidden" value="${taskInfoDto.id}" /> <c:choose>
											<c:when test="${taskInfoDto.status eq INACTIVE.code}">
												<a data-oper="active" class="btn btn-sm btn-default" role="button" data-loading-text="启用..">启用</a>
												<a href="admin/updateTask?id=${taskInfoDto.id}&app=${taskInfoDto.appName}&task=${taskInfoDto.taskName}" class="btn btn-sm btn-default" role="button">修改</a>
												<a data-oper="delete" class="btn btn-sm btn-default" role="button" data-loading-text="删除..">删除</a>
											</c:when>
											<c:when test="${taskInfoDto.status eq READY.code}">
												<a href="admin/inactiveTask?id=${taskInfoDto.id}&app=${taskInfoDto.appName}&task=${taskInfoDto.taskName}" class="btn btn-sm btn-default" role="button">禁用</a>
												<a href="admin/runTask?id=${taskInfoDto.id}&app=${taskInfoDto.appName}&task=${taskInfoDto.taskName}" class="btn btn-sm btn-default" role="button">运行</a>
											</c:when>
											<c:when test="${taskInfoDto.status eq RUNNING.code}">
													<a href="admin/resetTaskStatus?id=${taskInfoDto.id}&app=${taskInfoDto.appName}&task=${taskInfoDto.taskName}" class="btn btn-default" role="button">重置状态</a>
											</c:when>
										</c:choose></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>

				<div class="row task-detail-tabs" id="task-detail-tab"></div>
			</div>
		</div>
	</c:if>
</div>

<div class="modal fade" id="confirmDelete" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">任务删除提示</h4>
			</div>
			<div class="modal-body">
				<span class="label label-warning">删除操作不可回退，删除任务的同时也将删除其执行历史。</span>
				确认要彻底删除<span id="appSpanId" class="label label-default"></span>中的任务 <span class="label label-default" id="taskSpanId"></span>吗？
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				<a href="#" class="btn btn-primary">确认删除</a>
			</div>
		</div>
	</div>
</div>


<div class="modal fade" id="alertDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content panel-warning">
			<div class="modal-header panel-heading">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title"></h4>
			</div>
			<div class="modal-body"></div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
</div>


<%@include file="common/footer.jsp"%>
<script type="text/javascript" src="/task/RES/js/jquery/bootstrap.js"></script>
<script type="text/javascript" src="/task/RES/js/jquery/jquery.dataTables.js"></script>
<script type="text/javascript" src="/task/RES/js/jquery/datatables/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="/task/RES/js/listTask.js"></script>
</body>
</html>