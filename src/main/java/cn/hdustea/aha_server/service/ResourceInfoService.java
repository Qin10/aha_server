package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.dao.ResourceInfoMapper;
import cn.hdustea.aha_server.entity.ResourceInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 资源详细信息服务类
 *
 * @author STEA_YY
 **/
@Service
public class ResourceInfoService {
    @Resource
    private ResourceInfoMapper resourceInfoMapper;

    public ResourceInfo getResourceInfoByResourceId(int resourceId) {
        return resourceInfoMapper.selectByResId(resourceId);
    }

    public void updateResourceInfoByResourceId(ResourceInfo resourceInfo, int resourceId) {
        resourceInfoMapper.updateByResId(resourceInfo, resourceId);
    }
}
