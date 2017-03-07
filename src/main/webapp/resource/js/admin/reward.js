/**初始化加载*/
$(document).ready(function() {
	appendQueryFieldToPageForm();
	initTabs();
	initializeAction();
	
});

function appendQueryFieldToPageForm() {
	var searchText = $("#searchText").val();
	var selectVal = $("#prdStatus").val();
	var isReturn = $("#isReturn").val();
	//alert('searchText = ' + searchText + ', queryStatus = ' + queryStatus);
	var inputSearchText = $("<input type='hidden' name='prdId' value='" + searchText + "' />")
    var select = $("<input type='hidden' name='prdStatus' value='" + selectVal + "' />")
    var isReturnInput = $("<input type='hidden' name='isReturn' value='" + isReturn + "' />")
	$("#reward").append(inputSearchText);
	$("#reward").append(select);
	$("#reward").append(isReturnInput);
	
}

//头上Tab页面切换
function initTabs() {
	var pathName=window.document.location.pathname;
	if (pathName.indexOf("/manage/ord/list/reward") == 0) {
		$("#tabReward").addClass("active");
	}else if (pathName.indexOf("/manage/ord/list") == 0) {
		$("#tabOrd").addClass("active");
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
    //返本返息
    $("#btn_reward").click(function () {
        // get all selected req_ids
    	var selectStr = getSelectedItems(checkbox);
        if (selectStr.length == 0) {
        	alert('请先选择返本返息请求！');
        	return;
        }
        // alert(selectStr);
        if (!confirm('确定要返本返息吗？')) {
        	return;
        }
        $.ajax({
        	url:'/manage/ord/list/callReturn',
        	data:'acceptData='+selectStr,
        	type:'post',
        	success:function(r){
        		if(r.status == 'true'){
        			location.reload(true);
        		}else{
        			$("#tips").html(r.msg);
        			$("#tips").show();
        		}
        	}
        })
        
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