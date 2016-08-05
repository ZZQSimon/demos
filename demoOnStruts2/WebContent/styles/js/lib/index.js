$(function(){ 
	
	/* for collapsible content */
	$('.warning-wrapper a.warning-triger').click(function(){
		var trigBtn = $(this),
			collapsibleCont = trigBtn.siblings('.cont');
		
		if(trigBtn.hasClass('open')){
			trigBtn.removeClass('open');
			collapsibleCont.hide();
		}else{
			trigBtn.addClass('open');
			collapsibleCont.show();
		}
	});
	
	$("#sys_time_span").html(showLocale(new Date()));	
	
	//load nav
	$.post(WEB_APP+"/navigation/main-nav", function(data,status){
		var mainNavigation = "";	
		$.each(data,function(k,v){
				mainNavigation = mainNavigation + "<li>";			  
				mainNavigation = mainNavigation + "<a href=\"#\" id=\""+v.id+"\"><h1><img src=\"" + WEB_APP + v.imageUrl + "\"/></h1><h2>"+v.description+"</h2></a></li>";
		});

		$(".main-nav").html(mainNavigation);
	
		$('.main-nav > li').on('click',function(){
			$(".warning-wrapper").css("left","184px");
			$(this).addClass('active').siblings().removeClass('active');
			$('#outermost').showLoading();
			$("#outermost").load(WEB_APP+"/navigation/leftmenu?navigationId=" + $(this).find("a").attr("id"),function(){
				$('#outermost').hideLoading();
			});
		});
		$('.main-nav > li:first').addClass('active');
		$('.main-nav > li:first').click();
	});
	
		
	//load system alarm list.
	$("#bottom_alarm_list").load(WEB_APP+"/navigation/alarm",function(){
	    $(".dcim_alarm_table tr").mouseover(function(){
		$(this).addClass("over");}).mouseout(function(){
		$(this).removeClass("over");}) ;
		$(".dcim_alarm_table tr:even").addClass("alt");
	});
});  

function showLocale(objD)
{
	var str;
	var yy = objD.getYear();
	if(yy<1900) yy = yy+1900;
	var MM = objD.getMonth()+1;
	if(MM<10) MM = '0' + MM;
	var dd = objD.getDate();
	if(dd<10) dd = '0' + dd;
	var hh = objD.getHours();
	if(hh<10) hh = '0' + hh;
	var mm = objD.getMinutes();
	if(mm<10) mm = '0' + mm;
	var ss = objD.getSeconds();
	if(ss<10) ss = '0' + ss;
	var ww = objD.getDay();
	if  ( ww==0 )  colorhead="<font color=\"#FF0000\">";
	if  ( ww > 0 && ww < 6 )  colorhead="<font color=\"#373737\">";
	if  ( ww==6 )  colorhead="<font color=\"#008000\">";
	if  (ww==0)  ww="星期日";
	if  (ww==1)  ww="星期一";
	if  (ww==2)  ww="星期二";
	if  (ww==3)  ww="星期三";
	if  (ww==4)  ww="星期四";
	if  (ww==5)  ww="星期五";
	if  (ww==6)  ww="星期六";
	str =  yy + "-" + MM + "-" + dd + " " + hh + ":" + mm + ":" + ss + "  " + ww ;
	return(str);
}
function tick()
{
	var today;
	today = new Date();
	$("#sys_time_span").html(showLocale(today));
	window.setTimeout("tick()", 1000);
}
tick();


function loadContent(a_element){
	
	var socket_session_id =WEB_SOCKET_SESSION_ID;
	if($(a_element).attr("floor")=="false"){
		$("#dcim_floor").load(WEB_APP+"/navigation/loadFloor");
//		var socket_session_id =$(window.parent.document).contents().find("#dcim_main_content_02")[0].contentWindow.WEB_SOCKET_SESSION_ID;
		$.post(WEB_APP+"/navigation/handle", "target_desc="+a_element.innerHTML+"&sessionId="+socket_session_id, function(data) {
			$("#dcim_main_content_02").attr("src",WEB_APP+"/navigation/userContent?rte="+encodeURIComponent($(a_element).attr("tlink"))+"&resourceId="+$(a_element).attr("id"));
		}); 
	}else{
		$("#dcim_floor").load(WEB_APP+"/navigation/loadFloor?parentId="+$(a_element).attr("id"));
//		var socket_session_id =$(window.parent.document).contents().find("#dcim_main_content_02")[0].contentWindow.WEB_SOCKET_SESSION_ID;
		$.post(WEB_APP+"/navigation/handle", "target_desc="+a_element.innerHTML+"&sessionId="+socket_session_id); 
	}
	getTrack(a_element);
}

function getTrack(a_element){
	$(".location").children().children(":first").nextAll().remove();
	$.post(WEB_APP+"/auth/resource/getTrack", "id="+$(a_element).attr("id"), function(data) {
		$(".location").children().children(":first").after("<b>>>"+data[2].description+"</b><b>>>"+data[1].description+"</b><b>>>"+data[0].description+"</b>");
	});
	
}

