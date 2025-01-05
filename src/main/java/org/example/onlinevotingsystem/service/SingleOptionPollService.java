package org.example.onlinevotingsystem.service;

import org.example.onlinevotingsystem.dao.SingleOptionPollDAO;
import org.example.onlinevotingsystem.model.SingleOptionPoll;

import java.sql.SQLException;

public class SingleOptionPollService {
    private SingleOptionPollDAO dao;

    public SingleOptionPollService(SingleOptionPollDAO dao) {
        this.dao = dao;
    }

    /**
     * 检查投票ID是否唯一
     *
     * @param id 投票ID
     * @return 如果ID唯一返回true，否则返回false
     * @throws SQLException 数据库异常
     */
    public boolean isIdUnique(String id) throws SQLException {
        return dao.isIdUnique(id);
    }

    /**
     * 创建单选投票
     *
     * @param poll 单选投票对象
     * @throws SQLException 数据库异常
     */
    public void createPoll(SingleOptionPoll poll) throws SQLException {
        dao.save(poll);
    }
}