package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.config.TencentCosConfig;
import cn.hdustea.aha_server.constants.RealNameAuthenticationConstants;
import cn.hdustea.aha_server.constants.UserConstants;
import cn.hdustea.aha_server.dto.RealNameAuthenticationDto;
import cn.hdustea.aha_server.entity.RealNameAuthentication;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.exception.apiException.daoException.UpdateException;
import cn.hdustea.aha_server.mapper.RealNameAuthenticationMapper;
import cn.hdustea.aha_server.vo.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 实名认证服务类
 *
 * @author STEA_YY
 **/
@Service
public class RealNameAuthenticationService {
    @Resource
    private RealNameAuthenticationMapper realNameAuthenticationMapper;
    @Resource
    private UserService userService;
    @Resource
    private CosService cosService;
    @Resource
    private TencentCosConfig tencentCosConfig;

    public Integer getAuthenticationStatusByUserId(int userId) {
        UserVo userVo = userService.getUserVoById(userId);
        return userVo.getAuthenticated();
    }

    public RealNameAuthenticationVo getRealNameAuthenticationVoByUserId(Integer userId) throws SelectException {
        RealNameAuthenticationVo realNameAuthenticationVo = realNameAuthenticationMapper.selectVoByUserId(userId);
        if (realNameAuthenticationVo == null) {
            throw new SelectException("该用户未进行实名认证");
        }
        return realNameAuthenticationVo;
    }

    public RealNameAuthentication getExistedRealNameAuthenticationByUserId(Integer userId) throws SelectException {
        RealNameAuthentication realNameAuthentication = realNameAuthenticationMapper.selectByPrimaryKey(userId);
        if (realNameAuthentication == null) {
            throw new SelectException("该用户未进行实名认证");
        }
        return realNameAuthentication;
    }

    public PageVo<List<RealNameAuthenticationVo>> getRealNameAuthenticationVosPagable(int pageNum, int pageSize, Integer status, Integer type) {
        PageHelper.startPage(pageNum, pageSize);
        PageHelper.orderBy("rna_upload_time desc");
        List<RealNameAuthenticationVo> realNameAuthenticationVos = realNameAuthenticationMapper.selectAllVoByConditions(type, status);
        PageInfo<RealNameAuthenticationVo> pageInfo = new PageInfo<>(realNameAuthenticationVos);
        return new PageVo<>(pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getList());
    }

    public CosPolicyVo signDownloadAuthenticationFileByUserIdAndFileType(int userId, String fileType) throws SelectException {
        RealNameAuthenticationVo realNameAuthenticationVo = getRealNameAuthenticationVoByUserId(userId);
        String filename;
        switch (fileType) {
            case "studentCard": {
                if (realNameAuthenticationVo.getStudentCardFilename() == null) {
                    throw new SelectException("学生证照片不存在！");
                }
                filename = realNameAuthenticationVo.getStudentCardFilename();
                break;
            }
            case "idCardFront": {
                if (realNameAuthenticationVo.getStudentCardFilename() == null) {
                    throw new SelectException("身份证正面照片不存在！");
                }
                filename = realNameAuthenticationVo.getIdCardFrontFilename();
                break;
            }
            case "idCardBack": {
                if (realNameAuthenticationVo.getStudentCardFilename() == null) {
                    throw new SelectException("身份证背面照片不存在！");
                }
                filename = realNameAuthenticationVo.getIdCardBackFilename();
                break;
            }
            default: {
                throw new SelectException("fileType参数取值错误！");
            }
        }
        return cosService.signDownloadAuthorization(filename);
    }

    public CosPostPolicyVo signUpload(String filename, int userId) {
        return cosService.signPostAuthorization("/authentication/" + userId + "/" + filename, tencentCosConfig.getProfileBucketName());
    }

    public void updateRealNameAuthenticationByUserId(RealNameAuthenticationDto realNameAuthenticationDto, int userId) {
        RealNameAuthentication realNameAuthentication = new RealNameAuthentication();
        BeanUtils.copyProperties(realNameAuthenticationDto, realNameAuthentication);
        realNameAuthentication.setUserId(userId);
        realNameAuthenticationMapper.updateByPrimaryKey(realNameAuthentication);
    }

    public void saveRealNameAuthentication(RealNameAuthentication realNameAuthentication) {
        realNameAuthenticationMapper.insertSelective(realNameAuthentication);
    }

    @Transactional(rollbackFor = Exception.class)
    public void checkAuthenticationByUserId(int userId, int status) throws SelectException, UpdateException {
        RealNameAuthentication realNameAuthentication = getExistedRealNameAuthenticationByUserId(userId);
        switch (status) {
            case RealNameAuthenticationConstants.STATUS_PASSED: {
                realNameAuthentication.setStatus(status);
                realNameAuthentication.setPassTime(new Date());
                realNameAuthenticationMapper.updateByPrimaryKey(realNameAuthentication);
                userService.updateAuthenticated(userId, UserConstants.AUTHENTICATION_STATUS_AUTHENTICATED);
                break;
            }
            case RealNameAuthenticationConstants.STATUS_NOT_PASSED: {
                realNameAuthentication.setStatus(status);
                realNameAuthenticationMapper.updateByPrimaryKey(realNameAuthentication);
                userService.updateAuthenticated(userId, UserConstants.AUTHENTICATION_STATUS_CHECK_NOT_PASS);
                break;
            }
            default: {
                throw new UpdateException("status取值错误！");
            }
        }
    }
}
