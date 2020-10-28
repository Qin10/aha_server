package cn.hdustea.aha_server.dao;

import cn.hdustea.aha_server.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * 用户的数据库操作接口
 *
 * @author STEA_YY
 **/
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByPhone(@Param("phone") String phone);

    int updatePasswordByPhone(@Param("updatedPassword") String updatedPassword, @Param("phone") String phone);

    int updatesignedNoticeByPhone(@Param("updatedsignedNotice") Boolean updatedsignedNotice, @Param("phone") String phone);

}