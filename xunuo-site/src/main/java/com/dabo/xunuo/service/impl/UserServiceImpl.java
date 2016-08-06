package com.dabo.xunuo.service.impl;

import com.dabo.xunuo.common.exception.SysException;
import com.dabo.xunuo.dao.UserCertificateMapper;
import com.dabo.xunuo.dao.UserMapper;
import com.dabo.xunuo.entity.User;
import com.dabo.xunuo.entity.UserCertificate;
import com.dabo.xunuo.event.impl.UserRegEvent;
import com.dabo.xunuo.service.BaseSerivce;
import com.dabo.xunuo.service.IUserService;
import com.dabo.xunuo.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户业务实现
 */
@Service
public class UserServiceImpl extends BaseSerivce implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserCertificateMapper userCertificateMapper;

    @Override
    public User getByPhone(String phone) throws SysException {
        return userMapper.getByPhone(phone);
    }

    @Override
    public User getByUserId(long userId) throws SysException {
        return userMapper.getById(userId);
    }

    @Override
    public void createUser(User user, UserCertificate userCertificate) throws SysException {
        //新建用户
        userMapper.insert(user);
        //保存用户密码
        userCertificateMapper.insert(userCertificate);
        //发布用户注册事件
        UserRegEvent userRegEvent=new UserRegEvent(user);
        eventBus.post(userRegEvent);
    }

    @Override
    public UserCertificate getUserCertificate(User user) throws SysException {
        return userCertificateMapper.getByUserId(user.getId());
    }
}
