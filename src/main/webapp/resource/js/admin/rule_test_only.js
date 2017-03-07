var dsps = null;
var areaUi = null;

$(document).ready(function() {
	initData();
	initSearch();
	initForm();
	initValidate();
});

//出事化数据
function initData(){
	$.ajax({
		  url: _ctx + "/manage/dsp/getAllDsps",
		  dataType: 'json',
		  async : false,
		  type:'post',
		  success: function(data){
			  dsps = data;
		  }
	});
}

// 初始化搜索控件
function initSearch() {
	$('#dspIdSearch').keydown(function(e){
		if(e.keyCode==13){
			var value = $.trim($(this).val());
			if (value != '') {
				$("#searchForm").submit();
			}
		}
	});
}

//初始化表单控件
function initForm() {
	$("#ruleDisplayDialog #name").mouseover(function(){
		this.title=this.value;
	});
	$("#startTime").datetimepicker({
		startDate:new Date(),
		minuteStep:1
	});
	$("#endTime").datetimepicker({
		startDate:new Date(),
		minuteStep:1,
        seconds : 59
	});
	//售卖广告位
	adpCombox = $("#adpSellCodes_add").combox({
		type : "server",
		url : _ctx + "/manage/rule/getAllAdpSells",
		rows : 4,
		cols : 3,
		onConfirm : function(eles){
			//排序
			eles.sort(function(a,b){
				return a.text>b.text ? true : false;
			});
			var adpSellCodes = [];
			//清空已选
			$("#selectedAdpSells").empty();
			for(var i=0; i<eles.length; i++) {
				adpSellCodes.push(eles[i].value);
				var ele = $('<div style="margin:5px"><span class="glyphicon glyphicon-minus" value="'
						+ eles[i].value + '"></span><label style="margin-left:5px">'
						+ eles[i].text + '</label></div>')
						.appendTo($("#selectedAdpSells"));
				ele.find("span").on("click",function(e){
					var code = e.target.getAttribute("value");
					adpCombox.remove(code);
					$(e.target).parent().remove();
					var adpSellCodes = $("#adpSellCodes").val().split(",");
					adpSellCodes.splice(adpSellCodes.indexOf(code), 1);
					$("#adpSellCodes").val(adpSellCodes.join(","));
				});
			}
			$("#adpSellCodes").val(adpSellCodes.join(","));
		}
	});
	// 地域控件
	areaUi = areaReady();
	// 指定买家
	$("#dspNames_1").combox({
		type : "local",
		data : dsps,
		rows : 4,
		cols : 4,
		onConfirm : function(eles){
			onChangeDsp(eles, 1);
		}
	});
	$("#price_add").on("click", function(e){
		addPricePolicy();
	});
	// 设定提交表单操作
	$("#ruleDisplayDialog_submit").click(function(){
		var t = $("#ruleForm").valid();
		if (!t) return;
		if(!validateForm()) return;
		generatePricePolicy();
		generatePlayOrder();
		ajaxForm();
	});
}

// 添加一条底价设置策略
function addPricePolicy(){
	var index = parseInt($("#priceIndex").val()) + 1;
	$("#priceIndex").val(index);
	var priceDom = $("<tr></tr>").appendTo($("#prices"));
	$("<td>指定买家：</td>").appendTo(priceDom);
	$("<td><input type=\"text\" id=\"dspNames_" + index + "\" class=\"form-control\" placeholder=\"点击选择买家\" readOnly/>" +
			"<input type=\"hidden\" id=\"dspIds_" + index + "\"  class=\"form-control\"/></td>").appendTo(priceDom);
	$("<td>底价：</td>").appendTo(priceDom);
	$("<td><input type=\"text\" id=\"basePrice_" + index + "\" class=\"form-control\"/></td>").appendTo(priceDom);
	$("<td>分/CPM</td>").appendTo(priceDom);
	$("<td><span id=\"price_minus_" + index + "\" class=\"glyphicon glyphicon-minus\" style=\"margin:5px\"></span></td>").appendTo(priceDom);
	
	$("#dspNames_" + index).combox({
		type : "local",
		data : dsps,
		rows : 4,
		cols : 4,
		onConfirm : function(eles){
			onChangeDsp(eles, index);
		}
	});
	$("#price_minus_" + index).on("click", function(){
		$.combox.getBox("dspNames_" + index).dispose();
		$(this).parent().parent().remove();
	});
}

