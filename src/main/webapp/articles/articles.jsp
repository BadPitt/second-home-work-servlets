<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="https://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet">
    <style type="text/css"><%@include file='/styles.css'%></style>
    <title>Articles</title>
</head>
<body>
<%@include file='/header_template.jsp'%>
<c:forEach var="article" items="${articles}">
    <form action="${pageContext.request.contextPath}/articles_servlet" method="post">
        <table id="article_table">
            <tr>
                <td id="article_title">${article.getTitle()}</td>
            </tr>
            <tr>
                <td>${article.getSource()}</td>
            </tr>
            <tr>
                <td>Author: ${article.getAuthor().getName()} Date: ${article.getFormattedDate()}</td>
            </tr>
            <tr>
                <td>
                    <button type="submit" name="button" value="view_more">View more</button>
                </td>
                <c:if test="${article.getAuthor().getName() eq sessionScope.login_id or sessionScope.is_admin}">
                <td>
                    <button type="submit" name="button" value="edit_article">Edit</button>
                </td>
                <td>
                    <button type="submit" name="button" value="delete_article">Delete</button>
                </td>
                </c:if>
                <td>
                    <input type="hidden" name="article_id" value="${article.getId()}">
                    <input type="hidden" name="article_update_date" value="${article.getUpdateDate()}">
                </td>
            </tr>
        </table>
    </form>
</c:forEach>
<c:if test="${not empty sessionScope.login_id and sessionScope.is_active}">
<form action="${pageContext.request.contextPath}/articles_servlet" method="post">
    <button type="submit" name="button" value="add_article">Write article</button>
</form>
</c:if>
</body>
</html>
