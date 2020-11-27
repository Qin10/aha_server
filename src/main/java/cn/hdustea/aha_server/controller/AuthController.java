package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.annotation.RequestLimit;
import cn.hdustea.aha_server.annotation.RequiresLogin;
import cn.hdustea.aha_server.config.UserOperationLogConfig;
import cn.hdustea.aha_server.dto.ChangePasswordDto;
import cn.hdustea.aha_server.dto.LoginUserDto;
import cn.hdustea.aha_server.dto.RegisterUserDto;
import cn.hdustea.aha_server.exception.apiException.daoException.InsertException;
import cn.hdustea.aha_server.vo.TokenAndPersonalUserInfoVo;
import cn.hdustea.aha_server.entity.Contract;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.exception.apiException.daoException.UpdateException;
import cn.hdustea.aha_server.exception.apiException.smsException.MessageCheckException;
import cn.hdustea.aha_server.service.AuthService;
import cn.hdustea.aha_server.util.ThreadLocalUtil;
import cn.hdustea.aha_server.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * 授权鉴权类请求
 *
 * @author STEA_YY
 **/
@RestController
@Slf4j(topic = "userOperationLog")
public class AuthController {
    protected static final String MODULE_NAME = "注册登录模块";
    @Resource
    private AuthService authService;
    @Resource
    private UserOperationLogConfig userOperationLogConfig;

    /**
     * 通过账号密码登录
     *
     * @param loginUserDto 包含账号密码的实体，从请求Json中获取
     */
    @RequestLimit(amount = 5, time = 300)
    @PostMapping("/login")
    public ResponseBean<TokenAndPersonalUserInfoVo> login(@RequestBody @Validated LoginUserDto loginUserDto) throws Exception {
        TokenAndPersonalUserInfoVo tokenAndPersonalUserInfoVo = authService.login(loginUserDto);
        log.info(userOperationLogConfig.getFormat(), MODULE_NAME, "用户登录", "");
        return new ResponseBean<>(200, "登录成功", tokenAndPersonalUserInfoVo);
    }

    /**
     * 通过手机号注册
     *
     * @param registerUserDto 包含注册信息的实体
     */
    @RequestLimit(amount = 1)
    @PostMapping("/register")
    public ResponseBean<TokenAndPersonalUserInfoVo> register(@RequestBody @Validated RegisterUserDto registerUserDto) throws Exception {
        authService.register(registerUserDto);
        LoginUserDto loginUserDto = new LoginUserDto(registerUserDto.getPhone(), registerUserDto.getPassword());
        TokenAndPersonalUserInfoVo tokenAndPersonalUserInfoVo = authService.login(loginUserDto);
        log.info(userOperationLogConfig.getFormat(), MODULE_NAME, "用户注册", "");
        return new ResponseBean<>(200, "注册成功", tokenAndPersonalUserInfoVo);
    }

    /**
     * 修改密码
     *
     * @param changePasswordDto 存放修改密码相关信息的实体类
     * @param phone             手机号
     */
    @RequestLimit(amount = 1)
    @PostMapping("/changePassword/{phone}")
    public ResponseBean<Object> changePassword(@RequestBody @Validated ChangePasswordDto changePasswordDto, @PathVariable("phone") String phone) throws MessageCheckException, SelectException {
        authService.changePassword(changePasswordDto, phone);
        return new ResponseBean<>(200, "密码修改成功！", null);
    }

    /**
     * 签署服务协议
     *
     * @throws SelectException 用户不存在异常
     */
    @RequestLimit(amount = 1, time = 180)
    @RequiresLogin(requireSignNotice = false)
    @GetMapping("/sign/notice")
    public ResponseBean<TokenVo> signNotice() throws SelectException, UpdateException {
        String phone = ThreadLocalUtil.getCurrentUser();
        String updatedToken = authService.signNotice(phone);
        TokenVo tokenVo = new TokenVo();
        tokenVo.setToken(updatedToken);
        return new ResponseBean<>(200, "已同意用户协议", tokenVo);
    }

    /**
     * 签署合同
     *
     * @param file     合同签名文件
     * @param contract 合同信息实体类
     */
    @RequestLimit(amount = 1, time = 180)
    @RequiresLogin
    @PostMapping("/sign/contract")
    public ResponseBean<TokenVo> signContract(MultipartFile file, @Validated Contract contract) throws IOException, UpdateException, SelectException, InsertException {
        String phone = ThreadLocalUtil.getCurrentUser();
        String updatedToken = authService.signContract(phone, file, contract);
        TokenVo tokenVo = new TokenVo();
        tokenVo.setToken(updatedToken);
        return new ResponseBean<>(200, "已签署合同", tokenVo);
    }

    /**
     * 登出
     */
    @RequiresLogin
    @GetMapping("/logout")
    public ResponseBean<Object> logout() {
        String phone = ThreadLocalUtil.getCurrentUser();
        authService.logout(phone);
        return new ResponseBean<>(200, "登出成功", null);
    }
}
