<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
    <%@ include file="/resources/head_styles.jsp" %>
    <title>Edit user</title>
</head>
<body>
<%@include file='/resources/header_template.jsp' %>
<sf:form name="Edit" class="login-form" method="post">
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
                   value="${user.getAuthorites()}">
            <label class="mdl-textfield__label" for="iuadmin">User's role:</label>
        </div>
        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
            <input name="user_is_active"
                   class="mdl-textfield__input"
                   type="text"
                   id="iuactive"
                   value="${user.getIsActive()}">
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
        <input name="confirm_edit_user" type="hidden">
        <input name="user_id" type="hidden" value="${user.getId()}">
        <input name="user_version" type="hidden" value="${user.getVersion()}">
    </div>
</sf:form>
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
