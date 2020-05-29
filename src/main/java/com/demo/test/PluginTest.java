package com.demo.test;

import com.demo.mapper.CommonUserMapper;
import com.demo.mapper.UserMapper;
import com.demo.pojo.CommonUser;
import com.demo.pojo.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.List;

public class PluginTest {
    private UserMapper userMapper;
    private CommonUserMapper commonUserMapper;

    @Before
    public void before() throws IOException {
        // 1.Resources工具类，配置文件的加载，把配置文件加载成字节输入流
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        // 2.解析了配置文件，并创建了sqlSessionFactory
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        // 3.生成sqlSession （默认开启一个事务，但是该事务不会自动提交， 参数为true为自动提交）
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        userMapper = sqlSession.getMapper(UserMapper.class);
        commonUserMapper = sqlSession.getMapper(CommonUserMapper.class);
    }

    @Test
    public void testSelect(){
        List<User> users = userMapper.selectAll();
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void testPageHelper(){
        // 设置分页参数
        PageHelper.startPage(1,2);
        List<User> userList = userMapper.selectAll();
        for (User user : userList) {
            System.out.println(user);
        }

        PageInfo<User> pageInfo = new PageInfo<>(userList);
        System.out.println("总条数"+pageInfo.getTotal());
        System.out.println("总页数"+pageInfo.getPages());
        System.out.println("当前页"+pageInfo.getPageNum());
        System.out.println("每页显示长度"+pageInfo.getPageSize());
        System.out.println("是否第一页"+pageInfo.isIsFirstPage());
        System.out.println("是否最后一页"+pageInfo.isIsLastPage());
    }

    @Test
    public void testCommonMapper(){
        // select 接口
        List<CommonUser> users = commonUserMapper.select(null);
        for (CommonUser user : users) {
            System.out.println(user);
        }
    }
}
