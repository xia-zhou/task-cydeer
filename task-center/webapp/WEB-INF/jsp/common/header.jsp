<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="icon" href="../../favicon.ico">
<title>任务调度系统</title>
<!-- Bootstrap core CSS -->
<link href="/task/RES/css/bootstrap.min.css" rel="stylesheet">
<!-- Custom styles for this template -->
<link href="/task/RES/css/task.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="/task/RES/css/datatables/dataTables.bootstrap.css">
<script type="text/javascript">
	var taskGlobal = taskGlobal || {
		'appName' : '${param.app}',
		'dataTablesLang': {
			"emptyTable" : "没有数据",
			"info" : "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条",
			"infoEmpty" : "显示第 0 至 0 项结果，共 0 项",
			"lengthMenu" : "每页 _MENU_ 条",
			"paginate" : {
				"first" : "首页",
				"previous" : "上页",
				"next" : "下页",
				"last" : "末页",
			},
			"search" : "搜索:"
		}
	};
</script>
</head>
<body>
	<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="/task/listTask">统一任务调度系统</a>
			</div>
			<div class="navbar-collapse collapse">
				<ul class="nav navbar-nav navbar-right">
					<li><a href="#">管理</a></li>
				</ul>
			</div>
		</div>
	</div>