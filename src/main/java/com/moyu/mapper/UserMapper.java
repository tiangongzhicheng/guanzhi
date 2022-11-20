package com.moyu.mapper;

import com.moyu.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

/**
 * Created by Administrator on 2018/4/6.
 */

@Mapper
public interface UserMapper {

    @Select("select * from user where id = #{value}")
    User getUser( Integer id);


    @SelectProvider(type = UserMapper.UserMapperProvider.class, method = "getUserByWhere")
    List<User> getUserByWhere(User user);

    class UserMapperProvider{

        public String getUserByWhere(User user){
            SQL sql = new SQL().SELECT("*")
                    .FROM("user");
                    if(user.getUserName() != null && !"".equals(user.getUserName())){
                        sql.WHERE("user_name like CONCAT('%', #{userName} , '%')");
                    }

            return sql.toString();

        }


    }

}
