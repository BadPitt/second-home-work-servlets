<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Users</title>
</head>
<body>
<c:if test="${not empty sessionScope.login_id and sessionScope.is_admin}">
    <table>
        <th>ID</th>
        <th>NAME</th>
        <th>IS USER AN ADMIN</th>
        <th>IS USER ACTIVE</th>
        <c:forEach var="user" items="${users}">
            <form action="${pageContext.request.contextPath}/users" method="post">
                <tr>
                    <td>${user.getId()}</td>
                    <td>${user.getName()}</td>
                    <td>${user.isAdmin()}</td>
                    <td>${user.isActive()}</td>
                    <td>
                        <button type="submit" name="button" value="edit_user">Edit</button>
                        <input type="hidden" name="user_id" value="${user.getId()}">
                    </td>
                    <td>
                        <button type="submit" name="button" value="delete_user">Delete</button>
                        <input type="hidden" name="user_id" value="${user.getId()}">
                    </td>
                </tr>
            </form>
        </c:forEach>
        <form action="${pageContext.request.contextPath}/users" method="post">
            <tr>
                <td>
                    <button name="button" type="submit" value="add_user">Add new user</button>
                </td>
            </tr>
        </form>
    </table>
</c:if>
</body>
</html>
