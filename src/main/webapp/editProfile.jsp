<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>编辑个人信息</title>
    <link rel="stylesheet" href="css/Profile.css">
</head>
<body>
<div class="container">
    <h2>编辑个人信息</h2>
    <a href="ViewProfile" class="back-link">返回</a>
    <form action="EditProfile" method="post">
        <div class="form-group">
            <label for="username">用户名:</label>
            <input type="text" id="username" name="username" value="${user.username}" required>
        </div>
        <div class="form-group">
            <label for="email">邮箱:</label>
            <input type="email" id="email" name="email" value="${user.email}" required>
        </div>
        <button type="submit" class="edit-btn">提交</button>
    </form>
    <c:if test="${not empty error}">
        <p class="error-message">${error}</p>
    </c:if>
</div>
</body>
</html>