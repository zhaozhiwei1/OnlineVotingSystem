package org.example.onlinevotingsystem.service;


import org.example.onlinevotingsystem.dao.UserDao;
import org.example.onlinevotingsystem.model.User;

import java.util.List;

public class UserService {

    private UserDao userDao = new UserDao();

    public boolean register(User user) {
        if (userDao.findByUsername(user.getUsername()) != null) {
            return false; // 用户名已存在
        }
        if (userDao.findByEmail(user.getEmail()) != null) {
            return false; // 邮箱已存在
        }
        return userDao.createUser(user);
    }

    public String login(String username, String password) {
        User user = userDao.findByUsername(username);
        if (user == null) {
            return "用户名不存在";
        }
        if (user.isLocked()) {
            return "账号已被锁定，请联系客服解锁";
        }
        if (!user.getPassword().equals(password)) {
            int attempts = user.getFailedLoginAttempts() + 1;
            userDao.updateFailedLoginAttempts(username, attempts);
            if (attempts >= 3) {
                userDao.lockUser(username);
                return "密码错误，账号已被锁定，请联系客服解锁";
            }
            return "密码错误，剩余尝试次数: " + (3 - attempts);
        }
        userDao.updateFailedLoginAttempts(username, 0); // 重置失败次数
        return "登录成功";
    }

    public boolean resetPassword(String username, String email, String newPassword) {
        User user = userDao.findByUsername(username);
        if (user == null || !user.getEmail().equals(email)) {
            return false; // 用户名或邮箱不匹配
        }
        return userDao.updatePassword(username, newPassword);
    }


    public User getUserByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public boolean updateUser(User user) {
        return userDao.updateUser(user);
    }

    public List<User> getAllUsers(String searchQuery) {
        return userDao.findAllUsers(searchQuery);
    }

    public boolean resetPassword(int userId) {
        return userDao.updatePasswordById(userId, "12345678");
    }

    public boolean blockUser(int userId) {
        return userDao.lockUserById(userId);
    }

    public boolean unblockUser(int userId) {
        return userDao.unlockUserById(userId);
    }
}


