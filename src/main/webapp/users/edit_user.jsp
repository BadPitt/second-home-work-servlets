<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="https://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet">
    <style type="text/css"><%@include file='/styles.css'%></style>
    <title>Edit user</title>
</head>
<body>
<c:if test="${not empty sessionScope.login_id and sessionScope.is_admin}">
    <form action="${pageContext.request.contextPath}/users" method="post">
        <table>
            <th>NAME</th>
            <th>IS USER ADMIN</th>
            <th>IS USER ACTIVE</th>
            <tr>
                <td>
                    <input name="user_name" type="text" value="${user.getName()}">
                </td>
                <td>
                    <input name="user_is_admin" type="text" value="${user.isAdmin()}">
                </td>
                <td>
                    <input name="user_is_active" type="text" value="${user.isActive()}">
                </td>
            </tr>
            <tr>
                <td>
                    <button type="submit" name="button" value="confirm_edit_user">Save changes</button>
                </td>
            </tr>
        </table>
        <input name="user_id" type="hidden" value="${user.getId()}">
    </form>
</c:if>
</body>
</html>
