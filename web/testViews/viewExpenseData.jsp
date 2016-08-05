<%--
  Created by IntelliJ IDEA.
  User: perrythomson
  Date: 8/4/16
  Time: 12:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>View Expense Data</title>
</head>
<body>
<h1>View Expense Data</h1>
<br><br>
<table>
    <tr>
        <td>Expense ID:</td>
        <td><c:out value="${expense.id}" /></td>
    </tr>
    <tr>
        <td>Str:</td>
        <td><c:out value="${expense.employee}" /></td>
    </tr>
    <tr>
        <td>Str:</td>
        <td><c:out value="${expense.amount}" /></td>
    </tr>
    <tr>
        <td>Str:</td>
        <td><c:out value="${expense.expDate}" /></td>
    </tr>
    <tr>
        <td>Str:</td>
        <td><c:out value="${test.categoryType}" /></td>
    </tr>
</table>
<br><br>
<a href="/">Home</a>
&nbsp;&nbsp;&nbsp;&nbsp;
<a href="/mvc_test/viewAllExpenseRecords">View All Expense Records</a>
</body>
</html>

