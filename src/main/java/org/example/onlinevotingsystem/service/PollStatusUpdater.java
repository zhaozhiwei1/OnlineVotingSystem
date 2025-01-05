package org.example.onlinevotingsystem.service;

import org.example.onlinevotingsystem.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PollStatusUpdater {

    private final Connection connection;
    private ScheduledExecutorService scheduler;
    private PollStatusListener listener;

    public PollStatusUpdater(Connection connection) {
        this.connection = connection;
    }

    /**
     * 设置投票状态监听器
     */
    public void setListener(PollStatusListener listener) {
        this.listener = listener;
    }

    /**
     * 启动定时任务，定期更新投票状态
     */
    public void start() {
        if (scheduler == null || scheduler.isShutdown()) {
            scheduler = Executors.newScheduledThreadPool(1);
            scheduler.scheduleAtFixedRate(this::updatePollStatus, 0, 10, TimeUnit.SECONDS);
            if (listener != null) {
                listener.onPollStatusUpdaterStarted();
            }
        }
    }

    /**
     * 停止定时任务
     */
    public void stop() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
            if (listener != null) {
                listener.onPollStatusUpdaterStopped();
            }
        }
    }

    /**
     * 更新投票状态
     */
    private void updatePollStatus() {
        System.out.println("执行投票状态更新任务：" + new java.util.Date());
        try {
            // 更新状态为“进行中”
            String updateActiveQuery = "UPDATE polls SET status = '进行中' WHERE start_time <= NOW() AND end_time > NOW()";
            try (PreparedStatement activeStmt = connection.prepareStatement(updateActiveQuery)) {
                int rowsUpdated = activeStmt.executeUpdate();
                System.out.println("更新为‘进行中’的行数：" + rowsUpdated);
                if (listener != null) {
                    listener.onPollStatusUpdated("进行中", rowsUpdated);
                }
            }

            // 更新状态为“已关闭”
            String updateClosedQuery = "UPDATE polls SET status = '已关闭' WHERE end_time <= NOW()";
            try (PreparedStatement closedStmt = connection.prepareStatement(updateClosedQuery)) {
                int rowsUpdated = closedStmt.executeUpdate();
                System.out.println("更新为‘已关闭’的行数：" + rowsUpdated);
                if (listener != null) {
                    listener.onPollStatusUpdated("已关闭", rowsUpdated);
                }
            }

            // 更新状态为“已过期”
            String updateExpiredQuery = "UPDATE polls SET status = '已过期' WHERE end_time <= DATE_SUB(NOW(), INTERVAL 1 MONTH)";
            try (PreparedStatement expiredStmt = connection.prepareStatement(updateExpiredQuery)) {
                int rowsUpdated = expiredStmt.executeUpdate();
                System.out.println("更新为‘已过期’的行数：" + rowsUpdated);
                if (listener != null) {
                    listener.onPollStatusUpdated("已过期", rowsUpdated);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            if (listener != null) {
                listener.onPollStatusUpdateError(e);
            }
        }
    }

    /**
     * 投票状态监听器接口
     */
    public interface PollStatusListener {
        void onPollStatusUpdaterStarted();
        void onPollStatusUpdaterStopped();
        void onPollStatusUpdated(String status, int rowsUpdated);
        void onPollStatusUpdateError(Exception e);
    }
}