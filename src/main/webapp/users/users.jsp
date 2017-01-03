<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="https://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/material.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <title>Users</title>
</head>
<body>
<%@include file='/header_template.jsp' %>
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
            <form action="${pageContext.request.contextPath}/users_servlet" method="post">
                <tr>
                    <td>${user.getId()}</td>
                    <td class="mdl-data-table__cell--non-numeric">${user.getName()}</td>
                    <td class="mdl-data-table__cell--non-numeric">${user.isAdmin()}</td>
                    <td class="mdl-data-table__cell--non-numeric">${user.isActive()}</td>
                    <td class="mdl-data-table__cell--non-numeric">
                        <button type="submit" name="button" value="edit_user">Edit</button>
                    </td>
                    <td class="mdl-data-table__cell--non-numeric">
                        <button type="submit" name="button" value="delete_user">Delete</button>
                    </td>
                    <input type="hidden" name="user_id" value="${user.getId()}">
                    <input type="hidden" name="user_version" value="${user.getVersion()}">
                </tr>
            </form>
        </c:forEach>
        </tbody>
        <form action="${pageContext.request.contextPath}/users_servlet" method="post">
            <button id="write_article_static_button" type="submit" name="button" value="add_user"
                    class="mdl-button mdl-js-button mdl-button--fab mdl-js-ripple-effect mdl-button--colored">
                <i class="material-icons">add</i>
            </button>
        </form>
    </table>
</div>
</body>
</html>
