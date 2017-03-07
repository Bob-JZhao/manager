$(document).ready(function() {
	initTabs();
	checkPaymentForm();
	doPrvLiveSave();
	doAssetLiveSave();
	$("#btnBack").click(function() {
		window.history.back();
	});
});

// 头上Tab页面切换
function initTabs() {
	var pathName = window.document.location.pathname;
	if (pathName.indexOf("/manage/live/prv/") == 0) {
		$("#tabLivePrv").addClass("active");
	} else if (pathName.indexOf("/manage/live/asset/") == 0) {
		$("#tabLiveAsset").addClass("active");
	} else if (pathName.indexOf("/manage/live/payment/") == 0) {
		$("#tabLivePayment").addClass("active");
	} else if (pathName.indexOf("/manage/live/product/") == 0) {
		$("#tabLive").addClass("active");
	}
}
function checkPaymentForm() {
	$("#livePaymentForm").validate({
		async : false,
		cache : false,
		debug : false,
		errorPlacement : function(error, element) {
			error.appendTo(element.parent("div"));
			error.css("color", "#ff0000");
		},
		submitHandler : function(form) {
			form.submit();
		},
		rules : {
			optType : {
				required : true
			},
			amount : {
				required : true,
				number : true,
				range : [ 1, 99999999.99 ],
				maxlength : 10
			},
			astId : {
				required : true
			},
			optTime0 : {
				required : true
			}
		},
		messages : {
			optType : {
				required : '操作类型不能为空'
			},
			amount : {
				required : '金额不能为空',
				number : '只能输入数字',
				maxlength : '最多不10位',
				range : '只能输入1-99999999.99'
			},
			astId : {
				required : '资产不能为空'
			},
			optTime0 : {
				required : '操作时间不能为空'
			}
		}
	});
}

function doPaymentLiveSave() {
	var optType = $('#optType').val();
	var assetType = $('#assetType').val();
	var id = $('#id').val();
	var amountHis = 0;
	var reg = /^[1-9]{1}\d*(\.\d{1,2})?$/;
	var amountThis = parseFloat($('#amount').val());
	var amountLoan = parseFloat($('#amountLoan').val());
	var amountRep = parseFloat($('#amountRep').val());
	var amountExp = parseFloat($('#amountExp').val());
	if (assetType == 1 && reg.test(amountThis) && ast_id != null && ast_id != "") {
		if (id != null && id != "") {
			jQuery.ajax({
				type : "POST",
				async : false,
				cache : false,
				url : "/manage/live/payment/getAssetPaymentVo/" + id,
				success : function(json) {
					if (json != null && json.length > 0) {
						amountHis = parseFloat(json[0].amount);
					}
				}
			});
		}
		if (optType == 1) {
			var valueLoan = amountLoan - amountHis + amountThis - amountExp;
			if (valueLoan > 0) {
				if (confirm("放款总金额大于预期规模，确定进行操作？")) {
					checkPaymentForm();
					return true;
				} else {
					//window.history.back();
					return false;
				}
			}
		} else {
			var valueRep = amountRep - amountHis + amountThis - amountExp;
			if (valueRep > 0) {
				if (confirm("还款总金额大于预期规模，确定进行操作？")) {
					checkPaymentForm();
					return true;
				} else {
					//window.history.back();
					return false;
				}
			}
		}
	} else if (assetType == 2 && reg.test(amountThis) && ast_id != null && ast_id != "") {
		if(optType==2 && amountThis > amountExp){	
			alert("赎回金额不能大于预期规模");
			return false;
		}
	}
}

