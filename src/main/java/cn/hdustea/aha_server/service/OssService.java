package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.bean.CallbackBean;
import cn.hdustea.aha_server.bean.CallbackBodyBean;
import cn.hdustea.aha_server.bean.OssPolicyBean;
import cn.hdustea.aha_server.config.AliyunOSSConfig;
import cn.hdustea.aha_server.util.JacksonUtil;
import cn.hdustea.aha_server.util.RestTemplateUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;

/**
 * oss服务类
 *
 * @author STEA_YY
 **/
@Service
public class OssService {
    @Resource
    private OSS oss;
    @Resource
    private AliyunOSSConfig aliyunOSSConfig;
    @Resource
    private RestTemplateUtil restTemplateUtil;

//    public String signDownload(String fileName) {
//    }

    public OssPolicyBean signUpload(String dir, String callbackUrl, String account) throws Exception {
        String host = "http://" + aliyunOSSConfig.getBucketName() + "." + aliyunOSSConfig.getEndpoint();
        Date expiration = new Date(new Date().getTime() + aliyunOSSConfig.getExpireTime() * 1000);
        PolicyConditions policyConditions = new PolicyConditions();
        policyConditions.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);
        String policyStr = oss.generatePostPolicy(expiration, policyConditions);
        String policyBase64 = JacksonUtil.toBase64String(policyStr.getBytes());
        String signature = oss.calculatePostSignature(policyStr);
        CallbackBean callbackBean = new CallbackBean();
        callbackBean.setCallbackUrl(aliyunOSSConfig.getCallbackHost() + callbackUrl);
        callbackBean.setCallbackBody("{\"object\":${object},\"mimeType\":${mimeType},\"size\":${size},\"account\":\"" + account + "\"}");
        callbackBean.setCallbackBodyType("application/json");
        String callbackJson = JacksonUtil.obj2json(callbackBean);
        String callbackBase64 = JacksonUtil.toBase64String(callbackJson.getBytes());
        OssPolicyBean ossPolicyBean = new OssPolicyBean();
        ossPolicyBean.setHost(host);
        ossPolicyBean.setAccessid(aliyunOSSConfig.getAccessKeyId());
        ossPolicyBean.setPolicy(policyBase64);
        ossPolicyBean.setDir(dir);
        ossPolicyBean.setCallback(callbackBase64);
        ossPolicyBean.setSignature(signature);
        ossPolicyBean.setExpire(expiration.getTime());
        return ossPolicyBean;
    }

    public boolean verifyOSSCallbackRequest(HttpServletRequest request, String ossCallbackBody)
            throws NumberFormatException, IOException {
        boolean ret;
        String authorizationInput = request.getHeader("Authorization");
        String pubKeyInput = request.getHeader("x-oss-pub-key-url");
        byte[] authorization = BinaryUtil.fromBase64String(authorizationInput);
        byte[] pubKey = BinaryUtil.fromBase64String(pubKeyInput);
        String pubKeyAddr = new String(pubKey);
        if (!pubKeyAddr.startsWith("http://gosspublic.alicdn.com/")
                && !pubKeyAddr.startsWith("https://gosspublic.alicdn.com/")) {
            return false;
        }
        ResponseEntity<String> response = restTemplateUtil.get(pubKeyAddr, String.class);
        String retString = response.getBody();
        if (retString == null) {
            return false;
        }
        retString = retString.replace("-----BEGIN PUBLIC KEY-----", "");
        retString = retString.replace("-----END PUBLIC KEY-----", "");
        String queryString = request.getQueryString();
        String uri = request.getRequestURI();
        String authStr = java.net.URLDecoder.decode(uri, "UTF-8");
        if (queryString != null && !queryString.equals("")) {
            authStr += "?" + queryString;
        }
        authStr += "\n" + ossCallbackBody;
        ret = doCheck(authStr, authorization, retString);
        return ret;
    }

    private boolean doCheck(String content, byte[] sign, String publicKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = BinaryUtil.fromBase64String(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
            java.security.Signature signature = java.security.Signature.getInstance("MD5withRSA");
            signature.initVerify(pubKey);
            signature.update(content.getBytes());
            boolean bverify = signature.verify(sign);
            return bverify;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}