<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE>
<html lang="en">
<head>
    <title>Profile Settings</title>
    <link rel="stylesheet" href="styles/menu.css" type="text/css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">
</head>
<body>

<%@ include file="sidebarMenu.jsp" %>

<div class="content">
    <div class="container">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h4 class="text-right">Profile Settings</h4>
        </div>
        <form action="editUser" action="POST" id="editUser">
            <div class="row mt-2">
                <div class="col-md-6"><label class="labels">First Name</label><input name="firstName" type="text" class="form-control" placeholder="first name" value="${user.userInfo.firstName}"></div>
                <div class="col-md-6"><label class="labels">Last Name</label><input name="lastName" type="text" class="form-control" placeholder="last name" value="${user.userInfo.lastName}"></div>
            </div>
            <div class="row mt-3">
                <div class="col-md-12"><label class="labels">Phone Number</label><input name="phoneNumber" type="text" class="form-control" placeholder="enter phone number" value="${user.userInfo.phoneNumber}"></div>
                <div class="col-md-12"><label class="labels">Username</label><input name="username" type="text" class="form-control" placeholder="enter username" value="${user.userInfo.username}"></div>
            </div>
            <div class="mt-5 text-center"><button class="btn btn-primary profile-button" type="submit">Save Profile</button></div>
        </form>
    </div>
</div>

<%@ include file="messageModal.html" %>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<script src="js/editUser.js"></script>
<script src="https://kit.fontawesome.com/3e95ffcee6.js" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
        integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
        crossorigin="anonymous"></script>
</body>
</html>
