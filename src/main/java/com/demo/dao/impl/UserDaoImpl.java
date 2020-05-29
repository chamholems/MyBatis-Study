package com.demo.dao.impl;

import com.demo.dao.IUserDao;
import com.demo.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class UserDaoImpl implements IUserDao {
    @Override
    public List<User> findAll() throws IOException {
        // 1.Resources工具类，配置文件的加载，把配置文件加载成字节输入流
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        // 2.解析了配置文件，并创建了sqlSessionFactory
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        // 3.生成sqlSession （默认开启一个事务，但是该事务不会自动提交， 参数为true为自动提交）
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 4.SqlSession调用方法
        List<User> userList = sqlSession.selectList("userMapper.findAll");
        return userList;
    }
}
