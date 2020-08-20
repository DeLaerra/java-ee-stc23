<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:useBean id="user" class="lesson22.pojo.User" />

<table class="table">
    <thead>
    <tr>
        <th>UUID</th>
        <th>Никнейм</th>
        <th>Пароль</th>
        <th>Email</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${users}">
        <tr>
            <td>${user.uuid}</td>
            <td>${user.nickname}</td>
            <td>${user.password}</td>
            <td>${user.email}</td>
            <td><a href="${pageContext.request.contextPath}/showuser?id=${user.uuid}">Подробнее...</a></td>
            <td><a href="${pageContext.request.contextPath}/edituser?id=${user.uuid}">Изменить</a></td>
            <td><form method="post" action="deleteuser" name=${user.uuid}>
                <input name="user_uuid" type="hidden" id=${user.uuid} value="${user.uuid}">
                <button type="submit" class="btn btn-primary">Удалить</button>
            </form></td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<br>
<a href="${pageContext.request.contextPath}/">На главную</a>