$(document).ready(function() {
	initTabs();
	onChange();
	initValidate();

});

// 头上Tab页面切换
function initTabs() {
	var pathName = window.document.location.pathname;
	if (pathName.indexOf("/manage/tlt/list") == 0) {
		$("#tabTltOverview").addClass("active");
	} else if (pathName.indexOf("/manage/tlt/rec") == 0) {
		$("#tabTltRecharge").addClass("active");
	} else if (pathName.indexOf("/manage/tlt/bind") == 0) {
		$("#tabTltBindcard").addClass("active");
	} else if (pathName.indexOf("/manage/tlt/pay") == 0) {
		$("#tabTltSubpay").addClass("active");
	} else if (pathName.indexOf("/manage/tlt/commertenant") == 0) {
		$("#tabTltSubcommertenant").addClass("active");
	}
}

function initValidate() {
	$("#commertenantPayForm").validate({
		// errorElement: "em",
		debug : false,
		errorPlacement : function(error, element) {
			error.appendTo(element.parent("div"));
			error.css("color", "#ff0000");
		},
		submitHandler : function(form) {
			form.submit();
		},
		rules : {
			paySn : {
				required : true,
				maxlength : 64,
				remote : {
					type : "POST", // 数据发送方式
					url : "/manage/tlt/verifyPaySn",
					data : {
						paySn : function() {
							return $("#paySn").val();
						},
						id : function() {
							return $("#id").val();
						}
					}
				},
			},
			payAmount : {
				digits : true,
				maxlength : 9,
				required : true,
			},
			itemNo : {
				required : true,
				digits : true,
				maxlength : 6,
			},
			payType : {
				required : true,
			},
			submitTime0 : {
				required : true,
			},
			paySuccessTime0 : {
				required : true,
			}
		},
		messages : {
			paySn : {
				required : '交易流水不能为空',
				maxlength : '最多不超过64个字符',
				remote : '交易流水已经存在',
			},
			itemNo: {
				digits : '只能填整数',
				maxlength : '最多不超过6位整数',
				required : '交易序号不能为空',
			},
			payAmount : {
				digits : '只能填整数',
				maxlength : '最多不超过9位整数',
				required : '交易金额不能为空',
			},
			payType : {
				required : '交易类型不能为空',
			},
			submitTime0 : {
				required : '提交时间不能为空',
			},
			paySuccessTime0 : {
				required : '完成时间不能为空',
			},
		}
	});
}
function onChange() {
	var value = $("#period").val();
	if(value == "direct"){
		$("#timePeriod").show();
	}else{
		$("#timePeriod").hide();
	}
}

function checkSubmit() {
		$("#commertenantSubmit").submit();
}
function downSubmit(){
	var downStart = $("#downStart").val();
	var downEnd = $("#downEnd").val();
	if(downStart==null || downStart=='' || downEnd==null || downEnd==''){
		alert('请选择开始和结束时间');
		return;
	}
	var date = new Date();
	var dates = date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
	if(DateDiff(dates,downStart)>40 || DateDiff(downEnd,downStart)>40){
		alert('不能取40天之前的数据!');
		return;
	}
	if(downStart>downEnd){
		alert('开始结束时间不正确');
		return;
	}
	if(DateDiff(dates,downEnd) < 1 || dates<downEnd){
		alert('只能下载前一天以前的对账文件');
		return;
	}
	if (!confirm('确定要下载对账文件？')) {
    	return;
    }
	
	 $.ajax({
     	url:'/manage/download/savedown',
     	data:{downStart:downStart,
     		downEnd:downEnd
     	},
     	type:'post',
     	success:function(r){
     		alert(r.msg);
     	}
     })
}

function DateDiff(sDate1,  sDate2){   
    var  aDate,  oDate1,  oDate2,  iDays ; 
    aDate  =  sDate1.split("-")  ;
    oDate1  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0]) ;   
    aDate  =  sDate2.split("-")  
    oDate2  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0]) ; 
    iDays  =  parseInt(Math.abs(oDate1  -  oDate2)  /  1000  /  60  /  60  /24);    
    return iDays  
} 