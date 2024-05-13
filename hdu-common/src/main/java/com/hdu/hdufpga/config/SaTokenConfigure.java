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
                    .notMatch("/auth/**")        // 排除掉的 path 列表，可以写多个
                    .check(r -> StpUtil.checkLogin());        // 要执行的校验动作，可以写完整的 lambda 表达式
            // 根据路由划分模块，不同模块不同鉴权
            // todo 记得具体路径优先配，通配符往后配置
            // >=3
            SaRouter.match("/user/delete", "/user/update",
                            "/cb/listPage", "/cb/get",
                            "/class//listPage", "/class/get")
                    .check(r -> StpUtil.checkRole(RoleEnum.ADMIN.getRoleId()));
            // >=1
            SaRouter.match("/chapter/recordFinish", "/chapter/getChapterRecordByUserId",
                            "/paper/getAllPaperByClassId", "/paper/handInPaper", "/paper/getHandInInfoByUserId", "/paper/updateHandInInfo",
                            "/problem/getProblems", "/problem/checkAnswer",
                            "/testRecord/getMaxScore",
                            "/studentStudyRecord/updateResourceRecord")
                    .check(r -> StpUtil.checkRoleOr(RoleEnum.STUDENT.getRoleId(), RoleEnum.TEACHER.getRoleId(), RoleEnum.ADMIN.getRoleId()));


            // >=1
            SaRouter.match("/cb/**",
                            "/file/**",
                            "/token/**",
                            "/waiting/**")
                    .check(r -> StpUtil.checkRoleOr(RoleEnum.STUDENT.getRoleId(), RoleEnum.TEACHER.getRoleId(), RoleEnum.ADMIN.getRoleId()));
            // >=2
            SaRouter.match("/user/**",
                            "/class/**",
                            "/testRecord/**",
                            "/studentStudyRecord/**")
                    .check(r -> StpUtil.checkRoleOr(RoleEnum.TEACHER.getRoleId(), RoleEnum.ADMIN.getRoleId()));
            // >=3
            SaRouter.match("/role/**",
                            "/department/**",
                            "/chapter/**",
                            "/knowledge/**",
                            "/problem/**",
                            "/resource/**")
                    .check(r -> StpUtil.checkRole(RoleEnum.ADMIN.getRoleId()));

            SaRouter.match("/goods/**", r -> StpUtil.checkRole("goods"));
            SaRouter.match("/orders/**", r -> StpUtil.checkRole("orders"));
            SaRouter.match("/notice/**", r -> StpUtil.checkRole("notice"));
            SaRouter.match("/comment/**", r -> StpUtil.checkRole("comment"));
        })).addPathPatterns("/**");
    }
}
