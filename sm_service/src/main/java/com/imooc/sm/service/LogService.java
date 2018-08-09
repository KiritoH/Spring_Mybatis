package com.imooc.sm.service;

import com.imooc.sm.entity.Log;

import java.util.List;

public interface LogService {
    /**
     * 记录日志
     * 如此可以屏蔽类型参数
     */
    //记录系统日志
    void addSystemLog(Log log);
    //记录登录日志
    void addLoginLog(Log log);
    //记录操作日志
    void addOperationLog(Log log);

    /**
     * 得到日志
     */
    //得到系统日志
    List<Log> getSystemLog();
    //得到登录日志
    List<Log> getLoginLog();
    //得到操作日志
    List<Log> getOperationLog();
}
