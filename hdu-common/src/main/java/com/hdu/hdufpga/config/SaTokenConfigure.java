package com.hdu.hdufpga.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import com.hdu.hdufpga.entity.enums.RoleEnum;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {
    // 注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，定义详细认证规则
        registry.addInterceptor(new SaInterceptor(handler -> {
            // 指定一条 match 规则
            SaRouter
                    .match("/**")    // 拦截的 path 列表，可以写多个 */
                    .notMatch("/user/doLogin")        // 排除掉的 path 列表，可以写多个
                    .check(r -> StpUtil.checkLogin());        // 要执行的校验动作，可以写完整的 lambda 表达式
            // 根据路由划分模块，不同模块不同鉴权

            SaRouter.match("/user/**", r -> StpUtil.checkRoleOr(RoleEnum.TEACHER.getRoleId(), RoleEnum.ADMIN.getRoleId()));
            SaRouter.match("/cb/**",
                            "/file/**",
                            "/token/**",
                            "/waiting/**",
                            "/chapter/recordFinish", "/chapter/getChapterRecordByUserId")
                    .check(r -> StpUtil.checkRoleOr(RoleEnum.STUDENT.getRoleId(), RoleEnum.TEACHER.getRoleId(), RoleEnum.ADMIN.getRoleId()));
            SaRouter.match("/role/**",
                            "/user/delete", "/user/update",
                            "/department/**",
                            "/cb/listPage", "/cb/get",
                            "/chapter/**")
                    .check(r -> StpUtil.checkRole(RoleEnum.ADMIN.getRoleId()));

            SaRouter.match("/goods/**", r -> StpUtil.checkRole("goods"));
            SaRouter.match("/orders/**", r -> StpUtil.checkRole("orders"));
            SaRouter.match("/notice/**", r -> StpUtil.checkRole("notice"));
            SaRouter.match("/comment/**", r -> StpUtil.checkRole("comment"));
        })).addPathPatterns("/**");
    }
}
