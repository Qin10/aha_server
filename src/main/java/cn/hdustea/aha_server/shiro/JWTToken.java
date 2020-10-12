package cn.hdustea.aha_server.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * JWT令牌的实体类
 *
 * @author STEA_YY
 **/
public class JWTToken implements AuthenticationToken {
    // 密钥
    private final String token;

    public JWTToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
