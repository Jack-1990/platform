package cn.seocoo.platform.service.printRecord.impl;


import java.util.List;
import java.util.Map;

import cn.seocoo.platform.dao.printRecord.inf.PrintRecordDao;
import cn.seocoo.platform.model.PrintRecord;
import cn.seocoo.platform.service.printRecord.inf.PrintRecordService;

public class PrintRecordServiceImpl  implements PrintRecordService{

	  private PrintRecordDao printRecordDao;

    public PrintRecordDao getPrintRecord() {
        return printRecordDao;
    }
    public void setPrintRecordDao(PrintRecordDao printRecordDao) {
         this.printRecordDao = printRecordDao;
    }

    @Override
    public PrintRecord queryPrintRecord(PrintRecord printRecord){
        return printRecordDao.queryPrintRecord(printRecord);
    }

    @Override
    public List<PrintRecord> queryPrintRecordList(PrintRecord printRecord){
        return printRecordDao.queryPrintRecordList(printRecord);
    }
    @Override
    public void savePrintRecord(PrintRecord printRecord){
          printRecordDao.savePrintRecord(printRecord);
    }
    @Override
    public void updatePrintRecord(PrintRecord printRecord){
        printRecordDao.updatePrintRecord(printRecord);
    }
    @Override
    public void deletePrintRecord(PrintRecord printRecord){
        printRecordDao.deletePrintRecord(printRecord);
    }
    @Override
    public List<PrintRecord> queryPrintRecordPage(Map map){
        return printRecordDao.queryPrintRecordPage(map);
    }
    @Override
    public Integer queryPrintRecordPageCount(Map map){
        return printRecordDao.queryPrintRecordPageCount(map);
    }
    
	@Override
	public List<PrintRecord> findByPrintIp(PrintRecord pr) {
		// TODO Auto-generated method stub
		return printRecordDao.findByPrintIp(pr);
	}
	
	@Override
	public List<PrintRecord> findAll(PrintRecord pr) {
		return printRecordDao.findAll(pr);
	}

 
}
