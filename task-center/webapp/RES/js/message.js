function sendAll() {
	var queueName = $("#queueName").val();
	var cousmerAppName = $("#cousmerAppName").val();
	$.ajax({
		url : 'sendBatch',
		data : {
			queueName : queueName,
			cousmerAppName : cousmerAppName
		},
		dataType : 'json',
		method : 'POST'
	}).done(function(result) {
		if (result.successflag == 1) {
			window.location.replace('listMessage');
		} else {
			AlertDialog.warning(result.body);
		}
	}).fail(function(jqXHR, textStatus, errorThrown) {
	}).always(function(jqXHR, textStatus, errorThrown) {
		_this.$source.button('reset');
	});
}

function search() {
	var queueName = $("#queueName").val();
	var cousmerAppName = $("#cousmerAppName").val();
	this.location.href = "listMessage?queueName=" + queueName
			+ "&cousmerAppName=" + cousmerAppName;
}
