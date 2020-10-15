package cn.hdustea.aha_server.util;

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
    public static boolean verify(String token, String account, String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("account", account)
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
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static String getAccount(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("account").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 生成签名,10分钟后过期
     *
     * @param account 用户凭证
     * @param secret  秘钥
     * @return 加密的token
     */
    public static String sign(String account, String secret,int expireTime) {
        Date date = new Date(System.currentTimeMillis() + (expireTime * 1000));
        Algorithm algorithm = Algorithm.HMAC256(secret);
        // 附带username信息
        return JWT.create()
                .withClaim("account", account)
                .withExpiresAt(date)
                .sign(algorithm);
    }
}