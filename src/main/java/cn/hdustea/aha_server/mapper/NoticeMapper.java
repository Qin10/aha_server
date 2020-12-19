package cn.hdustea.aha_server.mapper;

import cn.hdustea.aha_server.entity.Notice;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * ${description}
 *
 * @author STEA_YY
 **/
public interface NoticeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Notice record);

    int insertSelective(Notice record);

    Notice selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Notice record);

    int updateByPrimaryKey(Notice record);

    List<Notice> selectAllByConditions(@Param("enable") Boolean enable, @Param("currentTime") Date currentTime);
}