package cn.hdustea.aha_server.dao;

import cn.hdustea.aha_server.entity.SysLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Component;

/**
 * 系统日志Dao类
 *
 * @author STEA_YY
 */
@Component
@Mapper
public interface SysLogDao {
    @Insert("INSERT INTO sys_log (account,operation,time,method,params,ip,create_time) VALUES (#{account},#{operation},#{time},#{method},#{params},#{ip},#{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void saveSysLog(SysLog sysLog);
}