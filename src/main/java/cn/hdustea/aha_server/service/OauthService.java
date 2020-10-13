package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.dao.OauthDao;
import cn.hdustea.aha_server.entity.Oauth;
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

    /**
     * 根据第三方凭证编号获取凭证实体
     *
     * @param oauthType 第三方凭证类型
     * @param oauthId   第三方凭证编号
     * @return 第三方登录凭证
     */
    public Oauth getOauthByOauthTypeAndOauthId(String oauthType, String oauthId) {
        return oauthDao.findOauthByOauthTypeAndOauthId(oauthType, oauthId);
    }
}
