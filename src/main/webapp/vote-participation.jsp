<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>投票参与</title>
    <link rel="stylesheet" href="css/vote-participation.css">
</head>
<body>
<div class="container">
    <h2>投票参与</h2>
    <a href="index.html" class="back-link">返回首页</a>
    <div class="search-bar">
        <form action="VoteParticipation" method="GET">
            <input type="text" name="searchQuery" placeholder="搜索投票ID或主题">
            <select name="pollType">
                <option value="all">全部</option>
                <option value="single">单选</option>
                <option value="multiple">多选</option>
            </select>
            <button type="submit" class="search-btn">搜索</button>
        </form>
    </div>

    <div class="vote-list">
        <c:forEach var="poll" items="${singleOptionPolls}">
            <div class="poll-box">
                <h3>${poll.theme}</h3>
                <p>投票ID: ${poll.id}</p>
                <p>${poll.description}</p>
                <p>创建者: ${poll.creatorUsername}</p>
                <a href="VoteServlet?pollId=${poll.id}&pollType=single" class="view-btn">参与投票</a>
            </div>
        </c:forEach>

        <c:forEach var="poll" items="${multipleOptionPolls}">
            <div class="poll-box">
                <h3>${poll.theme}</h3>
                <p>投票ID: ${poll.id}</p>
                <p>${poll.description}</p>
                <p>创建者: ${poll.creatorUsername}</p>
                <a href="VoteServlet?pollId=${poll.id}&pollType=multiple" class="view-btn">参与投票</a>
            </div>
        </c:forEach>
    </div>
</div>
</body>
</html>