<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>投票管理</title>
    <link rel="stylesheet" href="css/admin-vote-manage.css">
</head>
<body>
<div class="container">
    <h2>投票管理</h2>
    <a href="admin.html" class="back-link">返回管理后台</a>
    <div class="search-bar">
        <form action="AdminVoteManage" method="GET">
            <input type="text" name="searchQuery" placeholder="搜索投票ID或主题">
            <button type="submit" class="search-btn">搜索</button>
        </form>
    </div>

    <div class="vote-list">
        <table>
            <thead>
            <tr>
                <th>投票ID</th>
                <th>投票主题</th>
                <th>创建者</th>
                <th>创建时间</th>
                <th>结束时间</th>
                <th>过期时间</th>
                <th>状态</th>
                <th>类型</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody id="voteTableBody">
            <!-- 动态插入投票数据 -->
            ${voteTableContent}
            </tbody>
        </table>
    </div>
</div>
<script>
    function confirmDelete(pollId) {
        if (confirm("确定要删除这个投票吗？此操作不可撤销。")) {
            window.location.href = "AdminDeletePoll?pollId=" + pollId;
        }
    }

    function togglePollStatus(pollId, isActive) {
        const newStatus = isActive ? "关闭" : "开启";
        if (confirm("确定要" + newStatus + "这个投票吗？")) {
            window.location.href = "TogglePollStatus?pollId=" + pollId + "&status=" + (isActive ? "closed" : "open");
        }
    }
</script>
</body>
</html>