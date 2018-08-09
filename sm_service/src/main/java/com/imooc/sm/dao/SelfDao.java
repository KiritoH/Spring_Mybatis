package com.imooc.sm.dao;

import com.imooc.sm.entity.Staff;
import org.springframework.stereotype.Repository;

@Repository("selfDao")
public interface SelfDao {
    //由账号来查找账号
    Staff selectByAccount(String account);

}
