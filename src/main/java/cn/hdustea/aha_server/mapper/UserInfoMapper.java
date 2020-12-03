package cn.hdustea.aha_server.mapper;

import cn.hdustea.aha_server.entity.UserInfo;
import cn.hdustea.aha_server.vo.UserRoughInfoVo;
import org.apache.ibatis.annotations.Param;

/**
 * ${description}
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

    int updateAvatarUrlByUserId(@Param("updatedAvatarUrl") String updatedAvatarUrl, @Param("userId") Integer userId);

    UserRoughInfoVo selectRoughVoByPrimaryKey(Integer userId);
}