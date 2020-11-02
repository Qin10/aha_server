package cn.hdustea.aha_server.mapper;

import java.util.List;

import cn.hdustea.aha_server.entity.ResourceInfo;
import org.apache.ibatis.annotations.Param;

/**
 * ${description}
 *
 * @author STEA_YY
 **/
public interface ResourceInfoMapper {
    int deleteByPrimaryKey(Integer resId);

    int insert(ResourceInfo record);

    int insertSelective(ResourceInfo record);

    ResourceInfo selectByPrimaryKey(Integer resId);

    int updateByPrimaryKeySelective(ResourceInfo record);

    int updateByPrimaryKey(ResourceInfo record);

    ResourceInfo selectByResId(@Param("resId") Integer resId);

    int updateByResId(@Param("updated") ResourceInfo updated, @Param("resId") Integer resId);

}