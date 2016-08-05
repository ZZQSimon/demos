//define(function() {

//常量定义
var maskId = "body";
//var maskId = "#mask";
var formId = "#formid";

var DCIM = function(selector, context) {
	return new DCIM.prototype.init(selector, context);
};

DCIM.prototype = {
	init : function(selector, context) {
		this.selector = selector;
		return this;
	},
	load : function(url, maskId, params, callback) {
		maskId = maskId || "body";
		$(maskId).showLoading();
		$(this.selector).load(url, params, callback);
		$(maskId).hideLoading();
	},
	post : function(url, data, callback, type) {
		
		if($.isFunction(data)){
			callback = data;
			data = "";
		}
		options = {
				url : url,
				type : "post",
				dataType : type,
				data : data|| $(this.selector).serialize(),
				success : callback|| defaultsuccess,
				complete:function(){$(maskId).hideLoading();}
			};
		// 表单验证
//		if (!$(this.selector).form('validate')) {
//			return;
//		}
		$(maskId).showLoading();
		$.ajax(url, options);
	}
};
// 引入jQuery
//DCIM.prototype = $.extend({}, $.prototype, DCIM.prototype);
// 原型对象覆盖init构造器的原型对象
DCIM.prototype.init.prototype = DCIM.prototype;

// DCIM常量定义
DCIM.statusCode = {
	ok : 200,
	error : 300,
	timeout : 301
};
DCIM.resource  = {
		jianzhu : 255,
		peidian : 256,
		caiji : 257
	};
DCIM.messager = $.messager;
var defaultsuccess = function(data) {
	if (data.message) {
		DCIM.messager.alert("提示消息", data.message, "info", function() {
			if (data.returnUrl) {
				location.href = "../" + data.returnUrl;
			}
		});
	}
};
var defaulterror = function(data) {
	DCIM.messager.alert("错误提示", "网络错误", "error");
};

DCIM.ajax = function(url, options) {

	if (typeof url === "object") {
		options = url;
		url = undefined;
	}
	options = options || {};
	options.success = options.success || defaultsuccess;
	options.error = options.error || defaulterror;
	options.data = options.data || $(formId).serialize();
	// 在回调函数执行前关闭遮罩层
	options.complete = function() {
		$(maskId).hideLoading();
	};

	// 表单验证
	if (!$(formId).form('validate')) {
		return;
	}
	$(maskId).showLoading();
	$.ajax(url, options);
};

DCIM.validate = function() {
	return $(formId).form('validate');
};
$.each([ "get", "post" ], function(i, method) {
	DCIM[method] = function(url, data, callback, type) {
		// shift arguments if data argument was omitted
		if ($.isFunction(data)) {
			type = type || callback;
			callback = data;
			data = undefined;
		}
		return DCIM.ajax({
			url : url,
			type : method,
			dataType : type,
			data : data,
			success : callback
		});
	};
});

//验证方法扩展
$.extend($.fn.validatebox.defaults.rules, {   
    minLength: {   
        validator: function(value, param){   
            return value.length >= param[0];   
        },   
        message: '请至少输入{0}个字符.'
    },   
    maxLength: {
    	validator: function(value, param){   
            return value.length <= param[0];   
        },
        message: '请最多输入{0}个字符.'
    },
    telephone: {   
    	validator: function(value, param){   
    		var r = /^0?1[3|4|5|8][0-9]\d{8}$|0\d{2,3}-\d{5,9}|0\d{2,3}-\d{5,9}/;
    		return r.test(value);   
    	},   
    	message: '请输入正确的电话号码.'  
    }, 
    naturalNumber: {   
    	validator: function(value, param){
    		var r = /^0$|^(\+?[1-9][0-9]*)$/;
    		return r.test(value);  
    	},   
    	message: '请输入非负整数.'  
    },
    number: {   
    	validator: function(value, param){
    		var r = /^\+?[1-9][0-9]*$/;
    		return r.test(value);   
    	},   
    	message: '请输入正整数.'  
    },
    alphabetic: {   
    	validator: function(value, param){
    		var r = /^[A-Za-z]+$/;
    		return r.test(value);   
    	},   
    	message: '请输入字母.'  
    },
    nospace: {   
    	validator: function(value, param){
    		var r = /^$| /;
    		return !r.test(value);   
    	},   
    	message: '不能包含空格.'  
    },
    
    float: {
    	validator: function(value, param){
    		var r = /^\+?\-?(([1-9]\d*)|([1-9]\d*\.[0-9]+)|([0-9]\.[0-9]+)|0)$/;
    		
    		if(param==null){
    			return r.test(value);
    		}else{
    			if(r.test(value)){
        			var splits = value.split('.'); 
        			if(splits.length == 1){
        				return value.length <= param[0] - param[1];
        			}else{
        				return (splits[0].length + splits[1].length) <= param[0] 
        							&& splits[0].length <= param[0] - param[1]
        							&& splits[1].length <= param[1];
        			}
        		}
        		return false;
    		}
    	},
    	message: '请输入浮点数.精度[{0},{1}]'
    }
});

