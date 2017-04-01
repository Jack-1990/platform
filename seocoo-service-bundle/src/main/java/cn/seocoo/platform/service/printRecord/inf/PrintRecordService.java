package cn.seocoo.platform.service.printRecord.inf;


import java.util.List;
import java.util.Map;

import cn.seocoo.platform.model.PrintRecord;

public interface PrintRecordService {

    public PrintRecord queryPrintRecord(PrintRecord printRecord);

    public List<PrintRecord> queryPrintRecordList(PrintRecord printRecord);

    public void savePrintRecord(PrintRecord printRecord);

    public void updatePrintRecord(PrintRecord printRecord);

    public void deletePrintRecord(PrintRecord printRecord);

    public List<PrintRecord> queryPrintRecordPage(Map map);

    public Integer queryPrintRecordPageCount(Map map);
    
    public List<PrintRecord> findByPrintIp(PrintRecord pr);
    
    public List<PrintRecord> findAll(PrintRecord pr);
}
