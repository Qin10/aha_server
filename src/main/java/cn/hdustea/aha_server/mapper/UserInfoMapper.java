package cn.hdustea.aha_server.mapper;
import java.util.List;

import cn.hdustea.aha_server.entity.UserInfo;
import cn.hdustea.aha_server.vo.UserRoughInfoVo;
import org.apache.ibatis.annotations.Param;

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

    List<UserRoughInfoVo> selectAllByNicknameLike(@Param("likeNickname")String likeNickname);

    List<UserRoughInfoVo> selectAllByTrueNameLike(@Param("likeTrueName")String likeTrueName);

    List<UserRoughInfoVo> selectAllByPhoneLike(@Param("likePhone")String likePhone);
}