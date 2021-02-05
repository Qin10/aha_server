package cn.hdustea.aha_server.mapper;
import cn.hdustea.aha_server.entity.ProjectResourceFinancialScheme;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* ${description}
*
* @author STEA_YY
**/
public interface ProjectResourceFinancialSchemeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProjectResourceFinancialScheme record);

    int insertSelective(ProjectResourceFinancialScheme record);

    ProjectResourceFinancialScheme selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProjectResourceFinancialScheme record);

    int updateByPrimaryKey(ProjectResourceFinancialScheme record);

    ProjectResourceFinancialScheme selectByAwardLevel(@Param("awardLevel")Integer awardLevel);

    List<ProjectResourceFinancialScheme> selectAll();


}