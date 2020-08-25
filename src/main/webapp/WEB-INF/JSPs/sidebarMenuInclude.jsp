<div class="sidebar">
    <p>Hello, <strong>${user.firstName} ${user.lastName}</strong> <br>
        Your ID is <strong>${user.userId}</strong></p>
    <a onclick="post('menu', {option: 'Add Another Account'}, 'get')" href="#">Add Another Account</a>
    <a onclick="post('menu', {option: 'Transfer'}, 'get')" href="#">Transfer</a>
    <a onclick="post('menu', {option: 'Manage Accounts'}, 'get')" href="#">Manage Accounts</a>
    <a onclick="post('menu', {option: 'Notifications'}, 'get')" href="#">Notifications</a>
    <a onclick="post('menu', {option: 'Edit User'}, 'get')" href="#">Edit User</a>
    <a onclick="post('menu', {option: 'Statistics'}, 'get')" href="#">Statistics</a>
    <a onclick="post('menu', {option: 'Log Out'}, 'get')" href="#">Log Out</a>
</div>
