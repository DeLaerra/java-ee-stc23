<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="myTags" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<myTags:template>
    <jsp:attribute name="header">
        <h1>${article.header}</h1>
        (${article.date})

        <br>
    </jsp:attribute>
    <jsp:body>

        <br>
        <p>${article.text}</p>

        <br>
        <a href="${pageContext.request.contextPath}/">На главную</a>
    </jsp:body>
</myTags:template>



