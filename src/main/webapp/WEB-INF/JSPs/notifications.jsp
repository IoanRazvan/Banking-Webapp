<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bat" uri="/WEB-INF/bankingApplicationTags.tld" %>
<%@ taglib prefix="fct" tagdir="/WEB-INF/tags" %>
<html>
<head>
    <title>Notifications</title>
    <link rel="stylesheet" href="<c:url value='/styles/menu.css'/>" type="text/css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">
</head>
<body>
<%@ include file="sidebarMenuInclude.jsp" %>
<div class="content">
    <div class="container">
        <h1>Notifications</h1>
        <table class="table">
            <thead>
            <tr>
                <th>Notification Message</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="d" items="${requestScope.data}">
            <tr>
                <td>
                    ${d.sourceAccount.owner.firstName} ${d.sourceAccount.owner.lastName} wants to send
                        <strong>
                            <c:set var="factor">
                                <fct:factorTag sourceCurrency="${d.sourceAccount.currency}" targetCurrency="${d.targetAccount.currency}"/>
                            </c:set>
                            ${factor * d.amount} ${d.targetAccount.currency}
                        </strong>
                </td>
                <td>
                    <div class="btn-group">
                        <button class="btn btn-success" onclick="post('notifications', {action: 'accept', sourceId: ${d.sourceAccount.accountId}, targetId:${d.targetAccount.accountId}, date: '${d.transactionTimestamp}', amount:${d.amount}}, 'post')"><i class="fas fa-check"></i></button>
                        <button class="btn btn-danger" onclick="post('notifications', {action: 'decline', sourceId: ${d.sourceAccount.accountId}, targetId:${d.targetAccount.accountId}, date: '${d.transactionTimestamp}', amount:${d.amount}}, 'post')"><i class="fas fa-times"></i></button>
                    </div>
                </td>
            <tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<script src="https://kit.fontawesome.com/3e95ffcee6.js" crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<script src="<c:url value='/js/script.js'/>"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
        integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
        crossorigin="anonymous"></script>
</body>
</html>
