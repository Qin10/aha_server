package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.dto.UserDto;
import cn.hdustea.aha_server.entity.User;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.mapper.UserMapper;
import cn.hdustea.aha_server.vo.PageVo;
import cn.hdustea.aha_server.vo.UserManagementVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    public User getExistUserById(int id) throws SelectException {
        User user = userMapper.selectByPrimaryKey(id);
        if (user == null) {
            throw new SelectException("用户不存在！");
        } else {
            return user;
        }
    }

    public PageVo<List<UserManagementVo>> getAllUserManagementVoPagable(int pageNum, int pageSize, Integer roleId, Boolean signedNotice, Boolean signedContract, Integer typeId, String phoneLike, String nicknameLike, String trueNameLike, String sortBy, String orderBy) throws SelectException {
        String currentSortBy = "u_id";
        String currentOrderBy = "desc";
        if (sortBy != null && !sortBy.equals("")) {
            switch (sortBy) {
                case "time": {
                    currentSortBy = "u_id";
                    break;
                }
                case "role": {
                    currentSortBy = "u_role_id";
                    break;
                }
                case "phone": {
                    currentSortBy = "u_phone";
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
        List<UserManagementVo> userManagementVos = userMapper.selectAllManagementVoByConditions(roleId, signedNotice, signedContract, typeId, phoneLike, nicknameLike, trueNameLike);
        PageInfo<UserManagementVo> pageInfo = new PageInfo<>(userManagementVos);
        return new PageVo<>(pageInfo.getPageNum(), pageInfo.getSize(), pageInfo.getList());
    }

    public UserManagementVo getUserManagementVoById(int id) {
        return userMapper.selectManagementVoByPrimaryKey(id);
    }

    /**
     * 根据手机号查询用户
     *
     * @param phone 手机号
     * @return 用户实体类
     */
    public User getExistUserByPhone(String phone) throws SelectException {
        User user = userMapper.selectByPhone(phone);
        if (user == null) {
            throw new SelectException("用户不存在！");
        } else {
            return user;
        }
    }

    public User getUserByPhone(String phone) {
        return userMapper.selectByPhone(phone);
    }

    /**
     * 保存用户
     *
     * @param user 用户实体类
     */
    public void saveUser(User user) {
        userMapper.insertSelective(user);
    }

    public void deleteUserById(int id) {
        userMapper.deleteByPrimaryKey(id);
    }

    public void updateUserById(UserDto userDto, int id) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        user.setId(id);
        userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 修改密码
     *
     * @param phone       手机号
     * @param newPassword 新的密码
     */
    public void updatePassword(String phone, String newPassword) {
        userMapper.updatePasswordByPhone(newPassword, phone);
    }

    /**
     * 修改是否同意服务协议标识
     *
     * @param phone        手机号
     * @param signedNotice 是否同意服务协议
     */
    public void updatesignedNotice(String phone, boolean signedNotice) {
        userMapper.updateSignedNoticeByPhone(signedNotice, phone);
    }

    /**
     * 修改是否签署合同标识
     *
     * @param phone          手机号
     * @param signedContract 是否签署合同
     */
    public void updateSignedContract(String phone, boolean signedContract) {
        userMapper.updateSignedContractByPhone(signedContract, phone);
    }
}
