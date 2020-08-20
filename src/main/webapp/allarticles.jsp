<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:useBean id="article" class="lesson22.pojo.Article" />

<table class="table">
    <thead>
    <tr>
        <th>Заголовок</th>
        <th>Дата создания</th>
        <th>Подробнее..</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="article" items="${articles}">
        <tr>
            <td scope="row">${article.header}</td>
            <td>${article.date}</td>
            <td><a href="${pageContext.request.contextPath}/showarticle?id=${article.article_uuid}">Подробнее...</a></td>
            <td><a href="${pageContext.request.contextPath}/editarticle?id=${article.article_uuid}">Изменить</a></td>
            <td><form method="post" action="deletearticle" name=${article.article_uuid}>
                <input name="article_uuid" type="hidden" id=${article.article_uuid} value="${article.article_uuid}">
                <button type="submit" class="btn btn-primary">Удалить</button>
            </form></td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<br>
<a href="${pageContext.request.contextPath}/">На главную</a>
