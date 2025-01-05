<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>单选投票</title>
  <link rel="stylesheet" href="css/vote.css">
</head>
<body>
<div class="container">
  <h2>单选投票</h2>
  <a href="vote-participation.jsp" class="back-link">返回投票参与</a>
  <form id="voteForm" action="VoteServlet" method="post">
    <input type="hidden" name="pollId" value="${pollId}">
    <input type="hidden" name="pollType" value="${pollType}">
    <div class="form-group">
      <label>请选择一个选项:</label>
      <c:forEach var="option" items="${options}">
        <div class="vote-option">
          <input type="radio" name="voteOption" value="${option.trim()}" required>
          <label>${option.trim()}</label>
        </div>
      </c:forEach>
    </div>
    <c:if test="${hasVoted}">
      <p class="voted-message">您已经投过票了，不能重复投票。</p>
    </c:if>
    <button type="submit" class="submit-btn" ${hasVoted ? 'disabled' : ''}>提交投票</button>
  </form>
</div>
</body>
</html>