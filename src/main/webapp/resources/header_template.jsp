<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<section id="section_header">
    <form action="${pageContext.request.contextPath}/auth/" method="post">
        <c:choose>
            <c:when test="${not empty sessionScope.login_id}">
                <button id="header_button"
                        type="submit"
                        name="logout"
                        class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent">
                    Logout
                </button>
            </c:when>
            <c:otherwise>
                <button id="header_button"
                        type="submit"
                        name="button"
                        value="login"
                        class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent">
                    Login
                </button>
            </c:otherwise>
        </c:choose>
        <c:if test="${not empty sessionScope.login_id}">
            <button id="header_change_password"
                    type="submit"
                    name="change_password"
                    class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent">
                Change password
            </button>
        </c:if>
    </form>
    <form action="${pageContext.request.contextPath}/users/" method="post">
        <c:if test="${not empty sessionScope.login_id and sessionScope.is_admin}">
            <button id="header_button"
                    type="submit"
                    name="button"
                    value="show_users"
                    class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent">
                Users
            </button>
        </c:if>
        <c:if test="${not empty sessionScope.login_id}">
            <t1 id="header_user_name">Hello, ${sessionScope.login_id}</t1>
        </c:if>
        <c:if test="${not sessionScope.is_active and not empty sessionScope.login_id}">
            <t1 id="header_blocked_message">You are blocked!</t1>
        </c:if>
    </form>
</section>