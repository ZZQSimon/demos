<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-81">
<title>列表</title>
</head>
<body>
	<center>
		<form
			action="${pageContext.request.contextPath}/userAction.action"
			method="post" name="loginForm">
			用户名：<input type="text" name="user.username"><br> 密码：<input
				type="password" name="user.password"><br> <input
				type="submit" value="登陆" name="submit">
		</form>
	</center>
</body>
</html>