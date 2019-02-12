<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://example.com/functions" prefix="f" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <title>Add new user</title>
</head>
<body>

<form method="POST" action='meals' name="frmMeal">
    Meal ID : <input type="text" readonly="readonly" name="id"
                     value="<c:out value="${mealTo.id}" />"/> <br/>
    Date : <input
        type="text" name="dateTime"
        value="<c:out value="${f:formatLocalDate(mealTo.dateTime, 'HH:mm dd.MM.yyyy')}" />"/> <br/>
    Description : <input
        type="text" name="description"
        value="<c:out value="${mealTo.description}" />"/> <br/>
    Calories : <input type="text" name="calories"
                      value="<c:out value="${mealTo.calories}" />"/> <br/> <input
        type="submit" value="Submit"/>
</form>
</body>
</html>