package com.demo.mapper;

import com.demo.pojo.User;
import org.apache.ibatis.annotations.*;
import org.mybatis.caches.redis.RedisCache;

import java.io.IOException;
import java.util.List;

@CacheNamespace(implementation = RedisCache.class)
public interface UserMapper {

    /**
     * 查询所有用户
     */
    public List<User> findAll() throws IOException;

    /**
     * 查询所有用户的所有订单
     */
    public List<User> findUserOrders();



    /**
     * 添加用户
     */
    @Insert("insert into user values (#{id},#{username})")
    void addUser(User user);

    /**
     * 更新用户
     */
    @Update("update user set username = #{username} where id = #{id}")
    void updateUser(User user);

    /**
     * 删除用户
     */
    @Delete("delete from user where id = #{id}")
    void deleteUser(Integer id);

    /**
     * 查询全部
     */
    @Select("select * from user")
    List<User> selectAll();

    /**
     * 根据id查用户
     * @param id 用户id
     * @return
     */
    @Select("select * from user where id = #{id}")
    User findById(Integer id);


    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "orderList", column = "id", javaType = List.class,
                    many = @Many(select = "com.demo.mapper.OrderMapper.findOrderByUserId"))
    })
    @Select("select * from user")
    List<User> findUserOrderList();


    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "roleList", column = "id", javaType = List.class,
                    many = @Many(select = "com.demo.mapper.RoleMapper.findRoleByUid")),
    })
    @Select("select * from user")
    List<User> findUserAndRole();
}
