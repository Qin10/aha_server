package cn.hdustea.aha_server.mapper;
import org.apache.ibatis.annotations.Param;

import cn.hdustea.aha_server.entity.ResourceInfo;

/**
 * 资源详细信息的数据库操作接口
 *
 * @author STEA_YY
 **/
public interface ResourceInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ResourceInfo record);

    int insertSelective(ResourceInfo record);

    ResourceInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ResourceInfo record);

    int updateByPrimaryKey(ResourceInfo record);

    ResourceInfo selectByResId(@Param("resId")Integer resId);

    int updateByResId(@Param("updated")ResourceInfo updated,@Param("resId")Integer resId);


}