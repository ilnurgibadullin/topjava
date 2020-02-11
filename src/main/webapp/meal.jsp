<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html>
<head>
<link type="text/css"
    href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet" />
<title>Add new meal</title>
</head>
<body>
    <form method="POST" action="/meals" name="frmAddMeal">
        Дата/Время : <input type="text" name="dateTime"
            value="<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${mealTo.dateTime}" />" /> <br />
        Описание : <input type="text" name="description"
            value="<c:out value="${mealTo.description}" />" /> <br />
        Калории : <input type="text" name="calories"
            value="<c:out value="${mealTo.calories}" />" /> <br />
        <input type="submit" value="Submit" />
    </form>
</body>
</html>