function doAssetLiveSave() {
	var havedAmount = parseFloat($("#havedAmount").val());
	var amountExp = parseFloat($("#amountExp").val());
	var type = $("#type").val();
	if(havedAmount>amountExp && type==1){
		alert("预期规模不能小于以匹配金额");
		return false;
	}
	$("#liveAssetForm").validate({
		debug : false,
		errorPlacement : function(error, element) {
			error.appendTo(element.parent("div"));
			error.css("color", "#ff0000");
		},
		submitHandler : function(form) {
			form.submit();
		},

		rules : {
			name : {
				required : true,
				maxlength : 256,
				remote : {
					type : "POST", // 数据发送方式
					url : "/manage/live/asset/verifyName",
					data : {
						id : function() {
							return $("#id").val();
						},
						name : function() {
							return $("#name").val();
						}
					}
				}
			},
			contractId : {
				required : true,
				maxlength : 64
			},
			type : {
				required : true
			},
			amountExp : {
				required : true,
				digits : true,
				range : [ 1, 999999999 ]
			},
			duration : {
				required : true,
				digits : true,
				range : [ 1, 9999 ]
			},
			durationUnit : {
				required : true
			},
			returnRate : {
				required : true,
				range : [ 1, 99.99 ],
				maxlength : 5
			},/*
			loaneeIdno : {
				required : true,
				maxlength : 32
			},
			loaneeName : {
				required : true,
				maxlength : 32
			},*/
			lenderName : {
				required : true,
				maxlength : 32
			},/*
			lenderIdno : {
				required : true,
				maxlength : 32
			},*/
			prvId : {
				required : true
			},
			repayment : {
				required : true,
				maxlength : 128
			},
			paidType : {
				required : true
			},
			status : {
				required : true
			},
			amountAct : {
				digits : true,
				range : [ 0, 999999999 ]

			},
			purpose: {
				required : function(){
					if($("#type").val()==1)return true;
					return false;
				},
				maxlength : 6
			}
			           
		},
		messages : {
			name : {
				required : '名称不能为空',
				maxlength : '最多不超过256个汉字',
				remote : '名称不能重复'
			},
			amountAct : {
				digits : '只能填整数',
				range : '最多不超过9位整数'
			},
			contractId : {
				required : '合同编号不能为空',
				maxlength : '最多不超过64个字符'
			},
			type : {
				required : '资产类型不能为空',
			},
			amountExp : {
				required : '预计规模不能为空',
				digits : '只能填整数',
				range : '最多不超过9位整数'
			},
			duration : {
				required : '期限不能为空',
				digits : '只能填整数',
				range : '只能输入1-9999'
			},
			durationUnit : {
				required : '期限类型不能为空'
			},
			returnRate : {
				required : '预计收益不能为空',
				maxlength : '最多不5位',
				range : '只能输入1-99.99'
			},/*
			loaneeIdno : {
				required : '借款人身份证号码不能为空',
				maxlength : '最多不超过32个字符'
			},
			loaneeName : {
				required : '借款人姓名不能为空',
				maxlength : '最多不超过32个字符'
			},*/
			lenderName : {
				required : '债权(人/组织)名称不能为空',
				maxlength : '最多不超过32个字符'
			},
			/*
			lenderIdno : {
				required : '债权(人/组织)号码不能为空',
				maxlength : '最多不超过32个字符'
			},
			*/
			prvId : {
				required : '供应商不能为空'
			},
			repayment : {
				required : '付息不能为空',
				maxlength : '最多不超过128个字符'
			},
			paidType : {
				required : '放款不能为空'
			},
			status : {
				required : '资产状态不能为空'
			},
			purpose: {
				required : '资产用途不能为空',
				maxlength : '最多不超过6个字符'
			}

		}
	});
}

function doPrvLiveSave() {
	$("#livePrvForm").validate({
		debug : false,
		errorPlacement : function(error, element) {
			error.appendTo(element.parent("div"));
			error.css("color", "#ff0000");
		},
		submitHandler : function(form) {
			form.submit();
		},

		rules : {
			name : {
				required : true,
				maxlength : 128,
				remote : {
					type : "POST", // 数据发送方式
					url : "/manage/live/prv/verifyName",
					data : {
						id : function() {
							return $("#id").val();
						},
						name : function() {
							return $("#name").val();
						}
					}
				}
			},

			fullName : {
				required : true,
				maxlength : 256
			}
		},
		messages : {
			name : {
				required : '名称不能为空',
				maxlength : '最多不超过128个汉字',
				remote : '名称不能重复'
			},
			fullName : {
				required : '全称不能为空',
				maxlength : '最多不超过256个汉字'
			}
		}
	});
}

