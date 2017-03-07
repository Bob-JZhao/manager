/**初始化加载*/
$(document).ready(function() {
	initTabs();
});

//头上Tab页面切换
function initTabs() {
	var pathName=window.document.location.pathname;
	if (pathName.indexOf("/manage/ad/list/prv") == 0) {
		$("#tabPushPrv").addClass("active");
	} else if (pathName.indexOf("/manage/ad/list/info") == 0) {
		$("#tabPushInfo").addClass("active");
	} else if (pathName.indexOf("/manage/ad/list") == 0) {
		$("#tabPushProd").addClass("active");
	} else {
		$("#tabPushProd").addClass("active");
	}
}


    

    




