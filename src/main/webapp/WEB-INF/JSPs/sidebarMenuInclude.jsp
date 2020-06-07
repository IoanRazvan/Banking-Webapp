<div class="sidebar">
    <p>Hello, <strong>${user.firstName} ${user.lastName}</strong> <br>
        Your ID is <strong>${user.userId}</strong></p>
    <c:forEach var="option" items="${menuOptions}">
        <a onclick="post('menu', {option: '${option}'}, 'get')" href="#">${option}</a>
    </c:forEach>
</div>
