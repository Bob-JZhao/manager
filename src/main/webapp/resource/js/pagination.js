//////////////////////////////////
/////   分页JS   //////////////////
//////////////////////////////////

function changePageSize(formId, pageNo) {
	var actionUrl = $("#" + formId).attr("action");
	redirectTo(formId, pageNo)
//	$("#" + formId).attr("action", actionUrl + "/" + pageNo);
//	$("#" + formId).submit();
}

function redirectToFirst(formId) {
	redirectTo(formId, 1);
}

function redirectToPrevious(formId) {
	var pageNo = $("#pageNum").val();
	pageNo = pageNo - 1;
	if (pageNo < 1) pageNo = 1;
    // redirect to specified page
	redirectTo(formId, pageNo);
}

function redirectToNext(formId, pageTotal) {
	var pageNo = $("#pageNum").val();
	pageNo = pageNo + 1;
	if (pageNo > pageTotal) pageNo = pageTotal;
	redirectTo(formId, pageNo);
}

function redirectTo(formId, pageNo) {
	var actionUrl = $("#" + formId).attr("action");
	$("#" + formId).attr("action", actionUrl + "/" + pageNo);
	$("#" + formId).submit();
}

function changePageSizeByPageNum(formId, pageNo, value) {
	$("#" + formId + " input[name=pageSize]").val(value);
	redirectTo(formId, 1)
}

function redirectToByPageNum(formId, pageNo) {
	var actionUrl = $("#" + formId).attr("action");
	$("#" + formId).attr("action", actionUrl + "/" + pageNo);
	$("#" + formId).submit();
}