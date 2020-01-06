package com.qilu.qilucontroller.controller.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qilu.qilucommonservicefacade.entity.user.UserEntity;
import com.qilu.qilucommonservicefacade.facade.user.UserService;
import com.qilu.qilucommonservicefacade.req.user.LoginReq;
import com.qilu.qilucommonservicefacade.rsp.Result;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class UserControllerImp implements UserController {
    @Reference(version = "1.0.0")
    private UserService userService;
    @Override
    public Result login(LoginReq loginReq, HttpServletResponse httpRsp) {
        UserEntity userEntity = userService.login(loginReq);

        // 登录成功
        //doLoginSuccess(userEntity, httpRsp);
        return Result.newSuccessResult(userEntity);
    }

    /*private void doLoginSuccess(UserEntity userEntity, HttpServletResponse httpRsp) {
        // 生成SessionID
        String sessionID = RedisPrefixUtil.SessionID_Prefix + KeyGenerator.getKey();

        // 将 SessionID-UserEntity 存入Redis
        // TODO 暂时存储本地
//        redisService.set(sessionID, userEntity, sessionExpireTime);
        RedisServiceTemp.userMap.put(sessionID, userEntity);

        // 将SessionID存入HTTP响应头
        Cookie cookie = new Cookie(sessionIdName, sessionID);
        httpRsp.addCookie(cookie);
    }*/
}
