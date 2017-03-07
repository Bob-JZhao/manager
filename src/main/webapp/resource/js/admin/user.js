$(document).ready(function() {
	initTabs();
	initValidate();
});

// 头上Tab页面切换
function initTabs() {
	var pathName=window.document.location.pathname;
	
	if (pathName.indexOf("/manage/usr/website") == 0) {
		var queryStr = window.document.location.search;
		// alert(queryStr);
		if (queryStr.indexOf("type=1") != -1) {
			// 理财师Tab页
			$("#tabWebsiteCfp").addClass("active");
		} else {
			$("#tabWebsiteInvest").addClass("active");
		}
		appendQueryFieldToPageForm();
	} else {
		$("#tabManagerUser").addClass("active");
	}
}

//初始化表单校验
function initValidate(){
	$("#mngUsrForm").validate({
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
			loginId: {
				required: true,
				maxlength: 64,
				remote:{
					type: "POST",  //数据发送方式
		            url: "/manage/usr/verifyLoginId",
		            data:{
		            	id:function(){return $("#id").val();},
		            	name:function(){return $("#loginId").val();}
		            }
		        }
			},
			name: {
				required: true,
				maxlength: 64,
			},
			email: {
				required: true,
				maxlength: 256,
				email: true,
			},
			mobile: {
				digits: true,
				maxlength: 11,
			},
			passwordShow: {
				required: false,
				maxlength: 12,
				minlength: 6,
			},
			password: {
				required: true,
				maxlength: 12,
				minlength: 6,
			},
		},
		messages: {
			loginId:{
				required: '用户ID不能为空',
				maxlength: '最多不超过64个字符',
				remote: '用户登录ID已经存在',
			},
			name:{
				required: '真实姓名不能为空',
				maxlength: '最多不超过64个字符'
			},
			email : {
				required: '电子邮件不能为空',
				maxlength: '最多不超过256个字符',
				email: "请输入正确格式的电子邮件",
			},
			mobile: {
				digits: '只能填整数',
				maxlength: '最多不超过11位整数',
			},
			passwordShow: {
				required: '登录密码不能为空',
				maxlength: '密码长度必须小于等于12个字符',
				minlength: '密码长度必须大于等于6个字符',
			},
			password: {
				required: '登录密码不能为空',
				maxlength: '密码长度必须小于等于12个字符',
				minlength: '密码长度必须大于等于6个字符',
			},
		}
	});
}

/** check the validate of email */
function checkEmail(email){
	if($("#email").val()!=""){
		if (!$("#email").val().match(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/)) {
			//alert("邮箱格式不正确");
			//$("#email").focus();
			$("#verifyEmail").html("邮箱格式不正确");
		return false;
		}
	}
	return true; 
}


/** confirmation of stop */
function confirmStop(loginId){
	if(confirm("确实要停用用户登录ID为"+loginId+"的用户吗？")) return true;
	else return false;
}

/** confirmation of start */
function confirmStart(loginId){
	if(confirm("确实要启用用户登录ID为"+loginId+"的用户吗？")) return true;
	else return false;
}

/** stop */
function stop(usrId, loginId) {
	if(!confirmStop(loginId)){
		return false;
	}
	var userType = $("#type").val();
	window.location.href = '/manage/usr/website/stop/' + usrId + "?type=" + userType;
}

/** start */
function start(usrId, loginId) {
	if(!confirmStart(loginId)){
		return false;
	}
	var userType = $("#type").val();
	window.location.href = '/manage/usr/website/start/' + usrId + "?type=" + userType;
}

/**
 * 理财师名片审核-后台管理
 */
function sureVerify(isApproved) {
	var msg = "确定要审核通过此理财师身份吗？";
	if(isApproved != 1) {
		msg = "确定要拒绝此理财师身份吗？";
	}
	if(!confirm(msg)){
		return false;
	}
	verifyCard(isApproved);
}

// 调用后台审核、拒绝理财师名片
function verifyCard(isApproved) {
	//alert(isApproved);
	var flag = false;
	$.ajax({
		 type : "POST",
		 url : "/manage/usr/website/verifyCard/"+$("#usrVoId").val(),
		 data : {"approveStatus":isApproved},
		 dataType : "json",
		 async : false, 
		 error : function(){ alert('系统错误'); },
		 success : function(data){
			 data = eval(data);
			 flag = data.status;
			 window.location.reload();
		 }
	 });
	return flag;
}

function checkSubmit(){
	if($("#passwordShow").val() != null 
			&& $("#passwordShow").val() != "" 
			&& $("#passwordShow").val().length >= 6
			&& $("#passwordShow").val().length <= 12){
		//alert("pss:"+$('#passwordShow').val());
		$("#password").val($("#passwordShow").val());
	}
	//alert("ps:"+$('#password').val());
	
	$("#mngSubmit").submit();
}

function appendQueryFieldToPageForm() {
	var userType = $("#type").val();
	//alert('userType = ' + userType);
	var inputUserType = $("<input type='hidden' name='type' value='" + userType + "' />")
	$("#pageFormUsrWebsite").append(inputUserType);
}
