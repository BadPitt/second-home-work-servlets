<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="https://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet">
    <style type="text/css"><%@include file='/styles.css'%></style>
    <title>Users</title>
</head>
<body>
<c:if test="${not empty sessionScope.login_id and sessionScope.is_admin}">
    <%@include file='/header_template.jsp'%>
<div id="body_content">
    <table id="users_table">
        <th>ID</th>
        <th>NAME</th>
        <th>IS USER ADMIN</th>
        <th>IS USER ACTIVE</th>
        <c:forEach var="user" items="${users}">
            <form action="${pageContext.request.contextPath}/users_servlet" method="post">
                <tr>
                    <td>${user.getId()}</td>
                    <td>${user.getName()}</td>
                    <td>${user.isAdmin()}</td>
                    <td>${user.isActive()}</td>
                    <td>
                        <button type="submit" name="button" value="edit_user">Edit</button>
                    </td>
                    <td>
                        <button type="submit" name="button" value="delete_user">Delete</button>
                    </td>
                    <input type="hidden" name="user_id" value="${user.getId()}">
                    <input type="hidden" name="user_version" value="${user.getVersion()}">
                </tr>
            </form>
        </c:forEach>
        <form action="${pageContext.request.contextPath}/users_servlet" method="post">
            <tr>
                <td>
                    <button name="button" type="submit" value="add_user">Add new user</button>
                </td>
            </tr>
        </form>
    </table>
</div>
</c:if>
</body>
</html>
