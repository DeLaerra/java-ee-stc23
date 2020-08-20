<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="myTags" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<myTags:template>
    <jsp:attribute name="header">
        <h1>Добавление нового пользователя</h1>
                </jsp:attribute>
    <jsp:body>
        <form method="post" action="adduser" name=${user}>
            <label>Никнейм:</label>
            <input type="text" name="user_nickname" value="${user.nickname}">
            <label>Пароль:</label>
            <input type="text" name="user_password" value="${user.password}">
            <label>Email:</label>
            <input type="text" name="user_email" value="${user.email}">
            <button type="submit" class="btn btn-primary">Добавить</button>

            <br>
            <a href="${pageContext.request.contextPath}/">На главную</a>
        </form>
    </jsp:body>
</myTags:template>

