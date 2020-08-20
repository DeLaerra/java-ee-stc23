<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="myTags" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<myTags:template>
    <jsp:attribute name="header">
        <form method="post" action="editarticle" name=${article.article_uuid}>
                <input name="article_uuid" type="hidden" value="${article.article_uuid}">
            <h1><input type="text" name="article_header" value="${article.header}"></h1>
        (${article.date})

        <br>
        </jsp:attribute>
        <jsp:body>

            <br>
            <p><input type="text" name="article_text" value="${article.text}"></p>
            <button type="submit" class="btn btn-primary">Изменить</button>

        <br>
        <a href="${pageContext.request.contextPath}/">На главную</a>
            </form>
        </jsp:body>
</myTags:template>

