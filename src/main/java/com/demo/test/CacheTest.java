package com.demo.test;

import com.demo.mapper.UserMapper;
import com.demo.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class CacheTest {

    private UserMapper userMapper;

    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void before() throws IOException {
        // 1.Resources工具类，配置文件的加载，把配置文件加载成字节输入流
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        // 2.解析了配置文件，并创建了sqlSessionFactory
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        // 3.生成sqlSession （默认开启一个事务，但是该事务不会自动提交， 参数为true为自动提交）
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        userMapper = sqlSession.getMapper(UserMapper.class);
    }

    @Test
    public void fistLevelCache() throws IOException {
        // 第一次查询id为1的用户
        User user1 = userMapper.findById(1);
        System.out.println("*******第一次查询的user对象*******");
        System.out.println(user1);

//        System.out.println("*******修改第一次查询的user对象*******");
//        user1.setUsername("原始数据");
//        userMapper.updateUser(user1);
        test();


        System.out.println("*******第二次查询*******");
        // 第二次查询id为1的用户
        User user2 = userMapper.findById(1);
        System.out.println(user2);

        System.out.println(user1 == user2);
    }

    @Test
    public void test() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = new User();
        user.setId(1);
        user.setUsername("修改后的数据");
        mapper.updateUser(user);
        System.out.println("test方法修改user1,修改后为：");
        User user1 = mapper.findById(1);
        System.out.println("*******修改后查询的user对象*******");
        System.out.println(user1);

    }

    @Test
    public void secondLevelCache(){
        SqlSession sqlSession1 = sqlSessionFactory.openSession();
        SqlSession sqlSession2 = sqlSessionFactory.openSession();
        SqlSession sqlSession3 = sqlSessionFactory.openSession();

        UserMapper mapper1 = sqlSession1.getMapper(UserMapper.class);
        UserMapper mapper2 = sqlSession2.getMapper(UserMapper.class);
        UserMapper mapper3 = sqlSession3.getMapper(UserMapper.class);

        User user1 = mapper1.findById(1);
        // 清空一级缓存
        sqlSession1.close();
        User user2 = mapper2.findById(1);
        // 比较地址值： false （二级缓存存储的不是对象  而是对象的数据）
        System.out.println(user1 == user2);

        User user = new User();
        user.setId(1);
        user.setUsername("二级缓存修改");
        mapper3.updateUser(user);
        // 此时二级缓存被清空
        User user3 = mapper2.findById(1);

    }


}
