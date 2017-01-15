<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/resources/head_styles.jsp" %>
    <title>New article</title>
</head>
<body>
    <%@include file='/resources/header_template.jsp'%>
    <sec:authentication property="principal.username"
                        var="name" scope="page"/>
    <sf:form action="${pageContext.request.contextPath}/articles/"
             modelAttribute="article"
             method="post">
        <table id="article_table">
            <tr>
                <th>
                    <sf:input path="title" type="text" name="article_title" value=""/>
                </th>
            </tr>
            <tr>
                <td>
                    <sf:textarea path="source" name="article_source"/>
                </td>
            </tr>
            <tr>
                <td>
                    <input type="submit" name="confirm_add_article">
                    <input type="hidden" name="user_name" value="${name}">
                </td>
            </tr>
        </table>
    </sf:form>
</body>
</html>
