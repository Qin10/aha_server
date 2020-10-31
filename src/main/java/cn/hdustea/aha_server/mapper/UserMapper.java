package cn.hdustea.aha_server.mapper;

import cn.hdustea.aha_server.entity.User;import org.apache.ibatis.annotations.Param;

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

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByPhone(@Param("phone") String phone);

    int updatePasswordByPhone(@Param("updatedPassword") String updatedPassword, @Param("phone") String phone);

    int updateSignedNoticeByPhone(@Param("updatedSignedNotice") Boolean updatedSignedNotice, @Param("phone") String phone);

    int updateSignedContractByPhone(@Param("updatedSignedContract")Boolean updatedSignedContract,@Param("phone")String phone);


}