package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.dto.SlideShowResourceDto;
import cn.hdustea.aha_server.entity.SlideShowResource;
import cn.hdustea.aha_server.mapper.SlideShowResourceMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 轮播图资源服务类
 *
 * @author STEA_YY
 **/
@Service
public class SlideShowResourceService {
    @Resource
    private SlideShowResourceMapper slideShowResourceMapper;

    public List<SlideShowResource> getAllSlideShowResourceByConditions(Boolean enabled) {
        return slideShowResourceMapper.selectAllByConditions(enabled);
    }

    public SlideShowResource getSlideShowResourceById(int id) {
        return slideShowResourceMapper.selectByPrimaryKey(id);
    }

    public void saveSlideShowResource(SlideShowResourceDto slideShowResourceDto) {
        SlideShowResource slideShowResource = new SlideShowResource();
        BeanUtils.copyProperties(slideShowResourceDto, slideShowResource);
        slideShowResource.setUploadTime(new Date());
        slideShowResourceMapper.insertSelective(slideShowResource);
    }

    public void updateSlideShowResourceById(SlideShowResourceDto slideShowResourceDto, int id) {
        SlideShowResource slideShowResource = new SlideShowResource();
        BeanUtils.copyProperties(slideShowResourceDto, slideShowResource);
        slideShowResource.setId(id);
        slideShowResourceMapper.updateByPrimaryKeySelective(slideShowResource);
    }

    public void deleteSlideShowResourceById(int id) {
        slideShowResourceMapper.deleteByPrimaryKey(id);
    }
}
