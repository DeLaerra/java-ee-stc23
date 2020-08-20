<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/date.tld" prefix="datetag" %>

<jsp:useBean id="article" class="lesson22.pojo.Article" />

<h1>Добавление новой статьи</h1>
<form method="post" action="addarticle" autocomplete="off">
    <div class="form-group">
        <label for="header">Заголовок</label>
        <input name="header" type="text" class="form-control" id="header" value="">
    </div>
    <div class="form-group">
        <label for="text">Текст</label>
        <input name="text" type="text" class="form-control" id="text" value="">
    </div>
    <div class="form-group">
        <label for="date">Дата</label>
        <input name="date" type="date" class="form-control" id="date" value="<datetag:DateTag plus="1"/>">
    </div>
    <button type="submit" class="btn btn-primary">Submit</button>
</form>