package com.imooc.sm.dao;

import com.imooc.sm.entity.Department;
import com.imooc.sm.entity.Staff;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffDao {

    void insert(Staff staff);
    void delete(Integer id);
    void update(Staff staff);
    Staff selectById(Integer id);
    List<Staff> selectAll();
}
