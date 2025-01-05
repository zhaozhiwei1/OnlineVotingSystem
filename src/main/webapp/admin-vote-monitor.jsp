<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>投票监控</title>
    <link rel="stylesheet" href="css/admin-vote-monitor.css">
</head>
<body>
<div class="container">
    <h2>投票监控</h2>
    <a href="admin.html" class="back-link">返回管理后台</a>
    <div class="search-bar">
        <form action="AdminVoteMonitor" method="GET">
            <input type="text" name="searchQuery" placeholder="搜索投票主题或用户名">
            <button type="submit" class="search-btn">搜索</button>
        </form>
    </div>

    <div class="vote-monitor-list">
        <table>
            <thead>
            <tr>
                <th>投票ID</th>
                <th>投票主题</th>
                <th>投票用户</th>
                <th>投票选项</th>
                <th>投票时间</th>
            </tr>
            </thead>
            <tbody id="voteMonitorTableBody">
            <!-- 动态插入投票监控数据 -->
            ${voteMonitorTableContent}
            </tbody>
        </table>
    </div>
</div>
</body>
</html>