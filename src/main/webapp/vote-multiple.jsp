<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>多选投票</title>
  <link rel="stylesheet" href="css/vote.css">
</head>
<body>
<div class="container">
  <h2>多选投票</h2>
  <a href="vote-participation.jsp" class="back-link">返回投票参与</a>
  <form id="voteForm" action="VoteServlet" method="post" onsubmit="return validateVote()">
    <input type="hidden" name="pollId" value="${pollId}">
    <input type="hidden" name="pollType" value="${pollType}">
    <div class="form-group">
      <label>请选择多个选项（最多 ${maxChoices} 个）:</label>
      <c:forEach var="option" items="${options}">
        <div class="vote-option">
          <input type="checkbox" name="voteOption" value="${option.trim()}">
          <label>${option.trim()}</label>
        </div>
      </c:forEach>
    </div>
    <button type="submit" class="submit-btn">提交投票</button>
  </form>
</div>
<script>
  const maxChoices = ${maxChoices}; // 从后端获取最大选择数

  function validateVote() {
    const checkboxes = document.querySelectorAll('input[name="voteOption"]:checked');
    if (checkboxes.length === 0) {
      alert('请至少选择一个选项');
      return false;
    }
    if (checkboxes.length > maxChoices) {
      alert(`最多只能选择 ${maxChoices} 个选项`);
      return false;
    }
    return true;
  }
</script>
</body>
</html>