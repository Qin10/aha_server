package cn.hdustea.aha_server.mapper;

import cn.hdustea.aha_server.entity.UserCollection;
import org.apache.ibatis.annotations.Param;import java.util.List;

/**
 * ${description}
 *
 * @author STEA_YY
 **/
public interface UserCollectionMapper {
    int deleteByPrimaryKey(@Param("userId") Integer userId, @Param("projectId") Integer projectId);

    int insert(UserCollection record);

    int insertSelective(UserCollection record);

    UserCollection selectByPrimaryKey(@Param("userId") Integer userId, @Param("projectId") Integer projectId);

    int updateByPrimaryKeySelective(UserCollection record);

    int updateByPrimaryKey(UserCollection record);

    List<UserCollection> selectAllByUserId(@Param("userId") Integer userId);
}