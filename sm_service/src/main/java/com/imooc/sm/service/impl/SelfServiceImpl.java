package com.imooc.sm.service.impl;

import com.imooc.sm.dao.SelfDao;
import com.imooc.sm.dao.StaffDao;
import com.imooc.sm.entity.Staff;
import com.imooc.sm.service.SelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("selfService")
public class SelfServiceImpl implements SelfService {
    @Autowired
    private StaffDao staffDao;
    @Autowired
    private SelfDao selfDao;
    //登录
    public Staff login(String account, String password) {
        Staff staff = selfDao.selectByAccount(account);
        //有可能获得不到
        //如果没获得到
        if (staff==null)return null;
        //如果密码一致
        if (staff.getPassword().equals(password))return staff;
        return null;
    }

    //修改密码
    public void changePassword(Integer id, String password) {
        //得到编号
        Staff staff = staffDao.selectById(id);
        //修改对象密码
        staff.setPassword(password);
        //更新
        staffDao.update(staff);
    }
}
