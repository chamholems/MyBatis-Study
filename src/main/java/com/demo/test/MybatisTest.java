package com.demo.test;

import com.demo.dao.IUserDao;
import com.demo.dao.impl.UserDaoImpl;
import com.demo.mapper.UserMapper;
import com.demo.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.*;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MybatisTest {

    /**
     * 加载核心配置文件
     */
    private final InputStream resourceAsStream;

    /**
     * 获得sqlSession工厂对象
     */
    private final SqlSessionFactory sqlSessionFactory;

    /**
     * 获得sqlSession 对象
     */
    private final SqlSession sqlSession;

    public MybatisTest() throws IOException {
        // 1.Resources工具类，配置文件的加载，把配置文件加载成字节输入流
        resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        // 2.解析了配置文件，并创建了sqlSessionFactory
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        // 3.生成sqlSession （默认开启一个事务，但是该事务不会自动提交， 参数为true为自动提交）
        sqlSession = sqlSessionFactory.openSession();
    }

    /**
     * 查找所有用户
     */
    @Test
    public void findAll() {
        // 4.SqlSession调用方法
        List<User> userList = sqlSession.selectList("com.demo.mapper.UserMapper.findAll");
        for (User user : userList) {
            System.out.println(user);
        }
        // 释放资源
        sqlSession.close();
    }

    /**
     * 添加用户
     */
    @Test
    public void saveUser() {
        User user = new User();
        user.setId(4);
        user.setUsername("cham");
        sqlSession.insert("com.demo.mapper.UserMapper.saveUser",user);
        sqlSession.commit();
        // 释放资源
        sqlSession.close();
    }

    /**
     * 查找单个用户
     */
    @Test
    public void findUser() {
        User user = new User();
        user.setId(3);
        user.setUsername("kk");
        User userInfo = sqlSession.selectOne("com.demo.mapper.UserMapper.findUser", user);
        System.out.println(userInfo);
        // 释放资源
        sqlSession.close();
    }

    /**
     * 修改用户
     */
    @Test
    public void updateUser(){
        User user = new User();
        user.setId(4);
        user.setUsername("ccham");
        sqlSession.update("userMapper.updateUser",user);
        sqlSession.commit();
        sqlSession.close();
    }

    /**
     * 删除用户
     */
    @Test
    public void deleteUser(){
        sqlSession.delete("userMapper.deleteUser",2);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void test1() throws IOException {
        IUserDao userDao = new UserDaoImpl();
        List<User> all = userDao.findAll();
        for (User user : all) {
            System.out.println(user);
        }

    }

    @Test
    public void test2() throws IOException {
        // 1.Resources工具类，配置文件的加载，把配置文件加载成字节输入流
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        // 2.解析了配置文件，并创建了sqlSessionFactory
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        // 3.生成sqlSession （默认开启一个事务，但是该事务不会自动提交， 参数为true为自动提交）
        SqlSession sqlSession = sqlSessionFactory.openSession();

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> all = mapper.findAll();
        for (User user : all) {
            System.out.println(user);
        }

    }

}
