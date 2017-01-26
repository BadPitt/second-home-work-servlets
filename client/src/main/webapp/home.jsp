<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/resources/head_styles.jsp"%>
    <title>Publishing IS</title>
</head>
<body>
<%@ include file="resources/header_template.jsp"%>
<div id="body_content">
    <p>
        <t1>Choose action</t1>
    </p>
<form action="${pageContext.request.contextPath}/auth/login">
    <p>
        <button type="submit"
                name="login"
                class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent">
            Login
        </button>
    </p>
</form>
<form action="${pageContext.request.contextPath}/articles/">
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
