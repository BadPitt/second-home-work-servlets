<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="https://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet">
    <spring:url value="/resources/styles.css" var="mystyles" />
    <spring:url value="/resources/styles/material.min.css" var="materialstyles" />
    <spring:url value="/resources/styles/material.min.js" var="materialjs" />
    <link rel="stylesheet" href="${mystyles}">
    <link rel="stylesheet" href="${materialstyles}"/>
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <script type="text/javascript" src="${materialjs}"></script>
    <title>Publishing IS</title>
</head>
<body>
<%@ include file="resources/header_template.jsp"%>
<div id="body_content">
    <p>
        <t1>Choose action</t1>
    </p>
<form action="${pageContext.request.contextPath}/auth/" method="post">
    <p>
        <button type="submit"
                name="button"
                value="login"
                class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent">
            Login
        </button>
    </p>
</form>
<form action="${pageContext.request.contextPath}/articles/" method="post">
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
</body>
</html>
