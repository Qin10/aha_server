package cn.hdustea.aha_server.mapper;
import java.util.List;

import cn.hdustea.aha_server.entity.UserCollection;
import org.apache.ibatis.annotations.Param;

/**
 * ${description}
 *
 * @author STEA_YY
 **/
public interface UserCollectionMapper {
    int deleteByPrimaryKey(@Param("userId") Integer userId, @Param("resId") Integer resId);

    int insert(UserCollection record);

    int insertSelective(UserCollection record);

    UserCollection selectByPrimaryKey(@Param("userId") Integer userId, @Param("resId") Integer resId);

    int updateByPrimaryKeySelective(UserCollection record);

    int updateByPrimaryKey(UserCollection record);

    List<UserCollection> selectAllByUserId(@Param("userId")Integer userId);

}