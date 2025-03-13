package com.qfleaf.usercenter.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qfleaf.usercenter.model.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qfleaf.usercenter.model.vo.UserListVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
* @author qianfang
* @description 针对表【user(用户表)】的数据库操作Mapper
* @createDate 2025-02-21 21:43:47
* @Entity com.qfleaf.usercenter.model.User
*/
public interface UserMapper extends BaseMapper<User> {
    @Select("select user_id, username, phone, email, gender, create_time, update_time, role, is_deleted from public.user ${ew.customSqlSegment}")
    IPage<UserListVO> selectUserListPageVo(IPage<UserListVO> page, @Param("ew") Wrapper<User> wrapper);
}




