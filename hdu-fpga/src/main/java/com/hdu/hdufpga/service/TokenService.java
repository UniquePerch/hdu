package com.hdu.hdufpga.service;

import com.hdu.hdufpga.entity.vo.UserConnectionVO;
import com.hdu.hdufpga.entity.vo.UserVO;
import com.hdu.hdufpga.exception.IdentifyException;
import com.hdu.hdufpga.exception.NullTokenException;
import com.hdu.hdufpga.exception.TokenExpiredException;
import com.hdu.hdufpga.exception.UserQueueException;

public interface TokenService {
    String generateToken(UserVO userVO) throws IdentifyException;

    Boolean checkToken(String token) throws NullTokenException, TokenExpiredException;

    UserConnectionVO reload(String token) throws UserQueueException;
}
