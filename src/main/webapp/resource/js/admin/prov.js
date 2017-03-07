$(document).ready(function() {
	//initData();
	initSearch();
	initForm();
	initValidate();
	initTabs()
	//bindUpload();
});

function bindUpload(sessionId){
	$("#Up").click(function () {
        if ($("#fileQueue").html() == "") {
            $("#result").attr('style','color:red').html("请选择上传文件！");
        }else{
        	  $('#uploadify').uploadify('upload','*'); 
        }
    });

    $("#Clear").click(function () {
        $('#uploadify').uploadify('cancel','*');
    });


    $("#uploadify").uploadify({  
    	'swf':'/manage/resources/uploadify/uploadify.swf',
    	'uploader': '/manage/prv/uploadProviderLogo;jsessionid='+sessionId,
    	//'uploader': '/manage/prv/uploadProviderLogo',
        'buttonImg'      : '/manage/resources/uploadify/buttonImg.png',  
        'queueID'        : 'fileQueue',  
        'auto'           : false,  
        'multi'          : true,  
        'queueSizeLimit' : 999,  
        'fileTypeExts'        : '*.png;*.gif;*.jpg;*.bmp;*.jpeg',  
        'fileTypeDesc'       : '图片文件(*.png;*.gif;*.jpg;*.bmp;*.jpeg)',  
        'buttonText': '选择文件',
        'onClearQueue': function (event, data) {
            
        	$('#result').attr('style',"color:red").text('请选择要上传的文件');
        },
        'onUploadSuccess':function(file, data, response){  
        	             //  alert(data);  
         } , 
        'onQueueComplete':function(data)   
        {  
            $('#result').html(data.uploadsSuccessful  +'个图片上传成功'); 
        },
        'onSelectError':function(file, errorCode, errorMsg){
            switch(errorCode) {
                case -110:
                	 $('#result').html("文件 ["+file.name+"] 大小超出系统限制的" + jQuery('#uploadify').uploadify('settings', 'fileSizeLimit') + "大小！");
                    break;
                case -120:
                	 $('#result').html("文件 ["+file.name+"] 大小异常！");
                    break;
                case -130:

                	 $('#result').html("文件 ["+file.name+"] 类型不正确！");
                    break;
            }
        },
        onFallback:function(){
        	 $('#result').html('flash 不兼容点击<a href="https://get.adobe.com/flashplayer/?loc=cn" target="_blank">adobe flash player<a>进入官网下载或升级！');
        }
        
    });  

}
function initTabs() {
	var pathName=window.document.location.pathname;
	if( pathName.indexOf("/manage/prv/") == 0 ){
		$("#tabPrv").addClass("active");
	} else if(pathName.indexOf("/manage/prvGrade/") == 0)
	{
		$("#tabGrade").addClass("active");
	}
	 
}

//搜索控件支持回车操作
function initSearch() {
	$('#searchText').keydown(function(e){
		if(e.keyCode==13){
			var value = $.trim($(this).val());
			if (value != '') {
				$("#searchForm").submit();
			}
		}
	});
}

//初始化表单控件
function initForm() {
	// 设定提交表单操作
	$("#btn_add_prov").click(function() {
		window.location.href = "/manage/prv/add";
	});
	$("#btn_upload_prov").click(function(){
		window.location.href = "/manage/prv/addLogo";
	});
	// 设定返回操作
	$("#btnBack").click(function() {
		window.history.back();
	});
	
	$("#btn_add_grade").click(function() {
		window.location.href = "/manage/prvGrade/add";
	});
}


