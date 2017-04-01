package cn.seocoo.platform.dao.printRecord.impl;


import java.util.List;
import java.util.Map;

import cn.seocoo.platform.dao.printRecord.inf.PrintRecordDao;
import cn.seocoo.platform.model.PrintRecord;

import com.tydic.framework.base.dao.EntityManagerImpl;

public class PrintRecordDaoImpl extends EntityManagerImpl<PrintRecord, Integer> implements PrintRecordDao{

    @Override
    public PrintRecord queryPrintRecord(PrintRecord printRecord){
        return entityDao.findObj("PrintRecord.queryPrintRecord", printRecord);
    }

    @Override
    public List<PrintRecord> queryPrintRecordList(PrintRecord printRecord){
        return entityDao.find("PrintRecord.queryPrintRecord", printRecord);
    }
    @Override
    public void savePrintRecord(PrintRecord printRecord){
         entityDao.save("PrintRecord.savePrintRecord", printRecord);
    }
    @Override
    public void updatePrintRecord(PrintRecord printRecord){
         entityDao.update("PrintRecord.updatePrintRecord", printRecord);
    }
    @Override
    public void deletePrintRecord(PrintRecord printRecord){
         entityDao.remove("PrintRecord.deletePrintRecord", printRecord);
    }
    @Override
    public List<PrintRecord> queryPrintRecordPage(Map map){
        return entityDao.find("PrintRecord.queryPrintRecordPage", map);
    }
    @Override
    public Integer queryPrintRecordPageCount(Map map){
        return (Integer) entityDao.find("PrintRecord.queryPrintRecordPageCount", map).get(0);
    }
    
	@Override
	public List<PrintRecord> findByPrintIp(PrintRecord pr) {
		// TODO Auto-generated method stub
		return entityDao.find("PrintRecord.queryByPrintIp", pr);
	}
	
	@Override
	public List<PrintRecord> findAll(PrintRecord pr) {
		return entityDao.find("PrintRecord.queryByMerchant", pr);
	}
}
