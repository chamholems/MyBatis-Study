package com.demo.dao;

import com.demo.pojo.User;

import java.io.IOException;
import java.util.List;

public interface IOrderDao {

    /**
     * 查询所有用户
     */
    public List<User> findAll() throws IOException;
}
