package cn.hdustea.aha_server.mapper;

import cn.hdustea.aha_server.entity.SlideShowResource;import org.apache.ibatis.annotations.Param;import java.util.List;

/**
 * ${description}
 *
 * @author STEA_YY
 **/
public interface SlideShowResourceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SlideShowResource record);

    int insertSelective(SlideShowResource record);

    SlideShowResource selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SlideShowResource record);

    int updateByPrimaryKey(SlideShowResource record);

    List<SlideShowResource> selectAllByConditions(@Param("enabled") Boolean enabled);
}