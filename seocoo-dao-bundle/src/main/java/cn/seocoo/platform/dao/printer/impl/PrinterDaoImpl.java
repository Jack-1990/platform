package cn.seocoo.platform.dao.printer.impl;

import java.util.List;
import java.util.Map;

import cn.seocoo.platform.dao.printer.inf.PrinterDao;
import cn.seocoo.platform.model.Printer;

import com.tydic.framework.base.dao.EntityManagerImpl;

public class PrinterDaoImpl extends EntityManagerImpl<Printer, Integer> implements PrinterDao{

    @Override
    public Printer queryPrinter(Printer printer){
        return entityDao.findObj("Printer.queryPrinter", printer);
    }

    @Override
    public List<Printer> queryPrinterList(Printer printer){
        return entityDao.find("Printer.queryPrinter", printer);
    }
    @Override
    public void savePrinter(Printer printer){
         entityDao.save("Printer.savePrinter", printer);
    }
    @Override
    public void updatePrinter(Printer printer){
         entityDao.update("Printer.updatePrinter", printer);
    }
    @Override
    public void deletePrinter(Printer printer){
         entityDao.remove("Printer.deletePrinter", printer);
    }
    @Override
    public List<Printer> queryPrinterPage(Map map){
        return entityDao.find("Printer.queryPrinterPage", map);
    }
    @Override
    public Integer queryPrinterPageCount(Map map){
        return (Integer) entityDao.find("Printer.queryPrinterPageCount", map).get(0);
    }
    
    @Override
	public List<Printer> queryAllPrintList(Map map) {
		// TODO Auto-generated method stub
		return entityDao.find("Printer.queryAllPrintList", map);
	}
    
    public void deleteSnCode(Printer printer){
        entityDao.remove("Printer.deleteSnCode", printer);
   }
    
    public int insertPrinter(Printer printer){
    	return  (Integer) entityDao.save("Printer.insertPrinter", printer);
   }
}
