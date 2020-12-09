package cn.hdustea.aha_server.mapper;
import cn.hdustea.aha_server.entity.User;
import cn.hdustea.aha_server.vo.UserContribPointVo;
import cn.hdustea.aha_server.vo.UserManagementVo;
import cn.hdustea.aha_server.vo.UserVo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

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

    UserVo selectVoByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectById(@Param("id") Integer id);

    int updatePasswordById(@Param("updatedPassword") String updatedPassword, @Param("id") Integer id);

    int updateSignedNoticeById(@Param("updatedSignedNotice") Boolean updatedSignedNotice, @Param("id") Integer id);

    int updateSignedContractById(@Param("updatedSignedContract") Boolean updatedSignedContract, @Param("id") Integer id);

    List<UserContribPointVo> selectIdAndContribPoint();

    UserManagementVo selectManagementVoByPrimaryKey(Integer id);

    List<UserManagementVo> selectAllManagementVoByConditions(@Param("roleId") Integer roleId, @Param("signedNotice") Boolean signedNotice, @Param("signedContract") Boolean signedContract, @Param("typeId") Integer typeId, @Param("nicknameLike") String nicknameLike, @Param("trueNameLike") String trueNameLike);

    int updateDecContribPointById(@Param("deductedContribPoint")BigDecimal deductedContribPoint,@Param("id")Integer id);
}