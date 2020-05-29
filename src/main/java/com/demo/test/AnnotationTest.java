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
import java.util.List;

public class AnnotationTest {

    private UserMapper userMapper;

    @Before
    public void before() throws IOException {
        // 1.Resources工具类，配置文件的加载，把配置文件加载成字节输入流
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        // 2.解析了配置文件，并创建了sqlSessionFactory
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        // 3.生成sqlSession （默认开启一个事务，但是该事务不会自动提交， 参数为true为自动提交）
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        userMapper = sqlSession.getMapper(UserMapper.class);
    }

    @Test
    public void testSelect(){
        List<User> users = userMapper.selectAll();
        for (User user : users) {
            System.out.println(user); 
        }
    }

    @Test
    public void testInsert(){
        User user = new User();
        user.setId(6);
        user.setUsername("测试数据");
        userMapper.addUser(user);
        testSelect();
    }

    @Test
    public void testUpdate(){
        User user = new User();
        user.setId(6);
        user.setUsername("修改测试数据");
        userMapper.updateUser(user);
        testSelect();
    }

    @Test
    public void testDelete(){
        userMapper.deleteUser(6);
        testSelect();
    }
}
