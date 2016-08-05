<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>



<form name="newExpenseRecord" method="POST" action="/mvc_cust/addNewExpenseRecord">
    Employee Name: <input type="text" name="employee" /><br>
    Expense Amount: <input type="text" name="amount" /><br>
    Expense Date: <input type="text" name="expDate" /><br>
    Category Type: <select name="categoryType">
    <c:forEach var="categoryType" items="${categoryTypes}">
        <option value="<c:out value="${categoryType}"/>"><c:out value="${categoryType}"/></option></c:forEach>
</select><br>
    <input type="submit">
</form>

&nbsp;&nbsp;&nbsp;&nbsp;
<a href="/">Home</a>
<br>
<a href="/mvc_test/viewAllExpenseRecords">View All Expense Records</a>

</body>
</html>
