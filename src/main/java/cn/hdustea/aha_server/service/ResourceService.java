package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.dao.ResourceMapper;
import cn.hdustea.aha_server.dao.UserInfoMapper;
import cn.hdustea.aha_server.entity.User;
import cn.hdustea.aha_server.entity.UserInfo;
import cn.hdustea.aha_server.exception.apiException.daoException.DeleteException;
import cn.hdustea.aha_server.exception.apiException.daoException.UpdateException;
import org.springframework.stereotype.Service;
import cn.hdustea.aha_server.entity.Resource;

/**
 * 竞赛资源的服务类
 *
 * @author STEA_YY
 **/
@Service
public class ResourceService {
    @javax.annotation.Resource
    private ResourceMapper resourceMapper;
    @javax.annotation.Resource
    private UserService userService;

    public Resource getResourceById(int id) {
        return resourceMapper.selectByPrimaryKey(id);
    }

    public void saveResource(Resource resource) {
        resourceMapper.insertSelective(resource);
    }

    public void saveResourceAndAuthor(Resource resource, String phone) {
        User user = userService.getUserByPhone(phone);
        resource.setAuthorUserId(user.getId());
        resourceMapper.insertSelective(resource);
    }

    public void updateResource(Resource resource) throws UpdateException {
        if (resourceMapper.selectByPrimaryKey(resource.getId()) == null) {
            throw new UpdateException("不存在对应记录！");
        }
        resourceMapper.updateByPrimaryKeySelective(resource);
    }

    public boolean hasPermission(String phone, int id) {
        Integer authorUserId = resourceMapper.selectAuthorUserIdById(id);
        User user = userService.getUserByPhone(phone);
        return user.getId().equals(authorUserId);
    }

    public void deleteResourceById(int id) throws DeleteException {
        if (resourceMapper.selectByPrimaryKey(id) == null) {
            throw new DeleteException("不存在对应记录！");
        }
        resourceMapper.deleteByPrimaryKey(id);
    }
}
