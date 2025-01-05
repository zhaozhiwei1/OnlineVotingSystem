<%--
  Created by IntelliJ IDEA.
  User: 86152
  Date: 2024/12/30
  Time: 08:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>查看投票结果</title>
    <link rel="stylesheet" href="css/view-results.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>
<div class="container">
    <h2>投票结果</h2>
    <a href="vote-manage.jsp" class="back-link">返回投票管理</a>
    <div class="chart-container">
        <canvas id="voteChart"></canvas>
    </div>
</div>
<script>
    const voteResults = [
        <c:forEach var="result" items="${voteResults}">
        { option: "${result.voteOption}", count: ${result.voteCount} },
        </c:forEach>
    ];

    const ctx = document.getElementById('voteChart').getContext('2d');
    const voteChart = new Chart(ctx, {
        type: 'pie',
        data: {
            labels: voteResults.map(result => result.option),
            datasets: [{
                data: voteResults.map(result => result.count),
                backgroundColor: [
                    '#FF6384', '#36A2EB', '#FFCE56', '#4BC0C0', '#9966FF', '#FF9F40'
                ]
            }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: {
                    position: 'top',
                },
                title: {
                    display: true,
                    text: '投票结果扇形图'
                }
            }
        }
    });
</script>
</body>
</html>
