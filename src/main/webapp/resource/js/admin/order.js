 var fileName="";
 var dataurl="";
/**初始化加载*/
$(document).ready(function() {
	appendQueryFieldToPageForm();
	initTabs();
	$("#close").click(function(){
		$("#upload").hide();
	})
	

if(document.querySelector('#uploadLocalImgButton') !=null){
	document.querySelector('#uploadLocalImgButton').addEventListener('change', function () {
		$("#loading").show();
		var fileUrl=$("#uploadLocalImgButton").val();
		fileName = fileUrl.split("\\").pop();
		
	    lrz(this.files[0])
	        .then(function (rst) {
	            // 处理成功会执行
	            console.log(rst);
	            dataurl = rst.base64;
	            console.log(dataurl);
	            
	    		$("#preview").attr("src",dataurl);
	    		$("#preview").show();
	    		
	    		/*理财师个人中心移除上传按钮不能使用属性*/
	    		$("#confirm").removeAttr("disabled");
	    		$("#loading").hide();
	    		
	        })
	        .catch(function (err) {
	            // 处理失败会执行
	        })
	        .always(function () {
	            // 不管是成功失败，都会执行
	        });
	});
}
})
function viewBigImg(ordId){
	var imgUrl = $("#img"+ordId).attr("src");
	$("#viewBigImg").show();
	$("#bigImg").attr("src",imgUrl);
}
function closeBigImg(imgUrl){
	$("#viewBigImg").hide();
	$("#bigImg").attr("src",imgUrl);
}
function upload(prdId,usrId){
	$("#preview").attr("src","");
  	$("#upload").show();
	$("#ordId").val(prdId);
	$("#usrId").val(usrId);
  }

function appendQueryFieldToPageForm() {
	var searchText = $("#searchText").val();
	var inputSearchText = $("<input type='hidden' name='mobile' value='" + searchText + "' />")
    $("#order").append(inputSearchText);
	
}

//头上Tab页面切换
function initTabs() {
	var pathName=window.document.location.pathname;
	if (pathName.indexOf("/manage/ord/list/live") == 0) {
		$("#tabLiveOrd").addClass("active");
	} else if(pathName.indexOf("/manage/ord/list") == 0) {
		$("#tabOrd").addClass("active");
	} else if (pathName.indexOf("/manage/ord/reward") == 0) {
		$("#tabReward").addClass("active");
	}
	 else if (pathName.indexOf("/manage/ord/payfeedback") == 0) {
			$("#tabPayFeedback").addClass("active");
		}
}

function completeContract(ordId,usrId){
	
	if(!confirm("确定设置完成合同吗?")){
		return ;
	}
  	jQuery.ajax({
		url:'/manage/ord/list/callContract/'+ordId+'/'+usrId,
		type:'post',
		success:function(re){
			var json = eval("(" + re + ")");
			if(json.status==0){
				alert("状态修改失败");
			}else{
    			$("[name=b3"+ordId+"]").hide();
    			$("[name=b4"+ordId+"]").show();
    			$("#s"+ordId).html("已完成");
				
			}
		}
	});
  }


function completePay(ordId,prdId){
	if(!confirm("确定设置付款完成吗?")){
		return ;
	}
    var isDone = $("#status"+ordId).val();
  	jQuery.ajax({
		url:'/manage/ord/list/callPay/'+ordId+'/'+prdId,
		type:'post',
		success:function(re){
			var json = eval("(" + re + ")");
			if(json.status==0){
				alert("状态修改失败");
			}else{
				if(isDone == 1){
					$("[name=b1"+ordId+"]").hide();
					$("[name=b2"+ordId+"]").hide();
					$("[name=b4"+ordId+"]").show();
					$("#s"+ordId).html("已完成");
				}else{
					$("[name=b1"+ordId+"]").hide();
					$("[name=b2"+ordId+"]").hide();
					$("[name=b3"+ordId+"]").show();
					$("#s"+ordId).html("待签合同");
				}
			}
		}
	});
  }

function sendImage(){
	$("#loading").show();
	
	var ordId = $("#ordId").val();
	var usrId = $("#usrId").val();
	if(!fileName){
		alert("请选择图片");
		return ;
	}
    jQuery.ajax({
        url: "/manage/uploadCompress",
        data: "imageData="+dataurl.split(",")[1]+"&fileName="+fileName+"&ordId="+ordId+"&reqId="+ordId+"&usrId="+usrId,
        type: "POST",
		
		success:function(json){
		
			if(json.status=="false"){
				$("#message").text("图片上传出错");
			}else{
				$("#img"+ordId).attr("src","http://"+json.imgUrl);
				$("#img"+ordId).show();
				$("#upload").hide();
				$("[name=b2"+ordId+"]").show();
			}
			$("#loading").hide();
		},
		error:function(){
			alert("upload error");
			$("#loading").hide();
		}
	});
}
function requestReqId(){
	$.ajax({
	     type: "GET",
	     url: "/generateReqId",
	     data: { prefix: prefix},
	     dataType: "json",
	     success: function (data) {
	    	 reqId = data.result;
	    	 $("#REQ_ID").val(reqId);
	     }
	});
}

function downLoadSubmit(){
	var prdId = $("#prdId").val();
	var orderType = $("#orderType").val();
	var prdName = $("#prdName").val();
	var payMode = $("#payMode").val();
	var searchPrdName = $("#searchPrdName").val();
	var searchText = $("#searchText").val();
	var url = '/manage/ord/list/downLoad?prdId='+prdId
			+'&orderType='+orderType
			+'&prdName='+prdName
			+'&searchPrdName='+searchPrdName
			+'&searchText='+searchText
			+'&payMode='+payMode
	window.open(url);
}


function cancleOrder(ordId,mngId){
	if(!confirm("确定要取消该订单吗?")){
		return ;
	}
	 
    var isDone = $("#mess"+ordId).html();
  	jQuery.ajax({
		url:'/manage/ord/list/cancleOrder/'+ordId+'/'+mngId,
		type:'post',
		success:function(re){
			var json = eval("(" + re + ")");
			if(json.status==0){
				alert("订单取消失败");
			}else{
				$("[name=b1"+ordId+"]").hide();
				$("[name=b2"+ordId+"]").hide();
				$("[name=b3"+ordId+"]").show();
				$("#s"+ordId).html("订单已关闭");
				isDone = isDone.replace(/取消订单/g, "-") ;
				isDone = isDone.replace(/上传交易凭证/g, "");
				isDone = isDone.replace(/完成支付/g, "");
				isDone = isDone.replace(/完成合同/g, "");
				$("#mess"+ordId).html(isDone);
				 
			}
		}
	});
  }
