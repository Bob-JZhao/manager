$(document).ready(function() {
	initTabs();
});

// 头上Tab页面切换
function initTabs() {
	var pathName = window.document.location.pathname;
	if (pathName.indexOf("/manage/livedaily/listview") == 0) {
		$("#tabLiveIndex").addClass("active");
	} else if (pathName.indexOf("/manage/livedaily/list/fund") == 0) {
		$("#tabLiveDailyFund").addClass("active");
	} else if (pathName.indexOf("/manage/livedaily/list/asset") == 0) {
		$("#tabLiveDailyAsset").addClass("active");
	}
}
function clearDate() {
	$("#dataStart").val('');
	$("#dataEnd").val('');
}

function downLoadSubmit(){
	if(confirm("确定要导出资金日报列表吗?")){
		var dataStart = $("#dataStart").val();
		var dataEnd = $("#dataEnd").val();
		var url = '/manage/livedaily/downLoad?dataStart='+dataStart
				+'&dataEnd='+dataEnd;
		window.open(url);
	}
}
