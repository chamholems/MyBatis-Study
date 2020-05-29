package com.demo.test;

import com.demo.mapper.OrderMapper;
import com.demo.mapper.UserMapper;
import com.demo.pojo.Order;
import com.demo.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MultiTableTest {

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

    public MultiTableTest() throws IOException {
        // 1.Resources工具类，配置文件的加载，把配置文件加载成字节输入流
        resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        // 2.解析了配置文件，并创建了sqlSessionFactory
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        // 3.生成sqlSession （默认开启一个事务，但是该事务不会自动提交， 参数为true为自动提交）
        sqlSession = sqlSessionFactory.openSession();
    }

    /**
     * 一对一查询
     */
    @Test
    public void oneOnOne(){
        OrderMapper mapper = sqlSession.getMapper(OrderMapper.class);
        List<Order> orders = mapper.findAll();
        for (Order order : orders) {
            System.out.println(order);
        }
    }

    /**
     * 一对多
     */
    @Test
    public void oneToMany(){
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> userOrders = mapper.findUserOrders();
        for (User userOrder : userOrders) {
            System.out.println(userOrder);
        }
    }
}
