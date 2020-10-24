package cn.hdustea.aha_server.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

import cn.hdustea.aha_server.entity.User;
import org.springframework.stereotype.Component;

import javax.jws.soap.SOAPBinding;

/**
 * ${description}
 *
 * @author STEA_YY
 **/
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    User selectByPhone(@Param("phone") String phone);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int updatePasswordByPhone(@Param("updatedPassword")String updatedPassword,@Param("phone")String phone);


}