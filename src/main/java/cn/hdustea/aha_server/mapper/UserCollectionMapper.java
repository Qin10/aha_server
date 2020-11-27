package cn.hdustea.aha_server.mapper;

import cn.hdustea.aha_server.entity.UserCollection;
import cn.hdustea.aha_server.vo.UserCollectionVo;
import org.apache.ibatis.annotations.Param;import java.util.List;

/**
 * 用户收藏表mapper
 *
 * @author STEA_YY
 **/
public interface UserCollectionMapper {
    int deleteByPrimaryKey(@Param("userPhone") String userPhone, @Param("projectId") Integer projectId);

    int insert(UserCollection record);

    int insertSelective(UserCollection record);

    UserCollection selectByPrimaryKey(@Param("userPhone") String userPhone, @Param("projectId") Integer projectId);

    int updateByPrimaryKeySelective(UserCollection record);

    int updateByPrimaryKey(UserCollection record);

    List<UserCollection> selectAllByUserPhone(@Param("userPhone") String userPhone);

    List<UserCollectionVo> selectAllVoByUserPhone(@Param("userPhone") String userPhone);
}