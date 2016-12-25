<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit article</title>
</head>
<body>
<c:if test="${not empty sessionScope.login_id and sessionScope.is_active}">
    <form action="${pageContext.request.contextPath}/articles" method="post">
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
                </td>
            </tr>
        </table>
    </form>
</c:if>
</body>
</html>
