package com.imooc.sm.service.impl;

import com.imooc.sm.dao.StaffDao;
import com.imooc.sm.entity.Staff;
import com.imooc.sm.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("staffService")
public class StaffServiceImpl implements StaffService {

    @Autowired
    private StaffDao staffDao;

    public void add(Staff staff) {
        /**
         * 注意以下操作是Dao层和Service层的基本区别,
         * 在Service层并不是都直接调用Dao层方法,
         * 可能会涉及到一些默认设置,这就需要用到业务层来增强
          */
        //设置默认密码
        staff.setPassword("123456");
        //设置入职时间
        staff.setWorkTime(new Date());
        //设置账号状态
        staff.setStatus("正常");
        staffDao.insert(staff);
    }

    public void remove(Integer id) {
        staffDao.delete(id);
    }

    public void edit(Staff staff) {
        staffDao.update(staff);
    }

    public Staff get(Integer id) {
        return staffDao.selectById(id);
    }

    public List<Staff> getAll() {
        return staffDao.selectAll();
    }
}
