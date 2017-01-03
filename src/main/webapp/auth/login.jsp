<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="https://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/material.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <title>Login</title>
</head>
<body>
<%@include file='/header_template.jsp' %>
<form class="login-form" action="${pageContext.request.contextPath}/auth_servlet" method="post">
    <div id="body_content" class="mdl-card mdl-shadow--2dp">
        <div id="article_title" class="mdl-card__title mdl-card--expand">
            <h2 class="mdl-card__title-text">Login</h2>
        </div>
        <div class="mdl-textfield mdl-js-textfield">
            <input name="user_name" class="mdl-textfield__input" type="text" id="login">
            <label class="mdl-textfield__label" for="login">Login...</label>
        </div>
        <div class="mdl-textfield mdl-js-textfield">
            <input name="user_password"
                   class="mdl-textfield__input"
                   type="password" id="password">
            <label class="mdl-textfield__label" for="password">Password...</label>
        </div>
        <button type="submit"
                name="button"
                class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent"
                value="confirm_login" style="margin-bottom: 20px;">Confirm</button>
        <button type="submit"
                name="button"
                class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent"
                value="reg">Registration</button>
    </div>
</form>
<script type="text/javascript" src="${pageContext.request.contextPath}/styles/material.min.js"></script>
</body>
</html>
