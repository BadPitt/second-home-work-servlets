<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<section id="section_header">
    <form action="${pageContext.request.contextPath}/home" method="post">
        <c:choose>
            <c:when test="${not empty sessionScope.login_id}">
                <button id="header_log_button" type="submit" name="button" value="logout">Logout</button>
            </c:when>
            <c:otherwise>
                <button id="header_log_button" type="submit" name="button" value="login">Login</button>
            </c:otherwise>
        </c:choose>
    </form>
    <c:if test="${not empty sessionScope.login_id}">
        <t1 id="header_user_name">Hello, ${sessionScope.login_id}</t1>
    </c:if>
</section>