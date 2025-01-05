<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>个人信息</title>
    <link rel="stylesheet" href="css/Profile.css">
</head>
<body>
<div class="container">
    <h2>个人信息</h2>
    <a href="index.html" class="back-link">返回首页</a>
    <div class="profile-info">
        <p><strong>用户名:</strong> ${user.username}</p>
        <p><strong>邮箱:</strong> ${user.email}</p>
    </div>
    <a href="EditProfile" class="edit-btn">编辑信息</a>
</div>
</body>
</html>