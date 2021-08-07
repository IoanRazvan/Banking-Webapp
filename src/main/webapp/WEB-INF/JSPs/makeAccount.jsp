<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Menu</title>
    <c:choose>
        <c:when test="${user.mainAccount == null}">
            <link rel="stylesheet" href="styles/main.css" type="text/css">
        </c:when>
        <c:otherwise>
            <link rel="stylesheet" href="styles/menu.css" type="text/css">
        </c:otherwise>
    </c:choose>
    <link rel="stylesheet" href="styles/menu.css" type="text/css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">
</head>
<body>
<c:if test="${user.mainAccount != null}">
    <%@ include file="sidebarMenu.jsp" %>
</c:if>
<div class="content">
    <div class="container">
        <div class="form-join">
            <c:choose>
                <c:when test="${user.mainAccount == null}">
                    <h2>Let's make your first account</h2>
                </c:when>
                <c:otherwise>
                    <h1>Make another account</h1>
                </c:otherwise>
            </c:choose>
            <form action="insertNewAccount" method="post">
                <div class="form-row">
                    <div class="form-group col-md-8">
                        <input type="number" min="5" class="form-control" name="sold" placeholder="Enter sold">
                    </div>
                    <div class="form-group col-md-4">
                        <select name="currency" class="form-control">
                            <option value="euro">EURO</option>
                            <option value="ron">RON</option>
                            <option value="dollar">DOLLAR</option>
                        </select>
                    </div>
                </div>
                <button class="btn btn-primary" type="submit">Finish</button>
            </form>
        </div>
    </div>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
        integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
        crossorigin="anonymous"></script>
</body>
</html>
