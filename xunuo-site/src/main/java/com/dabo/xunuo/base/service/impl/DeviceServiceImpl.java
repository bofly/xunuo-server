package com.dabo.xunuo.base.service.impl;

import com.dabo.xunuo.base.common.exception.SysException;
import com.dabo.xunuo.base.dao.DeviceInfoMapper;
import com.dabo.xunuo.base.entity.DeviceInfo;
import com.dabo.xunuo.base.service.BaseSerivce;
import com.dabo.xunuo.base.service.IDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * Created by zhangbin on 16/8/5.
 */
@Service
public class DeviceServiceImpl extends BaseSerivce implements IDeviceService {

    @Autowired
    private DeviceInfoMapper deviceInfoMapper;

    @Override
    public void createDevice(String deviceId) throws SysException {
        DeviceInfo deviceInfo=new DeviceInfo();
        deviceInfo.setDeviceId(deviceId);
        deviceInfo.setCreateTime(System.currentTimeMillis());
        deviceInfoMapper.insert(deviceInfo);
    }

    @Override
    public DeviceInfo getByDeviceId(String deviceId) throws SysException {
        return deviceInfoMapper.getByDeviceId(deviceId);
    }

    @Override
    public void userLogin(String deviceId, long userId) throws SysException {
        deviceInfoMapper.bindUser(deviceId,userId,System.currentTimeMillis());
    }
}
