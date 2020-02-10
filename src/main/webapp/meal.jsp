<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html>
<head>
<link type="text/css"
    href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet" />
<title>Add new meal</title>
</head>
<body>
    <form method="POST" action='MealServlet' name="frmAddUser">
        ID : <input type="text" readonly="readonly" name="mealId"
            value="<c:out value="${meal.id}" />" /> <br />
        Дата/Время : <input
            type="text" name="dateTime"
            value="<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${meal.dateTime}" />" /> <br />
        Описание : <input
            type="text" name="description"
            value="<c:out value="${meal.description}" />" /> <br />
        Калории : <input type="text" name="calories"
            value="<c:out value="${meal.calories}" />" /> <br /> <input
            type="submit" value="Submit" />
    </form>
</body>
</html>