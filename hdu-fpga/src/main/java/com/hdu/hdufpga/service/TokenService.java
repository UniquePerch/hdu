package com.hdu.hdufpga.service;

import com.hdu.hdufpga.entity.Result;
import com.hdu.hdufpga.entity.vo.UserVO;

public interface TokenService {
    Result generateToken(UserVO userVO);

    Result checkToken(String token);

    Result reload(String token);
}
