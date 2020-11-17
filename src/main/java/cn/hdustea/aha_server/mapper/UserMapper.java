package cn.hdustea.aha_server.mapper;

import cn.hdustea.aha_server.vo.PersonalUserInfoVo;
import cn.hdustea.aha_server.vo.UserContribPointVo;

import java.util.List;

import cn.hdustea.aha_server.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * 用户表mapper
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

    int updateSignedNoticeByPhone(@Param("updatedSignedNotice") Boolean updatedSignedNotice, @Param("phone") String phone);

    int updateSignedContractByPhone(@Param("updatedSignedContract") Boolean updatedSignedContract, @Param("phone") String phone);

    List<UserContribPointVo> selectPhoneAndContribPoint();

    PersonalUserInfoVo selectPersonalVoByPhone(@Param("phone") String phone);
}