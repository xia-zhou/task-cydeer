$(function() {
	var taskTable = $('#tasks-table').DataTable({
		paging : false,
		searching : false,
		scrollX : true,
		scrollY : 400,
		scrollCollapse : true,
		language : taskGlobal.dataTablesLang,
		columnDefs : [ {
			"orderable" : false,
			"targets" : 8
		} ]
	});

	// click task
	$('#tasks-table tbody').on(
			'click',
			'tr.task-row',
			function() {
				var selectRow = $(this);
				var rowData = taskTable.row(this).data();
				$.ajax({
					url : 'viewTask',
					data : {
						id : rowData[1],
						app : taskGlobal.appName,
						task : rowData[0]
					},
					dateType : 'html'
				}).done(
						function(html) {
							selectRow.addClass('info').siblings().removeClass(
									'info');
							$('#task-detail-tab').empty().html(html);
							new ViewDetail(taskGlobal.appName, rowData[0],
									rowData[1]).showDetail();
						});
			});

	// active task
	$('#tasks-table tbody').on('click', 'td.task-oper a', function() {
		var $btn = $(this).button('loading');
		var task = $(this).siblings('#taskName').val();
		var taskId = $(this).siblings('#taskId').val();
		var oper = $(this).data('oper') + 'Task';
		new TaskOper(taskGlobal.appName, task, taskId, $btn)[oper]();
		return false;
	});

	var TaskOper = function(appName, taskName, id, btn) {
		this.appName = appName;
		this.taskName = taskName;
		this.taskId = id;
		this.$source = btn
	};
	TaskOper.prototype.activeTask = function() {
		var _this = this;
		$.ajax({
			url : 'admin/activeTask',
			data : {
				id : _this.taskId,
				app : _this.appName,
				task : _this.taskName
			},
			dataType : 'json',
			method : 'POST'
		}).done(
				function(result) {
					if (result.successflag == 1) {
						window.location.replace('/task/listTask?app='
								+ _this.appName);
					} else {
						AlertDialog.warning(result.body);
					}
				}).fail(function(jqXHR, textStatus, errorThrown) {
			AlertDialog.danger(result.body);
		}).always(function(jqXHR, textStatus, errorThrown) {
			_this.$source.button('reset');
		});
	}

	TaskOper.prototype.deleteTask = function() {
		// admin/deleteTask?app=${taskInfoDto.appName}&task=${taskInfoDto.taskName}
		var _this = this;
		$('#confirmDelete').on(
				'show.bs.modal',
				function(e) {
					_this.$source.button('reset');
					$('#appSpanId').text(_this.appName);
					$('#taskSpanId').text(_this.taskName);
					var url = 'admin/deleteTask?id=' + _this.taskId
							+ '&app=' + _this.appName + '&task='
							+ _this.taskName;
					$(this).find('.btn-primary').attr('href', url);
				}).modal('show');
	};

	var AlertDialog = function() {
	}

	AlertDialog._alert = function(typeClass, title, msg) {
		$('#alertDialog').on('show.bs.modal', function(e) {
			$(this).find('div.modal-body').text(msg);
			$(this).find('.modal-title').text(title);
			$(this).find('div.modal-content').removeClass(function() {
				var classes = $(this).attr('class').split(' ');
				for (var i = 0, l = classes.length; i < l; i++) {
					if (classes[i].indexOf('panel-') == 0) {
						return classes[i];
					}
				}
			}).addClass(typeClass);
		}).modal('show');
	}

	AlertDialog.primary = function(msg) {
		AlertDialog._alert('panel-primary', '', msg);
	}
	AlertDialog.success = function(msg) {
		AlertDialog._alert('panel-success', '成功提示', msg);
	}
	AlertDialog.info = function(msg) {
		AlertDialog._alert('panel-info', '信息提示', msg);
	}

	AlertDialog.warning = function(msg) {
		AlertDialog._alert('panel-warning', '警告提示', msg);
	}

	AlertDialog.danger = function(msg) {
		AlertDialog._alert('panel-danger', '错误提示', msg);
	}

	$('#deleteAppBtn').click(function() {
		$.ajax({
			url : 'admin/deleteApp',
			data : {
				app : taskGlobal.appName
			},
			dataType : 'json',
			method : 'POST'
		}).done(function(result) {
			if (result.successflag == 1) {
				var app = $('#taskGlobal.appName').val();
				window.location.replace('/task/listTask');
			} else {
				AlertDialog.info('该业务系统下有定时任务存在，请先删除所有的定时任务后再删除业务系统');
			}
		}).fail(function(jqXHR, textStatus, errorThrown) {
			AlertDialog.danger('删除业务系统失败!')
		});
	});

	$('#addTaskSpan').click(
			function() {
				window.location.replace('/task/admin/addTask?app='
						+ taskGlobal.appName);
			});

	var ViewDetail = function(app, task, taskId) {
		this.appName = app;
		this.taskName = task;
		this.taskId = taskId;
	}

	ViewDetail.prototype.showFirstTab = function() {

	}
	ViewDetail.prototype.traceTableView = function() {
		var _this = this;
		var traceTable = $('#taskTraceTable')
				.DataTable(
						{
							"processing" : true,
							"serverSide" : true,
							"searching" : false,
							"orderMulti" : false,
							"bLengthChange" : false,
							destroy : true,
							ajax : {
								url : 'listTaskTraces?id=' + _this.taskId
										+ '&app=' + _this.appName + '&task='
										+ _this.taskName,
								type : 'POST',
								dataType : 'json',
								contentType : 'application/json;charset=UTF-8',
								data : function(d) {
									return JSON.stringify(d);
								}
							},
							"columns" : [
									{
										"data" : "triggerTimeString",
									// "width" : "10%",
									},
									{
										"data" : "executeStartTimeString",
										// "width" : "10%",
										"orderable" : false,
									},
									{
										"data" : "executeEndTimeString",
										// "width" : "10%",
										"orderable" : false,
									},
									{
										"data" : "taskHost",
										// "width" : "12%",
										"orderable" : false,
									},
									{
										"data" : "appHost",
										// "width" : "12%",
										"orderable" : false,
									},
									{
										"data" : null,
										// "width" : "7%",
										"orderable" : false,
										render : function(data, type, row) {
											if (data.status == '1') {
												return '开始调度';
											} else if (data.status == '2') {
												return '通知成功';
											} else if (data.status == '3') {
												return '执行失败';
											} else if (data.status == '4') {
												return '远程执行成功';
											} else if (data.status == '5') {
												return '远程执行失败';
											}else{
												return '异常';
											}
												
										}
									},
									{
										"data" : null,
										"class" : "details-control",
										"orderable" : false,
										render : function(data, type, row) {
											if (data.status == '3' ||data.status == '5') {
												return '<button type="button" class="btn btn-sm btn-info" data-toggle="collapse">查看详情</button>';
											}else{
												return '';
											}
										},
									// "width" : "7%",
									},
									{
										"data" : null,
										"orderable" : false,
										render : function(data, type, row) {
											return '<button type="button" class="btn btn-sm btn-info" onclick="viewLog('
													+ data.id
													+ ')">查看日志</button>';
										},
									// "width" : "7%",
									} ],
							scrollX : true,
							language : taskGlobal.dataTablesLang,
							columnDefs : [ {
								"orderable" : false,
								"targets" : 6
							} ],
							order : [ [ 0, "desc" ] ]
						});

		/*
		 * Formatting function for row details - modify as you need
		 */
		function formatTraceError(d) {
			// `d` is the original data object for the
			// row
			return '<table class="table" style="padding-left:50px;">' + '<tr>'
					+ '<td colspan="7">' + '<div class="collapse">'
					+ d.errorMsg + '</div>' + '</td>' + '</tr>'
			'</table>';
		}

		$('#taskTraceTable tbody').on('click', 'td.details-control button',
				function() {
					var tr = $(this).closest('tr');
					var row = traceTable.row(tr);
					if (row.child.isShown()) {
						// This row is already open - close it
						row.child.hide();
						tr.removeClass('shown');
					} else {
						// Open this row
						row.child(formatTraceError(row.data())).show();
						tr.addClass('shown');
					}
					$('.collapse', $(row.child()[0])).collapse();
				})
	}

	ViewDetail.prototype.showDetail = function() {
		var _this = this;
		// task info tab
		$('#taskTab a:first').click(function(e) {
			e.preventDefault()
			$(this).tab('show')
		});
		// task execute traces tab
		$('#taskTab a:last').click(function(e) {
			e.preventDefault()
			_this.traceTableView();
			$(this).tab('show');
		});
	}

});
function viewLog(jobid) {
	window.location.replace('/task/listJobLog?id=' + jobid);
}