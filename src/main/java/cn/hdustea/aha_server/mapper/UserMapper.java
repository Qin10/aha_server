package cn.hdustea.aha_server.mapper;

import cn.hdustea.aha_server.entity.User;
import cn.hdustea.aha_server.vo.UserContribPointVo;
import cn.hdustea.aha_server.vo.UserManagementVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    UserManagementVo selectManagementVoByPrimaryKey(Integer id);

    List<UserManagementVo> selectAllManagementVoByConditions(@Param("roleId") Integer roleId, @Param("signedNotice") Boolean signedNotice, @Param("signedContract") Boolean signedContract, @Param("typeId") Integer typeId, @Param("phoneLike") String phoneLike, @Param("nicknameLike") String nicknameLike, @Param("trueNameLike") String trueNameLike, @Param("sortBy") String sortBy, @Param("orderBy") String orderBy);
}