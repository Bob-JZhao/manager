$(document).ready(function() {
	initValidate();
});


//初始化表单校验
function initValidate(){
	$("#mngUsrEditPwdForm").validate({
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
			oldPassword: {
				required: true,
				maxlength: 20,
			},
			newPassword: {
				required: true,
				maxlength: 20,
			},
			confirmPassword: {
				required: true,
				maxlength: 20,
			}
		},
		messages: {
			oldPassword:{
				required: '请输入原密码',
				maxlength: '最多不超过20个字符',
			},
			newPassword : {
				required: '请输入新密码',
				maxlength: '最多不超过20个字符'
			},
			confirmPassword: {
				required: '请输入确认密码',
				maxlength: '最多不超过20个字符',
			}
		}
	});
}