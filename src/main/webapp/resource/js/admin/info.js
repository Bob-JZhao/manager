
var titleRule = true;
$(function(){
	
	if($("#id").val().length>0){
		titleRule = false;
	}
	$("#title").change(function(){
		titleRule = true;
	})
})
function validateSubmit(){
	$("#tips").html("");
	var editor = UM.getEditor('infoText');//mini版本的editor
	if($("#title").val().length <= 0){
		$("#tips").html("请输入标题");
		return false;
	}
	if($.trim(editor.getContent()).length <= 0){
		$("#tips").html("请输入正文");
		return false;
	}
	
	if($("#preview").attr("src").length<=0){
		$("#tips").html("请选择封面图片");
		return false;
	}
	
	$("#infoForm").validate({
		debug: false,
		errorPlacement: function(error, element) {
			error.appendTo(element.parent("div"));
			error.css("color", "red");
		},
		rules: {
			title:{
				required: true,
				maxlength: 30
			},
			source: {
				required: true,
				maxlength: 10
			},
			sourceUrl: {
				required: true,
				url: true
			},
			artAbstract: {
				required: true,
				maxlength: 200
			},
			imageUrl: {
				required: true
			},

		},
        messages: {
			title:{
				required: '标题不能为空',
				maxlength: '最多不超过30个汉字'
			},
			source:{
				required: '来源不能为空',
				maxlength: '最多不超过10个汉字'
			},
			sourceUrl:{
				required: '原文链接不能为空',
				url: "请输入正确的url"
			},
			artAbstract:{
				required: '摘要不能为空',
				maxlength: '最多不超过200个汉字'
			},
			imageUrl:{
				required: '封面图片不能为空'
			}
        }
	})
	var result = false;
	var data = {infoContent:editor.getContent()};
	var url = "/manage/info/checkContentLength";
	if(titleRule){
		url = "/manage/info/check";
		data = {infoContent:editor.getContent(),title:$("#title").val(),artType:$("#artType").val()}
	}
	$.ajax({
		url:url,
		data:data,
		type:"post",
		async:false,
		dataType:"json",
		success:function(re){
			if(re['status']=="ok"){result = true;}
			else{$("#tips").html(re['msg']);}
		}
	})
	return result;

	
}

//下面用于图片上传预览功能
function setImagePreview() {
	
	$("#preview").attr("src","");
	$("#localImag").hide();
	if(/(.png|.jpg|.gif|.jpeg)$/.test($("#image").val())){}else{alert("请选择正确的图片");return false;}
	var width = $("#preview").attr("width");
	var height = $("#preview").attr("height");
	var docObj=document.getElementById("image");
	var imgObjPreview=document.getElementById("preview");
	var localImagId = document.getElementById("localImag");
	if(docObj.files &&docObj.files[0]){
		//火狐下，直接设img属性
		imgObjPreview.style.display = 'block';
		localImagId.style.display = 'block';
		imgObjPreview.style.width = width;
		imgObjPreview.style.height = height;

		//火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式
		imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);
		}
	else{
		//IE下，使用滤镜
		docObj.select();
		var imgSrc = document.selection.createRange().text;
		//必须设置初始大小
		localImagId.style.display = 'block';
		localImagId.style.width = width;
		localImagId.style.height = height;
		try{
			localImagId.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
			localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
		}catch(e){
			alert("您上传的图片格式不正确，请重新选择!");
			return false;
		}
		imgObjPreview.style.display = 'none';
		document.selection.empty();
	}
	return true;
}
function validatePushSubmit(){
	var position = $("#position").val();
	if(position.length<=0){$("#tips").html("推荐位置不能为空");return false;}
	if($("#articleId").val().length<=0){$("#tips").html("文章Id为空,请重新从下拉列表中选择文章");return false;}
	if($("#preview").attr("src").length<=0){$("#tips").html("图片不能为空");return false;}
	if($("#id").val().length<=0){
		var result = false;
		$.ajax({
			url:"/manage/ad/list/checkPosition/"+position,
			type:"get",
			async:false,
			dateType:"json",
			success:function(re){
				if(re['status']=="ok"){result = true;}
			}
		})
		$("#tips").html("推荐位置已存在，请推荐别的位置或去修改");
		return result;
	}
}