package com.demo.test;

import com.demo.mapper.OrderMapper;
import com.demo.mapper.UserMapper;
import com.demo.pojo.Order;
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

public class AnnotationMultTableTest {

    private OrderMapper orderMapper;
    private UserMapper userMapper;

    @Before
    public void before() throws IOException {
        // 1.Resources工具类，配置文件的加载，把配置文件加载成字节输入流
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        // 2.解析了配置文件，并创建了sqlSessionFactory
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        // 3.生成sqlSession （默认开启一个事务，但是该事务不会自动提交， 参数为true为自动提交）
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        orderMapper = sqlSession.getMapper(OrderMapper.class);
        userMapper = sqlSession.getMapper(UserMapper.class);
    }

    /**
     * 注解 一对一
     */
    @Test
    public void onToOne() {
        List<Order> orderAndUser = orderMapper.findOrderAndUser();
        for (Order order : orderAndUser) {
            System.out.println(order);
        }
    }

    /**
     * 注解 一对多
     */
    @Test
    public void oneToMany() {
        List<User> userOrderList = userMapper.findUserOrderList();
        for (User user : userOrderList) {
            System.out.println(user);
        }
    }

    /**
     * 注解 多对多
     */
    @Test
    public void manyToMany() {
        List<User> userRoleList = userMapper.findUserAndRole();
        for (User user : userRoleList) {
            System.out.println(user);
        }
    }


}
