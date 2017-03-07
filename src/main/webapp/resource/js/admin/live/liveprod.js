var prohavedamount = 0;// 当前为标的分配的资产总和
var errorcount = 0;
var pageNum = 1;
/** 初始化加载 */
$(document).ready(function() {
	initTabs();
	initAssetAmountValidate();
	changeUnit();
	initForm();
	validateSubmit();
	batchSave();
});

/** 初始化表单事件 */
function initForm() {

	// 设定提交表单操作
	$("#btn_add_prd").click(function() {
		window.location.href = "/manage/live/product/add";
	});

	// 设定返回操作
	$("#btnBack").click(function() {
		window.location.href = "/manage/live/product/list/1";
	});

}

// 头上Tab页面切换
function initTabs() {
	var pathName = window.document.location.pathname;
	if (pathName.indexOf("/manage/live/product/list/1") == 0) {
		$("#tabLive ").addClass("active");
	} else {
		$("#tabLive ").addClass("active");
	}
}

//改变分页条数的时候触发
function changePageSizeByPageNum(formId, pageNo, value) {
	$("#" + formId + " input[name=pageSize]").val(value);
	loadAssetListPage(1);
}
//分页的时候触发
function redirectToByPageNum(formId, pageNo) {
	loadAssetListPage(pageNo);
}

function loadAssetListPage(pagenum) {
	pageNum = pagenum;
	var pid = $("#id").val();
	var name = $("#searchAssetName").val();
	if (!name) {
		name = "";
	}
	var pageSize = $("#pageSize").val();
	if (!pageSize) {
		pageSize = "";
	}
	var data = {
		"pageNum" : pageNum,
		"name" : name,
		"pid" : pid,
		"pageSize" : pageSize
	};
	var dataUrl = "/manage/live/product/selectAst";
	$("#asset").load(dataUrl, data, function() {
		$("#livast tr:gt(1)").each(function(i, obj) {
			var id = $(obj).attr("id").replace('haved', '');
			var trdata = $("#asset_" + id);
			trdata.find("button").html('已添加').prop('disabled', 'disabled');
			
			trdata.find("input[type='checkbox']").prop('disabled', 'disabled');
			trdata.find("input[type='checkbox']").removeAttr("type").css("display","none");
			
		});

	}).fadeIn(300);

}

//仅显示基金
function onlyFundShow(){
	var onlyFund = $("#onlyFund");
	var livast = $("#livast").html();
	if(onlyFund.prop("checked")){
		$("#livast tr:gt(1)").each(function(i, obj) {
			var type = $(obj).children('td').eq(1).html();
			if($.trim(type) != "基金"){
				var id = $(this).attr("id").replace('haved', '');
				$("#"+id).attr("name","");
				$(obj).hide();
			}
		});
	}else{
		$("#livast tr:gt(1)").each(function(i, obj) {
			var type = $(obj).children('td').eq(1).html();
			if($.trim(type) != "基金"){
				var id = $(this).attr("id").replace('haved', '');
				$("#"+id).attr("name","astAmountMoney");
				$(obj).show();
			}
		});
	}
	calcFundScale(true);
}

function closeSelectAst() {
	//移除仅显示基金的复选框
	$("#onlyFund").removeAttr("checked");
	//显示所有影藏的东西
	$("#livast tr:gt(1)").each(function(i, obj) {
		var type = $(obj).children('td').eq(1).html();
		if($.trim(type) != "基金"){
			var id = $(this).attr("id").replace('haved', '');
			$("#"+id).attr("name","astAmountMoney");
			$(obj).show();
		}
	});
	
	$("#asset").hide();
}

function deleteAsset(id) {
	if (confirm("确认删除这条资产吗?")) {
		if ($("#error_" + id).html() != "") {
			errorcount = errorcount - 1;
			$("#erroramount").val(errorcount);
		}
		
		//匹配资产总规模
		var deleteMoney = parseInt($("#yfpMoney").text()-$("#"+id).val());
		$("#yfpMoney").text(deleteMoney);
		
		$("#haved" + id).remove();
		
		calcFundScale(true);
	}
	
}

/** 删除 */
function del(prdId, name) {
	if (!confirmDelete(name)) {
		return false;
	}
	window.location.href = '/manage/live/product/delete/' + prdId;
}

/** 查看分配资产列表 */
function showAssetList(prdId) {
	window.location.href = '/manage/live/product/asset/list/' + prdId + '/1';
}

