<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="https://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet">
    <style type="text/css"><%@include file='/styles.css'%></style>
    <title>${article.getTitle()}</title>
</head>
<body>
<%@include file='/header_template.jsp'%>
<form action="${pageContext.request.contextPath}/articles" method="post">
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
        <c:if test="${article.getAuthor().getName() eq sessionScope.login_id or sessionScope.is_admin}">
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
        </c:if>
    </table>
</form>
<c:forEach var="comment" items="${comments}">
<form action="${pageContext.request.contextPath}/articles" method="post">
    <table>
        <tr>
            <td>user: ${comment.getUser().getName()} date: ${comment.getFormattedDate()}</td>
        </tr>
        <tr>
            <td>
                <c:choose>
                    <c:when test="${pageContext.request.getAttribute(\"edit_comment_id\") eq comment.getId()}">
                        <input type="text" name="comment_source" value="${comment.getSource()}">
                    </c:when>
                    <c:otherwise>
                        ${comment.getSource()}
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
        <c:if test="${comment.getUser().getName() eq sessionScope.login_id or sessionScope.is_admin}">
        <tr>
            <td>
                <c:choose>
                    <c:when test="${pageContext.request.getAttribute(\"edit_comment_id\") eq comment.getId()}">
                        <button type="submit" name="button" value="confirm_edit_comment">Confirm edit</button>
                    </c:when>
                    <c:otherwise>
                        <button type="submit" name="button" value="edit_comment">Edit</button>
                    </c:otherwise>
                </c:choose>
            </td>
            <td>
                <button type="submit" name="button" value="delete_comment">Delete</button>
            </td>
            <td>
                <input type="hidden" name="article_id" value="${article.getId()}">
                <input type="hidden" name="comment_id" value="${comment.getId()}">
                <input type="hidden" name="user_name" value="${sessionScope.login_id}">
            </td>
        </tr>
        </c:if>
    </table>
</form>
</c:forEach>
<c:if test="${not empty sessionScope.login_id and sessionScope.is_active}">
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
</c:if>
</body>
</html>
