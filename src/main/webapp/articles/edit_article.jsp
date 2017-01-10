<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="https://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/styles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/material.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <title>Edit article</title>
</head>
<body>
<c:if test="${not empty sessionScope.login_id and sessionScope.is_active}">
    <%@include file='/resources/header_template.jsp'%>
    <form action="${pageContext.request.contextPath}/articles_servlet" method="post">
        <table>
            <th>
                <input type="text" name="article_title" value="${article.getTitle()}">
            </th>
            <tr>
                <td>
                    <textarea name="article_source">${article.getSource()}</textarea>
                </td>
            </tr>
            <tr>
                <td>
                    <input type="submit" name="button" value="confirm_edit_article">
                </td>
                <td>
                    <input type="hidden" name="article_id" value="${article.getId()}">
                    <input type="hidden" name="article_user_id" value="${article.getAuthor().getId()}">
                    <input type="hidden" name="article_date" value="${article.getDate()}">
                    <input type="hidden" name="article_update_date" value="${article.getUpdateDate()}">
                </td>
            </tr>
        </table>
    </form>
</c:if>
<script type="text/javascript" src="${pageContext.request.contextPath}/styles/material.min.js"></script>
</body>
</html>