/** 确认删除提示 */
function confirmDelete(name) {
	if (confirm("您确定要删除产品名称是 " + name + " 的数据吗？"))
		return true;
	else
		return false;
}

function edit(pid) {
	window.location.href = '/manage/live/product/edit/' + pid;
}

function addAsset(id) {

	var money = parseInt($("#asset_" + id).find("td:eq(4)").text()) + parseInt($("#yfpMoney").text());
	//设置已分配资产规模
	$("#yfpMoney").text(money);

	addAssetTr(id);
	
	calcFundScale(true);
}

function isAdd() {
	var isAdd = false;
	if (!$("#id").val()) {
		isAdd = true
	}
	return isAdd;
}

function changeUnit() {
	var amount = parseFloat($("#amount").val());
	if ($("#amountUnit").val() == '1') {
		amount = amount * 10000;
	} else {
		amount = amount * 100000000;
	}
	$("#amount1").val(Math.round(amount));
}

function prodHavedAmount() {
	prohavedamount = 0;
	$("#livast input[type='text']").each(function() {
		prohavedamount = prohavedamount + parseInt($(this).val());
	});
}

function checkSelectAsset() {
	var selectAsset = $("#livast tr:gt(1)");
	if (selectAsset.length == 0) {
		alert("还未给产品分配资产");
		return false;
	} else {
		return true;
	}
}
function checkAssetAmount() {
	errorCount = parseFloat($("#erroramount").val());
	var isval = true;
	if (errorcount != 0) {
		alert("检查输入额度，确保正确");
		return false;
	} else {
		isval = true;
	}

	$("#livast input[type='text']").each(function(i, obj) {
		if ($(obj).val() == '' || !isInt($(obj).val()) || $(obj).val() == '0') {
			alert("额度必填且只为正整数");
			isval = false;
			return isval;
		}
	});
	return isval;
}

function checkProTotalAmount() {
	prodHavedAmount();
	var tamount = Math.round($("#amount1").val());
	if (prohavedamount != tamount) {
		var tip = "产品规模与已分配的资产金额不匹配\n产品规模:" + tamount + "元\n" + "已配规模:"
				+ prohavedamount + "元";
		alert(tip);
		return false;
	} else {
		return true;
	}

}

function checkFrom(form) {
	// 检验是否未选择资产
	if (!checkSelectAsset()) {
		return false;
	}
	// 检查是否输入金额的格式和合法性
	if (!checkAssetAmount()) {
		return false;
	}
	// 检查分配资产规模与产品总规模
	if (!checkProTotalAmount()) {
		return false;
	}
	saveSelectProdAsset();
	form.submit();
}

