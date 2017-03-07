$(document).ready(function() {
	initTabs();
	onChange();
	onChangePrd();
	checkRedeemCodeForm();
	$("#btnRedeemBack").click(function() {
		window.location.href = "/manage/rpt/redeemCode/list";
	});
});

// 头上Tab页面切换
function initTabs() {
	var pathName = window.document.location.pathname;
	if (pathName.indexOf("/manage/rpt/list") == 0) {
		$("#tabCountOverview").addClass("active");
	}
	if (pathName.indexOf("/manage/rpt/detaillist") == 0) {
		$("#tabDetailOverview").addClass("active");
	}
	if (pathName.indexOf("/manage/rpt/userdata") == 0) {
		$("#tabUserDataOverview").addClass("active");
		$("#dataType").attr("disabled", "disabled");
		createChartRegister();
	}
	if (pathName.indexOf("/manage/rpt/taxi") == 0) {
		$("#tabTaxiDataOverview").addClass("active");
	}
	if (pathName.indexOf("/manage/rpt/appvercount") == 0) {
		$("#tabappvercountview").addClass("active");
	}
	if (pathName.indexOf("/manage/rpt/redeemCode") == 0) {
		$("#tabRedeemCodeview").addClass("active");
	}
	if (pathName.indexOf("/manage/rpt/channel") == 0) {
		$("#tabChannel").addClass("active");
	}
}

function onChange() {
	var value = $("#period").val();
	if (value == "direct") {
		$("#timePeriod").show();
	} else {
		$("#timePeriod").hide();
	}
}
function onChangePrd() {
}
function viewSubmit() {
	var prdName = encodeURI($("#searchText").val());
	var url = "?prdType=" + $("#prdType").val() + "&userType="
			+ $("#userType").val() + "&prdId=" + $("#prdId").val()
			+ "&prdName=" + prdName + "&searchText=" + prdName + "&timeStart="
			+ $("#timeStart").val() + "&timeEnd=" + $("#timeEnd").val()
			+ "&period=" + $("#period").val();
	window.document.location.href = "/manage/rpt/detaillist/1" + url;
}

function onChangeDataType() {
	var viewType = $("#viewType").val();
	if (viewType == 3 || viewType == 4) {
		$('#dataType').removeAttr("disabled");
		$('#dataType').val(0);
	} else {
		$("#dataType").attr("disabled", "disabled");
		$('#dataType').val(0);
	}
}

function createChartRegister() {
	var registerOptions = {
		chart : {
			renderTo : 'container',
			type : 'column'
		},
		title : {
			text : '每日注册用户数'
		},
		xAxis : {
			categories : [],
			crosshair : true
		},
		yAxis : {
			min : 0,
			title : {
				text : '人数'
			}
		},
		exporting : {
			enabled : false
		},
		credits : {
			enabled : false
		},
		tooltip : {
			headerFormat : '<span style="font-size:10px">{point.key}</span><table>',
			pointFormat : '<tr><td style="color:{series.color};">{series.name}: </td>'
					+ '<td style=""><b>{point.y}</b></td></tr>',
			footerFormat : '</table>',
			shared : true,
			useHTML : true
		},
		plotOptions : {
			column : {
				pointPadding : 0.2,
				borderWidth : 0
			}
		},
		series : [ {
			name : '理财师注册人数',
			data : []

		}, {
			name : '投资者注册人数',
			data : []

		}, {
			name : '注册总人数',
			data : []
		}, {
			type : 'line',
			name : '注册走势线',
			data : [],
			marker : {
				lineWidth : 2,
				lineColor : Highcharts.getOptions().colors[2],
				fillColor : 'white'
			}
		} ]
	}

	$.ajax({
		url : '/manage/rpt/datalist',
		dataType : "json",
		data : {
			viewType : $("#viewType").val(),
			dataType : $("#dataType").val(),
			dataStart : $("#dataStart").val(),
			dataEnd : $("#dataEnd").val()
		},
		success : function(data) {
			financial_arr = [];
			investor_arr = [];
			sum_num_arr = [];
			for (i in data) {
				var r = data[i];
				financial_arr.push([ r.collectDate, r.registerFinancialNum ]);
				investor_arr.push([ r.collectDate, r.registerInvestorNum ]);
				sum_num_arr.push([ r.collectDate, r.registerSumNum ]);
			}
			registerOptions.series[0].data = financial_arr;
			registerOptions.series[1].data = investor_arr;
			registerOptions.series[2].data = sum_num_arr;
			registerOptions.series[3].data = sum_num_arr;
			var chart = new Highcharts.Chart(registerOptions);

		},
		cache : false
	});
}

