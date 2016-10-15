<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/header.jsp"%>
<link rel="stylesheet" type="text/css" href="/task/RES/css/bootstrapValidator.css">

<div class="container-fluid">
	<div class="page-header">
		<h2>${isAdd ? '添加任务':'修改任务' }</h2>
	</div>
	<c:choose>
		<c:when test="${not empty error}">
			<div class="alert alert-danger" role="alert" id="errorMsg">业务系统${app}不存在</div>
		</c:when>
		<c:otherwise>
			<form id="addTaskForm" action="saveTask" class="form-horizontal" method="post">
				<input type="hidden" name="appName" value="${param.app}">
				<div class="form-group">
					<label class="col-sm-2 control-label">系统名称</label>
					<div class="col-sm-6">
						<p class="form-control-static">${param.app}</p>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">任务名称</label>
					<div class="col-sm-6">
						<c:choose>
							<c:when test="${empty taskInfoDto.status}">
								<input type="text" class="form-control" name="taskName" placeholder="任务名称"  required>
							</c:when>
							<c:otherwise>
								<input type="hidden" name="id" value="${taskInfoDto.id}">
								<input type="hidden" name="taskName" value="${taskInfoDto.taskName}">
								<p class="form-control-static">${taskInfoDto.taskName}</p>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">任务描述</label>
					<div class="col-sm-6">
						<textarea class="form-control" rows="5" name="description" placeholder="任务描述"><c:out
								value="${taskInfoDto.description}" escapeXml="false" /></textarea>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">调度策略</label>
					<div class="col-sm-6">
						<input type="text" class="form-control" name="cronExpression" placeholder="Cron表达式" value="${taskInfoDto.cronExpression}">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">${isJavaApp ? "Bean Name" : "服务名称"}</label>
					<div class="col-sm-6">
						<input type="text" class="form-control" name="beanName"
							placeholder="${isJavaApp ? 'Bean Name' : '服务名称'}" 
							value="${taskInfoDto.beanName}" 
							data-bv-notempty="true"
							data-bv-notempty-message="${isJavaApp ? 'Bean Name' : '服务名称'}不能为空"
							/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">方法名称</label>
					<div class="col-sm-6">
						<input type="text" class="form-control" name="methodName" placeholder="任务执行的方法"
							value="${taskInfoDto.methodName}" 
							data-bv-notempty="true"
							data-bv-notempty-message="方法名不能为空"
							/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">未成功是否强制执行</label>
					<div class="col-sm-6">
						<input type="radio" name="isForceDo" value="1" <c:if test="${taskInfoDto.isForceDo=='1'}"> checked="checked" </c:if> />是 
						<input type="radio" name="isForceDo" value="0" <c:if test="${taskInfoDto.isForceDo=='0'||taskInfoDto.isForceDo==null}"> checked="checked" </c:if> />否
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">报警邮件人</label>
					<div class="col-sm-6">
						<input type="text" class="form-control" name="alarmPerson" placeholder="报警邮件人，逗号分隔"
							value="${taskInfoDto.alarmPerson}" />
					</div>
				</div>
<!-- 				<div class="form-group"> -->
<!-- 					<label class="col-sm-2 control-label">参数</label> -->
<!-- 					<div class="col-sm-6"> -->
<!-- 						<textarea class="form-control" rows="5" name="taskBeanInfoDto.parameters" -->
<%-- 							placeholder="${isJavaApp ? 'java.lang.String=JSON字符串 &#10;java.util.Map=JSON字符串' : '参数1&#10;参数2'}"><c:out --%>
<%-- 								value="${taskInfoDto.taskBeanInfoDto.parameters}" escapeXml="false" /></textarea> --%>
<!-- 					</div> -->
<!-- 				</div> -->
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<button type="submit" class="btn btn-default">提交</button>
						<button type="button" class="btn btn-default" onclick="window.history.back()">取消</button>
					</div>
				</div>
			</form>

		</c:otherwise>
	</c:choose>
</div>
<%@include file="../common/footer.jsp"%>
<script type="text/javascript" src="/task/RES/js/jquery/bootstrapValidator.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$('#addTaskForm').bootstrapValidator({
	        feedbackIcons: {
	            valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	taskName: {
	        		message: '任务名不符合要求',
	        		validators: {
	        			notEmpty: {
	        				message: '任务名不能为空'
	        			},
	        			remote: {
	        				message: '任务名已存在',
	        				data: function(validator){
	        					return {
	        						appName: taskGlobal.appName,
	        						taskName: validator.getFieldElements('taskName').val()
	        					}
	        				},
	                        url: '/task/admin/duplicateTaskName'
	        			}
	        		}
	        	},
	        	cronExpression:{
	        		message: 'Cron表达式不符合要求',
	        		validators: {
	        			notEmpty: {
	        				message: 'Cron表达式不能为空'
	        			},
	        			remote: {
	        				message: 'Cron表达式格式错误',
	                        url: '/task/admin/verifyCron'
	        			}
	        		}
	        	},
	        	'taskBeanInfoDto.methodName':{
	        		message: '任务名称不能为空',
	        		validators: {
	        			notEmpty: {
	        				message: '方法名不能为空'
	        			}
	        		}
	        	},
	        }
		});
	});
</script>
</body>
</html>