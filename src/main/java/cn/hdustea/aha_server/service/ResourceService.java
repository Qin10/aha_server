package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.dao.ResourceInfoMapper;
import cn.hdustea.aha_server.dao.ResourceMapper;
import cn.hdustea.aha_server.dao.UserInfoMapper;
import cn.hdustea.aha_server.entity.ResourceInfo;
import cn.hdustea.aha_server.entity.User;
import cn.hdustea.aha_server.entity.UserInfo;
import cn.hdustea.aha_server.exception.apiException.daoException.DeleteException;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.exception.apiException.daoException.UpdateException;
import cn.hdustea.aha_server.util.RedisUtil;
import org.springframework.stereotype.Service;
import cn.hdustea.aha_server.entity.Resource;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;

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
    private ResourceInfoMapper resourceInfoMapper;
    @javax.annotation.Resource
    private UserService userService;
    @javax.annotation.Resource
    private OssService ossService;

    /**
     * 根据id获取资源
     *
     * @param id 资源id
     * @return 资源实体类
     */
    public Resource getResourceById(int id) {
        return resourceMapper.selectByPrimaryKey(id);
    }

    /**
     * 新建资源
     *
     * @param resource 资源实体类
     */
    public void saveResource(Resource resource) {
        resourceMapper.insertSelective(resource);
    }

    /**
     * 新建资源并保存用户手机号
     *
     * @param resource 资源实体类
     * @param phone    用户手机号
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveResourceAndAuthor(Resource resource, String phone) {
        resource.setAuthorPhone(phone);
        resourceMapper.insertSelective(resource);
        ResourceInfo resourceInfo = new ResourceInfo();
        resourceInfo.setResId(resource.getId());
        resourceInfoMapper.insertSelective(resourceInfo);
    }

    /**
     * 修改资源
     *
     * @param resource 资源实体类
     * @throws UpdateException 修改失败异常
     */
    public void updateResource(Resource resource) throws UpdateException {
        if (resourceMapper.selectByPrimaryKey(resource.getId()) == null) {
            throw new UpdateException("不存在对应记录！");
        }
        resourceMapper.updateByPrimaryKeySelective(resource);
    }

    /**
     * 判断当前用户是否具有操作资源权限
     *
     * @param phone 用户手机号
     * @param id    资源id
     * @return 判断结果
     */
    public boolean hasPermission(String phone, int id) {
        Integer authorUserId = resourceMapper.selectAuthorUserIdById(id);
        User user = userService.getUserByPhone(phone);
        return user.getId().equals(authorUserId);
    }

    /**
     * 根据id删除资源
     *
     * @param id 资源id
     * @throws DeleteException 删除失败异常
     */
    public void deleteResourceById(int id) throws DeleteException {
        if (resourceMapper.selectByPrimaryKey(id) == null) {
            throw new DeleteException("不存在对应记录！");
        }
        resourceMapper.deleteByPrimaryKey(id);
    }

    public String signDownloadResourceByid(int id) throws SelectException {
        Resource resource = resourceMapper.selectByPrimaryKey(id);
        if (resource == null) {
            throw new SelectException("不存在对应记录！");
        }
        if (resource.getFilename() == null) {
            throw new SelectException("资源文件为空！");
        }
        URL url = ossService.signDownload(resource.getFilename());
        return url.toString();
    }
}
