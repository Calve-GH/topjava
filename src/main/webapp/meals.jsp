<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .excess {
            color: red;
        }

        dl {
            background: none repeat scroll 0 0 #FAFAFA;
            margin: 8px 0;
            padding: 0;
        }

        dt {
            display: inline-block;
            width: 55px;
        }

        dd {
            display: inline-block;
            margin-left: 8px;
            vertical-align: top;
        }
    </style>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <h2>Meals</h2>
    <a href="meals?action=create">Add Meal</a>
    <hr/>
    <form method="get" action="meals">
        <input type="hidden" name="action" value="uid">
        <select name="user">
            <option value="USER1" id="1" selected>USER1
            <option value="ADMIN" id="2">ADMIN
            <option value="USER2" id="3">USER2
        </select>
        <input type="submit" value="change">
    </form>
    <form method="get" action="meals">
        <input type="hidden" name="action" value="filter">
        <table border="1" cellpadding="8" cellspacing="0">
            <tr>
                <td>
                    <dl>
                        <dt>DateStart:</dt>
                        <dd><input type="date" name="ds" value=""></dd>
                    </dl>
                </td>
                <td>
                    <dl>
                        <dt>DateEnd:</dt>
                        <dd><input type="date" value="" name="de"></dd>
                    </dl>
                </td>
            </tr>
            <tr>
                <td>
                    <dl>
                        <dt>TimeStart:</dt>
                        <dd><input type="time" value="" name="ts"></dd>
                    </dl>
                </td>
                <td>
                    <dl>
                        <dt>TimeEnd:</dt>
                        <dd><input type="time" value="" name="te"></dd>
                    </dl>
                </td>
            </tr>
            <tr>
                <td>
                </td>
                <td>
                    <input type="submit" value="Filter">
                </td>
            </tr>
        </table>
    </form>

    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealTo"/>
            <tr class="${meal.excess ? 'excess' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>