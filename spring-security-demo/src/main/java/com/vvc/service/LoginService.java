package com.vvc.service;

import com.vvc.constant.ResponseResult;
import com.vvc.entity.User;

public interface LoginService {
    public ResponseResult login(User user);
}
