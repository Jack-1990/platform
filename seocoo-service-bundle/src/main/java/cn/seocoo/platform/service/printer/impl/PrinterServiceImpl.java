package cn.seocoo.platform.service.printer.impl;

import java.util.List;
import java.util.Map;

import cn.seocoo.platform.dao.printer.inf.PrinterDao;
import cn.seocoo.platform.model.Printer;
import cn.seocoo.platform.service.printer.inf.PrinterService;

public class PrinterServiceImpl  implements PrinterService{

	  private PrinterDao printerDao;

    public PrinterDao getPrinter() {
        return printerDao;
    }
    public void setPrinterDao(PrinterDao printerDao) {
         this.printerDao = printerDao;
    }

    @Override
    public Printer queryPrinter(Printer printer){
        return printerDao.queryPrinter(printer);
    }

    @Override
    public List<Printer> queryPrinterList(Printer printer){
        return printerDao.queryPrinterList(printer);
    }
    @Override
    public void savePrinter(Printer printer){
          printerDao.savePrinter(printer);
    }
    @Override
    public void updatePrinter(Printer printer){
        printerDao.updatePrinter(printer);
    }
    @Override
    public void deletePrinter(Printer printer){
        printerDao.deletePrinter(printer);
    }
    @Override
    public List<Printer> queryPrinterPage(Map map){
        return printerDao.queryPrinterPage(map);
    }
    @Override
    public Integer queryPrinterPageCount(Map map){
        return printerDao.queryPrinterPageCount(map);
    }
    
	@Override
	public List<Printer> queryAllPrintList(Map map) {
		return printerDao.queryAllPrintList(map);
	}
	
    public void deleteSnCode(Printer printer){
	      printerDao.deleteSnCode(printer);
	  }
	  
    public int insertPrinter(Printer printer){
    	return printerDao.insertPrinter(printer);
	}
}
