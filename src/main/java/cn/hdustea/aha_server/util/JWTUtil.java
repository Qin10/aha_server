package cn.hdustea.aha_server.util;

import cn.hdustea.aha_server.dto.JwtPayloadDto;
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
    public static boolean verify(String token, JwtPayloadDto jwtPayloadDto, String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("account", jwtPayloadDto.getAccount())
                    .withClaim("roleName", jwtPayloadDto.getRoleName())
                    .withClaim("signedNotice", jwtPayloadDto.isSignedNotice())
                    .withClaim("signedContract", jwtPayloadDto.isSignedContract())
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
    public static JwtPayloadDto getPayload(String token) {
        JwtPayloadDto jwtPayloadDto = new JwtPayloadDto();
        try {
            DecodedJWT jwt = JWT.decode(token);
            jwtPayloadDto.setAccount(jwt.getClaim("account").asString());
            jwtPayloadDto.setRoleName(jwt.getClaim("roleName").asString());
            jwtPayloadDto.setSignedNotice(jwt.getClaim("signedNotice").asBoolean());
            jwtPayloadDto.setSignedContract(jwt.getClaim("signedContract").asBoolean());
        } catch (JWTDecodeException | NullPointerException ignored) {

        }
        return jwtPayloadDto;
    }

    /**
     * 根据用户实体类打包payload
     *
     * @param user 用户实体类
     * @return payload封装实体
     */
    public static JwtPayloadDto packagePayload(User user) {
        JwtPayloadDto jwtPayloadDto = new JwtPayloadDto();
        jwtPayloadDto.setAccount(user.getPhone());
        jwtPayloadDto.setRoleName(user.getRole().getName());
        jwtPayloadDto.setSignedNotice(user.getSignedNotice());
        jwtPayloadDto.setSignedContract(user.getSignedContract());
        return jwtPayloadDto;
    }

    /**
     * 生成签名,10分钟后过期
     *
     * @param jwtPayloadDto payload封装实体
     * @param secret         秘钥
     * @return 加密的token
     */
    public static String sign(JwtPayloadDto jwtPayloadDto, String secret, int expireTime) {
        Date date = new Date(System.currentTimeMillis() + (expireTime * 1000));
        Algorithm algorithm = Algorithm.HMAC256(secret);
        // 附带username信息
        return JWT.create()
                .withClaim("account", jwtPayloadDto.getAccount())
                .withClaim("roleName", jwtPayloadDto.getRoleName())
                .withClaim("signedNotice", jwtPayloadDto.isSignedNotice())
                .withClaim("signedContract", jwtPayloadDto.isSignedContract())
                .withExpiresAt(date)
                .sign(algorithm);
    }
}