package cn.hdustea.aha_server.mapper;

import org.apache.ibatis.annotations.Param;

import cn.hdustea.aha_server.entity.UserInfo;

/**
 * 用户公共信息
 *
 * @author STEA_YY
 **/
public interface UserInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Integer id);

    UserInfo selectByUserId(@Param("userId") Integer userId);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);

    int updateByUserId(@Param("updated") UserInfo updated, @Param("userId") Integer userId);

    int updateAvatarUrlByUserId(@Param("updatedAvatarUrl") String updatedAvatarUrl, @Param("userId") Integer userId);

    int updateResumeIdByUserId(@Param("updatedResumeId") String updatedResumeId, @Param("userId") Integer userId);

}