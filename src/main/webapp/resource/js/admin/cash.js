/**初始化加载*/
$(document).ready(function() {
	initTabs();
	initializeAction();
	appendQueryFieldToPageForm();
	initForm();
});

//头上Tab页面切换
function initTabs() {
	var pathName=window.document.location.pathname;
	if (pathName.indexOf("/manage/cash/file") == 0) {
		$("#tabCashFile").addClass("active");
	} else if (pathName.indexOf("/manage/cash/items") == 0) {
		$("#tabBatchItem").addClass("active");
	} else {
		$("#tabCashReq").addClass("active");
	}
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
    
    // 导出报盘文件 
    $("#btn_export_file").click(function () {
        // get all selected req_ids
    	var selectStr = getSelectedItems(checkbox);
        if (selectStr.length == 0) {
        	alert('请先选择提现申请！');
        	return;
        }
        // alert(selectStr);
        if (!confirm('确定要导出报盘文件吗？')) {
        	return;
        }
        $("#reqIds").val(selectStr);
    	
    	// request backend service
        $("#searchForm").attr("action", "/manage/cash/exportFile");
    	$("#searchForm").submit();
        
    });
    
    // 审核提现申请
    $("#btn_examine_through").click(function () {
        // get all selected req_ids
    	var selectStr = getSelectedItems(checkbox);
        if (selectStr.length == 0) {
        	alert('请先选择提现申请！');
        	return;
        }
//        alert(selectStr);
        if (!confirm('审核通过后，系统将会把提现金额转入用户的银行卡账户。确定要通过审核吗？')) {
        	return;
        }
        $("#reqIds").val(selectStr);
    	
    	// request backend service
        $("#searchForm").attr("action", "/manage/cash/cardAndPay");
    	$("#searchForm").submit();
        
    });
    
    // 拒绝提现请求 
    $("#btn_reject_req").click(function () {
    	// get all selected req_ids
    	var selectStr = getSelectedItems(checkbox);
        if (selectStr.length == 0) {
        	alert('请先选择提现申请！');
        	return;
        }
        // alert(selectStr);
        if (!confirm('确定要拒绝提现申请吗？')) {
        	return;
        }
        $("#reqIds").val(selectStr);
    	
    	// request backend service
        $("#searchForm").attr("action", "/manage/cash/rejectRequests");
    	$("#searchForm").submit();
    });
    
    // 搜索按钮
    $("#btn_search_req").click(function () {
    	$("#searchForm").attr("action", "/manage/cash/list");
    	$("#searchForm").submit();
    });   	
}

// get all selected items
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

function appendQueryFieldToPageForm() {
	var searchText = $("#searchText").val();
	var queryStatus = $("#queryStatus").val();
	//alert('searchText = ' + searchText + ', queryStatus = ' + queryStatus);
	var inputSearchText = $("<input type='hidden' name='searchText' value='" + searchText + "' />")
    var inputQueryStatus = $("<input type='hidden' name='queryStatus' value='" + queryStatus + "' />")
    $("#pageFormCash").append(inputSearchText);
	$("#pageFormCash").append(inputQueryStatus);
}

function initForm() {
	$("#createTimeStart").datetimepicker({
		startDate:new Date()-1,
		minuteStep:1
	});
	$("#createTimeEnd").datetimepicker({
		startDate:new Date(),
		minuteStep:1,
        seconds : 59
	});
}

// 显示报盘文件的明细
function showCashFile(fileId) {
	// alert('show cash file: ' + fileId);
	document.location.href = "/manage/cash/items/showfile?fileId=" + fileId;
}

// 下载报盘文件
function download(fileId) {
	// alert('download cash file: ' + fileId);
	document.location.href = "/manage/cash/file/download?fileId=" + fileId;
	
}
