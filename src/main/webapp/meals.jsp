<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<html>
<head>
    <title>Meals</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table>
    <tr>
        <th>Дата/Время</th>
        <th>Описание</th>
        <th>Калории</th>
        <th colspan=2>Действие</th>
    </tr>

    <jsp:useBean id="mealsTo" scope="request" type="java.util.List"/>
    <c:forEach var="mealTo" items="${mealsTo}">
        <c:if test="${mealTo.excess == true}">
            <tr style="color: red">
        </c:if>
        <c:if test="${mealTo.excess == false}">
            <tr style="color: green">
        </c:if>
            <td style="border:1px solid gray;">${mealTo.dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))}</td>
            <td style="border:1px solid gray;">${mealTo.description}</td>
            <td style="border:1px solid gray;">${mealTo.calories}</td>
            <td><a href="MealServlet?action=edit&mealId=<c:out value="${mealTo.id}"/>">Update</a></td>
            <td><a href="MealServlet?action=delete&mealId=<c:out value="${mealTo.id}"/>">Delete</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