function livePrvEdit(provId) {
	window.location.href = '/manage/live/prv/edit/' + provId;
}
function liveAssetEdit(assetId) {
	var actionUrl = $("#searchForm").attr("action");
	var pageSize = $("#pageSize").val();
	var pageNum = $("#pageNum").val();
	var searchAssetName = $("#searchAssetName").val();
	var data = {url:actionUrl,pageSize:pageSize,pageNum:pageNum,
			searchAssetName:searchAssetName};
	var url = encodeURI(JSON.stringify(data));
	window.location.href = '/manage/live/asset/edit/' + assetId+'?url='+url;;
}
function livePaymentEdit(paymentId) {
	window.location.href = '/manage/live/payment/edit/' + paymentId;
}
function delAsset(assetId, assetName) {
	if (confirm("您确定要删除资产:" + assetName + " 的数据吗？")) {
		window.location.href = '/manage/live/asset/delete/' + assetId;
	}
}
function delPayment(paymentId) {
	if (confirm("您确定要删除这条数据吗？")) {
		window.location.href = '/manage/live/payment/delete/' + paymentId;
	}
}

function loadAsset(assetId, id) {
	if (assetId != null && assetId != '')
		jQuery.ajax({
			type : "POST",
			url : "/manage/live/payment/getAssetVo/" + assetId,
			success : function(json) {
				if (json != null && json.length > 0) {
					$('#amountExp').val(json[0].amountExp);
					$('#amountLoan').val(json[0].amountLoan);
					$('#amountRep').val(json[0].amountRep);
					var type = json[0].type;
					$('#assetType').val(type);
					if (type == 1) {
						$('#assetTypeText').val("债券转让");
						if (id != null && id != "") {

						} else {
							$('#optType').html("");
							$("#optType").append(
									"<option value='1'>增加放款</option>");
							$("#optType").append(
									"<option value='2'>增加还款</option>");
						}

						$("#amountDiv").show();
					} else if (type == 2) {
						$('#assetTypeText').val("收益权转让");
						if (id != null && id != "") {

						} else {
							$('#optType').html("");
							$("#optType").append(
									"<option value='1'>申购</option>");
							$("#optType").append(
									"<option value='2'>赎回</option>");
						}
						$("#amountDiv").hide();
					}
				}
			}
		});
}
function initHisData(){
	alert("●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●\r" +
			"请仔细阅读一下提示信息！\r" +
			"●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●\r"+ 
			"★★提示★★\r" +
			"* 需要初始化的标的改为已发布,或者已满标.\r" +
			"* 删除活期订单的匹配信息...\r" +
			"●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●\r"+ 
			"★★警告★★" +
			"\r[此操作只在技术指导之下才能操作,非技术人员操作责任自负！]\r"+
			"●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●\r" );
	if(confirm("确认进行历史数据初始化?\r" +
			"●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●\r"+ 
			"[此操作只在技术指导之下才能操作,非技术人员操作责任自负！]\r"+
			"●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●\r")){
	 $.ajax({
	     	url:'/manage/live/asset/initHisData',
	     	type:'post',
	     	success:function(r){
	     		alert(r.msg);
	     	}
	     })
	}
}

function changeAsset(astId,astName){
	if(confirm("确认将资产["+astName+"]设为已回款吗?")){
		jQuery.ajax({
			type : "POST",
			data:{astId:astId},
			url : "/manage/live/asset/changeStatus",
			success : function(r) {
				alert(r.msg);
	     		if(r.status=='true'){
	     			window.location.href = "/manage/live/asset/list/1";
	     		}
			}
		});
	}
}