<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>反馈</title>
  <link rel="stylesheet" href="css/feedback.css">
</head>
<body>
<div class="container">
  <h2>反馈</h2>
  <a href="index.html" class="back-link">返回首页</a>
  <form id="feedbackForm" action="SubmitFeedback" method="post">
    <div class="form-group">
      <label for="feedbackType">反馈类型:</label>
      <select id="feedbackType" name="feedbackType" required>
        <option value="建议">建议</option>
        <option value="问题">问题</option>
        <option value="其他">其他</option>
      </select>
    </div>

    <div class="form-group">
      <label for="feedbackContent">反馈内容:</label>
      <textarea id="feedbackContent" name="feedbackContent" rows="4" required></textarea>
    </div>

    <div class="form-group">
      <label for="contactPhone">联系电话:</label>
      <input type="text" id="contactPhone" name="contactPhone">
    </div>

    <div class="form-group">
      <button type="submit">提交反馈</button>
    </div>
  </form>
</div>
</body>
</html>