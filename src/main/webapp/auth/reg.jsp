<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="https://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet">
    <style type="text/css"><%@include file='/styles.css'%></style>
    <title>Registration</title>
</head>
<body>
<div id="body_content">
<form name="Reg" action="${pageContext.request.contextPath}/auth_servlet" method="post">
    <table id="users_table">
        <th>NAME</th>
        <th>PASSWORD</th>
        <th>REPEAT PASSWORD</th>
        <tr>
            <td>
                <input id="uname" name="user_name" type="text" value="">
            </td>
            <td>
                <input id="upass" name="user_password" type="password" value="">
            </td>
            <td>
                <input id="upassr" name="user_password_repeated" type="password" value="">
            </td>
        </tr>
        <tr>
            <td>
                <button type="button"
                        onclick="checkSubmit()">Confirm</button>
                <input name="button" type="hidden" value="confirm_reg">
            </td>
        </tr>
    </table>
</form>
</div>

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
 	if (1 == submitcount ) {
 		document.Reg.submit();
 	} else {
        alert("You have already submitted this form");
 	}
}
</script>

</body>
</html>
