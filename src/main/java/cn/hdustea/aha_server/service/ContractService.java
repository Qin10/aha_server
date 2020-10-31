package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.mapper.ContractMapper;
import cn.hdustea.aha_server.entity.Contract;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 合同服务类
 *
 * @author STEA_YY
 **/
@Service
public class ContractService {
    @Resource
    private ContractMapper contractMapper;

    public void saveContract(Contract contract) {
        contractMapper.insert(contract);
    }
}
