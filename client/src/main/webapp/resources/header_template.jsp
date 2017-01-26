<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<section id="section_header">
    <sec:authorize access="isAuthenticated()">
    <sec:authentication property="principal.username"
                             var="loginId" scope="request" />
    </sec:authorize>
        <sec:authorize access="hasAnyRole('ROLE_USER', 'ROLE_ADMIN')">
            <sf:form action="/auth/login" method="get">
                <button id="header_button"
                        type="submit"
                        name="logout"
                        class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent">
                    Logout
                </button>
            </sf:form>
            </sec:authorize>
            <sec:authorize access="hasRole('ROLE_ANONYMOUS')">
                <sf:form action="/auth/login">
                    <button id="header_button"
                            type="submit"
                            name="login"
                            class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent">
                        Login
                    </button>
                </sf:form>
            </sec:authorize>
            <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_USER')">
                <sf:form action="/auth/" >
                    <button id="header_change_password"
                            type="submit"
                            name="change_password"
                            class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent">
                        Change password
                    </button>
                </sf:form>
            </sec:authorize>
            <input type="hidden"
                   name="${_csrf.parameterName}"
                   value="${_csrf.token}"/>
        <sec:authorize access="hasRole('ROLE_ADMIN')">
    <sf:form action="${pageContext.request.contextPath}/users/" method="post">
            <button id="header_button"
                    type="submit"
                    name="button"
                    value="show_users"
                    class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent">
                Users
            </button>
    </sf:form>
        </sec:authorize>
        <c:if test="${not empty loginId}">
            <t1 id="header_user_name">Hello, ${loginId}</t1>
        </c:if>
        <%--<c:if test="${not sessionScope.is_active and not empty loginId}">
            <t1 id="header_blocked_message">You are blocked!</t1>
        </c:if>--%>
        <input type="hidden"
               name="${_csrf.parameterName}"
               value="${_csrf.token}"/>
    </form>
</section>