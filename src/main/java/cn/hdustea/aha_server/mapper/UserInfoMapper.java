package cn.hdustea.aha_server.mapper;
import cn.hdustea.aha_server.entity.UserInfo;
import cn.hdustea.aha_server.vo.UserRoughInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户详细信息表mapper
 *
 * @author STEA_YY
 **/
public interface UserInfoMapper {
    int deleteByPrimaryKey(String userPhone);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(String userPhone);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);

    int updateAvatarUrlByUserPhone(@Param("updatedAvatarUrl") String updatedAvatarUrl, @Param("userPhone") String userPhone);

    List<UserRoughInfoVo> selectAllByNicknameLike(@Param("likeNickname")String likeNickname);

    List<UserRoughInfoVo> selectAllByTrueNameLike(@Param("likeTrueName")String likeTrueName);

    List<UserRoughInfoVo> selectAllByPhoneLike(@Param("likePhone")String likePhone);
}