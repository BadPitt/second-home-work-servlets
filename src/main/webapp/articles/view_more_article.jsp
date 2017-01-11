<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/resources/head_styles.jsp"%>
    <title>${article.getTitle()}</title>
</head>
<body>
<%@include file='/resources/header_template.jsp'%>
<form action="${pageContext.request.contextPath}/articles/" method="post">
    <div class="articles-flex-container">
        <div id="article_card" class="demo-card-square mdl-card mdl-shadow--2dp">
            <div id="article_title" class="mdl-card__title mdl-card--expand">
                <h2 class="mdl-card__title-text">${article.getTitle()}</h2>
            </div>
            <div class="mdl-card__supporting-text">
                ${article.getSource()}
            </div>
            <div class="mdl-card__menu">
                <h1 class="mdl-card__subtitle-text">
                    Author: ${article.getAuthor().getName()}
                </h1>
                <h1 class="mdl-card__subtitle-text">
                    Date: ${article.getFormattedDate()}
                </h1>
            </div>
            <div class="mdl-card__actions mdl-card--border">
                <c:if test="${article.getAuthor().getName() eq sessionScope.login_id or sessionScope.is_admin}">
                    <button type="submit" name="edit_article"
                            class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">Edit</button>
                    <button type="submit" name="delete_article"
                            class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">Delete</button>
                </c:if>
                <input type="hidden" name="article_id" value="${article.getId()}">
                <input type="hidden" name="article_update_date" value="${article.getUpdateDate()}">
            </div>
        </div>
    </div>
</form>
<c:forEach var="comment" items="${comments}">
<form action="${pageContext.request.contextPath}/articles/" method="post">
    <div id="article_card" class="demo-card-square mdl-card mdl-shadow--2dp">
        <div class="mdl-card__supporting-text">
            <c:choose>
                <c:when test="${editCommentId eq comment.getId()}">
                    <input type="text"
                           name="comment_source"
                           value="${comment.getSource()}">
                </c:when>
                <c:otherwise>
                    ${comment.getSource()}
                </c:otherwise>
            </c:choose>
        </div>
        <div class="mdl-card__menu">
            <h1 class="mdl-card__subtitle-text">
                User: ${comment.getUser().getName()}
            </h1>
            <h1 class="mdl-card__subtitle-text">
                Date: ${comment.getFormattedDate()}
            </h1>
        </div>
        <div class="mdl-card__actions mdl-card--border">
            <c:if test="${comment.getUser().getName() eq sessionScope.login_id or sessionScope.is_admin}">
                <c:choose>
                    <c:when test="${editCommentId eq comment.getId()}">
                        <button type="submit"
                                class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect"
                                name="confirm_edit_comment">Confirm edit</button>
                    </c:when>
                    <c:otherwise>
                        <button type="submit"
                                class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect"
                                name="edit_comment">Edit</button>
                    </c:otherwise>
                </c:choose>
                <button type="submit"
                        name="delete_comment"
                        class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">Delete</button>
            </c:if>
            <input type="hidden" name="article_id" value="${article.getId()}">
            <input type="hidden" name="article_update_date" value="${article.getUpdateDate()}">
            <input type="hidden" name="comment_id" value="${comment.getId()}">
            <input type="hidden" name="comment_date" value="${comment.getDate()}">
            <input type="hidden" name="comment_update_date" value="${comment.getUpdateDate()}">
            <input type="hidden" name="user_name" value="${comment.getUser().getName()}">
        </div>
    </div>

</form>
</c:forEach>
<c:if test="${not empty sessionScope.login_id and sessionScope.is_active}">
<form action="${pageContext.request.contextPath}/articles/" method="post">
    <table>
        <tr>
            <td><input type="text" name="comment_source" value=""></td>
        </tr>
        <tr>
            <td>
                <button type="submit" name="send_comment">Send comment</button>
                <input type="hidden" name="article_id" value="${article.getId()}">
            </td>
        </tr>
    </table>
</form>
</c:if>
</body>
</html>
