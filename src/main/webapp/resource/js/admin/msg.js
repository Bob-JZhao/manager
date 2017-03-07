$(document).ready(function() {
	$("#userType").change(function(){
		if($("#userType").val()==9){
			$("#uid").show()
		}else{
			$("#uid").hide();
		}
	})
});


//初始化表单校验
function initValidate(){
	$("#msgForm").validate({
		// errorElement: "em",
		debug: false,
		errorPlacement: function(error, element) {
			error.appendTo(element.parent("div"));
			error.css("color", "#ff0000");
		},
		submitHandler: function(form) {
            form.submit();
        },
		rules: {
			title: {
				required: true,
				maxlength: 64,
			},
			content: {
				required: false,
				maxlength: 1024,
			},
		},
		messages: {
			title:{
				required: '标题不能为空',
				maxlength: '最多不超过64个字符'
			},
			content: {
				maxlength: '最多不超过1024个字符',
			},
		}
	});
}

/** confirmation of stop */
function confirmStop(msgId){
	if(confirm("确实要禁用ID为"+msgId+"的消息吗？")) return true;
	else return false;
}

/** confirmation of start */
function confirmStart(msgId){
	if(confirm("确实要启用ID为"+msgId+"的消息吗？")) return true;
	else return false;
}

/** stop */
function stop(msgId) {
	if(!confirmStop(msgId)){
		return false;
	}
	window.location.href = '/manage/msg/stop/' + msgId;
}

/** start */
function start(msgId) {
	if(!confirmStart(msgId)){
		return false;
	}
	window.location.href = '/manage/msg/start/' + msgId;
}
function initDate() {
	$("#cus_sendTime").datetimepicker({
		startDate:new Date()-1,
		minuteStep:1
	});
}
