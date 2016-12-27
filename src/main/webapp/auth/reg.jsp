<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="https://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet">
    <style type="text/css"><%@include file='/styles.css'%></style>
    <title>Registration</title>
</head>
<body>
<div id="body_content">
<form action="${pageContext.request.contextPath}/auth" method="post">
    <table id="users_table">
        <th>NAME</th>
        <th>PASSWORD</th>
        <tr>
            <td>
                <input name="user_name" type="text" value="">
            </td>
            <td>
                <input name="user_password" type="password" value="">
            </td>
        </tr>
        <tr>
            <td>
                <button type="submit" name="button" value="confirm_reg">Confirm</button>
            </td>
        </tr>
    </table>
</form>
</div>
</body>
</html>
