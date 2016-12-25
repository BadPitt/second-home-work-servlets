<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${article.getTitle()}</title>
</head>
<body>
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
<c:forEach var="comment" items="${comments}">
<form action="${pageContext.request.contextPath}/articles" method="post">
    <table>
        <tr>
            <td>${comment.getUser().getName()}</td>
        </tr>
        <tr>
            <td>${comment.getSource()}</td>
        </tr>
        <tr>
            <td>${comment.getDate()}</td>
        </tr>
        <tr>
            <td>
                <button type="submit" name="button" value="edit_comment">Edit</button>
            </td>
            <td>
                <button type="submit" name="button" value="delete_comment">Delete</button>
            </td>
            <td>
                <input type="hidden" name="article_id" value="${article.getId()}">
                <input type="hidden" name="comment_id" value="${comment.getId()}">
            </td>
        </tr>
    </table>
</form>
</c:forEach>
<form action="${pageContext.request.contextPath}/articles" method="post">
    <table>
        <tr>
            <td><input type="text" name="comment_source" value=""></td>
        </tr>
        <tr>
            <td>
                <button type="submit" name="button" value="send_comment">Send comment</button>
                <input type="hidden" name="article_id" value="${article.getId()}">
                <input type="hidden" name="comment_id" value="${comment.getId()}">
                <input type="hidden" name="user_name" value="${sessionScope.login_id}">
            </td>
        </tr>
    </table>
</form>
</body>
</html>
