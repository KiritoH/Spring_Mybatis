package com.imooc.sm.service.impl;

import com.imooc.sm.dao.LogDao;
import com.imooc.sm.entity.Log;
import com.imooc.sm.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("logService")
public class LogServiceImpl implements LogService {

    @Autowired
    private LogDao logDao;
    public void addSystemLog(Log log) {
        //设置操作时间
        log.setOprTime(new Date());
        //设置判断类型
        log.setType("system");
        logDao.insert(log);
    }

    public void addLoginLog(Log log) {
        //设置操作时间
        log.setOprTime(new Date());
        //设置判断类型
        log.setType("login");
        logDao.insert(log);
    }

    public void addOperationLog(Log log) {
        //设置操作时间
        log.setOprTime(new Date());
        //设置判断类型
        log.setType("operation");
        logDao.insert(log);
    }

    public List<Log> getSystemLog() {
        return logDao.selectByType("system");
    }

    public List<Log> getLoginLog() {

        return logDao.selectByType("login");
    }

    public List<Log> getOperationLog() {
        return logDao.selectByType("operation");
    }
}
