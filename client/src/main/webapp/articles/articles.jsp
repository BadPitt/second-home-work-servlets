<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/resources/head_styles.jsp"%>
    <title>Articles</title>
</head>
<body>
<%@include file='/resources/header_template.jsp'%>
<sec:authorize access="isAuthenticated()">
    <sec:authentication property="principal.username"
                        var="loginId" scope="request"/>
    <sec:authorize access="hasRole('ROLE_ADMIN')" var="isAdmin"/>
</sec:authorize>
<div class="articles-flex-container">
<c:forEach var="article" items="${articles}">
        <div id="article_card" class="demo-card-square mdl-card mdl-shadow--2dp">
            <div id="article_title" class="mdl-card__title mdl-card--expand">
                <h2 class="mdl-card__title-text">${article.getTitle()}</h2>
            </div>
            <div class="mdl-card__supporting-text">
                ${article.getSource()}
            </div>
            <div class="mdl-card__menu">
                <h1 class="mdl-card__subtitle-text">
                    Author: ${article.getAuthor().getName()}"
                </h1>
                <h1 class="mdl-card__subtitle-text">
                    Date: ${article.getFormattedDate()}
                </h1>
            </div>
            <div class="mdl-card__actions mdl-card--border" style="display: flex;padding-bottom: 0;">
                <form action="${pageContext.request.contextPath}/articles/">
                <button type="submit" name="view_more"
                        class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">
                    View more
                </button>
                    <input type="hidden" name="article_id" value="${article.getId()}">
                    <input type="hidden" name="article_update_date" value="${article.getUpdateDate()}">
                </form>
                <c:choose>
                <c:when test="${article.getAuthor().getName() eq loginId or isAdmin}">
                <form action="${pageContext.request.contextPath}/articles/" method="post">
                    <button type="submit" name="edit_article"
                            class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">Edit</button>
                    <button type="submit" name="delete_article"
                            class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">Delete</button>
                    <input type="hidden"
                           name="${_csrf.parameterName}"
                           value="${_csrf.token}"/>
                    <input type="hidden" name="article_id" value="${article.getId()}">
                    <input type="hidden" name="article_update_date" value="${article.getUpdateDate()}">
                    <input name="user_name"
                           type="hidden"
                           value="${article.getAuthor().getName()}"/>
                </form>
                </c:when>
                    <c:otherwise>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form>
</c:forEach>
    </div>
<c:if test="${not empty loginId}">
<form action="${pageContext.request.contextPath}/articles/" method="post">
    <button id="write_article_static_button" type="submit" name="add_article"
            class="mdl-button mdl-js-button mdl-button--fab mdl-js-ripple-effect mdl-button--colored">
        <i class="material-icons">add</i>
    </button>
    <input type="hidden"
           name="${_csrf.parameterName}"
           value="${_csrf.token}"/>
</form>
</c:if>
</body>
</html>
