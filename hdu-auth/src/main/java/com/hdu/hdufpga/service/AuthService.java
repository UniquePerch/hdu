package com.hdu.hdufpga.service;

import cn.hutool.json.JSONUtil;
import com.hdu.hdufpga.entity.po.UserPO;
import com.hdu.hdufpga.entity.vo.UserVO;
import org.apache.commons.lang.CharEncoding;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthService implements UserDetailsService {
    @DubboReference
    UserService userService;

    @Resource
    HttpServletResponse response;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserPO userPO =  userService.getUserByUserName(username);
        if(userPO == null) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        List<GrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority(userPO.getUserRoleName()));
        UserVO userVO = new UserVO();
        userVO.setUsername(userPO.getUsername());
        userVO.setRealName(userPO.getRealName());
        userVO.setPrivilegeLevel(userPO.getPrivilegeLevel());
        userVO.setUserDepartmentName(userPO.getUserDepartmentName());
        response.setCharacterEncoding(CharEncoding.UTF_8);
        response.setHeader("login_user", JSONUtil.toJsonStr(userVO));
        return User
                .withUsername(username)
                .password(userPO.getPassword())
                .authorities(list)
                .build();
    }
}
