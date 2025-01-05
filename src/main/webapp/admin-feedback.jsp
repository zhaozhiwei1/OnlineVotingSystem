<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>用户反馈管理</title>
    <link rel="stylesheet" href="css/admin-feedback.css">
</head>
<body>
<div class="container">
    <h2>用户反馈管理</h2>
    <a href="admin.html" class="back-link">返回管理后台</a>
    <div class="search-bar">
        <form action="AdminFeedbackManage" method="GET">
            <input type="text" name="searchQuery" placeholder="搜索反馈内容或用户">
            <button type="submit" class="search-btn">搜索</button>
        </form>
    </div>

    <div class="feedback-list">
        <table>
            <thead>
            <tr>
                <th>反馈ID</th>
                <th>反馈类型</th>
                <th>反馈内容</th>
                <th>联系电话</th>
                <th>反馈用户</th>
                <th>反馈时间</th>
            </tr>
            </thead>
            <tbody id="feedbackTableBody">
            <!-- 动态插入反馈数据 -->
            ${feedbackTableContent}
            </tbody>
        </table>
    </div>
</div>
</body>
</html>