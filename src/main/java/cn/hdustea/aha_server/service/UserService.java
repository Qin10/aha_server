package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.dto.UserDto;
import cn.hdustea.aha_server.entity.User;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.mapper.UserMapper;
import cn.hdustea.aha_server.vo.PageVo;
import cn.hdustea.aha_server.vo.UserManagementVo;
import cn.hdustea.aha_server.vo.UserVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * 用户服务类
 *
 * @author STEA_YY
 **/
@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

    /**
     * 根据id查找用户，如果未找到则抛出异常
     *
     * @param id 用户id
     * @return 用户
     * @throws SelectException 查询异常
     */
    public UserVo getExistUserVoById(int id) throws SelectException {
        UserVo userVo = userMapper.selectVoByPrimaryKey(id);
        if (userVo == null) {
            throw new SelectException("用户不存在！");
        } else {
            return userVo;
        }
    }

    /**
     * 根据id查找用户
     *
     * @param id 用户id
     * @return 用户
     */
    public UserVo getUserVoById(int id) {
        return userMapper.selectVoByPrimaryKey(id);
    }

    /**
     * @param pageNum        页码
     * @param pageSize       分页大小
     * @param roleId         角色id
     * @param signedNotice   是否签署服务协议
     * @param signedContract 是否签署合同
     * @param typeId         用户类型id
     * @param nicknameLike   模糊昵称
     * @param trueNameLike   模糊真实姓名
     * @param sortBy         排序依据
     * @param orderBy        排序方式
     * @return 用户列表
     * @throws SelectException 查询异常
     */
    public PageVo<List<UserManagementVo>> getAllUserManagementVoPagable(int pageNum, int pageSize, Integer roleId, Boolean signedNotice, Boolean signedContract, Integer typeId, String nicknameLike, String trueNameLike, String sortBy, String orderBy) throws SelectException {
        String currentSortBy = "u_id";
        String currentOrderBy = "desc";
        if (sortBy != null && !sortBy.equals("")) {
            switch (sortBy) {
                case "time":
                case "id": {
                    currentSortBy = "u_id";
                    break;
                }
                case "role": {
                    currentSortBy = "u_role_id";
                    break;
                }
                case "contribPoint": {
                    currentSortBy = "u_contrib_point";
                    break;
                }
                default: {
                    throw new SelectException("'orderBy'参数取值错误！");
                }
            }
        }
        if (orderBy != null && !orderBy.equals("")) {
            switch (orderBy) {
                case "desc": {
                    currentOrderBy = "desc";
                    break;
                }
                case "asc": {
                    currentOrderBy = "asc";
                    break;
                }
                default: {
                    throw new SelectException("'orderBy'参数取值错误！");
                }
            }
        }
        PageHelper.startPage(pageNum, pageSize);
        PageHelper.orderBy(currentSortBy + " " + currentOrderBy);
        List<UserManagementVo> userManagementVos = userMapper.selectAllManagementVoByConditions(roleId, signedNotice, signedContract, typeId, nicknameLike, trueNameLike);
        PageInfo<UserManagementVo> pageInfo = new PageInfo<>(userManagementVos);
        return new PageVo<>(pageInfo.getPageNum(), pageInfo.getSize(), pageInfo.getList());
    }

    /**
     * 根据id获取用户管理信息
     *
     * @param id 用户id
     * @return 用户管理信息
     */
    public UserManagementVo getUserManagementVoById(int id) {
        return userMapper.selectManagementVoByPrimaryKey(id);
    }

    /**
     * 保存用户
     *
     * @param user 用户实体类
     */
    public void saveUser(User user) {
        userMapper.insertSelective(user);
    }

    /**
     * 根据id删除用户
     *
     * @param id 用户id
     */
    public void deleteUserById(int id) {
        userMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据id更新用户私有信息
     *
     * @param userDto 用户私有信息
     * @param id      用户id
     */
    public void updateUserById(UserDto userDto, int id) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        user.setId(id);
        userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 修改密码
     *
     * @param id          用户id
     * @param newPassword 新的密码
     */
    public void updatePassword(Integer id, String newPassword) {
        userMapper.updatePasswordById(newPassword, id);
    }

    /**
     * 修改是否同意服务协议标识
     *
     * @param id           用户id
     * @param signedNotice 是否同意服务协议
     */
    public void updatesignedNotice(Integer id, boolean signedNotice) {
        userMapper.updateSignedNoticeById(signedNotice, id);
    }

    /**
     * 修改是否签署合同标识
     *
     * @param id             用户id
     * @param signedContract 是否签署合同
     */
    public void updateSignedContract(Integer id, boolean signedContract) {
        userMapper.updateSignedContractById(signedContract, id);
    }

    public void updateAuthenticated(Integer id, boolean authenticated) {
        userMapper.updateAuthenticatedById(authenticated, id);
    }

    /**
     * 根据用户id减少Aha点
     *
     * @param id           用户id
     * @param descAhaPoint 减少的Aha点
     */
    public void updateDescAhaPoint(Integer id, BigDecimal descAhaPoint) {
        userMapper.updateDecAhaPointById(descAhaPoint, id);
    }

    /**
     * 根据用户id减少Aha币
     *
     * @param id            用户id
     * @param descAhaCredit 减少的Aha币
     */
    public void updateDescAhaCredit(Integer id, BigDecimal descAhaCredit) {
        userMapper.updateDecAhaCreditById(descAhaCredit, id);
    }

    /**
     * 根据用户id增加Aha点
     *
     * @param id          用户id
     * @param incAhaPoint 增加的Aha点
     */
    public void updateIncAhaPoint(Integer id, BigDecimal incAhaPoint) {
        userMapper.updateIncAhaPointById(incAhaPoint, id);
    }

    /**
     * 根据用户id增加Aha币
     *
     * @param id           用户id
     * @param incAhaCredit 增加的Aha币
     */
    @SuppressWarnings("unused")
    public void updateIncAhaCredit(Integer id, BigDecimal incAhaCredit) {
        userMapper.updateIncAhaCreditById(incAhaCredit, id);
    }
}
