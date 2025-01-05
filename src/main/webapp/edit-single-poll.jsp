<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>编辑单选投票</title>
    <link rel="stylesheet" href="css/edit-poll.css">
</head>
<body>
<div class="container">
    <h2>编辑单选投票</h2>
    <a href="vote-manage.jsp" class="back-link">返回投票管理</a>
    <form id="editSinglePollForm" action="EditSinglePoll" method="post">
        <input type="hidden" name="id" value="${poll.id}">
        <div class="form-group">
            <label for="voteTheme">投票主题:</label>
            <input type="text" id="voteTheme" name="voteTheme" value="${poll.theme}" required>
        </div>

        <div class="form-group">
            <label for="voteDescription">投票描述:</label>
            <textarea id="voteDescription" name="voteDescription" rows="4" required>${poll.description}</textarea>
        </div>

        <div class="form-group">
            <label>投票选项:</label>
            <div id="voteOptionsContainer">
                <c:forEach var="option" items="${poll.options}">
                    <div class="vote-option">
                        <input type="text" name="voteOptions[]" value="${option}" required>
                        <button type="button" class="remove-option-btn" onclick="removeOption(this)">删除</button>
                    </div>
                </c:forEach>
            </div>
            <button type="button" class="add-option-btn" onclick="addOption()">添加选项</button>
        </div>

        <div class="form-group">
            <label for="startTime">开始时间:</label>
            <input type="datetime-local" id="startTime" name="startTime" value="${poll.startTime}" required>
        </div>

        <div class="form-group">
            <label for="endTime">结束时间:</label>
            <input type="datetime-local" id="endTime" name="endTime" value="${poll.endTime}" required>
        </div>

        <div class="form-group">
            <button type="submit" class="submit-btn">保存修改</button>
        </div>
    </form>
</div>
<script>
    function addOption() {
        const container = document.getElementById('voteOptionsContainer');
        const newOption = document.createElement('div');
        newOption.className = 'vote-option';
        newOption.innerHTML = `
            <input type="text" name="voteOptions[]" placeholder="输入选项" required>
            <button type="button" class="remove-option-btn" onclick="removeOption(this)">删除</button>
        `;
        container.appendChild(newOption);
    }

    function removeOption(button) {
        const optionDiv = button.parentNode;
        optionDiv.remove();
    }
</script>
</body>
</html>