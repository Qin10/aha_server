package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.config.FileUploadPathConfig;
import cn.hdustea.aha_server.constants.RealNameAuthenticationConstants;
import cn.hdustea.aha_server.entity.RealNameAuthentication;
import cn.hdustea.aha_server.exception.apiException.daoException.InsertException;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.exception.apiException.daoException.UpdateException;
import cn.hdustea.aha_server.mapper.RealNameAuthenticationMapper;
import cn.hdustea.aha_server.util.FileUtil;
import cn.hdustea.aha_server.vo.PageVo;
import cn.hdustea.aha_server.vo.RealNameAuthenticationVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 实名认证服务类
 *
 * @author STEA_YY
 **/
@Service
public class RealNameAuthenticationService {
    @Resource
    private FileUploadPathConfig fileUploadPathConfig;
    @Resource
    private RealNameAuthenticationMapper realNameAuthenticationMapper;
    @Resource
    private UserService userService;

    public RealNameAuthenticationVo getRealNameAuthenticationVoByUserId(Integer userId) throws SelectException {
        RealNameAuthenticationVo realNameAuthenticationVo = realNameAuthenticationMapper.selectVoByPrimaryKey(userId);
        if (realNameAuthenticationVo == null) {
            throw new SelectException("该用户未进行实名认证");
        }
        return realNameAuthenticationVo;
    }

    public PageVo<List<RealNameAuthenticationVo>> getRealNameAuthenticationVosPagable(int pageNum, int pageSize, Integer status, Integer type) {
        PageHelper.startPage(pageNum, pageSize);
        PageHelper.orderBy("rna_upload_time desc");
        List<RealNameAuthenticationVo> realNameAuthenticationVos = realNameAuthenticationMapper.selectAllVoByConditions(type, status);
        PageInfo<RealNameAuthenticationVo> pageInfo = new PageInfo<>(realNameAuthenticationVos);
        return new PageVo<>(pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getList());
    }

    public void getAuthenticationFileByUserIdAndFileType(int userId, String fileType, HttpServletResponse response) throws SelectException, IOException {
        RealNameAuthenticationVo realNameAuthenticationVo = getRealNameAuthenticationVoByUserId(userId);
        String filePath = null;
        switch (fileType) {
            case "studentCard": {
                if (realNameAuthenticationVo.getStudentCardFilename() == null) {
                    throw new SelectException("学生证照片不存在！");
                }
                filePath = fileUploadPathConfig.getAuthenticationFilesPath() + realNameAuthenticationVo.getStudentCardFilename();
                break;
            }
            case "idCardFront": {
                if (realNameAuthenticationVo.getStudentCardFilename() == null) {
                    throw new SelectException("身份证正面照片不存在！");
                }
                filePath = fileUploadPathConfig.getAuthenticationFilesPath() + realNameAuthenticationVo.getIdCardFrontFilename();
                break;
            }
            case "idCardBack": {
                if (realNameAuthenticationVo.getStudentCardFilename() == null) {
                    throw new SelectException("身份证背面照片不存在！");
                }
                filePath = fileUploadPathConfig.getAuthenticationFilesPath() + realNameAuthenticationVo.getIdCardBackFilename();
                break;
            }
            default: {
                throw new SelectException("fileType参数取值错误！");
            }
        }
        FileUtil.download(filePath, response);
    }

    public void saveRealNameAuthentication(RealNameAuthentication realNameAuthentication) throws InsertException {
        try {
            realNameAuthenticationMapper.insert(realNameAuthentication);
        } catch (DuplicateKeyException e) {
            throw new InsertException("实名认证记录已存在！");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void checkAuthenticationByUserId(int userId, int status) throws SelectException, UpdateException {
        RealNameAuthenticationVo realNameAuthenticationVo = getRealNameAuthenticationVoByUserId(userId);
        switch (status) {
            case RealNameAuthenticationConstants.STATUS_PASSED: {
                realNameAuthenticationMapper.updateStatusByUserId(status, userId);
                userService.updateAuthenticated(userId, true);
                break;
            }
            case RealNameAuthenticationConstants.STATUS_NOT_PASSED: {
                try {
                    FileUtil.delete(fileUploadPathConfig.getAuthenticationFilesPath() + realNameAuthenticationVo.getStudentCardFilename());
                } catch (Exception ignore) {
                }
                try {
                    FileUtil.delete(fileUploadPathConfig.getAuthenticationFilesPath() + realNameAuthenticationVo.getIdCardFrontFilename());
                } catch (Exception ignore) {
                }
                try {
                    FileUtil.delete(fileUploadPathConfig.getAuthenticationFilesPath() + realNameAuthenticationVo.getIdCardBackFilename());
                } catch (Exception ignore) {
                }
                realNameAuthenticationMapper.deleteByPrimaryKey(userId);
                userService.updateAuthenticated(userId, false);
                break;
            }
            default: {
                throw new UpdateException("status取值错误！");
            }
        }
    }
}
