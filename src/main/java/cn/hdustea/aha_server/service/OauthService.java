package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.dao.OauthDao;
import cn.hdustea.aha_server.entity.Oauth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: aha_server
 * @description: 第三方登录的服务类
 * @author: HduStea_YY
 * @create: 2020-10-10 03:55
 **/
@Service
public class OauthService {
    @Autowired
    private OauthDao oauthDao;

    public Oauth getOauthByOauthTypeAndOauthId(String oauthType, String oauthId) {
        return oauthDao.findOauthByOauthTypeAndOauthId(oauthType, oauthId);
    }
}
