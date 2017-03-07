/** checkLogin submit*/

$(function(){
	$("#submit").click(function(){
		var userName = $.trim($("#username").val());
		var passWord = $.trim($("#password").val());
		if(userName == "" || userName == "用户名"){
			$("#loginMessage").html("请输入用户名");
			return false;
		}
		$("#loginMessage").html("");
		if(passWord == ""){
			$("#loginMessage").html("请输入密码");
			return false;
		}
		$("#loginMessage").html("");
	});
});