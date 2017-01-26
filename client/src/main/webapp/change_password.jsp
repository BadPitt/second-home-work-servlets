<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/resources/head_styles.jsp" %>
    <title>Change password</title>
</head>
<body>
<%@include file='resources/header_template.jsp'%>
    <sf:form name="Change" class="login-form" action="${pageContext.request.contextPath}/auth/" method="post">
        <div id="body_content" class="mdl-card mdl-shadow--2dp">
            <div id="article_title" class="mdl-card__title mdl-card--expand">
                <h2 class="mdl-card__title-text">Change password</h2>
            </div>
            <div class="mdl-textfield mdl-js-textfield">
                <input name="user_password"
                       class="mdl-textfield__input"
                       type="password" id="upass"
                       pattern=".*?(([A-Z])+(.*?)*([0-9])+|([0-9])+(.*?)*([A-Z])+)+.*?">
                <label class="mdl-textfield__label" for="upass">Password...</label>
                <span class="mdl-textfield__error">Password must contain capital symbol and number!</span>
            </div>
            <div class="mdl-textfield mdl-js-textfield">
                <input name="user_password_repeated"
                       class="mdl-textfield__input"
                       type="password" id="upassr"
                       pattern=".*?(([A-Z])+(.*?)*([0-9])+|([0-9])+(.*?)*([A-Z])+)+.*?">
                <label class="mdl-textfield__label" for="upassr">Repeat password...</label>
                <span class="mdl-textfield__error">Password must contain capital symbol and number!</span>
            </div>
                    <button type="submit"
                            name="confirm_change_password"
                            class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent"
                            onclick="checkSubmit()">Save changes</button>
        <input name="user_id" type="hidden" value="${user.getId()}">
        <input name="user_version" type="hidden" value="${user.getVersion()}">
        </div>
    </sf:form>
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
            document.Change.submit();
        } else {
            alert("You have already submitted this form");
        }
    }
</script>
</body>
</html>
