<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/header.jsp" %>
<link rel="stylesheet" type="text/css" href="/task/RES/css/bootstrapValidator.css">
	
	<div class="container-fluid">
		<div class="page-header">
			<h2>添加业务系统</h2>
		</div>
		<div class="alert alert-danger" role="alert" id="errorMsg" style="display:none;"></div>
		<form id="addAppForm" action="saveApp" class="form-horizontal" role="form" method="post">
			<div class="form-group">
				<label class="col-sm-2 control-label">系统名称</label>
				<div class="col-sm-6">
					<input type="text" class="form-control" name="appName" placeholder="请输入系统名称"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">系统类型</label>
				<div class="col-sm-6">
					<select name="appType" class="form-control">
						<option value="JAVA">JAVA</option>
						<option value="PHP">PHP</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-6">
					 <button type="submit" class="btn btn-default">提交</button>
					 <button type="submit" class="btn btn-default" onclick="window.history.back()">取消</button>
				</div>
			</div>
		</form>
	</div>
	<%@include file="../common/footer.jsp" %>
	<script type="text/javascript" src="/task/RES/js/jquery/bootstrapValidator.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$('#addAppForm').bootstrapValidator({
		        feedbackIcons: {
		            valid: 'glyphicon glyphicon-ok',
		            invalid: 'glyphicon glyphicon-remove',
		            validating: 'glyphicon glyphicon-refresh'
		        },
		        fields: {
		        	appName: {
		        		message: '系统名称不符合要求',
		        		validators: {
		        			notEmpty: {
		        				message: '系统名称不能为空'
		        			},
		        			remote: {
		        				message: '系统名称已存在',
		                        url: '/task/admin/duplicateAppName'
		        			}
		        		}
		        	},
		        }
			});
		});
	</script>
</body>
</html>