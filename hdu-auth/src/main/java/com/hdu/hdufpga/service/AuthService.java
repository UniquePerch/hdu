package com.hdu.hdufpga.service;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.captcha.generator.MathGenerator;
import cn.hutool.core.math.Calculator;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.hdu.hdufpga.entity.po.UserPO;
import com.hdu.hdufpga.entity.ro.LoginRO;
import com.hdu.hdufpga.entity.ro.VerificationCodeRO;
import com.hdu.hdufpga.util.LocalCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class AuthService {

    @DubboReference(check = false)
    private UserService userService;

    @Resource
    private LocalCache<String, Object> localCache;

    @Resource
    private SsoService ssoService;

    @Resource
    private HttpServletResponse response;

    /**
     * SSO登录
     *
     * @return 子系统的登录信息
     */
    public Object login(LoginRO loginRO) {
        String username = loginRO.getUsername();
        String password = loginRO.getPassword();
        String applicationName = loginRO.getApplicationName();
        String verificationCodeKey = loginRO.getVerificationCodeKey();
        String verificationCodeValue = loginRO.getVerificationCodeValue();
        // 如果已经登录则直接返回
        Object isLogin = isLogin(username, applicationName);
        if (Objects.nonNull(isLogin)) {
            return isLogin;
        }
        // 验证码是否正确
        if (StrUtil.isBlank(verificationCodeKey) || StrUtil.isBlank(verificationCodeValue)) {
            return null;
        }
        Integer code = (Integer) localCache.get(verificationCodeKey, true);
        if (!StrUtil.equals(String.valueOf(code), verificationCodeValue)) {
            return null;
        }
        // 查询用户信息
        //todo:根据用户学校进行鉴权
        UserPO userPO = userService.getUserByUserName(username, 1);
        if (Objects.isNull(userPO)) {
            return null;
        }
        // 比较密码
        if (!StrUtil.equals(password, userPO.getPassword())) {
            return null;
        }
        // 通知子系统登录并获取他们的token信息
        AbstractSsoService service = ssoService.getSsoService(applicationName);
        if (Objects.isNull(service)) {
            return null;
        }
        Object result = service.login(username);
        // 登录成功则登录SSO系统
        if (Objects.nonNull(result)) {
            StpUtil.login(username);
            return result;
        }
        return null;
    }

    /**
     * 退出登录
     *
     * @param username 用户名
     */
    public void logout(String username) {
        StpUtil.logout(username);
        for (AbstractSsoService service : ssoService.getAllServices()) {
            if (Objects.nonNull(service)) {
                service.logout(username);
            }
        }
    }

    /**
     * 检测是否登录
     *
     * @param username        用户名
     * @param applicationName 子系统名称
     * @return 若登录返回子系统Session信息
     */
    public Object isLogin(String username, String applicationName) {
        if (StpUtil.isLogin(username)) {
            AbstractSsoService service = ssoService.getSsoService(applicationName);
            if (Objects.isNull(service)) {
                return null;
            }
            return service.login(username);
        }
        return null;
    }

    /**
     * 生成四则运算验证码
     * <p>通过HttpServletResponse传递前端</p>
     * <p>uuid在Header中，结果存储在本地缓存</p>
     *
     * @param verificationCodeRO 验证码参数
     * @throws IOException
     */
    public void generateVerificationCode(VerificationCodeRO verificationCodeRO) throws IOException {
        // 验证码生成器
        MathGenerator mathGenerator = new MathGenerator(verificationCodeRO.getNumberLength());
        ShearCaptcha shearCaptcha = CaptchaUtil.createShearCaptcha(verificationCodeRO.getWidth(), verificationCodeRO.getHeight(), mathGenerator, verificationCodeRO.getThickness());
        // 生成唯一标识
        String uuid = RandomUtil.randomString(8);
        response.setHeader("uuid", uuid);
        // 计算结果
        String code = shearCaptcha.getCode();
        Integer result = (int) Calculator.conversion(code);
        // 存放到缓存中
        localCache.put(uuid, result, 1, TimeUnit.MINUTES);
        // 渲染到前端
        ServletOutputStream out = response.getOutputStream();
        shearCaptcha.write(out);
        out.close();
    }
}
