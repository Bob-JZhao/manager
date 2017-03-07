/**初始化加载*/
$(document).ready(function() {
	initTabs();
});

//头上Tab页面切换
function initTabs() {
	var pathName=window.document.location.pathname;
	if (pathName.indexOf("/manage/prd/contract/list") == 0) {
		$("#tabContract").addClass("active");
	}
}