/** 初始化表单校验 */
function validateSubmit() {
	$("#productForm").validate({
		debug : false,
		errorPlacement : function(error, element) {
			error.appendTo(element.parent("div"));
			error.css("color", "#ff0000");
		},
		submitHandler : function(form) {
			checkFrom(form);
		},
		rules : {
			name : {
				required : true,
				maxlength : 128,
				remote : {
					type : "POST",
					url : "/manage/live/product/checkName",
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

			amount : {
				required : true,
				maxlength : 10,
				range : [ 1, 1000000000 ]
			}
		},
		messages : {
			name : {
				required : '名称不能为空',
				maxlength : '最多不超过128个汉字',
				remote : '名称不能重复'
			},
			amount : {
				required : '请输入产品规模',
				maxlength : '长度最多不超过10位',
				range : '取值在1-1000000000之间'
			}
		}
	});
}

function initAssetAmountValidate() {

	$(document).on('keyup', "#livast input[type='text']", function() {
		validateAssetAmount($(this));
	});

	$("#amount").blur(function() {
		changeUnit();
	});
	$("#amountUnit").change(function() {
		changeUnit();
	});
}

function assethavedAmount(asid) {
	var amount = 0;
	$.ajax({
		type : "POST",
		url : '/manage/live/product/amount/' + asid,
		dataType : "text",
		async : false,
		success : function(data) {
			amount = eval("(" + data + ")").amount;
		},
		error : function() {
			alert(arguments[1]);
		}
	});
	return amount;
}

// common input blur funtion 1 validate astid amount 2. validata prod amount
function validateAssetAmount(obj) {
	prohavedamount = 0; // 重新计算分配额度
	var asid = obj.attr("id");
	var tamount = parseFloat($("#tamount_" + asid).val());// 资产总规模
	var amount = parseFloat(obj.val());// 当前标的为资产分配额度
	var havedamount = 0;// 其他标的为该资产分配的额度
	// var proamount=parseFloat($("#amount1").val());//产品总规模
	if(!isInt(obj.val())){
		if ($("#error_" + asid).html() == "") {
			errorcount = errorcount + 1;
		}
		$("#error_" + asid).html("<font color='red'>只能输入整数</font>");
		$("#erroramount").val(errorcount);
		return false;
	}
	// 只为整数
	if ( obj.val() == '0' || obj.val() == '') {
		if ($("#error_" + asid).html() == "") {
			errorcount = errorcount + 1;
		}
		$("#error_" + asid).html("<font color='red'>额度错误，至少分配一元</font>");
		$("#erroramount").val(errorcount);
		return false;
	}

	// 查找资产已有额度
	havedamount = assethavedAmount(asid);
	if (havedamount + amount > tamount) {
		if ($("#error_" + asid).html() == "") {
			errorcount = errorcount + 1;
		}

		$("#error_" + asid).html("<font color='red'>分配额度超出资产总规模</font>");
		$("#erroramount").val(errorcount);
		return false;
	}

	// else{
	// prodHavedAmount();
	// if(prohavedamount>proamount){
	// if($("#error_"+asid).html()==""){
	// errorcount=errorcount+1;
	// }
	// $("#error_"+asid).html("<font color='red'>分配额度超出产品总规模</font>");
	// $("#erroramount").val(errorcount);
	//	
	// return false;
	// }
	//	
	// }

	if ($("#error_" + asid).html() != "") {
		errorcount = errorcount - 1;
	}
	$("#erroramount").val(errorcount);
	$("#error_" + asid).html("");
	return true;

}

function saveSelectProdAsset() {
	var version = 1;
	if (!isAdd()) {
		version = $("#version").val();
	}
	// 构建设选择的资产列表json
	var index = -1;
	var astlistjson = new Array();
	$("#livast tr:gt(1)").each(function(i, obj) {
		index = index + 1;
		var tr = {};
		tr.astId = $(obj).find("input[type='text']").attr('id');
		tr.amount = $(obj).find("input[type='text']").val();
		tr.version = version;
		tr.isValid = 1;
		tr.prdId = $("#id").val();
		astlistjson[index] = tr;
	})
	// IE7 及以下不支持
	var json = JSON.stringify(astlistjson);
	$("#assetjson").val(json);// 设置值 随form一起提交 post
}

function ClearNullArr(arr) {
	for (var i = 0; i < arr.length; i++) {
		if (arr[i] || arr[i] == '') {
			arr.splice(i, 1);
		}
	}
	return arr;
}

// 正整数
function isInt(obj) {
	var numberRegex = "^([0-9]*)$";
	var re = new RegExp(numberRegex);
	if (obj.search(re) != -1)
		return true;
	else
		return false;

}

function changeStatusFB(prdId, name){
	if(confirm("确认发布[标的]【"+name+"】吗?\r\r[发布]后不可撤销,不能修改!请确认！")){
		$.ajax({
	     	url:'/manage/live/product/changeStatus',
	     	type:'post',
	     	data:{id:prdId,
	     		status:2},
	     	success:function(r){
	     		alert(r.msg);
	     		if(r.status=='true'){
	     			window.location.href = "/manage/live/product/list/1";
	     		}
	     	}
	     })
	}
}
function changeStatusMB(prdId, name){
	if(confirm("确认将[标的]【"+name+"】设为满标吗?\r\r[满标]后不可撤销,并且会释放尚未卖完的资产!请确认！")){
		$.ajax({
	     	url:'/manage/live/product/changeStatus',
	     	type:'post',
	     	data:{id:prdId,
	     		status:4},
	     	success:function(r){
	     		alert(r.msg);
	     		if(r.status=='true'){
	     			window.location.href = "/manage/live/product/list/1";
	     		}
	     	}
	     })
	}
}

//批量添加反选活期资产
function batchSave(){
	var money = 0 ;
	$(".checkBoxTitle :checkbox").each(function () {  
		if($(this).prop("checked")){
			var id = $(this).attr("id");
			addAssetTr(id);
			money += parseInt($("#asset_" + id).find("td:eq(4)").text());
		 }
	 });
	 money += parseInt($("#yfpMoney").text());
	 //设置已分配资产规模
	 $("#yfpMoney").text(money);
	 
	 calcFundScale(true);
}

/**
 * 动态追加资产列表
 * @param id
 */
function addAssetTr(id){
	var tr = {};
	var trdata = $("#asset_" + id);
	tr.id = id;
	tr.name = trdata.find("td:eq(1)").text();
	tr.type = trdata.find("td:eq(2)").text();
	tr.lenderName = trdata.find("td:eq(3)").text();
	tr.amountExp = trdata.find("td:eq(4)").text().replace('元', '');
	tr.havedAmount = trdata.find("td:eq(5)").text();
	tr.amount = trdata.find("td:eq(5)").text();
	
	$("#livast").append($("#tr").tmpl(tr));
	trdata.find("button").html('已添加').prop('disabled', 'disabled');
	trdata.find("input[type='checkbox']").prop('disabled', 'disabled');
	trdata.find("input[type='checkbox']").removeAttr("type").css("display","none");
}

//失去焦点计算获取资产
function addAstAmountMoney(){
	calcFundScale(true);
}

/**
 * 计算基金比例 
 * @param isSetAsset是否计算总资产规模
 */
function calcFundScale(isSetAsset){
	var aam = document.getElementsByName("astAmountMoney");
	var sumMoney = 0;
	var fundMoney = 0;
	for (var i = 0; i < aam.length; i++) {
		var array_element = aam[i].value ;
		if(array_element.length <= 0 || array_element == "" || isNaN(array_element)){
			array_element = 0;
		}
		//基金比例
		var trdata = $("#haved" + aam[i].id);
		var type = $.trim(trdata.find("td:eq(1)").text());
		if(type == "基金"){
			fundMoney += parseFloat(array_element);
		}
		sumMoney += parseFloat(array_element); 
	}
	
	//是否设置资产规模
	if(isSetAsset){
		$("#yfpMoney").text(sumMoney);
	}
	var fundScale = fundMoney/sumMoney * 100;
	if(sumMoney == 0){
		fundScale = 0;
	}
	$("#fundScale").text(fundScale.toFixed(2));
}


/********添加资产 仅显示基金*******/
function assetOnlyFundShow(pageNum){
	var pageSize = $("#pageSize").val();
	var assetOnlyFund = $("#assetOnlyFund");//复选框
	var productId = $("#id").val();//产品id
	var searchAssetName = $("#searchAssetName").val();//搜索框
	var ajaxUrl = "/manage/live/product/selectAstAjax";//后台url
	var assetOnlyFundFlag = "";//后台选中标识
	if(assetOnlyFund.prop("checked")){
		assetOnlyFundFlag = true;
	}
	$.ajax({
		type : "POST",
		url : ajaxUrl,
		dataType : "json",
		async : false,
		data : {"pid":productId , "pageNum" : pageNum ,"pageSize" : pageSize, "assetOnlyFundFlag" : assetOnlyFundFlag , "searchAssetName" : searchAssetName},
		success : function(data) {
			data = eval(data);
			$("#astListBody").html("");
			$.each(data.list.result,function(i,item){
				appenAstList(item);
			});
			$("#assetPage").html("");
			page(data.list.pageBean)
			
		},
		error : function() {
			alert("获取数据失败，请稍后再试..");
		}
	});
}

function appenAstList(item){
	var t = false;
	$("#livast tr:gt(1)").each(function(i, obj) {
		var id = $(obj).attr("id").replace('haved', '');
		if(id == item.id){//已经添加东西
			t = true;
		}
	})
	if(t){
		var tr = "<tr class='upDownTitle checkBoxTitle' id='asset_"+item.id+"'>";
		tr += "<td><input style='display:none' id='"+item.id+"' /></td>";
		var name = item.name;
		if(name.length > 64){
			name = name.substring(0, 64);
		}
		tr += "<td>" + name + "</td>";
		if(item.type == 2){
			tr += "<td>基金</td>";
		}else{
			tr += "<td>债权</td>";
		}
		var lenderName = item.lenderName;
		if(lenderName.length > 64){
			lenderName = lenderName.substring(0, 64);
		}
		tr += "<td>" + lenderName + "</td>";
		tr += "<td>"+item.amountExp+"元</td>";
		tr += "<td>"+item.havedAmount+"</td>";
		tr += "<input type='hidden' id='tamount_"+item.id+"' value='"+item.amountExp+"'>";
		tr += "<td><button type='button' class='btn btn-default' disabled='disabled'>已添加</button></td>"
		tr += "</tr>";
		
		$("#astListBody").append(tr);
	}else{
		var tr = "<tr class='upDownTitle checkBoxTitle' id='asset_"+item.id+"'>";
		tr += "<td><input type='checkbox' id='"+item.id+"' /></td>";
		var name = item.name;
		if(name.length > 64){
			name = name.substring(0, 64);
		}
		tr += "<td>" + name + "</td>";
		if(item.type == 2){
			tr += "<td>基金</td>";
		}else{
			tr += "<td>债权</td>";
		}
		var lenderName = item.lenderName;
		if(lenderName.length > 64){
			lenderName = lenderName.substring(0, 64);
		}
		tr += "<td>" + lenderName + "</td>";
		tr += "<td>"+item.amountExp+"元</td>";
		tr += "<td>"+item.havedAmount+"</td>";
		tr += "<input type='hidden' id='tamount_"+item.id+"' value='"+item.amountExp+"'>";
		tr += "<td><button type='button' class='btn btn-default' onclick='addAsset("+item.id+")'>添加</button></td>"
		tr += "</tr>";
		
		$("#astListBody").append(tr);
	}
	
}

function page(pageBean){
	var pages = pageBean.pages;
	var pageSize = $("#pageSize").val();
	var formId = '"searchForm"';
	var pageHtml = "";
	pageHtml += "<input type='hidden' name='pageNum' id='pageNum' value="+pageBean.pageIndex+" />";
	pageHtml += "	<ul class='pagination pagination-centered'>";
	if(pages == 1){
		pageHtml += "	<li><a href='javascript:;' class='active'>1</a></li>";
	}else if(pages >= 2 && pages <= 5){
		for (var pageIndex = 1; pageIndex <= pages; pageIndex++) {
			if(pageIndex == pageBean.pageIndex){
				pageHtml += "<li class='active'><a href='javascript:assetOnlyFundShow("+pageIndex+");'>"+pageIndex+"</a></li>";
			}else{
				pageHtml += "<li><a href='javascript:assetOnlyFundShow(" + pageIndex + ");'>" + pageIndex + "</a></li>";
			}
		}
	}else if(pages > 5){
		if(pageBean.pageIndex < 3){
			if(pageBean.pageIndex == 1){
				pageHtml += "<li class='active'><a href='javascript:assetOnlyFundShow(1);'>1</a></li>";
			}else{
				pageHtml += "<li><a href='javascript:assetOnlyFundShow(1);'>1</a></li>";
			}
			if(pageBean.pageIndex == 2){
				pageHtml += "<li class='active'><a href='javascript:assetOnlyFundShow(2);'>2</a></li>";
			}else{
				pageHtml += "<li><a href='javascript:assetOnlyFundShow(2);'>2</a></li>";
			}
			if(pageBean.pageIndex == 3){
				pageHtml += "<li class='active'><a href='javascript:assetOnlyFundShow(3);'>3</a></li>";
			}else{
				pageHtml += "<li><a href='javascript:assetOnlyFundShow(3);'>3</a></li>";
			}
			pageHtml += "<li class='total'><span>...</span></li>";
			
			var pages1 = pages - 1;
			for (var pageIndex = pages1; pageIndex <= pages; pageIndex++) {
				if(pageIndex == pageBean.pageIndex){
					pageHtml += "<li class='active'><a href='javascript:assetOnlyFundShow("+pageIndex+")'>"+pageIndex+"</a></li>";
				}else{
					pageHtml += "<li><a href='javascript:assetOnlyFundShow("+pageIndex+")'>"+pageIndex+"</a></li>";
				}
			}
		}else if(pageBean.pageIndex >= 3 && pageBean.pageIndex < pages - 3){
			if(1 == pageBean.pageIndex){
				pageHtml += "<li class='active'><a href='javascript:assetOnlyFundShow(1);'>1</a></li>";
			}else{
				pageHtml += "<li><a href='javascript:assetOnlyFundShow(1);'>1</a></li>";
			}
	    	var pages1 = pageBean.pageIndex - 1;
	    	if(pages1 != 2){
	    		pageHtml += "<li class='total'><span>...</span></li>";
	    	}
	    	var pages2 = pageBean.pageIndex + 1;
	    	
	    	for (var pageIndex = pages1; pageIndex <= pages2; pageIndex++) {
	    		if(pageIndex == pageBean.pageIndex){
		 			pageHtml += "<li class='active'><a href='javascript:assetOnlyFundShow("+pageIndex+");'>"+pageIndex+"</a></li>";
		 		}else{
		 			pageHtml += "<li><a href='javascript:assetOnlyFundShow("+pageIndex+");'>"+pageIndex+"</a></li>";
		 		}
			}
	   	 	pageHtml += "<li class='total'><span>...</span></li>";
	   	 	var pages3 = pages - 1;
	   	 	for (var pageIndex = pages3; pageIndex <= pages; pageIndex++) {
		   	 	if(pageIndex == pageBean.pageIndex){
					pageHtml += "<li class='active'><a href='javascript:assetOnlyFundShow("+pageIndex+");'>"+pageIndex+"</a></li>";
				}else{
					pageHtml += "<li><a href='javascript:assetOnlyFundShow("+pageIndex+");'>"+pageIndex+"</a></li>";
				}
			}
		}else if(pageBean.pageIndex >= pages - 3){
			if(1 == pageBean.pageIndex){
				pageHtml += "<li class='active'><a href='javascript:assetOnlyFundShow(1);'>1</a></li>";
			}else{
				pageHtml += "<li><a href='javascript:assetOnlyFundShow(1);'>1</a></li>";
			}
			pageHtml += "<li class='total'><span>...</span></li>";
	    	var pages3 = pages - 4;
	    	for (var pageIndex = pages3; pageIndex <= pages; pageIndex++) {
	    		if(pageIndex == pageBean.pageIndex){
	    			pageHtml += "<li class='active'><a href='javascript:assetOnlyFundShow("+pageIndex+");'>"+pageIndex+"</a></li>";
	    		}else{
	    			pageHtml += "<li><a href='javascript:assetOnlyFundShow("+pageIndex+");'>"+pageIndex+"</a></li>";
	    		}
			}
		}
	}else{
		pageHtml += "<li><a href='javascript:assetOnlyFundShow(1)' class='active'>1</a></li>";
	}
	pageHtml += "<li class='total'><span>当前第 "+pageBean.pageIndex+" 页</span></li>";
	pageHtml += "<li class='total'><span>共 "+pages+" 页</span></li>";
	pageHtml += "<li class='total'><span>总 "+pageBean.count+" 条数</span></li>";
  
	pageHtml += "<li class='pageSize'>";
	pageHtml += "<span>每页数量";
	pageHtml += "<select name='pageSize' id='pageSize' onChange='javascript:assetOnlyFundShow("+pageBean.pageIndex+")' style='height:20px;'>";
	pageHtml += "<option value="+pageBean.pageSize+">请选择</option>";
	if(pageBean.pageSize == 15){
		pageHtml += "<option value='15' selected >15</option>";
	}else{
		pageHtml += "<option value='15'>15</option>";
	}
	if(pageBean.pageSize == 25){
		pageHtml += "<option value='25' selected >25</option>";
	}else{
		pageHtml += "<option value='25'>25</option>";
	}
	if(pageBean.pageSize == 50){
		pageHtml += "<option value='50' selected >50</option>";
	}else{
		pageHtml += "<option value='50'>50</option>";
	}
	pageHtml += "</select>";
	pageHtml += "</span>";
	pageHtml += "</li>";
    pageHtml += "	</ul>";
    
    $("#assetPage").append(pageHtml);
    
}


/********一键添加赎回资产********/
function addRansom(){
	var onlyFund = $("#onlyFund");
	var assetOnlyFundFlag = "";
	if(onlyFund.prop("checked")){
		$("#livastDIV").html("");
		assetOnlyFundFlag = "true";
	}else{
		$("#livastDIV").html("");
		assetOnlyFundFlag = "";
	}
	$("#ransom").attr("disabled","true");
	var pid = $("#id").val();
	$.ajax({
		type : "POST",
		url : "/manage/live/product/selectAstOldAjax",
		dataType : "json",
		async : false,
		data : {"pid":pid , "assetOnlyFundFlag" : assetOnlyFundFlag},
		success : function(data) {
			data = eval(data);
			$.each(data,function(i,item){
				var t = true;
				$("#livast tr:gt(1)").each(function(i, obj) {
					var trName = $(obj).children('td').eq(0).html();
					if($.trim(trName) == $.trim(item.name)){
						t = false;
					}
				});
				if(t){
					var tr = {};
					var trdata = $("#asset_" + item.id);
					tr.id = item.id;
					tr.name = item.name;
					var type = "基金";
					if(item.type == 1){
						type = "债权";
					}
					tr.type = type;
					tr.lenderName = item.lenderName;
					tr.amountExp = item.amountExp;
					tr.havedAmount = item.havedAmount;
					tr.amount = item.amount;
					
					$("#livast").append($("#tr").tmpl(tr));
				}
			});
			
			calcFundScale(true);
		},
		error : function() {
			alert("获取数据失败，请稍后再试..");
		}
	});
}