function viewSubmit() {
	var viewType = $("#viewType").val();
	var dataType = $("#dataType").val();
	var dataStart = $("#dataStart").val();
	var dataEnd = $("#dataEnd").val();
	if (viewType == 0)
		createChartRegister();
	else if (viewType == 1)
		createChartLogin();
	else if (viewType == 2)
		createChartPayorder();
	else if (viewType == 3)
		createChartKeep();
	else if (viewType == 4)
		createChartActive();

}

function createChartLogin() {
	var loginOptions = {
		chart : {
			renderTo : 'container',
			type : 'column'
		},
		title : {
			text : '每日登陆用户数'
		},
		xAxis : {
			categories : [],
			crosshair : true
		},
		yAxis : {
			min : 0,
			title : {
				text : '人数'
			}
		},
		exporting : {
			enabled : false
		},
		credits : {
			enabled : false
		},
		tooltip : {
			headerFormat : '<span style="font-size:10px">{point.key}</span><table>',
			pointFormat : '<tr><td style="color:{series.color};">{series.name}: </td>'
					+ '<td style=""><b>{point.y}</b></td></tr>',
			footerFormat : '</table>',
			shared : true,
			useHTML : true
		},
		plotOptions : {
			column : {
				pointPadding : 0.2,
				borderWidth : 0
			}
		},
		series : [ {
			name : '理财师登陆人数',
			data : []

		}, {
			name : '投资者登陆人数',
			data : []

		}, {
			name : '登陆总人数',
			data : []
		}, {
			type : 'line',
			name : '登陆走势线',
			data : [],
			marker : {
				lineWidth : 2,
				lineColor : Highcharts.getOptions().colors[2],
				fillColor : 'white'
			}
		} ]
	}

	$.ajax({
		url : '/manage/rpt/datalist',
		dataType : "json",
		data : {
			viewType : $("#viewType").val(),
			dataType : $("#dataType").val(),
			dataStart : $("#dataStart").val(),
			dataEnd : $("#dataEnd").val()
		},
		success : function(data) {
			financial_arr = [];
			investor_arr = [];
			sum_num_arr = [];
			for (i in data) {
				var r = data[i];
				financial_arr.push([ r.collectDate, r.loginFinancialNum ]);
				investor_arr.push([ r.collectDate, r.loginInvestorNum ]);
				sum_num_arr.push([ r.collectDate, r.loginSumNum ]);
			}
			loginOptions.series[0].data = financial_arr;
			loginOptions.series[1].data = investor_arr;
			loginOptions.series[2].data = sum_num_arr;
			loginOptions.series[3].data = sum_num_arr;
			var chart = new Highcharts.Chart(loginOptions);
		},
		cache : false
	});
}