//添加提示
$.extend(true, $.fn.datagrid.defaults.view, {
	onAfterRender:function(target){
		var grid = $(target);
		var data = $(target).data('datagrid');
		if (data) {
			var panel = grid.datagrid('getPanel').panel('panel');
			panel.find('.datagrid-body').each(function() {
				// 获得表格对象
				var delegateEle = $(this).find('> div.datagrid-body-inner').length ? 
					$(this).find('> div.datagrid-body-inner')[0] : this;
				// 单元格添加提示框
				$(delegateEle).find('td').each(function(){
					if($(this).text()){//判断单元格内容是否为空
						// easyui提示框定义
						$(this).tooltip({
							position : 'bottom',
							content : $(this).text(),
							showDelay : 1000
						});
					}
				});
			});
		}
	}
});

//公共方法
function parseOption(button){
	var dataOption=$.trim($(button).attr("data-options"));
	if(dataOption){
    	if(dataOption.substring(0,1)!="{"){
    		dataOption="{"+dataOption+"}";
    	}
    	dataOption = eval("(" + dataOption + ")");
    	}
	return dataOption;
}

function openDialog(button){
	var dataOption=parseOption(button);
	$(button).append("<div id='dcim_tmp_dialogId'></div>");
	$("#dcim_tmp_dialogId").dialog({   
	    title: dataOption.title||'标题',   
	    width: dataOption.width||"80%",   
	    height: dataOption.height||"60%",   
	    closed:dataOption.closed|| false,   
	    cache: dataOption.cache||false,   
	    href: dataOption.url,
	    onOpen:function(){   
	    	if($.isFunction(dataOption.onOpen)){
	    		dataOption.onOpen();
	    	}
	    }, 
	    onClose:function(){
	    	if($.isFunction(dataOption.onClose)){
	    		dataOption.onClose();
	    	}
	    	$("#dcim_tmp_dialogId").html("");
	    },
	    modal: true  
	});
}

function queryGrid(form,datagrid){
	var param = {};
	var a = $(form).serializeArray();
	$.each(a, function(i,val){   
		param[val.name] = val.value;
	  }); 
	$(datagrid).datagrid('load', param); 
}

