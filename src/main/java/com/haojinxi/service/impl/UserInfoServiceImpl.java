package com.haojinxi.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haojinxi.entity.UserInfo;
import com.haojinxi.mapper.UserinfoMapper;
import com.haojinxi.service.UserInfoService;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl extends ServiceImpl<UserinfoMapper, UserInfo>implements UserInfoService {
}