function createChartPayorder() {
	var payorderOptions = {
		chart : {
			renderTo : 'container',
			type : 'column'
		},
		title : {
			text : '每日成交用户数'
		},
		xAxis : {
			categories : [],
			crosshair : true
		},
		yAxis : {
			min : 0,
			title : {
				text : '人数'
			}
		},
		exporting : {
			enabled : false
		},
		credits : {
			enabled : false
		},
		tooltip : {
			headerFormat : '<span style="font-size:10px">{point.key}</span><table>',
			pointFormat : '<tr><td style="color:{series.color};">{series.name}: </td>'
					+ '<td style=""><b>{point.y}</b></td></tr>',
			footerFormat : '</table>',
			shared : true,
			useHTML : true
		},
		plotOptions : {
			column : {
				pointPadding : 0.2,
				borderWidth : 0
			}
		},
		series : [ {
			name : '成交理财师数',
			data : []

		}, {
			name : '成交投资者数',
			data : []

		}, {
			name : '成交用户总数',
			data : []
		}, {
			type : 'line',
			name : '成交走势线',
			data : [],
			marker : {
				lineWidth : 2,
				lineColor : Highcharts.getOptions().colors[2],
				fillColor : 'white'
			}
		} ]
	}

	$.ajax({
		url : '/manage/rpt/datalist',
		dataType : "json",
		data : {
			viewType : $("#viewType").val(),
			dataType : $("#dataType").val(),
			dataStart : $("#dataStart").val(),
			dataEnd : $("#dataEnd").val()
		},
		success : function(data) {
			financial_arr = [];
			investor_arr = [];
			sum_num_arr = [];
			for (i in data) {
				var r = data[i];
				financial_arr.push([ r.collectDate, r.payorderFinancialNum ]);
				investor_arr.push([ r.collectDate, r.payorderInvestorNum ]);
				sum_num_arr.push([ r.collectDate, r.payorderSumNum ]);
			}
			payorderOptions.series[0].data = financial_arr;
			payorderOptions.series[1].data = investor_arr;
			payorderOptions.series[2].data = sum_num_arr;
			payorderOptions.series[3].data = sum_num_arr;
			var chart = new Highcharts.Chart(payorderOptions);
		},
		cache : false
	});
}

function createChartActive() {
	var activeOptions = {
		chart : {
			renderTo : 'container',
			type : 'column'
		},
		title : {
			text : '活跃用户数'
		},
		xAxis : {
			categories : [],
			crosshair : true
		},
		yAxis : {
			min : 0,
			title : {
				text : '人数'
			}
		},
		exporting : {
			enabled : false
		},
		credits : {
			enabled : false
		},
		tooltip : {
			headerFormat : '<span style="font-size:10px">{point.key}</span><table>',
			pointFormat : '<tr><td style="color:{series.color};">{series.name}: </td>'
					+ '<td style=""><b>{point.y}</b></td></tr>',
			footerFormat : '</table>',
			shared : true,
			useHTML : true
		},
		plotOptions : {
			column : {
				pointPadding : 0.2,
				borderWidth : 0
			}
		},
		series : [ {
			name : '理财师活跃人数',
			data : []

		}, {
			name : '投资者活跃人数',
			data : []

		}, {
			name : '活跃用户总数',
			data : []
		}, {
			type : 'line',
			name : '活跃用户走势线',
			data : [],
			marker : {
				lineWidth : 2,
				lineColor : Highcharts.getOptions().colors[2],
				fillColor : 'white'
			}
		} ]
	}

	$.ajax({
		url : '/manage/rpt/datalist',
		dataType : "json",
		data : {
			viewType : $("#viewType").val(),
			dataType : $("#dataType").val(),
			dataStart : $("#dataStart").val(),
			dataEnd : $("#dataEnd").val()
		},
		success : function(data) {
			financial_arr = [];
			investor_arr = [];
			sum_num_arr = [];
			for (i in data) {
				var r = data[i];
				financial_arr.push([ r.collectDate, r.activeFinancialNum ]);
				investor_arr.push([ r.collectDate, r.activeInvestorNum ]);
				sum_num_arr.push([ r.collectDate, r.activeSumNum ]);
			}
			activeOptions.series[0].data = financial_arr;
			activeOptions.series[1].data = investor_arr;
			activeOptions.series[2].data = sum_num_arr;
			activeOptions.series[3].data = sum_num_arr;
			var chart = new Highcharts.Chart(activeOptions);
		},
		cache : false
	});
}

