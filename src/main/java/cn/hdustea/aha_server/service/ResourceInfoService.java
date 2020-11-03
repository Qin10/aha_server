package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.entity.ResourceMember;
import cn.hdustea.aha_server.mapper.ResourceInfoMapper;
import cn.hdustea.aha_server.entity.ResourceInfo;
import cn.hdustea.aha_server.mapper.ResourceMemberMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 资源详细信息服务类
 *
 * @author STEA_YY
 **/
@Service
public class ResourceInfoService {
    @Resource
    private ResourceInfoMapper resourceInfoMapper;
    @Resource
    private ResourceMemberMapper resourceMemberMapper;

    public ResourceInfo getResourceInfoByResourceId(int resourceId) {
        return resourceInfoMapper.selectByResId(resourceId);
    }

    public void updateResourceInfoByResourceId(ResourceInfo resourceInfo, int resourceId) {
        resourceInfoMapper.updateByResId(resourceInfo, resourceId);
    }

    public List<ResourceMember> getAllResourceMemberByResId(int resourceId) {
        return resourceMemberMapper.selectAllByResId(resourceId);
    }

    public void saveResourceMembersByResId(List<ResourceMember> resourceMembers, int resourceId) {
        resourceMemberMapper.insertList(resourceMembers, resourceId);
    }

    public void saveResourceMemberByResId(ResourceMember resourceMember, int resourceId) {
        resourceMember.setResId(resourceId);
        resourceMemberMapper.insert(resourceMember);
    }

    public void deleteResourceMember(int resourceId, String phone) {
        resourceMemberMapper.deleteByPrimaryKey(resourceId, phone);
    }

    public void updateMemberJob(String job, int resourceId, String phone) {
        resourceMemberMapper.updateJobByResIdAndMemberPhone(job, resourceId, phone);
    }
}