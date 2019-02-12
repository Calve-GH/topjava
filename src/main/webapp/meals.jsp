<%--
  Created by IntelliJ IDEA.
  User: Calve
  Date: 11.02.2019
  Time: 20:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://example.com/functions" prefix="f" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Show All Meals</title>
</head>
<body>
<table border=1>
    <thead>
    <tr>
        <th>Meal Id</th>
        <th>Meal Date</th>
        <th>Meal Description</th>
        <th>Meal Calories</th>
        <th colspan=2>Action</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${meals}" var="meal">
        <c:set var="color" value="${meal.excess ? 'red' : 'green'}" />
        <tr style="color:${color};">
            <td><c:out value="${meal.id}"/></td>
            <td><c:out value="${f:formatLocalDate(meal.dateTime, 'HH:mm dd.MM.yyyy')}"/></td>
            <td><c:out value="${meal.description}"/></td>
            <td><c:out value="${meal.calories}"/></td>
            <td><a href="meals?action=edit&id=<c:out value="${meal.id}"/>">Update</a></td>
            <td><a href="meals?action=delete&id=<c:out value="${meal.id}"/>">Delete</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<p><a href="meals?action=insert">Add Meal</a></p>
</body>
</html>