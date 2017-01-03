<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="https://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/material.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <title>Publishing IS</title>
</head>
<body>
<%@include file='header_template.jsp'%>
<div id="body_content">
    <p>
        <t1>Choose action</t1>
    </p>
<form action="${pageContext.request.contextPath}/auth_servlet" method="post">
    <p>
        <button type="submit"
                name="button"
                value="login"
                class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent">
            Login
        </button>
    </p>
</form>
<form action="${pageContext.request.contextPath}/articles_servlet" method="post">
    <p>
        <button type="submit"
                name="button"
                value="show_articles"
                class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent">
            Articles
        </button>
    </p>
</form>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/styles/material.min.js"></script>
</body>
</html>