// 改变指定买家的选择
function onChangeDsp(eles, index){
	//排序
	eles.sort(function(a,b){
		return a.text>b.text ? true : false;
	});
	//清空已选
	$("#dspIds_" + index).empty();
	$("#dspNames_" + index).empty();
	var dspNames = [];
	var dspIds = [];
	for(var i=0; i<eles.length; i++) {
		dspIds.push(eles[i].value);
		dspNames.push(eles[i].text);
	}
	$("#dspIds_" + index).val(dspIds.join(","));
	$("#dspNames_" + index).val(dspNames.join(","));
	$("#dspNames_" + index).attr("title", dspNames.join(","));
}

//初始化表单校验
function initValidate(){
	//素材时长校验
	jQuery.validator.addMethod("durations", function(value, element) {
		if (value == "") {
			return true;
		}
		value = value.replace(/，/g, ",");
		var valid = true;
		for ( var i = 0; i < value.split(",").length; i++) {
			var num = value.split(",")[i];
			if (!num) {
				valid = false;
			}
			if (isNaN(num)) {
				valid = false;
			}
			if (parseFloat(num) < 0 || parseFloat(num) > 1000) {
				valid = false;
			}
			var nums = num.split(".");
			if(nums.length == 2 && nums[1].length>2) {
				valid = false;
			}
		}
		return valid;
	});

	$("#ruleForm").validate({
		errorElement: "em",
		errorPlacement: function(error, element) {
			error.appendTo(error.appendTo(element.parents("div")[0]));
		},
		rules: {
			name: {
				required: true,
				maxlength: 100,
				remote:{
					type: "get",  //数据发送方式
		            url:_ctx + "/manage/rule/ruleNameVertify"
		        }
			},
			startTime: {
				required: true
			},
			endTime: {
				required: true
			},
			playOrderBox : {
				required : true
			},
			areaName : {
				required: true
			},
			durations : {
				required: true,
				durations: true
			}
		},
		messages: {
			name:{
				required: '售卖规则名称不能为空',
				maxlength: '最多不超过100字符',
				remote: '名称重复'
			},
			startTime:{
				required: '开始时间不能为空'
			},
			endTime : {
				required: '结束时间不能为空'
			},
			playOrderBox : {
				required: '必须选择位序'
			},
			areaName : {
				required: '地域不能为空'
			},
			durations : {
				required: '素材时长不能为空',
				durations: '素材时长应为一个或多个以逗号分隔的最多两位小数的多个数字'
			}
		}
	});
}

// 校验表单
function validateForm() {
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	if(startTime > endTime) {
		errConfirm("开始时间不能晚于结束时间");
		return false;
	}
	if($("#adpSellCodes").val() == ""){
		errConfirm("售卖广告位不能为空");
		return false;
	}
	var maxIndex = $("#priceIndex").val();
	for(var i=1; i<=maxIndex; i++) {
		if($("#dspIds_" + i).length == 0)
			continue;
		if($("#dspIds_" + i).val() == ""){
			errConfirm("指定买家不能为空");
			return false;
		}
		if($("#basePrice_" + i).val() == ""){
			errConfirm("底价不能为空");
			return false;
		}
		if(!digitsRange($("#basePrice_" + i).val(), 0, 10000000)){
			errConfirm("底价应为0-10000000之间的整数");
			return false;
		}
	}
	return true;
}

// 错误提示
function errConfirm(text){
	swal({title: "售卖规则",
		  text: text,
		  type: "error", 
		  confirmButtonColor: "#DD6B55",   
		  confirmButtonText: "关闭", 
		  closeOnConfirm: true}
	);
}

// 添加售卖规则
function add() {
	resetForm();
	$("#ruleDisplayDialog h4.modal-title").html("添加售卖规则");
	$("#ruleDisplayDialog").modal({show : true});
}

// 修改售卖规则
function edit(ruleId) {
	resetForm();
	$("#ruleDisplayDialog h4.modal-title").html("修改售卖规则");
	fillData(ruleId);
	disableIput(false);
	$("#ruleDisplayDialog #name").attr("disabled", true);
	$("#ruleDisplayDialog").modal({show : true});
}

