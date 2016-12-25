<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Publishing IS</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/home" method="post">
    <c:if test="${not empty sessionScope.login_id}">
        <t1>Hello, ${sessionScope.login_id}</t1>
    </c:if>
    <p>
        <t1>Choose action</t1>
    </p>
    <c:if test="${not empty sessionScope.login_id and sessionScope.is_admin}">
        <p>
            <button type="submit" name="button" value="show_users">Users</button>
        </p>
    </c:if>
    <p>
        <button type="submit" name="button" value="login">Login</button>
    </p>
    <p>
        <button type="submit" name="button" value="show_articles">Articles</button>
    </p>
</form>
</body>
</html>
