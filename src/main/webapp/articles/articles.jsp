<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Articles</title>
</head>
<body>
<c:forEach var="article" items="${articles}">
    <form action="${pageContext.request.contextPath}/articles" method="post">
        <table>
            <tr>
                <td>${article.getTitle()}</td>
            </tr>
            <tr>
                <td>${article.getSource()}</td>
            </tr>
            <tr>
                <td>${article.getAuthor().getName()}</td>
            </tr>
            <tr>
                <td>${article.getDate()}</td>
            </tr>
            <tr>
                <td>
                    <button type="submit" name="button" value="view_more">View more</button>
                </td>
                <td>
                    <button type="submit" name="button" value="edit_article">Edit</button>
                </td>
                <td>
                    <button type="submit" name="button" value="delete_article">Delete</button>
                </td>
                <td>
                    <input type="hidden" name="article_id" value="${article.getId()}">
                </td>
            </tr>
        </table>
    </form>
</c:forEach>
<form action="${pageContext.request.contextPath}/articles" method="post">
    <button type="submit" name="button" value="add_article">Write article</button>
</form>
</body>
</html>
