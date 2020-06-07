<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bat" uri="/WEB-INF/bankingApplicationTags.tld" %>
<html>
<head>
    <title>Transfer Money</title>
    <link rel="stylesheet" href="<c:url value='/styles/menu.css'/>" type="text/css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">
</head>
<body>
<%@ include file="sidebarMenuInclude.jsp" %>
<div class="content">
    <div class="container">
        <h1>Transfer Money between accounts or to others</h1>
        <div class="form-row">
        </div>
        <div class="form-row">
            <div class="form-group col-md-4">
                <b>Transfer to: </b>
                <select id="transferToList" class="form-control"
                        onchange="httpGet('${initParam.applicationUrl}/transfer', 'transferToList')">
                    <option value="personal">personal account</option>
                    <option value="others">others</option>
                </select>
            </div>
        </div>
        <table id="unique" class="table">
            <c:choose>
                <c:when test="${requestScope.isPersonal == null}">
                </c:when>
                <c:when test="${requestScope.isPersonal == false}">
                    <thead>
                    <tr>
                        <th>Username</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Phone Number</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="mainAccount" items="${requestScope.mainAccounts}">
                        <tr>
                            <td>${mainAccount.owner.username}</td>
                            <td>${mainAccount.owner.firstName}</td>
                            <td>${mainAccount.owner.lastName}</td>
                            <td>${mainAccount.owner.phoneNumber}</td>
                            <td>
                                <button id="${mainAccount.accountId}" class="btn btn-success" data-toggle="modal"
                                        data-target="#sendMoneyModal"
                                        onclick="addMultipleHiddenInputs('formModify', {accountId: ${mainAccount.accountId}, isPersonal: false})">
                                    <i class="fas fa-arrow-left"></i>
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </c:when>
                <c:otherwise>
                    <thead>
                    <tr>
                        <th>AccountID</th>
                        <th>CreationDate</th>
                        <th>Sold</th>
                        <th>Currency</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="acc" items="${requestScope.personalAccounts}">
                        <tr>
                            <td>${acc.accountId}</td>
                            <td>${acc.creationDate}</td>
                            <td>${acc.sold}</td>
                            <td>${acc.currency}</td>
                            <td>
                                <button id="${acc.accountId}" class="btn btn-success" data-toggle="modal"
                                        data-target="#sendMoneyModal"
                                        onclick="addMultipleHiddenInputs('formModify', {accountId: ${acc.accountId}, isPersonal: true})">
                                    <i class="fas fa-arrow-left"></i>
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </c:otherwise>
            </c:choose>
        </table>
    </div>
</div>
<div class="modal" id="sendMoneyModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body">
                <form action="transfer" method="post" id="formModify">
                    <bat:warnIfError message="${message}"/>
                    <div class="form-group">
                        <input type="text" class="form-control" name="quantity"
                               placeholder="Enter the amount you want to transfer (minimum is 10)" required>
                    </div>
                    <button class="btn btn-primary" type="submit">Send</button>
                </form>
            </div>
        </div>
    </div>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<c:if test="${message != null}">
    <script>
        console.log("${requestScope.accountId}")
        $(document).ready(function () {
            $("#${buttonId}").trigger("click");
        })
    </script>
</c:if>
<script src="https://kit.fontawesome.com/3e95ffcee6.js" crossorigin="anonymous"></script>
<script src="<c:url value='/js/script.js'/>"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
        integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
        crossorigin="anonymous"></script>
</body>
</html>
