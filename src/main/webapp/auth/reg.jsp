<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/auth" method="post">
    <table>
        <th>NAME</th>
        <th>PASSWORD</th>
        <tr>
            <td>
                <input name="user_name" type="text" value="">
            </td>
            <td>
                <input name="user_password" type="text" value="">
            </td>
        </tr>
        <tr>
            <td>
                <button type="submit" name="button" value="confirm_reg">Confirm</button>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