// 查看售卖规则
function view(ruleId) {
	resetForm();
	$("#ruleDisplayDialog h4.modal-title").html("查看售卖规则");
	$("#ruleDisplayDialog").modal({show : true});
	fillData(ruleId);
	disableIput(true);
	$("#ruleDisplayDialog_close").attr("disabled", false);
	$("#ruleDisplayDialog span").hide();
}

function status(ruleId, status) {
	$.ajax({
		  url: _ctx + "/manage/rule/changeStatus?id=" + ruleId + "&status=" + status,
		  dataType: 'json',
		  async : false,
		  type:'post',
		  success: function(data){
			  swal({title: "修改状态",
				  text: "修改状态成功",
				  type: "success", 
				  confirmButtonColor: "#DD6B55",   
				  confirmButtonText: "关闭", 
				  closeOnConfirm: true},
				  function(){
					  window.location.reload();
				  }
			  );
		  }
	});
}

function fillData(ruleId) {
	var ruleBase = findRuleBaseById(ruleId);
	fillDialogData(ruleBase);
}

function findRuleBaseById(ruleId) {
	for(var i=0; i<$data.length; i++) {
		if ($data[i].id == ruleId) {
			return $data[i];
		}
	}
}

function fillDialogData(ruleBase) {
	fillArrayInput(["id", "name" ,"biddingType", "playOrder", "durations"], ruleBase);
	$("#ruleDisplayDialog #name").attr("title", ruleBase.name);
	$("#startTime").val(ruleBase.startTime.split(".")[0]);
	$("#endTime").val(ruleBase.endTime.split(".")[0]);
	$("#ruleDisplayDialog #ruleStatus" + ruleBase.ruleStatus).prop("checked", true);
	$("#ruleDisplayDialog #tradePriceType" + ruleBase.tradePriceType).prop("checked", true);
	
	fillAdpSells(ruleBase.adpSells);
	fillPlayOrder(ruleBase.playOrder);
	fillArea(ruleBase.areas);
	fillPricePolicy(ruleBase.prices);
}

function fillArrayInput(ids, ruleBase) {
	for(var i in ids) {
		var id = ids[i];
		$("#ruleDisplayDialog #" + id).val(ruleBase[id]);
	}
}

function fillAdpSells(adpSells) {
	var selected = [];
	for(var i in adpSells) {
		var sell = adpSells[i];
		selected.push({
			value : sell.code,
			text : sell.name
		});
	}
	$.combox.getBox("adpSellCodes_add").val(selected);
}

function fillPlayOrder(playOrder){
	playOrder = playOrder.split(",");
	for(var i in playOrder){
		$("#playOrder" + playOrder[i]).prop("checked",true);
	}
}

function fillArea(areas) {
	var ids = [];
	var names = [];
	for(var i in areas) {
		var area = areas[i];
		ids.push(area.id);
		names.push(area.name);
	}
	$("#areaId").val(ids.join(","));
	$("#areaName").val(names.join(","));
}

function fillPricePolicy(prices) {
	var groups = {};
	for(var i in prices) {
		var price = prices[i];
		if(groups.hasOwnProperty(price.groupId)) {
			var dspName = getDspNameById(price.dspId);
			group = groups[price.groupId];
			group.dspIds.push(price.dspId);
			group.dspNames.push(dspName);
			group.selected.push({
				text : dspName,
				value : price.dspId
			});
		} else {
			var dspName = getDspNameById(price.dspId);
			groups[price.groupId] = {
				dspIds : [price.dspId],
				dspNames : [getDspNameById(price.dspId)],
				basePrice : price.basePrice,
				selected : [{
					text : dspName,
					value : price.dspId
				}]
			};
		}
	}
	for(var groupId in groups) {
		var group = groups[groupId];
		if(groupId == 1) {
			$("#dspIds_1").val(group.dspIds.join(","));
			$("#dspNames_1").val(group.dspNames.join(","));
			$("#basePrice_1").val(group.basePrice);
			$.combox.getBox("dspNames_1").val(group.selected);
			$("#dspNames_" + index).attr("title", group.dspNames.join(","));
		} else {
			$("#price_add").click();
			var index = $("#priceIndex").val();
			$("#dspIds_" + index).val(group.dspIds.join(","));
			$("#dspNames_" + index).val(group.dspNames.join(","));
			$("#basePrice_" + index).val(group.basePrice);
			$.combox.getBox("dspNames_" + index).val(group.selected);
			$("#dspNames_" + index).attr("title", group.dspNames.join(","));
		}
	}
}

