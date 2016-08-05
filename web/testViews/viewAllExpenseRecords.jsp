<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>View All Expense Reports</title>
</head>
<body>
<h1>View All Expense Reports</h1>
<br><br>
<table>
    <tr>
        <th>Expense ID</th>
        <th>Employee</th>
        <th>Amount</th>
        <th>Expense Date</th>
        <th>Expense Category Type</th>
    </tr>
    <c:forEach var="expense" items="${expenses}">   <tr>
        <td>
            <a href="/mvc_test/viewExpenseData?id=${expense.id}">
                <c:out value="${expense.id}" />
            </a>
        </td>
        <td><c:out value="${expense.employee}" /></td>
        <td><c:out value="${expense.amount}" /></td>
        <td><c:out value="${expense.expDate}" /></td>
        <td><c:out value="${expense.categoryType}" /></td>

    </tr>
    </c:forEach>
</table>
<br><br>
<a href="/">Home</a>
</body>
</html>
<!-- for every array tests variable "test" get array tests !-->
<!-- show me the id from tests!-->
<!-- show me the str from tests!-->