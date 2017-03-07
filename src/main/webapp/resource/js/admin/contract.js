$(function(){
	$("#btnBack").click(function(){
		var pageNum = $("#pageNum").val();
		if(pageNum==""){pageNum = 1;}
		location.href="/manage/prd/contract/list/"+pageNum+"?searchText="+$("#searchText").val();
	})
	$("#name").change(function(){
		$("#prdId").val("");
		$("#prdType").val("");
		$("#type").val("");
		$("#prdType").val("");
	})
	$("#name").focus(function(){
		$("#options").hide();
		$("#container").hide();
		
	})
				
	
})

function reconfirm(name,type,id,prdType){
	$("#name").val(name);
	$("#type").val(type);
	$("#prdType").val(prdType);
	$("#prdId").val(id);
	$("#options").empty();
	$("#container").hide();
	checkProdContract(id);
}
	//验证产品长度
	function checkLength(){
		var result = false;
		$("#tips").html("");
		$.ajax({
			url:"/manage/prd/contract/checkLength",
			data:"contractText="+$("#contractTxet").html(),
			type:"post",
			dataType:"json",
			async:false,
			success:function(e){
				if(e.status=="false"){
					$("#tips").html("合同内容长度超过限制20000，或许是因为粘贴的内容含有太多样式代码");
					return false;
				}
				result = true;
				return true;
			},
			error:function(){
				$("#tips").html("请求服务器出错....");
				return false;
			}
		})
		return result;
	}
	//验证产品id和产品type
	function checkProdInfo() {
		$("#tips").html("");
		if($("#prdId").val()=="" || $("#type").val()==""){
			console.log("产品信息，产品名字请从下拉列表中选择");
			$("#tips").html("产品信息，产品名字请从下拉列表中选择");
			return false;
		}

		return true;
	}
	//判断产品是否已有合同
	function checkProdContract(pid){
		$("#tips").html("");
		var result = false;
		jQuery.ajax({
			url:"/manage/prd/contract/have/"+pid,
			type:"get",
			dataType:"json",
			async:false,
			success:function(e){
				if(e.status=="false"){
					$("#tips").html("该产品已有合同");
					return false;
				}
				result = true;
				return true;
			},
			error:function(){
				console.log("请求服务器出错....");
			}
		})
		return result;
	}