/**
 * @author zhangyx1
 * 实现多选控件.
 */
(function($) {
	$.combox = $.combox || {};
	$.extend($.combox, {
		version : "0.0.1",
		boxes : {},
		setBox : function(ele, box) {
			this.boxes[ele] = box;
		},
		getBox : function(ele){
			return this.boxes[ele];
		},
		delBox : function(ele) {
			delete this.boxes[ele];
		}
	});
	
	$.fn.combox = function(options){
		var _this = this;
		var id = this.attr("id");
		var box = $.combox.getBox(id);
		if(box) {
			return box;
		}
		var opts = $.extend(true, {}, $.fn.combox.defaults, options);
		generate(_this, opts);
		
		// 点击控件显示combox
		$(_this).click(function(e){
			opts.box.show();
		});
		
		box = {
			// 获取combox已选元素
			val : function(selected){
				if(selected){
					opts.selected = [].concat(selected);
					opts.curSelected = [].concat(selected);
					initSelected(opts);
					opts.onConfirm(opts.selected);
				} else {
					return opts.selected;
				}
			},
			//删除某个选中的元素
			remove : function(val){
				remove(opts.selected, val);
				remove(opts.curSelected, val);
			},
			// 清除combox已选元素
			clear : function() {
				opts.selected = [];
				opts.curSelected = [];
			},
			dispose : function(){
				destroyEvent(opts.box);
				$.combox.delBox(id);
				opts.box.remove();
			},
			// 显示combox
			showBox : function(){
				box.show();
			},
			// 隐藏combox
			hideBox : function(){
				box.hide();
			}
		};
		$.combox.setBox(id, box);
		return box;
	};
	
	//默认参数
	$.fn.combox.defaults = {
		box : null,
		head : null,
		rows : 4,			//行数
		cols : 4,			//列数
		pageNow : 0,		//当前页面
		totalPage : 0,		//总页数
		type : "local",		//数据来源类型，local，server
		url  : null,		//type为server时获取数据的URL
		data : [],			//数据
		selected : [],		//已选元素
		curSelected : [],	//已选元素
		onChecked : null,	//选择某个元素
		onUncheck : null,	//取消某个元素
		onConfirm : null,	//点击确定
		onCancel : null		//点击取消
	};
	
	//生成combox
	function generate(_this, opts) {
		initData(opts);
		var box = generateDom(_this, opts);
		initEvent(_this, box, opts);
	};
	
	//初始化数据
	function initData(opts) {
		if(opts.type == "server") {
			$.ajax({
				  url: opts.url,
				  dataType: 'json',
				  async : false,
				  type:'post',
				  success: function(data){
					  opts.data = data;
					  opts.totalPage = Math.ceil(opts.data.length/opts.cols/opts.rows);
				  },
				  error: function(xhr, type, exception) {
					  opts.data = [];
			      }
			});
		} else {
			opts.totalPage = Math.ceil(opts.data.length/opts.cols/opts.rows);
		}
	}
	
	//生成dom
	function generateDom(_this, opts){
		var box = $("<div style=\"width:560px;position: absolute; background-color: #FFFFFF;width:90%;border:1px solid rgba(0, 0, 0, 0.15);z-index: 1000;\"></div>").hide();
		// 多选元素
		var body = $("<table class=\"table table-condensed\" style=\"table-layout:fixed;word-break:break-all; word-wrap:break-all;\"></table>").appendTo(box);
		//分割线
		$("<hr style=\"margin: 5px; border-top:1px solid #000000\">").appendTo(box);
		var tail = $("<div class=\"form-group\"></div>").appendTo(box);
		var tailDiv = $("<div class=\"col-sm-4 col-sm-offset-8\" id=\"box_footer\"></div>").appendTo(tail);
		//确定按钮
		$("<input type=\"button\" class=\"btn btn-primary\" value=\"确定\">").appendTo(tailDiv);
		//取消按钮
		$("<input type=\"button\" class=\"btn btn-default\" value=\"取消\">").appendTo(tailDiv);
		
		// 生成多选列表
		var data = opts.data;
		var rows = opts.rows;
		var cols = opts.cols;
		var maxRows = Math.ceil(opts.data.length/cols);
		var rowsStart = opts.pageNow * rows;
		var rowsEnd = (opts.pageNow + 1) * rows;
		rowsEnd = (rowsEnd < maxRows ? rowsEnd : maxRows);
		var index = rowsStart * cols;
		for(var i=rowsStart; i<rowsEnd; i++) {
			var line = $("<tr></tr>").appendTo(body);
			for(var j=0; j<cols; j++, index++) {
				if(index < data.length) {
					$("<td><div class=\"checkbox\"><label><input type=\"checkbox\" index=" + index + " value=\"" + data[index].value + "\">" + data[index].text + "</label></div></td>").appendTo(line);
				} else {
					$("<td></td>").appendTo(line);
				}
			}
		}
		
		//页面
		var page = "<tr><td colspan=\"3\">";
		if(opts.pageNow > 0){
			page += "<a id=\"lastPage\" style=\"float:left;\">上一页</a>";
		}
		if(opts.pageNow < opts.totalPage - 1){
			page += "<a id=\"nextPage\" style=\"float:right;\">下一页</a>";
		}
		page += "</td></tr>";
		$(page).appendTo(body);
		$(_this).after(box);
		opts.box = box;
		initSelected(opts);
		return box;
	}
	
	function destroyEvent(box) {
		box.find("input[type=checkbox]").each(function(){
			this.onchange = null;
		});
		//点击确定事件
		box.find("input[value=确定]").off();
		//点击取消事件
		box.find("input[value=取消]").off();
		//上一页
		box.find("#lastPage").off();
		//下一页
		box.find("#nextPage").off();
	}
	
	// 注册事件
	function initEvent(_this, box, opts){
		// 处理checkbox选中事件
		box.find("input[type=checkbox]").each(function(){
			this.onchange = function(){
				var checked = this.checked;
				var index = $(this).attr("index");
				if(checked){
					opts.curSelected.push(opts.data[index]);
				} else {
					remove(opts.curSelected, opts.data[index].value);
				}
			};
		});
		
		//点击确定事件
		var confirm = box.find("input[value=确定]");
		confirm.on("click", function(){
			opts.selected = [].concat(opts.curSelected);
			box.hide();
			if(opts.onConfirm){
				opts.onConfirm(opts.selected);
			}
		});
		//点击取消事件
		var cancel = box.find("input[value=取消]");
		cancel.on("click", function(){
			opts.curSelected = [].concat(opts.selected);
			box.hide();
			if(opts.onCancel){
				opts.onCancel();
			}
		});
		//上一页
		var lastPage = box.find("#lastPage");
		lastPage.on("click", function(){
			if(opts.pageNow > 0){
				opts.pageNow -= 1;
				destroyEvent(box);
				box.remove();
				generateDom(_this, opts);
				initEvent(_this, opts.box, opts);
				opts.box.show();
			}
		});
		//下一页
		var nextPage = box.find("#nextPage");
		nextPage.on("click", function(){
			if(opts.pageNow < opts.totalPage - 1){
				opts.pageNow += 1;
				destroyEvent(box);
				box.remove();
				generateDom(_this, opts);
				initEvent(_this, opts.box, opts);
				opts.box.show();
			}
		});
		
		//设置点击空白处隐藏combox。
		$(document).mouseup(function(e){
		  if(!opts.box.is(e.target) && opts.box.has(e.target).length === 0){
			  opts.curSelected = [].concat(opts.selected);
			  initSelected(opts);
			  opts.box.hide();
		  }
		});
	}
	
	function initSelected(opts) {
		opts.box.find("input[type=checkbox]").each(function(){
			$(this).prop("checked", false);
		});
		//设置选中的元素
		opts.curSelected.forEach(function(e){
			opts.box.find("input[value="+e.value+"]").prop("checked",true);
		});
	}
	
	//删除某个元素
	function remove(array, val) {
		for(var i=0; i<array.length; i++) {
			if(array[i].value == val) {
				array.splice(i, 1);
				break;
			}
		}
	}
})($);