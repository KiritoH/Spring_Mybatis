package com.imooc.sm.service;

import com.imooc.sm.entity.Staff;

public interface SelfService {
    //登录
    Staff login(String account , String password);
    //修改密码
    void changePassword(Integer id, String password);

}
