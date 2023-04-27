package com.haojinxi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haojinxi.entity.User;
import com.haojinxi.mapper.UserMapper;
import com.haojinxi.service.UserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author haojinxi
 * @since 2023-03-05
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
