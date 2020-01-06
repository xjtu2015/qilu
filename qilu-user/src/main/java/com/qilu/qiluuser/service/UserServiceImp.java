package com.qilu.qiluuser.service;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.qilu.qilucommonservicefacade.entity.user.UserEntity;
import com.qilu.qilucommonservicefacade.exception.CommonBizException;
import com.qilu.qilucommonservicefacade.exception.ExpCodeEnum;
import com.qilu.qilucommonservicefacade.facade.user.UserService;
import com.qilu.qilucommonservicefacade.req.user.LoginReq;
import com.qilu.qilucommonservicefacade.req.user.UserQueryReq;
import com.qilu.qiluuser.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service (version = "1.0.0")
public class UserServiceImp implements UserService {
    @Autowired
    private UserDAO userDAO;
    @Override
    public UserEntity login(LoginReq loginReq) {
        // 校验参数
        checkParam(loginReq);

        // 创建用户查询请求
        UserQueryReq userQueryReq = buildUserQueryReq(loginReq);

        // 查询用户
        List<UserEntity> userEntityList = userDAO.findUsers(userQueryReq);

        // 查询失败
        if (CollectionUtils.isEmpty(userEntityList)) {
            throw new CommonBizException(ExpCodeEnum.LOGIN_FAIL);
        }

        // 查询成功
        return userEntityList.get(0);
    }
    private void checkParam(LoginReq loginReq) {
        // 密码不能为空
        if (StringUtils.isEmpty(loginReq.getPassword())) {
            throw new CommonBizException(ExpCodeEnum.PASSWD_NULL);
        }

        // 手机、mail、用户名 至少填一个
        if (StringUtils.isEmpty(loginReq.getUsername()) &&
                StringUtils.isEmpty(loginReq.getMail()) &&
                StringUtils.isEmpty(loginReq.getPhone())) {
            throw new CommonBizException(ExpCodeEnum.AUTH_NULL);
        }
    }

    private UserQueryReq buildUserQueryReq(LoginReq loginReq) {
        UserQueryReq userQueryReq = new UserQueryReq();
        // 设置密码
        userQueryReq.setPassword(loginReq.getPassword());
        // 设置用户名
        if (StringUtils.isNotEmpty(loginReq.getUsername())) {
            userQueryReq.setUsername(loginReq.getUsername());
        }
        // 设置邮箱
        if (StringUtils.isNotEmpty(loginReq.getMail())) {
            userQueryReq.setMail(loginReq.getMail());
        }
        // 设置手机号
        if (StringUtils.isNotEmpty(loginReq.getPhone())) {
            userQueryReq.setPhone(loginReq.getPhone());
        }

        return userQueryReq;
    }
}
