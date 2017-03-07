function confirmDelete(){
	if(confirm("您确定要删除吗？")) return true;
	else return false;
}

$(document).ready(function() {
	$("#dspDisplayDialog_submit").click(function(){
		var t = $("#dspForm").valid();
		if (!t) return;
		ajaxForm();
	});
	
	/*jQuery.validator.addMethod("code", function(value, element, param) { 
		var pattern = /^[a-zA-Z_][a-zA-Z_0-9]*$/; 
		return this.optional(element) || (pattern.test(value)); 
	},  "只能是英文数字下划线组成，英文或下划线开头");
	
	jQuery.validator.addMethod("validateUrl",function(value, element, param) {
		if(value != "") {
			return validateUrl(value);
		}
		return true;
	},  "不是正确的URL");*/
		
	/*$("#dspForm").validate({
		errorElement: "em",
		errorPlacement: function(error, element) {
			error.appendTo(error.appendTo(element.parents("div")[0]));
		},
//		success: function(label) {
//			label.text("ok!").addClass("success");
//		},
		rules: {
			dspId: {
				required: true,
				maxlength: 32,
				code:true,
				remote:{
					type: "get",  //数据发送方式
		            url:_ctx + "/manage/dsp/dspIdVertify?" + $("#dspId").val()
		        }
			},
			dspName: {
				required: true,
				maxlength: 255,
				remote:{
					type: "get",  //数据发送方式
		            url:_ctx + "/manage/dsp/dspNameVertify?" + $("#dspName").val()
		        }
			},
			biddingUrl: {
				required: true,
				validateUrl:true
			},
			cookieMappingUrl: "validateUrl",
			winnoticeUrl: 'validateUrl',
			maxTimeout : {
				required: true,
				digits: true,
                range: [1,1000000]
			}
		},
		messages: {
			dspId:{
				required: '编码不能为空',
				maxlength: '最多不超过32字符',
				remote: '编码重复'
			},
			dspName:{
				required: '名称不能为空',
				maxlength: '最多不超过255字符',
				remote: '名称重复'
			},
			biddingUrl : {
				required: '竞价请求接口不能为空',
				url: '不是正确的url',
			},
			cookieMappingUrl: '不是正确的url',
			winnoticeUrl: '不是正确的url',
			maxTimeout : {
				required: '超时时间不能为空',
				digits : '不是正整数',
				range : '取值范围为1-1000000'
			}
		}
	});*/
	
	$('#dspIdSearch').keydown(function(e){
		if(e.keyCode==13){
			var value = $.trim($(this).val());
			if (value != '') {
				$("#searchForm").submit();
			}
		}
	});
});

function edit(dspId) {
	resetForm();
	$("#dspDisplayDialog h4.modal-title").html("修改DSP");
	disableIput(false);
	clean();
	$("#dspId").attr("disabled", true);
	$("#dspName").attr("disabled", true);
	fillData(dspId);
	$("#dspDisplayDialog").modal({
		show : true,
		keyboard : false,
		backdrop : false
	});
	$("#dspDisplayDialog .btn").attr("disabled", false);
}

function view(dspId) {
	resetForm();
	$("#dspDisplayDialog h4.modal-title").html("查看DSP");
	clean();
	fillData(dspId);
	disableIput(true);
	$("#dspDisplayDialog").modal({
		show : true,
		keyboard : false,
		backdrop : false
	});
	$("#dspDisplayDialog .btn").attr("disabled", true);
	$("#dspDisplayDialog_close").attr("disabled", false);
}

function report(dspId) {
	var reportUrl = artemisUrl + "/qv/s2s?dspCode=" + dspId;
	window.open(reportUrl);
}

function add() {
	resetForm();
	$("#dspDisplayDialog h4.modal-title").html("添加DSP");
	disableIput(false);
	clean();
	$("#token").parent().parent().hide();
	$("#isPing").attr("checked", false);
	$("#isTest").attr("checked", false);
	$("#dspDisplayDialog").modal({
		show : true,
		keyboard : false,
		backdrop : false
	});
	$("#dspDisplayDialog .btn").attr("disabled", false);
	$("#isPing").val("1");
	$("#isTest").val("1");
}

function disableIput(isDisable) {
	$("#dspDisplayDialog input").attr("disabled", isDisable);
	$("#dspDisplayDialog select").attr("disabled", isDisable);
	$("#dspDisplayDialog checkbox").attr("disabled", isDisable);
}

function clean() {
	$("#dspDisplayDialog input").each(function(){
		$(this).val("");
	});
	$("#dspDisplayDialog select").each(function(){
		$(this).val("");
	});
	$("#token").text("");
}

function findDspBaseByDspId(dspId) {
	for(var i=0; i<$data.length; i++) {
		if ($data[i].dspId == dspId) {
			return $data[i];
		}
	}
}

function fillData(dspId) {
	var dspBase = findDspBaseByDspId(dspId);
	fillDialogData(dspBase);
}

function fillDialogData(dspBase) {
	fillArrayInput(["id", "dspId", "dspName", "biddingUrl" ,"cookieMappingUrl", "winnoticeUrl", "maxTimeout","matServer"], dspBase);
	$("#isPing").attr("checked", dspBase.isPing);
	$("#isTest").attr("checked", dspBase.isTest);
	$("#auditWay").val(dspBase.auditWay == 'first_used' ? 1 : 0);
	$("#isPing").val("1");
	$("#isTest").val("1");
	$("#token").text(dspBase.token);
}

function fillInput(id, dspBase) {
	$("#" + id).val(dspBase[id]);
}

function fillArrayInput(ids, dspBase) {
	for(var i=0; i<ids.length; i++) {
		fillInput(ids[i], dspBase);
	}
}

function resetForm() {
	$("#dspForm").validate().resetForm();
	$("#dspDisplayDialog button").attr("disabled", false);
	$("#token").parent().parent().show();
	disableIput(false);
	
}
var _ctx = "";
function ajaxForm() {
	$('#dspForm').ajaxSubmit({
	  url: _ctx + "/manage/dsp/save",
	  dataType: 'json',
	  
	  success: function(data){
		  var message = "提交失败！";
		  var type = "error";
		  if (data.isSuccess) {
			  message = "提交成功！";
			  type= "success";
		  }
		  $("#dspDisplayDialog button").attr("disabled", true);
		  disableIput(true);
		  swal({title: "DSP",
			  text:message,
			  type: type, 
			  confirmButtonColor: "#DD6B55",   
			  confirmButtonText: "关闭", 
			  closeOnConfirm: true}, 
			  function(){
				  $("#dspDisplayDialog").modal({show:false});
				  window.location.reload();
			  }
		  );
	  },
	  error: function(xhr, type, exception) {
		  if (xhr.responseText) {
			  var json = jQuery.parseJSON(xhr.responseText);
			  swal({title: "提示",text: json.message},function(){
				  $("#dspDisplayDialog").modal({show:false});
				  window.location.reload();
			  });
		  }
      }
	});
}