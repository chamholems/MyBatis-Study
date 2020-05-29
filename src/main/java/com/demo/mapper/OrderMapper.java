package com.demo.mapper;

import com.demo.pojo.Order;
import com.demo.pojo.User;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OrderMapper {

    public List<Order> findAll();

    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "orderTime", column = "order_time"),
            @Result(property = "total", column = "total"),
            @Result(property = "user", column = "uid", javaType = User.class, one = @One(select = "com.demo.mapper.UserMapper.findById")),
    })
    @Select("select * from `order`")
    List<Order> findOrderAndUser();

    @Select("select * from `order` where uid = #{uid}")
    List<Order> findOrderByUserId(Integer uid);
}
