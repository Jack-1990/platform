package cn.seocoo.platform.service.printFalse.impl;

import java.util.List;
import java.util.Map;

import cn.seocoo.platform.dao.printFalse.inf.PrintFalseDao;
import cn.seocoo.platform.model.PrintFalse;
import cn.seocoo.platform.service.printFalse.inf.PrintFalseService;

public class PrintFalseServiceImpl  implements PrintFalseService{

	  private PrintFalseDao printFalseDao;

    public PrintFalseDao getPrintFalse() {
        return printFalseDao;
    }
    public void setPrintFalseDao(PrintFalseDao printFalseDao) {
         this.printFalseDao = printFalseDao;
    }

    @Override
    public PrintFalse queryPrintFalse(PrintFalse printFalse){
        return printFalseDao.queryPrintFalse(printFalse);
    }

    @Override
    public List<PrintFalse> queryPrintFalseList(PrintFalse printFalse){
        return printFalseDao.queryPrintFalseList(printFalse);
    }
    @Override
    public void savePrintFalse(PrintFalse printFalse){
          printFalseDao.savePrintFalse(printFalse);
    }
    @Override
    public void updatePrintFalse(PrintFalse printFalse){
        printFalseDao.updatePrintFalse(printFalse);
    }
    @Override
    public void deletePrintFalse(PrintFalse printFalse){
        printFalseDao.deletePrintFalse(printFalse);
    }
    @Override
    public List<PrintFalse> queryPrintFalsePage(Map map){
        return printFalseDao.queryPrintFalsePage(map);
    }
    @Override
    public Integer queryPrintFalsePageCount(Map map){
        return printFalseDao.queryPrintFalsePageCount(map);
    }
}
