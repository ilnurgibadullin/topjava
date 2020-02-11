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
    <form method="POST" action="meals" name="frmAddMeal">
        Дата/Время : <input type="datetime" name="dateTime" value="<fmt:parseDate value="${meal.dateTime}"
        pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
            <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${parsedDateTime}" />" /> <br />
        Описание : <input type="text" name="description" placeholder="Описание"
            value="<c:out value="${meal.description}" />" /> <br />
        Калории : <input type="text" name="calories" placeholder="1000"
            value="<c:out value="${meal.calories}" />" /> <br />
        <input type="submit" value="Submit" />
    </form>
</body>
</html>