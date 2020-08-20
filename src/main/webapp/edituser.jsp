<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="myTags" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<myTags:template>
    <jsp:attribute name="header">
        <h1>Изменение данных пользователя</h1>
                </jsp:attribute>
    <jsp:body>
    <form method="post" action="edituser" name=${user.uuid}>
        <input name="user_uuid" type="hidden" value="${user.uuid}">
        <label>Никнейм:</label>
        <input type="text" name="user_nickname" value="${user.nickname}">
        <label>Пароль:</label>
        <input type="text" name="user_password" value="${user.password}">
        <label>Email:</label>
        <input type="text" name="user_email" value="${user.email}">
        <button type="submit" class="btn btn-primary">Изменить</button>

        <br>
        <a href="${pageContext.request.contextPath}/">На главную</a>
        </form>
    </jsp:body>
</myTags:template>
