<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>用户管理</title>
    <link rel="stylesheet" href="css/user-manage.css">
</head>
<body>
<div class="container">
    <h2>用户管理</h2>
    <a href="admin.html" class="back-link">返回管理后台</a>
    <div class="search-bar">
        <form action="UserManage" method="GET">
            <input type="text" name="searchQuery" placeholder="搜索用户名或邮箱">
            <button type="submit" class="search-btn">搜索</button>
        </form>
    </div>

    <div class="user-list">
        <table>
            <thead>
            <tr>
                <th>用户ID</th>
                <th>用户名</th>
                <th>邮箱</th>
                <th>状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody id="userTableBody">
            <!-- 动态插入用户数据 -->
            ${userTableContent}
            </tbody>
        </table>
    </div>
</div>
<script>
    function confirmResetPassword(userId) {
        if (confirm("确定要重置该用户的密码为12345678吗？")) {
            window.location.href = "ResetUserPassword?userId=" + userId;
        }
    }

    function confirmBlockUser(userId) {
        if (confirm("确定要拉黑该用户吗？")) {
            window.location.href = "BlockUser?userId=" + userId;
        }
    }

    function confirmUnblockUser(userId) {
        if (confirm("确定要解封该用户吗？")) {
            window.location.href = "UnblockUser?userId=" + userId;
        }
    }
</script>
</body>
</html>