function getDspNameById(dspId) {
	for(var i in dsps) {
		var dsp = dsps[i];
		if(dsp.value == dspId) {
			return dsp.text;
		}
	}
}

// 生成底价设置JSON
function generatePricePolicy(){
	var pricePolicys = [];
	var maxIndex = $("#priceIndex").val();
	var group = 1;
	for(var i=1; i<=maxIndex; i++) {
		if($("#dspIds_" + i).length == 0)
			continue;
		var dspIds = $("#dspIds_" + i).val().split(",");
		var basePrice = $("#basePrice_" + i).val();
		for(var j=0; j<dspIds.length; j++){
			var pricePolicy = {
					dspId : dspIds[j],
					basePrice : basePrice,
					groupId : group
			};
			pricePolicys.push(pricePolicy);
		}
		group++;
	}
	$("#pricesPolicy").val(JSON.stringify(pricePolicys));
}

function generatePlayOrder() {
	var playOrder = [];
	$("input[name=playOrderBox]:checked").each(function(){
		playOrder.push($(this).val());
	});
	$("#playOrder").val(playOrder.join(","));
}

// 重置表单
function resetForm() {
	$("#ruleForm").validate().resetForm();
	$("#ruleDisplayDialog button").attr("disabled", false);
	disableIput(false);
	clean();
	$("#ruleDisplayDialog span").show();
	areaUi.hideAreaComponent();
}

// 清除表单数据
function clean() {
	$("#ruleDisplayDialog input[type=text]").each(function(){
		$(this).val("");
	});
	$("#ruleDisplayDialog select").each(function(){
		$(this).val("");
	});
	//ID
	$("#id").val('');
	// 地域
	$("#areaId").val('');
	// 售卖广告位
	$("#selectedAdpSells").empty();
	$("#adpSellCodes").val('');
	$.combox.getBox("adpSellCodes_add").clear();
	$("input[name=playOrderBox]").each(function(){
		$(this).prop("checked",false);
	});
	// 指定买家
	var maxIndex = $("#priceIndex").val();
	$.combox.getBox("dspNames_1").clear();
	$("#dspIds_1").val('');
	$("#priceIndex").val(1);
	for(var i=2; i<=maxIndex; i++) {
		if($("#dspNames_" + i).length == 0)
			continue;
		$("#price_minus_" + i).click();
	}
}

function disableIput(isDisable) {
	$("#ruleDisplayDialog input").attr("disabled", isDisable);
	$("#ruleDisplayDialog select").attr("disabled", isDisable);
	$("#ruleDisplayDialog checkbox").attr("disabled", isDisable);
	$("#ruleDisplayDialog .btn").attr("disabled", isDisable);
}

function ajaxForm() {
	$('#ruleForm').ajaxSubmit({
	  url: _ctx + "/manage/rule/save",
	  dataType: 'json',
	  
	  success: function(data){
		  var message = "提交失败！";
		  var type = "error";
		  if (data.isSuccess) {
			  message = "提交成功！";
			  type= "success";
		  }
		  $("#ruleDisplayDialog button").attr("disabled", true);
		  disableIput(true);
		  swal({title: "售卖规则",
			  text:message,
			  type: type, 
			  confirmButtonColor: "#DD6B55",   
			  confirmButtonText: "关闭", 
			  closeOnConfirm: true}, 
			  function(){
				  if(data.isSuccess) {
					  $("#ruleDisplayDialog").hide();
					  window.location.reload();
				  } else {
					  $("#ruleDisplayDialog button").attr("disabled", false);
					  disableIput(true);
				  }
			  }
		  );
	  },
	  error: function(xhr, type, exception) {
		  if (xhr.responseText) {
			  var json = jQuery.parseJSON(xhr.responseText);
			  swal({title: "提示",text: json.message},function(){
				  $("#ruleDisplayDialog").modal({show:false});
				  window.location.reload();
			  });
		  }
      }
	});
}