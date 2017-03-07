/**初始化加载*/
$(document).ready(function() {
	appendQueryFieldToPageForm();
	initForm();
});

function appendQueryFieldToPageForm() {
	var searchText = $("#searchText").val();
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	//alert('searchText = ' + searchText + ', queryStatus = ' + queryStatus);
	var inputSearchText = $("<input type='hidden' name='searchText' value='" + searchText + "' />")
    var inputStartTime = $("<input type='hidden' name='startTime' value='" + startTime + "' />")
    var inputEndTime = $("<input type='hidden' name='endTime' value='" + endTime + "' />")
    $("#cps").append(inputSearchText);
	$("#cps").append(inputStartTime);
	$("#cps").append(inputEndTime);
	
}

function initForm() {
	$("#startTime").datetimepicker({
		startDate:new Date()-1,
		minuteStep:1
	});
	$("#endTime").datetimepicker({
		startDate:new Date(),
		minuteStep:1,
        seconds : 59
	});
}



