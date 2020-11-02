package cn.hdustea.aha_server.mapper;

import cn.hdustea.aha_server.entity.ResourceMember;
import org.apache.ibatis.annotations.Param;import java.util.List;

/**
 * ${description}
 *
 * @author STEA_YY
 **/
public interface ResourceMemberMapper {
    int deleteByPrimaryKey(@Param("resId") Integer resId, @Param("memberPhone") String memberPhone);

    int insert(ResourceMember record);

    int insertSelective(ResourceMember record);

    ResourceMember selectByPrimaryKey(@Param("resId") Integer resId, @Param("memberPhone") String memberPhone);

    int updateByPrimaryKeySelective(ResourceMember record);

    int updateByPrimaryKey(ResourceMember record);

    List<ResourceMember> selectAllByResId(@Param("resId")Integer resId);

    int insertList(@Param("list")List<ResourceMember> list,@Param("resId")Integer resId);

    int deleteByResId(@Param("resId")Integer resId);



    int updateJobByResIdAndMemberPhone(@Param("updatedJob")String updatedJob,@Param("resId")Integer resId,@Param("memberPhone")String memberPhone);


}