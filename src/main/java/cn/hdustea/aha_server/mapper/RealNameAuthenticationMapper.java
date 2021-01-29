package cn.hdustea.aha_server.mapper;

import cn.hdustea.aha_server.entity.RealNameAuthentication;import cn.hdustea.aha_server.vo.RealNameAuthenticationVo;import org.apache.ibatis.annotations.Param;import java.util.List;

/**
 * ${description}
 *
 * @author STEA_YY
 **/
public interface RealNameAuthenticationMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(RealNameAuthentication record);

    int insertSelective(RealNameAuthentication record);

    RealNameAuthentication selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(RealNameAuthentication record);

    int updateByPrimaryKey(RealNameAuthentication record);

    RealNameAuthenticationVo selectVoByPrimaryKey(Integer userId);

    List<RealNameAuthenticationVo> selectAllVoByConditions(@Param("type") Integer type, @Param("status") Integer status);

    int updateStatusByUserId(@Param("updatedStatus") Integer updatedStatus, @Param("userId") Integer userId);
}