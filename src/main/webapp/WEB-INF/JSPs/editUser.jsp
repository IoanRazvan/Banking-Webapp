<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bat" uri="/WEB-INF/bankingApplicationTags.tld" %>
<html>
<head>
    <title>Edit User Configurations</title>
    <link rel="stylesheet" href="<c:url value='/styles/menu.css'/>" type="text/css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">
</head>
<body>
<%@ include file="sidebarMenuInclude.jsp" %>
<div class="content">
    <div class="container">
        <h1>Edit Your Account Settings</h1>
        <c:forEach var="field" items="${fieldValuePairs}">
            <div class="row">
                <div class="col-sm">${field.key}: </div>
                <div class="col-sm">${field.value}</div>
                <div class="col-sm">
                    <button type="button" class="btn btn-primary" data-toggle="modal"
                            data-target="#modifyModal" onclick="addHiddenInput('modifyUser','${field.key}', 'selectedField')" id="${field.key}">
                        <i class="far fa-edit"></i>
                    </button><br>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
<div class="modal" id="modifyModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body">
                <form action="editUser" method="post" id="modifyUser">
                    <bat:warnIfError message="${message}"/>
                    <div class="form-group">
                        <input type="text" class="form-control" name="newValue" placeholder="Enter new value for the selected field" required>
                    </div>
                    <button class="btn btn-primary" type="submit">Modify</button>
                </form>
            </div>
        </div>
    </div>
</div>
<script src="https://kit.fontawesome.com/3e95ffcee6.js" crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<c:if test="${message != null}">
    <script>
        $(document).ready(function () {
            $("[id='${selectedField}']").trigger("click");
        })
    </script>
</c:if>
<script src="<c:url value='/js/script.js'/>"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
        integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
        crossorigin="anonymous"></script>
</body>
</html>
