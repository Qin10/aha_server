package cn.hdustea.aha_server.util;

import cn.hdustea.aha_server.dto.JwtPayloadBean;
import cn.hdustea.aha_server.entity.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

/**
 * JWT令牌颁发工具类
 *
 * @author STEA_YY
 **/
public class JWTUtil {

    /**
     * 校验token是否正确
     *
     * @param token  token令牌
     * @param secret 秘钥
     * @return 是否正确
     */
    public static boolean verify(String token, JwtPayloadBean jwtPayloadBean, String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("account", jwtPayloadBean.getAccount())
                    .withClaim("roleName", jwtPayloadBean.getRoleName())
                    .withClaim("signedNotice", jwtPayloadBean.isSignedNotice())
                    .withClaim("signedContract", jwtPayloadBean.isSignedContract())
                    .build();
            verifier.verify(token);
            return true;
        } catch (TokenExpiredException e) {
            throw e;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * 获得token中的Payload，无需secret解密也能获得
     *
     * @return token中的Payload数据
     */
    public static JwtPayloadBean getPayload(String token) {
        JwtPayloadBean jwtPayloadBean = new JwtPayloadBean();
        try {
            DecodedJWT jwt = JWT.decode(token);
            jwtPayloadBean.setAccount(jwt.getClaim("account").asString());
            jwtPayloadBean.setRoleName(jwt.getClaim("roleName").asString());
            jwtPayloadBean.setSignedNotice(jwt.getClaim("signedNotice").asBoolean());
            jwtPayloadBean.setSignedContract(jwt.getClaim("signedContract").asBoolean());
        } catch (JWTDecodeException | NullPointerException ignored) {

        }
        return jwtPayloadBean;
    }

    /**
     * 根据用户实体类打包payload
     *
     * @param user 用户实体类
     * @return payload封装实体
     */
    public static JwtPayloadBean packagePayload(User user) {
        JwtPayloadBean jwtPayloadBean = new JwtPayloadBean();
        jwtPayloadBean.setAccount(user.getPhone());
        jwtPayloadBean.setRoleName(user.getRole().getName());
        jwtPayloadBean.setSignedNotice(user.getSignedNotice());
        jwtPayloadBean.setSignedContract(user.getSignedContract());
        return jwtPayloadBean;
    }

    /**
     * 生成签名,10分钟后过期
     *
     * @param jwtPayloadBean payload封装实体
     * @param secret         秘钥
     * @return 加密的token
     */
    public static String sign(JwtPayloadBean jwtPayloadBean, String secret, int expireTime) {
        Date date = new Date(System.currentTimeMillis() + (expireTime * 1000));
        Algorithm algorithm = Algorithm.HMAC256(secret);
        // 附带username信息
        return JWT.create()
                .withClaim("account", jwtPayloadBean.getAccount())
                .withClaim("roleName", jwtPayloadBean.getRoleName())
                .withClaim("signedNotice", jwtPayloadBean.isSignedNotice())
                .withClaim("signedContract", jwtPayloadBean.isSignedContract())
                .withExpiresAt(date)
                .sign(algorithm);
    }
}