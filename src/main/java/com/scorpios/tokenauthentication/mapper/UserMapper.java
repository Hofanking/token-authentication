package com.scorpios.tokenauthentication.mapper;

import com.scorpios.tokenauthentication.entity.User;
import org.apache.ibatis.annotations.*;

/**
 * @author Think
 * @Title: UserMapper
 * @ProjectName token-authentication
 * @Description: TODO
 * @date 2019/1/1823:04
 */

@Mapper
public interface UserMapper {

        @Select("select * from t_user where username=#{name} and password=#{pw}")
        public User getUser(@Param("name") String name,@Param("pw") String pw);

//    @Select("select * from t_user where id=#{id}")
//    public User getUser(Integer id);


//        @Delete("delete from where id=#{id}")
//        public int deleteDeptById(Integer id);
//
//        @Options(useGeneratedKeys = true, keyProperty = "id")
//        @Insert("insert into department(departmentName) values (#{departmentName})")
//        public int insertDept(Department department);
//
//
//        @Update("update department set departmentName=#{departmentName} where id=#{id}")
//        public int updateDept(Department department);

}
