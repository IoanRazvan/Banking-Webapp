<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="fct" tagdir="/WEB-INF/tags" %>
<html>
<head>
    <title>Menu</title>
    <link rel="stylesheet" href="<c:url value='/styles/menu.css'/>" type="text/css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">
</head>
<%@ include file="sidebarMenuInclude.jsp" %>
<div class="content">
    <h1>These are your statistics</h1>
    <div class="container">
        <div class="btn-group">
            <button class="btn btn-primary" id="RONBtn">RON</button>
            <button class="btn btn-primary" id="EUROBtn">EURO</button>
            <button class="btn btn-primary" id="DOLLARBtn">DOLLAR</button>
        </div>
        <canvas id="euroChart"></canvas>
        <canvas id="ronChart"></canvas>
        <canvas id="dollarChart"></canvas>
    </div>
    <div class="container">
        <div class="btn-group">
            <button class="btn btn-primary" id="targetTrans">Target</button>
            <button class="btn btn-primary" id="sourceTrans">Source</button>
        </div>
        <table class="table" id="targetTable">
            <thead>
                <th>
                    AccountID
                </th>
                <th>
                    Amount
                </th>
                <th>
                    Currency
                </th>
                <th>
                    Status
                </th>
            </thead>
            <tbody>
                <c:forEach var="trans" items="${targetTransaction}">
                <tr>
                    <td>${trans.sourceAccount.accountId}</td>
                    <c:set var="factor">
                        <fct:factorTag sourceCurrency="${trans.sourceAccount.currency}" targetCurrency="${trans.targetAccount.currency}"/>
                    </c:set>
                    <td>${factor * trans.amount}</td>
                    <td>${trans.targetAccount.currency}</td>
                    <td>${trans.status}</td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
        <table class="table" id="sourceTable">
            <thead>
            <th>
                AccountID
            </th>
            <th>
                Amount
            </th>
            <th>
                Currency
            </th>
            <th>
                Status
            </th>
            </thead>
            <tbody>
            <c:forEach var="trans" items="${sourceTransaction}">
                <tr>
                    <td>${trans.targetAccount.accountId}</td>
                    <c:set var="factor">
                        <fct:factorTag sourceCurrency="${trans.sourceAccount.currency}" targetCurrency="${trans.targetAccount.currency}"/>
                    </c:set>
                    <td>${factor * trans.amount}</td>
                    <td>${trans.sourceAccount.currency}</td>
                    <td>${trans.status}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc="
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0"></script>
<script src="<c:url value = '/js/script.js'/>"></script>
<script>
    $(document).ready(function () {
        $("#targetTable").hide()
        $("#sourceTable").hide()
        $("#ronChart").hide();
        $("#dollarChart").hide();
        $("#euroChart").hide();
        $.getJSON("${initParam.applicationUrl}/statistics?curr=euro&type=sent", function (results) {
                let labels = [];
                let dataRecieved = [];
                let dataSent = [];

                labels = results[2].transactionData.sentMoneyList.map(function (item) {
                    return item.date
                })
                dataRecieved = results[2].transactionData.recievedMoneyList.map(function (item) {
                    return item.amount;
                })
                dataSent = results[2].transactionData.sentMoneyList.map(function (item) {
                    return item.amount;
                })
                console.log(labels)
                createChart(labels, [dataSent, dataRecieved], 'euroChart')
            }
        )
        $.getJSON("${initParam.applicationUrl}/statistics?curr=euro&type=sent", function (results) {
                let labels = [];
                let dataRecieved = [];
                let dataSent = [];

                labels = results[0].transactionData.sentMoneyList.map(function (item) {
                    return item.date
                })
                dataRecieved = results[0].transactionData.recievedMoneyList.map(function (item) {
                    return item.amount;
                })
                dataSent = results[0].transactionData.sentMoneyList.map(function (item) {
                    return item.amount;
                })
                console.log(labels)
                createChart(labels, [dataSent, dataRecieved], 'ronChart')
            }
        )
        $.getJSON("${initParam.applicationUrl}/statistics?curr=euro&type=sent", function (results) {
                let labels = [];
                let dataRecieved = [];
                let dataSent = [];

                labels = results[1].transactionData.sentMoneyList.map(function (item) {
                    return item.date
                })
                dataRecieved = results[1].transactionData.recievedMoneyList.map(function (item) {
                    return item.amount;
                })
                dataSent = results[1].transactionData.sentMoneyList.map(function (item) {
                    return item.amount;
                })
                console.log(labels)
                console.log("DEBUG")

                createChart(labels, [dataSent, dataRecieved], 'dollarChart')
            }
        )
        $("#RONBtn").click(function () {
            $("#euroChart").hide();
            $("#dollarChart").hide();
            $("#ronChart").show();
        })
        $("#EUROBtn").click(function () {
            $("#ronChart").hide();
            $("#dollarChart").hide();
            $("#euroChart").show();
        })
        $("#DOLLARBtn").click(function () {
            $("#ronChart").hide();
            $("#euroChart").hide();
            $("#dollarChart").show();
        })
        $("#targetTrans").click(function () {
            $("#sourceTable").hide()
            $("#targetTable").show()
        })
        $("#sourceTrans").click(function () {
            $("#targetTable").hide();
            $("#sourceTable").show();
        })
    })
    function createChart(labels, data, chartId) {
        let myChart = document.getElementById(chartId)
        let chart = new Chart(myChart, {
            type: 'line',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Money Sent',
                    data: data[0],
                    borderColor: 'rgba(75, 192, 192, 1)',
                    backgroundColor: 'rgba(75, 192, 192, 0.2)',
                }, {
                    label: 'Money Recieved',
                    data: data[1],
                    borderColor: 'rgba(75, 192, 50, 0.5)',
                    backgroundColor: 'rgba(75, 192, 50, 0.2)',
                }]
            },
        });
    }
</script>
</body>
</html>