package cn.hdustea.aha_server.mapper;

import cn.hdustea.aha_server.entity.ProjectResource;
import cn.hdustea.aha_server.vo.ProjectResourceVo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * ${description}
 *
 * @author STEA_YY
 **/
public interface ProjectResourceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProjectResource record);

    int insertSelective(ProjectResource record);

    ProjectResource selectByPrimaryKey(Integer id);

    ProjectResourceVo selectVoByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProjectResource record);

    int updateByPrimaryKey(ProjectResource record);

    List<ProjectResource> selectAllByProjectId(@Param("projectId") Integer projectId);

    List<ProjectResourceVo> selectAllVoByProjectIdAndNotFreezed(@Param("projectId") Integer projectId);

    List<ProjectResourceVo> selectAllVoByProjectIdAndFreezed(@Param("projectId") Integer projectId);

    int updatePreviewUrlById(@Param("updatedPreviewUrl") String updatedPreviewUrl, @Param("id") Integer id);

    int updateIncDownloadById(@Param("id") Integer id);

    int updateFreezedByFilename(@Param("updatedFreezed") Boolean updatedFreezed, @Param("filename") String filename);

    ProjectResource selectByFilename(@Param("filename") String filename);

    int updateScoreAndScoreCountById(@Param("updatedScore") BigDecimal updatedScore, @Param("updatedScoreCount") Integer updatedScoreCount, @Param("id") Integer id);

    int updatePriceAndDiscountById(@Param("updatedPrice")BigDecimal updatedPrice,@Param("updatedDiscount")BigDecimal updatedDiscount,@Param("id")Integer id);


}