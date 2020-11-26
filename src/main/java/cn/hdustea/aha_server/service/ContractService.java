package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.config.FileUploadPathConfig;
import cn.hdustea.aha_server.entity.Contract;
import cn.hdustea.aha_server.exception.apiException.daoException.InsertException;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.mapper.ContractMapper;
import cn.hdustea.aha_server.util.FileUtil;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 合同服务类
 *
 * @author STEA_YY
 **/
@Service
public class ContractService {
    @Resource
    private ContractMapper contractMapper;
    @Resource
    private FileUploadPathConfig fileUploadPathConfig;

    public Contract getContractByUserId(int userId) {
        return contractMapper.selectByUserId(userId);
    }

    public void getContractSignatureFile(int userId, HttpServletResponse response) throws IOException, SelectException {
        Contract contract = contractMapper.selectByUserId(userId);
        if (contract != null && contract.getSignatureFilename() != null) {
            String filePath = fileUploadPathConfig.getContractSignaturePath() + contract.getSignatureFilename();
            FileUtil.download(filePath, response);
        } else {
            throw new SelectException("合同文件不存在！");
        }
    }

    /**
     * 保存合同
     *
     * @param contract 合同信息
     */
    public void saveContract(Contract contract) throws InsertException {
        try {
            contractMapper.insert(contract);
        } catch (DuplicateKeyException e) {
            throw new InsertException("合同签署记录已存在！");
        }

    }
}
