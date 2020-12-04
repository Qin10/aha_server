package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.mapper.OauthMapper;
import cn.hdustea.aha_server.entity.Oauth;
import cn.hdustea.aha_server.vo.OauthVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 第三方登录的服务类
 *
 * @author STEA_YY
 **/
@Service
public class OauthService {
    @Resource
    private OauthMapper oauthMapper;

    /**
     * 根据第三方凭证类型和凭证号码获取凭证实体
     *
     * @param oauthType 第三方凭证类型
     * @param oauthId   第三方凭证编号
     * @return 第三方登录凭证
     */
    public Oauth getOauthByOauthTypeAndOauthId(String oauthType, String oauthId) {
        return oauthMapper.selectByOauthTypeAndOauthId(oauthType, oauthId);
    }

    /**
     * 根据第三方凭证类型和用户id获取凭证实体
     *
     * @param oauthType 第三方凭证类型
     * @param userId    用户id
     * @return 第三方登录凭证
     */
    public Oauth getOauthByOauthTypeAndUserId(String oauthType, Integer userId) {
        return oauthMapper.selectByPrimaryKey(userId, oauthType);
    }

    public List<OauthVo> getAllOauthVoByUserId(Integer userId){
        return oauthMapper.selectAllVoByUserId(userId);
    }

    /**
     * 保存第三方凭证
     *
     * @param oauth 第三方凭证
     */
    public void saveOauth(Oauth oauth) {
        oauthMapper.insertSelective(oauth);
    }
}
