package com.qilu.qilucommonservicefacade.facade.user;

import com.qilu.qilucommonservicefacade.entity.user.UserEntity;
import com.qilu.qilucommonservicefacade.req.user.LoginReq;

public interface UserService {
    public UserEntity login(LoginReq loginReq);
}
