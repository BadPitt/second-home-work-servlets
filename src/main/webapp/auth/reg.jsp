<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="https://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/material.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <title>Registration</title>
</head>
<body>
<%@include file='/header_template.jsp' %>
<form class="login-form" name="Reg" action="${pageContext.request.contextPath}/auth_servlet" method="post">
    <div id="body_content" class="mdl-card mdl-shadow--2dp">
        <div id="article_title" class="mdl-card__title mdl-card--expand">
            <h2 class="mdl-card__title-text">Registration</h2>
        </div>
        <div class="mdl-textfield mdl-js-textfield">
            <input name="user_name" class="mdl-textfield__input" type="text" id="uname">
            <label class="mdl-textfield__label" for="uname">Login...</label>
        </div>
        <div class="mdl-textfield mdl-js-textfield">
            <input name="user_password"
                   class="mdl-textfield__input"
                   type="password" id="upass"
                   pattern=".*?(([A-Z])+([0-9])+|([0-9])+([A-Z])+)+.*?">
            <label class="mdl-textfield__label" for="upass">Password...</label>
            <span class="mdl-textfield__error">Password must contain capital symbol and number!</span>
        </div>
        <div class="mdl-textfield mdl-js-textfield">
            <input name="user_password_repeated"
                   class="mdl-textfield__input"
                   type="password" id="upassr"
                   pattern=".*?(([A-Z])+([0-9])+|([0-9])+([A-Z])+)+.*?">
            <label class="mdl-textfield__label" for="upassr">Repeat password...</label>
            <span class="mdl-textfield__error">Password must contain capital symbol and number!</span>
        </div>
        <button type="button"
                class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent"
                onclick="checkSubmit()">Confirm
        </button>
        <input name="button" type="hidden" value="confirm_reg">
    </div>
</form>
<script type="text/javascript" src="${pageContext.request.contextPath}/styles/material.min.js"></script>
<script language="javascript">
    var submitcount = 0;

    function checkSubmit() {
        if (document.getElementById("uname").value != "" &&
            document.getElementById("upass").value != "" &&
            document.getElementById("upassr").value === document.getElementById("upass").value) {
            checkSubmitCount();
        } else {
            alert("Please, enter correct user name and password")
        }
    }

    function checkSubmitCount() {
        submitcount++;
        if (1 == submitcount) {
            document.Reg.submit();
        } else {
            alert("You have already submitted this form");
        }
    }
</script>
</body>
</html>
