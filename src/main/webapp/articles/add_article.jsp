<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/resources/head_styles.jsp" %>
    <title>New article</title>
</head>
<body>
<c:if test="${not empty sessionScope.login_id and sessionScope.is_active}">
    <%@include file='/resources/header_template.jsp'%>
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
                </td>
            </tr>
        </table>
    </sf:form>
</c:if>
</body>
</html>
