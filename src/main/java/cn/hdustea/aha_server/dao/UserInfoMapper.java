package cn.hdustea.aha_server.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

import cn.hdustea.aha_server.entity.UserInfo;
import org.springframework.stereotype.Component;

/**
 * ${description}
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

    int updateByUserId(@Param("updated")UserInfo updated,@Param("userId")Integer userId);

    int updateAvatarFilenameByUserId(@Param("updatedAvatarFilename")String updatedAvatarFilename,@Param("userId")Integer userId);

    int updateResumeIdByUserId(@Param("updatedResumeId")String updatedResumeId,@Param("userId")Integer userId);




}