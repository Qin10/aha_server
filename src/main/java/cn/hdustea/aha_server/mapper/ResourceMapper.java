package cn.hdustea.aha_server.mapper;

import cn.hdustea.aha_server.entity.Resource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ${description}
 *
 * @author STEA_YY
 **/
public interface ResourceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Resource record);

    int insertSelective(Resource record);

    Resource selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Resource record);

    int updateByPrimaryKey(Resource record);

    List<Resource> selectAll();



    String selectAuthorPhoneById(@Param("id") Integer id);

    List<Integer> selectId();

    int updateReadById(@Param("updatedRead") Integer updatedRead, @Param("id") Integer id);
}