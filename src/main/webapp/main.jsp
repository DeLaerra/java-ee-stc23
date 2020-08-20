<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/date.tld" prefix="datetag" %>
<%@taglib prefix="myTags" tagdir="/WEB-INF/tags" %>

<myTags:template>
    <jsp:attribute name="header">
        <h1>Статьи</h1>
        (<datetag:DateTag plus="1"/>)
    </jsp:attribute>
    <jsp:body>
        <ul>
            <li><a href="${pageContext.request.contextPath}/allarticles">Список статей</a></li>
            <li><a href="${pageContext.request.contextPath}/addarticle">Добавить новую статью</a></li>
        </ul>
        <br>
        <h1>Пользователи</h1>
        <ul>
            <li><a href="${pageContext.request.contextPath}/person/list">Список пользователей</a></li>
            <li><a href="${pageContext.request.contextPath}/adduser">Добавить нового пользователя</a></li>
        </ul>
    </jsp:body>
</myTags:template>