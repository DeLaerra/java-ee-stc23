<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
</head>
<body method="post" action="">
<p><%= request.getParameter("header") %></p>
<p><%= request.getParameter("date") %></p>
    <br>
    <p><%= request.getParameter("text") %></p>

    <br>
    <a href="${pageContext.request.contextPath}/">На главную</a>
</body>
</html>
