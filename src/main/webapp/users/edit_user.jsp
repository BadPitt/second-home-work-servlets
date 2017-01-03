<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="https://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/material.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <title>Edit user</title>
</head>
<body>
<%@include file='/header_template.jsp' %>
<form name="Edit" class="login-form" action="${pageContext.request.contextPath}/users_servlet" method="post">
    <div id="body_content" class="mdl-card mdl-shadow--2dp">
        <div id="article_title" class="mdl-card__title mdl-card--expand">
            <h2 class="mdl-card__title-text">Edit user</h2>
        </div>
        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
            <input name="user_name"
                   class="mdl-textfield__input"
                   type="text"
                   id="uname"
                   value="${user.getName()}">
            <label class="mdl-textfield__label" for="uname">Name:</label>
        </div>
        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
            <input name="user_is_admin"
                   class="mdl-textfield__input"
                   type="text"
                   id="iuadmin"
                   value="${user.isAdmin()}">
            <label class="mdl-textfield__label" for="iuadmin">Is user admin:</label>
        </div>
        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
            <input name="user_is_active"
                   class="mdl-textfield__input"
                   type="text"
                   id="iuactive"
                   value="${user.isActive()}">
            <label class="mdl-textfield__label" for="iuactive">Is user active:</label>
        </div>
        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
            <input name="user_password"
                   class="mdl-textfield__input"
                   type="password"
                   id="upass"
                   value="">
            <label class="mdl-textfield__label" for="upass">Password:</label>
        </div>
        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
            <input name="user_password_repeated"
                   class="mdl-textfield__input"
                   type="password"
                   id="upassr"
                   value="">
            <label class="mdl-textfield__label" for="upassr">Repeat password:</label>
        </div>
        <button type="button"
                class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent"
                onclick="checkSubmit()">Save changes
        </button>
        <input name="button" type="hidden" value="confirm_edit_user">
        <input name="user_id" type="hidden" value="${user.getId()}">
        <input name="user_version" type="hidden" value="${user.getVersion()}">
    </div>
</form>
<script type="text/javascript" src="${pageContext.request.contextPath}/styles/material.min.js"></script>
<script language="javascript">
    var submitcount = 0;

    function checkSubmit() {
        if ((document.getElementById("uname").value == "" &&
            document.getElementById("upass").value == "") ||
            document.getElementById("upassr").value === document.getElementById("upass").value) {
            checkSubmitCount();
        } else {
            alert("Please, enter correct user name and password")
        }
    }

    function checkSubmitCount() {
        submitcount++;
        if (1 == submitcount) {
            document.Edit.submit();
        } else {
            alert("You have already submitted this form");
        }
    }
</script>
</body>
</html>
