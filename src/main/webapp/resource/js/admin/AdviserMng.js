/**初始化加载*/
$(document).ready(function() {
	initTabs() ;
	initForm();
	addTr();
	validateSubmit();
	initUpload();
	initializeAction();
	var operationType =  $("#operationType_hi").val();
	var prdType =  $("#prdType_hi").val();
	
	
 	 
	if(prdType == 0 || null==prdType ){   //如果是空 或者是p2p的话  拆单 信托不显示
		$("#showIsSplitDiv").hide();// 默认不显示 是否是 拆单信托的div
		$("#showOperationTypeDiv").show();  //  显示是否站内购买
		$("#showIsSplitProductDiv").hide();
	}else if(prdType !=0 && prdType != null){
	 
		$("#showIsSplitDiv").show();
	}
	 
	
});

/** 确认删除提示 */
function confirmDelete(name){
	if(confirm("您确定要删除产品名称是 "+name+" 的数据吗？")) return true;
	else return false;
}

/** 动态添加上下限属性表格 */
function addTr(){
	$("#tab tr").attr("align","center");
	$("#but").click(function(){
		var _len = $("#tab tr").length;        
		$("#tab").append("<tr id="+_len+" align='center'>"
	        +"<td><input type='text' id='min_invest_money"+_len+"' name='min_invest_money' class='upDownInput' maxlength='10'/></td>"
	        +"<td><input type='text' id='max_invest_money"+_len+"' name='max_invest_money' class='upDownInput' maxlength='10'/></td>"
	        +"<td><input type='text' id='return_rate"+_len+"' name='return_rate' class='upDownInput' maxlength='5'/></td>"
	        +"<td><input type='text' id='reword_rate"+_len+"' name='reword_rate' class='upDownInput' maxlength='5'/></td>"
	        +"<td><input type='text' id='reword_factor"+_len+"' name='reword_factor' class='upDownInput' maxlength='5'/></td>"
	        +"<td>&nbsp;&nbsp;<a href=\'javascript:;\' onclick=\'deltr("+_len+")\'>删除</a></td>"
	        +"</tr>");            
	});
}

/** 删除表格 */
var deltr = function(index){
    $("tr[id='"+index+"']").remove();//删除当前行
}

/** 动态追加的表格validate不能验证，需手动验证*/
function checkSubmit(){
	 return true ;
	$("#productForm").attr("action","/manage/AdviserMng/save");
	
}

function isNum(str,regex) {
	 var re = new RegExp(regex);
	 if(str.search(re) != -1) return true;
	 else return false;
}
/** 初始化表单校验 */
function validateSubmit(){
	
	$("#productForm").validate({
		debug: false,
		errorPlacement: function(error, element) {
			error.appendTo(element.parent("div"));
			error.css("color", "#ff0000");
		},
		submitHandler: function(form) {
            form.submit();
        },
		rules: {
			name: {
				required: true,
				maxlength: 128 
			} 
		},
		messages: {
			name:{
				required: '名称不能为空',
				maxlength: '最多不超过128个汉字' 
			} 
			
		}
	});
}

/**初始化表单事件*/
function initForm(){
	
	// 设定提交表单操作
	$("#btn_add_prd").click(function() {
		window.location.href = "/manage/AdviserMng/add";
	});
	$("#btn_assign_case").click(function() {
		window.location.href = "/manage/AdviserMng/appass/add";
	});
	// 设定返回操作
	$("#btnBack").click(function() {
		window.history.back();
	});
	
	// P2P 产品类型只能添加一个阶梯信息
	$("#prdType").change(function(){
		var value = $(this).val();
		var operationType = $("#operationType").val();
		
		if(null!=value && value == 0){//p2p
			$("#but").html("");
			//不显示是否为拆单信托 和银行卡信息
			$("#showIsSplitProductDiv").hide();
			$("#showOperationTypeDiv").show();
			$("#showIsSplitDiv").hide();
			
			if(operationType == 2){//站外购买
				$("#showPurchaseLandingDivPC").show();
				$("#showPurchaseLandingDivM").show();
			}
			
		}else{
			$("#but").html("添加");
			$("#showPurchaseLandingDivPC").hide();
			$("#showPurchaseLandingDivM").hide();
			$("#showIsSplitProductDiv").show();
			$("#showOperationTypeDiv").hide();
			$("#showIsSplitDiv").show();
		}
		if(value !=1 ||  value !=2){
			$("#contractInfo1").hide();
			$("#contractInfo2").hide();
		}
		
	});
	//站内购买时显示
	$("#operationType").change(function(){
		var value = $(this).val();
		if(null!=value && value == 1){//p2p站内购买
			$("#showIsSplitProductDiv").show();
			$("#showPurchaseLandingDivPC").hide();
			$("#showPurchaseLandingDivM").hide();
		}else{
			$("#showIsSplitProductDiv").hide();
			$("#showPurchaseLandingDivPC").show();
			$("#showPurchaseLandingDivM").show();
		}
	});
	$("#isSplit").change(function(){
		if($("#isSplit").val()==1 && ($("#prdType").val() == 1 || $("#prdType").val() == 2)){
			$("#contractInfo1").show();
			$("#contractInfo2").show();
		}else{
			$("#contractInfo1").hide();
			$("#contractInfo2").hide();
		}
	})
	
}

/** 查看 */
function show(prdId){
	window.location.href = '/manage/prd/show/' + prdId;
}

/** 修改 */
function modify(adviserId) {
	var actionUrl = $("#searchForm").attr("action");
	var pageSize = $("#pageSize").val();
	var pageNum = $("#pageNum").val();
	var searchText = $("#searchText").val();
	 
	var data = {url:actionUrl,pageSize:pageSize,pageNum:pageNum,
			searchText:searchText};
	var url = encodeURI(JSON.stringify(data));
	window.location.href = '/manage/AdviserMng/edit/'+ adviserId+'?url='+url;
}

