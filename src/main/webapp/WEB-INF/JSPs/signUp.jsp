<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<html>
<head>
    <title>Register</title>
    <link rel="stylesheet" href="<c:url value='/styles/main.css'/>" type="text/css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
    integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="form-join">
        <h2>Join Us</h2>
        <form action="signUpVerification" method="post" id="signUpForm">
            <div class="form-row">
                <div class="form-group col-md-6">
                    <input class="form-control" id="firstName" type="text" name="firstName" value="<c:out value ='${user.firstName}'/>" placeholder="First Name" required>
                </div>
                <div class="form-group col-md-6">
                    <input class="form-control" id="lastName" type="text" name="lastName" value="<c:out value ='${user.lastName}'/>" placeholder="Last Name" required>
                </div>
            </div>
            <div class="form-group">
                <input id="phoneNumber" class="form-control" type="text" name="phoneNumber" value="<c:out value ='${user.phoneNumber}'/>" placeholder="Phone Number" required>
            </div>
            <div class="form-group">
                <input id="username" class="form-control" type="text" name="username" value="<c:out value ='${user.username}'/>" placeholder="Username" required>
            </div>
            <div class="form-group">
                <input id="password" minlength="6" class="form-control" type="password" name="password" placeholder="Password" required>
            </div>
            <button type="submit" class="btn btn-primary">Join</button>
        </form>
    </div>
</div>
<script src="js/signUp.js" onload="addAlertDiv('${message}');"></script>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<script src="js/script.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
        integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
        crossorigin="anonymous"></script>
</body>
</html>
