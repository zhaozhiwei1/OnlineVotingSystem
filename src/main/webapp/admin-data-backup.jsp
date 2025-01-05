<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>数据备份</title>
    <link rel="stylesheet" href="css/admin-data-backup.css">
</head>
<body>
<div class="container">
    <h2>数据备份</h2>
    <a href="admin.html" class="back-link">返回管理后台</a>

    <div class="data-backup-list">
        <h3>选择数据表</h3>
        <form action="AdminDataBackup" method="GET">
            <select name="tableName" onchange="this.form.submit()">
                <option value="">请选择数据表</option>
                <option value="user">用户表</option>
                <option value="polls">投票表</option>
                <option value="votes">投票记录表</option>
                <option value="feedback">反馈表</option>
            </select>
        </form>

        <c:if test="${not empty tableData}">
            <h3>${selectedTable} 数据</h3>
            <table>
                <thead>
                <tr>
                    <c:forEach var="column" items="${tableColumns}">
                        <c:choose>
                            <c:when test="${column == 'status'}">
                                <th class="status-column">${column}</th>
                            </c:when>
                            <c:otherwise>
                                <th>${column}</th>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="row" items="${tableData}">
                    <tr>
                        <c:forEach var="cell" items="${row}">
                            <c:choose>
                                <c:when test="${tableColumns[row.indexOf(cell)] == 'status'}">
                                    <td class="status-column">${cell}</td>
                                </c:when>
                                <c:otherwise>
                                    <td>${cell}</td>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <form action="ExportData" method="GET">
                <input type="hidden" name="tableName" value="${selectedTable}">
                <button type="submit" class="export-btn">导出为 CSV</button>
            </form>
        </c:if>
    </div>
</div>
</body>
</html>