/** 手动设置计息时间 */
function setFullScale(prdId) {
	var actionUrl = $("#searchForm").attr("action");
	var pageSize = $("#pageSize").val();
	var pageNum = $("#pageNum").val();
	var searchText = $("#searchText").val();
	var prdproType = $("#prdproType").val();
	var prdprvStatus = $("#prdprvStatus").val();
	var prdOperationType = $("#prdOperationType").val();
	var prdprvId = $("#prdprvId option:selected").val();
	var data = {url:actionUrl,pageSize:pageSize,pageNum:pageNum,
			searchText:searchText,prdproType:prdproType,prdprvStatus:prdprvStatus,
			prdOperationType:prdOperationType,prdprvId:prdprvId};
	var url = encodeURI(JSON.stringify(data));
	window.location.href = '/manage/prd/setFullScale/' + prdId+'?url='+url;
}

/** 手动设置投资人数和进度 */
function setProgress(prdId) {
	var actionUrl = $("#searchForm").attr("action");
	var pageSize = $("#pageSize").val();
	var pageNum = $("#pageNum").val();
	var searchText = $("#searchText").val();
	var prdproType = $("#prdproType").val();
	var prdprvStatus = $("#prdprvStatus").val();
	var prdOperationType = $("#prdOperationType").val();
	var prdprvId = $("#prdprvId option:selected").val();
	var data = {url:actionUrl,pageSize:pageSize,pageNum:pageNum,
			searchText:searchText,prdproType:prdproType,prdprvStatus:prdprvStatus,
			prdOperationType:prdOperationType,prdprvId:prdprvId};
	var url = encodeURI(JSON.stringify(data));
	window.location.href = '/manage/prd/setProgress/' + prdId+'?url='+url;
}


/** 删除 */
function del(prdId,name) {
	if(!confirmDelete(name)){
		return false;
	}
	window.location.href = '/manage/prd/delete/' + prdId;
}

/** 主站详情 */
function detail(prdId) {
	var homePage = $("#homePage").val();
	window.open(homePage + "/prod/" + prdId);
}


function initUpload() {
    $("#uploadfile").change(function() {
    	
    	/*var filename = $.trim($("#uploadfile").val().toLowerCase());
   	 	if(filename == ''){
   	 		$("#error_message").html("上传文件不能为空");
   	 		return false;
   	 	}
   	 	if(!/\.jpg|\.gif|\.png/.test(filename)){
	   		 alert("您只可以上传 jpg、gif 或 png文件，请重新上传图片");
	   		 return false;
   	 	}*/
   	 
		//document.getElementById("providerForm").action="/manage/prv/uploadProvLogo";
		$('#productForm').attr('action','/manage/prv/uploadProvLogo');
        $("#productForm").ajaxSubmit({
        	// beforeSubmit: validateImg,
            success: function(data) {
				$("#introFile").attr("value", "http://" + data);
				//document.getElementById("providerForm").action="/manage/prv/save";
            },
            error: function(data) {
            	alert("信托附件上传失败");
            }
        });
    });
}

//头上Tab页面切换
function initTabs() {
	var pathName=window.document.location.pathname;
	 
	if( pathName.indexOf("/manage/AdviserMng/org/") == 0 ){
		$("#tabOrg").addClass("active");
	} else if(pathName.indexOf("/manage/AdviserMng/appass/") == 0)
	{
		$("#tabAppass").addClass("active");
	}
	else if(pathName.indexOf("/manage/AdviserMng/adviserPer/") == 0){
		$("#tabAdvPer").addClass("active");
	} else{
		$("#tabAdv").addClass("active");
	}
}
/** 审核 */
function verify(prdId) {
	window.location.href = '/manage/prd/p2pmng/edit/' + prdId;
}
/** 忽略 */
function delDraft(prdId,name) {
	if(!confirmDeleteDraft(name)){
		return false;
	}
	window.location.href = '/manage/prd/p2pmng/delete/' + prdId;
}
/** 确认忽略提示 */
function confirmDeleteDraft(name){
	if(confirm("您确定要忽略产品名称是 "+name+" 的数据吗？")) return true;
	else return false;
}
/**进度审核*/
function verifyProgress(prdId) {
	window.location.href = '/manage/prd/p2pverify/edit/' + prdId;
}

function delDraftProgress(prdId,name) {
	if(!confirmDeleteDraft(name)){
		return false;
	}
	window.location.href = '/manage/prd/p2pverify/delete/' + prdId;
}

function initializeAction() {
	
	var checkbox = $("input[type='checkbox']");
	
    // 实现全选
    $('#btn_selectall').click(function() {
        checkbox.prop("checked", true);
    });
    
    // 实现反选
    $('#btn_reverse').click(function() {
        checkbox.prop("checked", function(index, attr) {
            return !attr;
        });
    });
    
    // 审核通过 
    $("#btn_export_file").click(function () {
        // get all selected req_ids
    	var selectStr = getSelectedItems(checkbox);
        if (selectStr.length == 0) {
        	alert('请先选审核数据！');
        	return;
        }
         
        if (!confirm('确定要把这些数据审核通过吗？')) {
        	return;
        }
        $("#prdIds").val(selectStr);
       
    	$("#ppsForm").submit();
        
    });
    
}

//get all selected items
function getSelectedItems(checkbox) {
	var selectStr = '';
	checkbox.each(function(i) {
		if ($(this).prop("checked")) {
			selectStr += $(this).val() + '|';
		}
    });
	if (selectStr.length > 0) {
		selectStr = selectStr.substr(0, selectStr.length -1);
	}
    return selectStr;
}
