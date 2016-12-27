<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="https://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet">
    <style type="text/css"><%@include file='styles.css'%></style>
    <title>Publishing IS</title>
</head>
<body>
<%@include file='header_template.jsp'%>
<div id="body_content">
    <p>
        <t1>Choose action</t1>
    </p>
<form action="${pageContext.request.contextPath}/auth" method="post">
    <p>
        <button type="submit" name="button" value="login">Login</button>
    </p>
</form>
<form action="${pageContext.request.contextPath}/articles" method="post">
    <p>
        <button type="submit" name="button" value="show_articles">Articles</button>
    </p>
</form>
</div>
</body>
</html>
