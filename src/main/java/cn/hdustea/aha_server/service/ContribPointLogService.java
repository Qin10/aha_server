package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.entity.ContribPointLog;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.mapper.ContribPointLogMapper;
import cn.hdustea.aha_server.vo.PageVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 贡献点变动日志服务类
 *
 * @author STEA_YY
 **/
@Service
public class ContribPointLogService {
    @Resource
    private ContribPointLogMapper contribPointLogMapper;

    public void saveContribPointLog(ContribPointLog contribPointLog) {
        contribPointLogMapper.insertSelective(contribPointLog);
    }

    public PageVo<List<ContribPointLog>> getAllContribPointLogPagableByUserIdAndConditions(int pageNum, int pageSize, int userId, Integer type, String sortBy, String orderBy) throws SelectException {
        String currentSortBy;
        String currentOrderBy;
        switch (sortBy) {
            case "time": {
                currentSortBy = "cl_time";
                break;
            }
            case "type": {
                currentSortBy = "cl_type";
                break;
            }
            case "contribPoint": {
                currentSortBy = "cl_aha_point_amount+cl_aha_credit_amount";
                break;
            }
            case "ahaPoint": {
                currentSortBy = "cl_aha_point_amount";
                break;
            }
            case "ahaCredit": {
                currentSortBy = "cl_aha_credit_amount";
                break;
            }
            default: {
                throw new SelectException("'sortBy'参数取值错误！");
            }
        }
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
        PageHelper.startPage(pageNum, pageSize);
        PageHelper.orderBy(currentSortBy + " " + currentOrderBy);
        List<ContribPointLog> contribPointLogs = contribPointLogMapper.selectAllByConditions(userId, type);
        PageInfo<ContribPointLog> pageInfo = new PageInfo<>(contribPointLogs);
        return new PageVo<>(pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getList());
    }
}
