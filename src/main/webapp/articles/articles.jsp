<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="https://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/material.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <title>Articles</title>
</head>
<body>
<%@include file='/header_template.jsp'%>
<div class="articles-flex-container">
<c:forEach var="article" items="${articles}">
    <form action="${pageContext.request.contextPath}/articles_servlet" method="post">
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
                <button type="submit" name="button" value="view_more"
                        class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">
                    View more
                </button>
                <c:if test="${article.getAuthor().getName() eq sessionScope.login_id or sessionScope.is_admin}">
                    <button type="submit" name="button" value="edit_article"
                            class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">Edit</button>
                    <button type="submit" name="button" value="delete_article"
                            class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">Delete</button>
                </c:if>
                    <input type="hidden" name="article_id" value="${article.getId()}">
                    <input type="hidden" name="article_update_date" value="${article.getUpdateDate()}">
            </div>
        </div>
    </form>
</c:forEach>
    </div>
<c:if test="${not empty sessionScope.login_id and sessionScope.is_active}">
<form action="${pageContext.request.contextPath}/articles_servlet" method="post">
    <button id="write_article_static_button" type="submit" name="button" value="add_article"
            class="mdl-button mdl-js-button mdl-button--fab mdl-js-ripple-effect mdl-button--colored">
        <i class="material-icons">add</i>
    </button>
</form>
</c:if>
<script type="text/javascript" src="${pageContext.request.contextPath}/styles/material.min.js"></script>
</body>
</html>
