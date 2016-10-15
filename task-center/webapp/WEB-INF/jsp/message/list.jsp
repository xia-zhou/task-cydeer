<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/header.jsp"%>

<c:set var="UNDO" value="<%=com.xiaoka.mid.mq.MessageStatusEnum.UNDO%>" />
<c:set var="SEND" value="<%=com.xiaoka.mid.mq.MessageStatusEnum.SEND%>" />
<c:set var="DELETE"
	value="<%=com.xiaoka.mid.mq.MessageStatusEnum.DELETE%>" />
<div class="container-fluid">
	<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
		<div class="page-header btn-group-sm">
			<h2>消息列表</h2>
		</div>
		<div class="row mb20">
			队列名称<input type="text" name="queueName" id="queueName"/> 消费者APP名字<input type="text" id="cousmerAppName"
				name=cousmerAppName /> <input type="button" name="search" value="搜索" onclick="search()"/><input
				type="button" name="sendAll" value="一键全部发送"  onclick="sendAll()"/>
		</div>
		<div class="row mb20">
			<table class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th>名称</th>
						<th>消费服务名称</th>
						<th>内容</th>
						<th>异常类型</th>
						<th>异常内容</th>
						<th>失败时间</th>
						<th>当前状态</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${messages}" var="message">
						<tr class="task-row">
							<td>${message.queueName}</td>
							<td>${message.cousmerAppName}</td>
							<td>${message.content}</td>
							<td>${message.level}</td>
							<td>${message.errorMessage}</td>
							<td>${message.errorTime}</td>
							<td><c:choose>
									<c:when test="${message.status eq UNDO.code}">${UNDO.name}</c:when>
									<c:when test="${message.status eq SEND.code}">${SEND.name}</c:when>
									<c:when test="${message.status eq DELETE.code}">${DELETE.name}</c:when>
								</c:choose></td>
							<td><c:choose>
									<c:when test="${message.status eq UNDO.code}">
										<a href="send?id=${message.id}"
											class="btn btn-sm btn-default" role="button">重新发送</a>
										<a href="delete?id=${message.id}"
											class="btn btn-sm btn-default" role="button">删除</a>
									</c:when>
								</c:choose></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>

<%@include file="../common/footer.jsp"%>
<script type="text/javascript" src="/task/RES/js/jquery/bootstrap.js"></script>
<script type="text/javascript"
	src="/task/RES/js/jquery/jquery.dataTables.js"></script>
<script type="text/javascript"
	src="/task/RES/js/jquery/jquery.min.js"></script>
<script type="text/javascript"
	src="/task/RES/js/jquery/datatables/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="/task/RES/js/message.js"></script>
</body>
</html>