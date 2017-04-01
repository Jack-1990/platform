package cn.seocoo.platform.service.printer.inf;


import java.util.List;
import java.util.Map;

import cn.seocoo.platform.model.Printer;

public interface PrinterService {

    public Printer queryPrinter(Printer printer);

    public List<Printer> queryPrinterList(Printer printer);

    public void savePrinter(Printer printer);

    public void updatePrinter(Printer printer);

    public void deletePrinter(Printer printer);

    public List<Printer> queryPrinterPage(Map map);

    public Integer queryPrinterPageCount(Map map);
    
    public List<Printer> queryAllPrintList(Map map);

	public void deleteSnCode(Printer printer);

	public int insertPrinter(Printer printer);
}
