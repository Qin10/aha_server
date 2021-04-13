package cn.hdustea.aha_server.mapper;

import cn.hdustea.aha_server.entity.User;
import cn.hdustea.aha_server.vo.*;
import org.apache.ibatis.annotations.Param;import java.math.BigDecimal;import java.util.List;

/**
 * ${description}
 *
 * @author STEA_YY
 **/
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    UserVo selectVoByPrimaryKey(Integer id);

    User selectById(@Param("id") Integer id);

    int updatePasswordById(@Param("updatedPassword") String updatedPassword, @Param("id") Integer id);

    int updateSignedNoticeById(@Param("updatedSignedNotice") Boolean updatedSignedNotice, @Param("id") Integer id);

    int updateSignedContractById(@Param("updatedSignedContract") Boolean updatedSignedContract, @Param("id") Integer id);

    int updateAuthenticatedById(@Param("updatedAuthenticated") Integer updatedAuthenticated, @Param("id") Integer id);

    List<UserContribPointVo> selectIdAndContribPoint();

    UserManagementVo selectManagementVoByPrimaryKey(Integer id);

    List<UserManagementVo> selectAllManagementVoByConditions(@Param("roleId") Integer roleId, @Param("signedNotice") Boolean signedNotice, @Param("signedContract") Boolean signedContract, @Param("typeId") Integer typeId, @Param("nicknameLike") String nicknameLike, @Param("trueNameLike") String trueNameLike);

    int updateDecAhaPointById(@Param("deductedAhaPoint") BigDecimal deductedAhaPoint, @Param("id") Integer id);

    int updateDecAhaCreditById(@Param("deductedAhaCredit") BigDecimal deductedAhaCredit, @Param("id") Integer id);

    int updateIncAhaPointById(@Param("addedAhaPoint") BigDecimal addedAhaPoint, @Param("id") Integer id);

    int updateIncAhaCreditById(@Param("addedAhaCredit") BigDecimal addedAhaCredit, @Param("id") Integer id);
}