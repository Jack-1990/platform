package cn.seocoo.platform.dao.printFalse.impl;

import java.util.List;
import java.util.Map;

import cn.seocoo.platform.dao.printFalse.inf.PrintFalseDao;
import cn.seocoo.platform.model.PrintFalse;

import com.tydic.framework.base.dao.EntityManagerImpl;

public class PrintFalseDaoImpl extends EntityManagerImpl<PrintFalse, Integer> implements PrintFalseDao{

    @Override
    public PrintFalse queryPrintFalse(PrintFalse printFalse){
        return entityDao.findObj("PrintFalse.queryPrintFalse", printFalse);
    }

    @Override
    public List<PrintFalse> queryPrintFalseList(PrintFalse printFalse){
        return entityDao.find("PrintFalse.queryPrintFalse", printFalse);
    }
    @Override
    public void savePrintFalse(PrintFalse printFalse){
         entityDao.save("PrintFalse.savePrintFalse", printFalse);
    }
    @Override
    public void updatePrintFalse(PrintFalse printFalse){
         entityDao.update("PrintFalse.updatePrintFalse", printFalse);
    }
    @Override
    public void deletePrintFalse(PrintFalse printFalse){
         entityDao.remove("PrintFalse.deletePrintFalse", printFalse);
    }
    @Override
    public List<PrintFalse> queryPrintFalsePage(Map map){
        return entityDao.find("PrintFalse.queryPrintFalsePage", map);
    }
    @Override
    public Integer queryPrintFalsePageCount(Map map){
        return (Integer) entityDao.find("PrintFalse.queryPrintFalsePageCount", map).get(0);
    }
}