function createChartKeep() {
	var keepOptions = {
		chart : {
			renderTo : 'container',
			type : 'line'
		},
		title : {
			text : '用户留存率'
		},
		xAxis : {
			categories : [],
			crosshair : true
		},
		yAxis : {
			min : 0,
			max:100,
			title : {
				text : '百分比'
			}
		},
		exporting : {
			enabled : false
		},
		credits : {
			enabled : false
		},
		tooltip : {
			headerFormat : '<span style="font-size:10px">{point.key}</span><table>',
			pointFormat : '<tr><td style="color:{series.color};">{series.name}: </td>'
					+ '<td style=""><b>{point.y}%</b></td></tr>',
			footerFormat : '</table>',
			shared : true,
			useHTML : true
		},
		plotOptions : {
			column : {
				pointPadding : 0.2,
				borderWidth : 0
			}
		},
		series : [ /*{
			name : '理财师注册人数',
			data : []

		}, {
			name : '投资者注册人数',
			data : []

		}, {
			name : '注册用户总数',
			data : []
		}, 
		*/{
			type : 'line',
			name : '理财师留存率',
			data : [],
			color : 'white',
			marker : {
				lineWidth : 2,
				lineColor : 'white',
				fillColor : 'white'
			}
		}, {
			type : 'line',
			name : '投资者留存率',
			data : [],
			color:'green',
			marker : {
				lineWidth : 2,
				lineColor : 'green',
				fillColor : 'white'
			}
		}, {
			type : 'line',
			name : '总留存率',
			data : [],
			color:'yellow',
			marker : {
				lineWidth : 2,
				lineColor : 'yellow',
				fillColor : 'white'
			}
		} ]
	}

	$.ajax({
		url : '/manage/rpt/datalist',
		dataType : "json",
		data : {
			viewType : $("#viewType").val(),
			dataType : $("#dataType").val(),
			dataStart : $("#dataStart").val(),
			dataEnd : $("#dataEnd").val()
		},
		success : function(data) {
			//financial_arr = [];
			//investor_arr = [];
			//sum_num_arr = [];
			financial_arr_line = [];
			investor_arr_line = [];
			sum_num_arr_line = [];
			for (i in data) {
				var r = data[i];
				//financial_arr.push([ r.collectDate, r.registerFinancialNum ]);
				//investor_arr.push([ r.collectDate, r.registerInvestorNum ]);
				//sum_num_arr.push([ r.collectDate, r.registerSumNum ]);
				financial_arr_line.push([ r.collectDate, r.keepFinancialRate ]);
				investor_arr_line.push([ r.collectDate, r.keepInvestorRate ]);
				sum_num_arr_line.push([ r.collectDate, r.keepSumRate ]);
			}
			//keepOptions.series[0].data = financial_arr;
			//keepOptions.series[1].data = investor_arr;
			//keepOptions.series[2].data = sum_num_arr;
			keepOptions.series[0].data = financial_arr_line;
			keepOptions.series[1].data = investor_arr_line;
			keepOptions.series[2].data = sum_num_arr_line;
			var chart = new Highcharts.Chart(keepOptions);
		},
		cache : false
	});
}

function clearDate() {
	$("#dataStart").val('');
	$("#dataEnd").val('');
}
function collectHisDate(){
	if (!confirm('手动汇总会删除历史数据重新汇总！确定要进行手动汇总？')) {
    	return;
    }
	
	 $.ajax({
     	url:'/manage/rpt/collecthisdata',
     	type:'post',
     	success:function(r){
     		alert(r.msg);
     	}
     })
}


function checkRedeemCodeForm() {
	$("#liveRedeemCodeForm").validate({
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
			amount : {
				required : true,
				digits : true,
				range : [1, 200000]
			},
			validDay : {
				required : true,
				digits : true,
				range : [1, 10]
			},
			expireTime : {
				required : true
			},
			number : {
				required : true,
				digits : true,
				range : [1, 200]
			}
		},
		messages : {
			amount : {
				required : "理财金金额不能为空",
				digits : "只能填整数",
				range : "只能输入1-200000"
			},
			validDay : {
				required : "有效期天数不能为空",
				digits : "只能填整数",
				range : "只能输入1-10"
			},
			expireTime : {
				required : "过期时间不能为空"
			},
			number : {
				required : "生成数量不能为空",
				digits : "只能填整数",
				range : "只能输入1-200"
			}
		}
	});
}
