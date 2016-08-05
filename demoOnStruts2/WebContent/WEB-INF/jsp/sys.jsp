<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/css/easyui/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/css/easyui/icon.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/css/showLoading.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/css/jquery.mCustomScrollbar.css">     

<script>
	var WEB_APP="${pageContext.request.contextPath}";
	var LOGIN_SESSION_USER="${login_session_user}";
	var LOGIN_SESSION_STYLE = "${login_session_style}";
</script>

<script type="text/javascript" src="${pageContext.request.contextPath}/styles/js/lib/jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/styles/js/lib/easyui.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/styles/js/lib/jquery.mCustomScrollbar.concat.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/styles/js/lib/jquery.showLoading.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/styles/js/lib/simons.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/styles/js/lib/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/styles/js/lib/DateUtil.js"></script>