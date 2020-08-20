<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="myTags" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<myTags:template>
    <jsp:attribute name="header">
        <h1>Авторизация</h1>
    </jsp:attribute>
    <jsp:body>
        <form method="post" action="login" name=${user.uuid}>
            <label>Email:</label>
            <input type="text" name="user_email" value="${user.email}">
            <label>Пароль:</label>
            <input type="password" name="user_password" value="${user.password}">
            <button type="submit" class="btn btn-primary">Вход</button>
        </form>
    </jsp:body>
</myTags:template>