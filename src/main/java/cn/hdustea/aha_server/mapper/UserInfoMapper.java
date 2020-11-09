package cn.hdustea.aha_server.mapper;

import cn.hdustea.aha_server.entity.UserInfo;import org.apache.ibatis.annotations.Param;

/**
 * 用户详细信息表mapper
 *
 * @author STEA_YY
 **/
public interface UserInfoMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);

    UserInfo selectByUserId(@Param("userId") Integer userId);

    int updateByUserId(@Param("updated") UserInfo updated, @Param("userId") Integer userId);

    int updateAvatarUrlByUserId(@Param("updatedAvatarUrl") String updatedAvatarUrl, @Param("userId") Integer userId);
}