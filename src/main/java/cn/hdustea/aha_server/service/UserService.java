package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.config.FileUploadPathConfig;
import cn.hdustea.aha_server.dao.UserMapper;
import cn.hdustea.aha_server.entity.User;
import cn.hdustea.aha_server.util.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * 用户服务类
 *
 * @author STEA_YY
 **/
@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

    public User getUserById(int id) {
        return userMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据手机号查询用户
     *
     * @param phone 手机号
     * @return 用户实体类
     */
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

    public void updateSignedContract(String phone, boolean signedContract) {
        userMapper.updateSignedContractByPhone(signedContract, phone);
    }
}
