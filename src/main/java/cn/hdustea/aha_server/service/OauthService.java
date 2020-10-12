package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.dao.OauthDao;
import cn.hdustea.aha_server.entity.Oauth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 第三方登录的服务类
 *
 * @author STEA_YY
 **/
@Service
public class OauthService {
    @Resource
    private OauthDao oauthDao;

    public Oauth getOauthByOauthTypeAndOauthId(String oauthType, String oauthId) {
        return oauthDao.findOauthByOauthTypeAndOauthId(oauthType, oauthId);
    }
}
