<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/resources/head_styles.jsp"%>
    <title>Login</title>
</head>
<body>
<%@include file='/resources/header_template.jsp' %>
<sf:form class="login-form" method="post" modelAttribute="user">
    <div id="body_content" class="mdl-card mdl-shadow--2dp">
        <div id="article_title" class="mdl-card__title mdl-card--expand">
            <h2 class="mdl-card__title-text">Login</h2>
        </div>
        <div class="mdl-textfield mdl-js-textfield">
            <sf:input path="name"
                      name="user_name"
                      class="mdl-textfield__input"
                      type="text" id="login"/>
            <label class="mdl-textfield__label" for="login">Login...</label>
        </div>
        <div class="mdl-textfield mdl-js-textfield">
            <sf:input path="password"
                      name="user_password"
                      class="mdl-textfield__input"
                      type="password" id="password"/>
            <label class="mdl-textfield__label" for="password">Password...</label>
        </div>
        <button type="submit"
                name="confirm_login"
                class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent"
                style="margin-bottom: 20px;">Confirm</button>
        <button type="submit"
                name="reg"
                class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent">Registration</button>
    </div>
</sf:form>
</body>
</html>
