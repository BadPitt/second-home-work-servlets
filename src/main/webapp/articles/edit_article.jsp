<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/resources/head_styles.jsp"%>
    <title>Edit article</title>
</head>
<body>
<%@include file='/resources/header_template.jsp'%>
    <sf:form action="${pageContext.request.contextPath}/articles/"
             modelAttribute="article"
             method="post">
        <table>
            <th>
                <sf:input path="title"
                          type="text"
                          name="article_title" value="${article.getTitle()}"/>
            </th>
            <tr>
                <td>
                    <sf:textarea path="source"
                                 placeholder="${article.getSource()}"
                                 name="article_source"/>
                </td>
            </tr>
            <tr>
                <td>
                    <input type="submit" name="confirm_edit_article">
                </td>
                <td>
                    <sf:input path="id"
                              type="hidden"
                              name="article_id" value="${article.getId()}"/>
                    <input type="hidden" name="article_user_name" value="${article.getAuthor().getName()}">
                    <sf:input path="date"
                              type="hidden"
                              name="article_date" value="${article.getDate()}"/>
                    <sf:input path="updateDate"
                              type="hidden"
                              name="article_update_date"
                              value="${article.getUpdateDate()}"/>
                </td>
            </tr>
        </table>
    </sf:form>
</body>
</html>
