package cn.hdustea.aha_server.mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

import cn.hdustea.aha_server.entity.Activity;

/**
 * ${description}
 *
 * @author STEA_YY
 **/
public interface ActivityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Activity record);

    int insertSelective(Activity record);

    Activity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Activity record);

    int updateByPrimaryKey(Activity record);

    List<Activity> selectAll();


}