function exportExcel(queryGrid,exportUrl){
	queryGrid = queryGrid||".easyui-datagrid";
	var options = $(queryGrid).datagrid('options');
	var columns = options.columns[0];	
	var param = jQuery.extend(true, {}, options.queryParams);
	var title = options.title;
	var url = exportUrl || options.url;
	
//	var data = $(queryForm).serializeArray();
//	var param = {};
//	
//	$.each(data, function(i,val){   
//		param[val.name] = val.value;
//	  }); 
	
	var paramCols = [];
	$.each(columns, function(i,val){ 
		if(val.hidden&&!val.export){return;}
		var paramCol = {};
		paramCol["field"] = val.field;
		paramCol["title"] = val.title;
		paramCols.push(paramCol);
	  }); 
	param["rowNames"] = paramCols;
	param["export"] = 1;
	param["title"] = title;
	//定义一个form表单
	var form=$("<form>");
	form.attr("style","display:none");
	form.attr("method","post");
	form.attr("action",url);
		
	$.each(param,function(i,val){
		var input=$("<input>");
		input.attr("type","hidden");
		input.attr("name",i);
		if(i=="rowNames"){
			input.attr("value",JSON.stringify(val));
		}else{
			input.attr("value",val);
		}
		form.append(input);
	});
	
		//将表单放置在web中
		$("body").append(form);
		form.submit();
}
	
	/**
	 * 格式化数值量
	 * 2016-04-18 11:51:06
	 * Add By neu.zhangzhq@neusoft.com
	 * 
	 * @param inValue
	 *            数值
	 * @param bits
	 *            小数位数
	 * @returns
	 */
	function toFixedByBits(inValue, bits) {
		if(bits==undefined||bits==null||bits=='null'||bits==''){//不格式化
			if(inValue==0){
				return inValue;
			}
			if(inValue == null || inValue == "" || inValue == "N/A") {
				return "N/A";
			}
			return inValue;
		}else if(bits==='0'){//整数
			if(inValue==0){
				return 0;
			}
			if(inValue == null || inValue == "" || inValue == "N/A") {
				return "N/A";
			}
			return parseFloat(inValue).toFixed();
		}else{//按位格式化
			if(inValue==0){
				return parseFloat(inValue).toFixed(bits);
			}
			if(inValue == null || inValue == "" || inValue == "N/A") {
				return "N/A";
			}
			return parseFloat(inValue).toFixed(bits);
		}
	}
	
	/**
	 * 根据颜色返回颜色十六进制编码
	 * @author neu.zhangzhq@neusoft.com
	 * @param color
	 * @returns {String}
	 */
	function getColorForDCIM(color){
		if(color=='red'||color=='r'){
			return '#FF5555';
		}else if(color=='green'||color=='g'){
			return '#00FF1A';
		}else if(color=='orange'||color=='o'){
			return '#FFB90F';
		}else if(color=='yellow'||color=='y'){
			return '#FFFF00';
		}else if(color=='purple'||color=='p'){
			return '#BF3EFF';
		}else if(color=='darkpurple'||color=='dp'){
			return '#BF3EFF';
		}else if(color=='blue'||color=='b'){
			return '#0000EE';
		}else if(color=='black'||color=='b'){
			return '#000000';
		}else if(color=='c1'||color=='c1'){
			return '#B2DFEE';
		}else if(color=='c2'||color=='c2'){
			return '#7B68EE';
		}else if(color=='c3'||color=='c3'){
			return '#48D1CC';
		}else{
			return '#CCCCCC';
		}
	}
	
	/**
	 * 加载时根据id值选择选项
	 * @author neu.zhangzhq
	 * @since 2016-05-09
	 * 
	 */
	function selectValue(e, type, value) {
		if (value != '') {
			if(type=='combobox'){
				$("#"+e).combobox('select', value);
			}else if(type=='combotree'){
				$("#"+e).combotree('setValue', value);
			}
		}
	}
	
	/**
	 * js实现MAP
	 * @returns
	 */
	function Map() {
		this.elements = new Array();
		//获取MAP元素个数
		this.size = function() {
			return this.elements.length;
		};
		//判断MAP是否为空
		this.isEmpty = function() {
			return (this.elements.length < 1);
		};
		//删除MAP所有元素
		this.clear = function() {
			this.elements = new Array();
		};
		//向MAP中增加元素（key, value)
		this.put = function(_key, _value) {
			this.elements.push({
				key : _key,
				value : _value
			});
		};
		//删除指定KEY的元素，成功返回True，失败返回False
		this.remove = function(_key) {
			var bln = false;
			try {
				for ( var i = 0; i < this.elements.length; i++) {
					if (this.elements[i].key == _key) {
						this.elements.splice(i, 1);
						return true;
					}
				}
			} catch (e) {
				bln = false;
			}
			return bln;
		};
		//获取指定KEY的元素值VALUE，失败返回NULL
		this.get = function(_key) {
			try {
				for ( var i = 0; i < this.elements.length; i++) {
					if (this.elements[i].key == _key) {
						return this.elements[i].value;
					}
				}
			} catch (e) {
				return null;
			}
		};
		//获取指定索引的元素（使用element.key，element.value获取KEY和VALUE），失败返回NULL
		this.element = function(_index) {
			if (_index < 0 || _index >= this.elements.length) {
				return null;
			}
			return this.elements[_index];
		};
		//判断MAP中是否含有指定KEY的元素
		this.containsKey = function(_key) {
			var bln = false;
			try {
				for ( var i = 0; i < this.elements.length; i++) {
					if (this.elements[i].key == _key) {
						bln = true;
					}
				}
			} catch (e) {
				bln = false;
			}
			return bln;
		};
		//判断MAP中是否含有指定VALUE的元素
		this.containsValue = function(_value) {
			var bln = false;
			try {
				for ( var i = 0; i < this.elements.length; i++) {
					if (this.elements[i].value == _value) {
						bln = true;
					}
				}
			} catch (e) {
				bln = false;
			}
			return bln;
		};
		//获取MAP中所有VALUE的数组（ARRAY）
		this.values = function() {
			var arr = new Array();
			for ( var i = 0; i < this.elements.length; i++) {
				arr.push(this.elements[i].value);
			}
			return arr;
		};
		//获取MAP中所有KEY的数组（ARRAY）
		this.keys = function() {
			var arr = new Array();
			for ( var i = 0; i < this.elements.length; i++) {
				arr.push(this.elements[i].key);
			}
			return arr;
		};
	}
	
	
	
	/**
	 * 对象数值比较函数
	 * 
	 * @author neu.zhangzhq
	 * @since2016-05-13
	 */
	function isObjectValueEqual(a, b) {
	    // Of course, we can do it use for in 
	    // Create arrays of property names
	    var aProps = Object.getOwnPropertyNames(a);
	    var bProps = Object.getOwnPropertyNames(b);
	 
	    // If number of properties is different,
	    // objects are not equivalent
	    if (aProps.length != bProps.length) {
	        return false;
	    }
	 	 
	    for (var i = 0; i < aProps.length; i++) {
	        var propName = aProps[i];
			if((typeof(a[propName])=='object')){
				if(typeof(a[propName])==typeof(b[propName])){
					return isObjectValueEqual(a[propName],b[propName]);
				}else{
					return false;
				}
			}
	        // If values of same property are not equal, objects are not equivalent
	        if (a[propName] !== b[propName]) {
	            return false;
	        }
	    }
	    // If we made it this far, objects are considered equivalent
	    return true;
	} 