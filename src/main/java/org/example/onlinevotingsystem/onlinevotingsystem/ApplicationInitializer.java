package org.example.onlinevotingsystem.onlinevotingsystem;

import org.example.onlinevotingsystem.service.PollStatusUpdater;
import org.example.onlinevotingsystem.util.DBUtil;

import java.sql.Connection;

public class ApplicationInitializer {

    public static void main(String[] args) {
        // 初始化数据库连接
        Connection connection = DBUtil.getConnection();

        // 启动投票状态更新定时任务
        PollStatusUpdater pollStatusUpdater = new PollStatusUpdater(connection);
        pollStatusUpdater.start();

        // 其他应用程序初始化逻辑
        System.out.println("投票状态更新定时任务已启动！");
    }
}