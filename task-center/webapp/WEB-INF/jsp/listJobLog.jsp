<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="common/header.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<div class="container-fluid">
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<div class="page-header btn-group-sm">
					<h2>
						日志内容
					</h2>
				</div>
				<div class="row mb20">
					<table class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th>等级</th>
								<th>内容</th>
								<th>时间</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${list}" var="taskInfoDto">
								<tr class="task-row">
									<td>${taskInfoDto.level}</td>
									<td>${taskInfoDto.content}</td>
									<td>${taskInfoDto.createDate}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
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