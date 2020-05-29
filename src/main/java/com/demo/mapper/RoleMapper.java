package com.demo.mapper;

import com.demo.pojo.Role;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface RoleMapper {

    /**
     * 根据用户ID查询拥有的角色
     * @param uid
     * @return
     */
    @Select("select * from role r,user_role ur where r.id=ur.role_id and user_id=#{uid}")
    List<Role> findRoleByUid(Integer uid);
}