function loadFloorContent(floor_element){
	$("#dcim_main_content_02").attr("src",WEB_APP+"/navigation/userContent?rte="+encodeURIComponent($(floor_element).attr("tlink"))+"&resourceId="+$(floor_element).attr("id"));
}

function referesh_dcim_alarm_summary_size(){
	var initial_dcim_alarm_summary_timeout = 5000;
	
	$.post(WEB_APP + '/dcim/alarm/getAlarmListSize', "", function(data) {
		$("#dcim_notice_summary").html(data);
	});
	
	window.setTimeout("referesh_dcim_alarm_summary_size()", initial_dcim_alarm_summary_timeout);
}
//referesh_dcim_alarm_summary_size();

function dcim_system_alarm_list(message) {
	console.log("add------" + jQuery.parseJSON(message).alarmId);
	$(mixAlarmLineInfo(jQuery.parseJSON(message))).insertBefore('#sys_bottom_list tr:first');
//	$('#system_empty').hide();
//	$.parser.parse("#sys_bottom_list");
	refreshStyles();
	$(".inTimeAlarm").click(inTimeAlarmClick);
}

function dcim_system_alarm_list_remove(message) {
	console.log("remove------" + message);
	$("#sys_bottom_list").children("tr[id='" + message + "']").remove();
	var trLength = $("#sys_bottom_list tr").length;
	if(trLength <= 5){
		refereshAlarm(trLength);
	}
}

function inTimeAlarmClick(){
	var dataOption = parseOption(this);
	var id = dataOption.id;
	
	$("#dcim_alarm_dialogId").dialog({
		title : '处理选中告警',
		width : "460px",
		height : "200px",
		closed : false,
		cache : false,
		method : "post",
		href : dataOption.url,
		onLoad : function() {
			$(".auto-savebutton").click(function() {
				bindSave(this, id);
			});
		},
		modal : true
	});
};

function mixAlarmLineInfo(obj){
	var alarmLevel = obj.alarmLevel==null?"":obj.alarmLevel;
	var asset = obj.assetName==null?"":obj.assetName;
	var alarmDescription = obj.alarmDescription==null?"":obj.alarmDescription;
	var alarmTime = obj.alarmStartTime==null?"":obj.alarmStartTime;
	var assetType = obj.assetType==null?"":obj.assetType;
	var alarmLocation = obj.locationName==null?"":obj.locationName;
//	var subSystem = obj.subSystem==null?"":obj.subSystem;
	if(alarmLevel=='一级'){
		alarmLevel='<td><div style="margin:0 auto;" class="yellow" title ="一级"></div></td>';
	}else if(alarmLevel=='二级'){
		alarmLevel='<td><div style="margin:0 auto;" class="orange" title ="二级"></div></td>';
	}else{
		alarmLevel='<td><div style="margin:0 auto;" class="alarmred" title ="三级"></div></td>';
	}
	
	return  "<tr id=\""+obj.alarmId+"\">"+alarmLevel+
			"<td><span class=\"easyui-tooltip\" title=\"" + asset + "\">" + asset + "</span></td>" +
			"<td><span class=\"easyui-tooltip\" title=\"" + alarmDescription + "\">" + alarmDescription + "</span></td>" +
			"<td><span class=\"easyui-tooltip\" title=\"" + alarmTime + "\">" + alarmTime + "</span></td>" +
			"<td><span class=\"easyui-tooltip\" title=\"" + assetType + "\">" + assetType + "</span></td>" +
			"<td><span class=\"easyui-tooltip\" title=\"" + alarmLocation + "\">" + alarmLocation + "</span></td>" +
//			"<td><span class=\"easyui-tooltip\" title=\"" + subSystem + "\">" + subSystem + "</span></td>" +
			"<td><a href=\"#\" class=\"inTimeAlarm\" data-options=\"id:'" + obj.alarmId
			+ "',url:'"+WEB_APP+"/alarmInfo/showHandleAlarm?ids="+obj.alarmId+"'\">处理</a></td></tr>";
}

function refereshAlarm(count){
	DCIM.post(WEB_APP + '/dcim/alarm/getAlarmList?count='+count, "", function(data) {
		if(data.length > 5){
			return;
		}
		for (var i=0;i<data.length;i++){
			$(mixAlarmLineInfo(data[i])).insertAfter('#sys_bottom_list tr:last');
		}
//		$('#system_empty').hide();
		refreshStyles();
		$(".inTimeAlarm").click(inTimeAlarmClick);
	});
}

//告警列表-表格每一列宽度重新计算，实现滚动条效果。
function refreshStyles(){
	var wraplen = $('.dcim_alarm_table thead tr th').length;
	var wrapnum = 100 / wraplen + '%';
	$('.dcim_alarm_table thead tr th').css('width', wrapnum);
	$('.dcim_alarm_table tbody tr td').css('width', wrapnum);	
}
