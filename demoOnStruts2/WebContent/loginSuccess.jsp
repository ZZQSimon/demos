<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/sys.jsp"%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-81">
<title>列表</title>
</head>
<script type="text/javascript">
$(function() {
	
	DCIM.messager.alert("提示消息", '恭喜你，用户【${login_session_user}】登陆成功', "info", function() {
		refreshTabGrid();
	});
	
	$(".tab-addbutton").click(function(){
		var gridDataTabs = $('#tabsList').datagrid('getData');
// 		if(gridDataTabs.total>5){
// 			$.messager.alert('提示消息','没有可添加的标签!',"info");
// 			return;
// 		}
		var dataOption = parseOption(this);
		console.log(dataOption);
// 		if(dataOption.disable){
// 			return;
// 		}
		var width = dataOption.width_pop || "80%";
		var height = dataOption.height_pop || "60%";
		$("#dcim_dialogId").dialog({   
		    title: '添加',   
		    width: width,   
		    height: height,   
		    closed: false,   
		    cache: false,   
		    href: dataOption.url,
		    onLoad:function(){   
		    	$(".auto-savebutton").click(function(){bindSave(this);});
				$(".auto-resetbutton").click(function() {
					$(this).parents("form").form("reset");
				});
		    }, 
		    onClose:function(){
		    	$("#dcim_dialogId").html("");
		    	$(".auto-querybutton").click();
		    },
		    modal: true  
		});
	});
	
	$(".tab-delbutton").click(function(){
		var dataOption = parseOption(this);
		if(dataOption.disable){
			return;
		}
		var index = dataOption.index || "id";
		var checkedList = $(".easyui-datagrid").datagrid('getChecked');
		if(!checkedList.length){
			$.messager.alert('提示消息','请选择要删除的数据!',"info");
		}else{
			DCIM.messager.confirm('确认', '确定要删除么?', function(r){
				if (r){
						var param = {};
						var indexArray=[];
						$.each(checkedList,function(k,v){
							indexArray.push(v[index]);					
						});
						param[index] = indexArray.join(",");
						DCIM.post(dataOption.url, param,function(data) {
							if (data.message) {
								DCIM.messager.alert("提示消息", data.message, "info", function() {
									refreshTabGrid();
								});
							}
					});
				}
			});
		}
	});
	
});
function refreshTabGrid(){
	$('#tabsList').datagrid({    
	    url:'${pageContext.request.contextPath}/json/getUsers',
	    iconCls: 'icon-edit',
		singleSelect: false,
		toolbar: '#tb',
		pagination:true,
		rownumbers:true,
		fitColumns: true,
		pageSize:10,
		pageList: [1,2,5,10],
		collapsible:true,
		method: 'post'
	});
}
</script>
<body>
	<div id="tabsconfig" class="easyui-layout" style="width: 100%; height: 100%;">
	<div data-options="region:'north'" style="height: 28px; overflow: hidden;">
		<div class="easyui-panel" data-options="iconCls: 'icon-edit',border:false" title="人员列表"></div>
	</div>
	<div data-options="region:'center'">
		<table id="tabsList" class="easyui-datagrid" fit="true">
			<thead>
				<tr>
					<th data-options="field:'id',sortable:true,checkbox:true"></th>
					<th data-options="field:'username',width:'20%'">姓名</th>
					<th data-options="field:'password',width:'10%',sortable:true">密码</th>
				</tr>
			</thead>
		</table>
	</div>
	<div id="tb" style="height: auto">
		<a href="javascript:void(0)" class="easyui-linkbutton tab-addbutton" data-options="iconCls:'icon-add',plain:true,width_pop:'60%',height_pop:'55%',url:'${pageContext.request.contextPath}/add'">添加</a>
		<a href="javascript:void(0)" class="easyui-linkbutton auto-editbutton" data-options="iconCls:'icon-edit',plain:true,index:'id',width_pop:'60%',height_pop:'55%',url:'${pageContext.request.contextPath}/tabsCfg/u'">编辑</a>
		<a href="javascript:void(0)" class="easyui-linkbutton tab-delbutton" data-options="iconCls:'icon-remove',plain:true,index:'id',url:'${pageContext.request.contextPath}/tabsCfg/d'">删除</a>
	</div>
</div>
</body>
</html>