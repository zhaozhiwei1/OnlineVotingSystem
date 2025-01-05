package org.example.onlinevotingsystem.service;

import org.example.onlinevotingsystem.dao.MultipleOptionPollDAO;
import org.example.onlinevotingsystem.model.MultipleOptionPoll;

import java.sql.SQLException;

public class MultipleOptionPollService {
    private MultipleOptionPollDAO dao;

    public MultipleOptionPollService(MultipleOptionPollDAO dao) {
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
     * 创建多选投票
     *
     * @param poll 多选投票对象
     * @throws SQLException 数据库异常
     */
    public void createPoll(MultipleOptionPoll poll) throws SQLException {
        dao.save(poll);
    }
}