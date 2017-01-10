<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/resources/head_styles.jsp" %>
    <title>Users</title>
</head>
<body>
<%@include file='/resources/header_template.jsp' %>
<div id="body_content">
    <table id="users_table" class="mdl-data-table mdl-js-data-table">
        <thead>
        <tr>
            <th>ID</th>
            <th>NAME</th>
            <th>IS USER ADMIN</th>
            <th>IS USER ACTIVE</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="user" items="${users}">
            <form method="post">
                <tr>
                    <td>${user.getId()}</td>
                    <td class="mdl-data-table__cell--non-numeric">${user.getName()}</td>
                    <td class="mdl-data-table__cell--non-numeric">${user.getIsAdmin()}</td>
                    <td class="mdl-data-table__cell--non-numeric">${user.getIsActive()}</td>
                    <td class="mdl-data-table__cell--non-numeric">
                        <button type="submit" name="edit_user">Edit</button>
                    </td>
                    <td class="mdl-data-table__cell--non-numeric">
                        <button type="submit" name="delete_user">Delete</button>
                    </td>
                    <input type="hidden" name="user_id" value="${user.getId()}">
                    <input type="hidden" name="user_version" value="${user.getVersion()}">
                </tr>
            </form>
        </c:forEach>
        </tbody>
        <form method="post">
            <button id="write_article_static_button" type="submit" name="add_user"
                    class="mdl-button mdl-js-button mdl-button--fab mdl-js-ripple-effect mdl-button--colored">
                <i class="material-icons">add</i>
            </button>
        </form>
    </table>
</div>
</body>
</html>