//初始化表单校验
function initValidate(){
	$("#providerForm").validate({
		// errorElement: "em",
		debug: false,
		errorPlacement: function(error, element) {
			error.appendTo(element.parent("div"));
			error.css("color", "#ff0000");
		},
		submitHandler: function(form) {
            form.submit();
        },
//		success: function(label) {
//			label.text("ok!").addClass("success");
//		},
		rules: {
			name: {
				required: true,
				maxlength: 128,
				remote:{
					type: "POST",  //数据发送方式
		            url: "/manage/prv/verifyName",
		            data:{
		            	id:function(){return $("#id").val();},
		            	name:function(){return $("#name").val();}
		            }
		        }
			},
			
			fullName: {
				required: true,
				maxlength: 256
			},
			code: {
				required: true,
				maxlength: 6,
				minlength: 3,
				remote:{
					type: "POST",  //数据发送方式
		            url: "/manage/prv/verifyCode",
		            data:{
		            	id:function(){return $("#id").val();},
		            	code:function(){return $("#code").val();}
		            }
		        }
			},
			registerCapital: {
				digits: true,
                range: [1,99999999]
			},
			startupTime: {
				required: true,
				maxlength:16 ,
				minlength:4
			}
		},
		messages: {
			name:{
				required: '名称不能为空',
				maxlength: '最多不超过128个汉字',
				remote: '名称不能重复'
			},
			fullName:{
				required: '全称不能为空',
				maxlength: '最多不超过256个汉字'
			},
			code : {
				required: 'code不能为空',
				maxlength: '最多不超过6个字符',
				minlength: '最少不低于3个字符' ,
				remote: 'CODE不能重复'
			},
			registerCapital: {
				digits: '只能填整数',
				range: '最多不超过8位整数'
			},
			startupTime: {
				required: '成立时间不能为空',
				maxlength: '最多不超过16个字符',
				minlength: '最少不低于4个字符' 
			}
		}
	});
	
	
	$("#gradeForm").validate({
		// errorElement: "em",
		debug: false,
		errorPlacement: function(error, element) {
			error.appendTo(element.parent("div"));
			error.css("color", "#ff0000");
		},
		submitHandler: function(form) {
            form.submit();
        },
		rules: {
			prvId: {
				required: true 
			},
			
			finalRate: {
				digits: true,
				required: true,
				maxlength: 2,
				range: [0,10]
			},
			scaleRate: {
				digits: true,
				required: true,
				maxlength: 2,
				range: [0,10]
			},
			returnRate: {
				digits: true,
				required: true,
				maxlength: 2,
				range: [0,10]
			},
			safeRate: {
				digits: true,
				required: true,
				maxlength: 2,
				range: [0,10]
			}
		},
		messages: {
			prvId:{
				required: '名称不能为空' 
				 
			},
			finalRate:{
				required: '请输入平台综合评价',
				maxlength: '长度最多不超过2位',
				range: '取值在1-10之间'
			},
			scaleRate:{
				required: '请输入规模指数',
				maxlength: '长度最多不超过2位',
				range: '取值在1-10之间'
			},
			returnRate:{
				required: '请输入收益指数',
				maxlength: '长度最多不超过2位',
				range: '取值在1-10之间'
			},
			safeRate:{
				required: '请输入安全指数',
				maxlength: '长度最多不超过2位',
				range: '取值在1-10之间'
			}
		}
	});
}

// edit provider modification page
function modify(provId) {
	window.location.href = '/manage/prv/edit/' + provId;
}

//show provider modification page
function show(provId) {
	//alert('show show');
	window.location.href = '/manage/prv/show/' + provId;
}

function consumeReport(provId) {
	alert("开发中......");
	//return false;
	//window.location.href = '/manage/prv/show/' + provId;
}

function modifygrade(id) {
	window.location.href = '/manage/prvGrade/edit/' + id;
}
//动态添加行
function addRow(current){
	var index=current+1;
	var row=$("<div class=\"form-group\" id=\"fg"+index+"\"><div class=\"col-sm-10\"><input type=\"file\" class=\"form-control\" id=\"prvlogo"+index+"\" name=\"prvlogo\"></div><input type=\"button\" class=\"btn btn-primary\" value=\"添加\" onclick=\"addRow("+index+")\">&nbsp;&nbsp; <input type=\"buttion\" class=\"btn btn-primary\" value=\"删除\" onclick=\"removeRow("+index+")\">&nbsp;&nbsp;</div>");
	var current=$("#fg"+current);
	current.after(row);
	//bind validate
	//$(row).rule();
}
//移除行
function removeRow(current){
	$("#fg"+current).remove();
}

/** 确认忽略提示 */
function confirmDelete(){
	if(confirm("您确定要删除该供应商的评级信息吗？")) return true;
	else return false;
}
 
 function change(){
	 var id = $("#prvId").val();
	 if(id == ""){
		 $("#error").html("请选择供应商").show();
		 return ;
	 }
	 $.ajax({
			type : "POST",
			url : '/manage/prvGrade/isProductGradeExist',
			data : {id:id },
			dataType : "json",
			async : false, 
			beforeSend : function(){
				$("#commError").html("正在提交中...").css("color","green");
			},
			 
			success : function(data){
				flag = eval(data);
				 if(data){
					 $("#error").html("应经存在").show();
				 }
				 else{
					 $("#error").hide();
				 }
				
			}
		});
 }

function saveGrade(){
	var id = $("#prvId").val();
	var form = $("#gradeForm");
	 if(id == ""){
		 $("#error").html("请 选择供应商").show();
		 return ;
	 }
	 else {
		 form.submit();
	 }

 
	 
}
function delgrade(id,prvId ) {
	if(!confirmDelete()){
		return false;
	}
	window.location.href = '/manage/prvGrade/delete/' + id+"-"+prvId;
}