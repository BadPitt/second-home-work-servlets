<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="https://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet">
    <style type="text/css"><%@include file='/styles.css'%></style>
    <title>New article</title>
</head>
<body>
<c:if test="${not empty sessionScope.login_id and sessionScope.is_active}">
    <%@include file='/header_template.jsp'%>
    <form action="${pageContext.request.contextPath}/articles_servlet" method="post">
        <table id="article_table">
            <tr>
                <th>
                    <input type="text" name="article_title" value="">
                </th>
            </tr>
            <tr>
                <td>
                    <textarea name="article_source"></textarea>
                </td>
            </tr>
            <tr>
                <td>
                    <input type="submit" name="button" value="confirm_add_article">
                </td>
            </tr>
        </table>
    </form>
</c:if>
</body>
</html>
