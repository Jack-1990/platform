package cn.seocoo.platform.service.printFalse.inf;

import java.util.List;
import java.util.Map;

import cn.seocoo.platform.model.PrintFalse;

public interface PrintFalseService {

    public PrintFalse queryPrintFalse(PrintFalse printFalse);

    public List<PrintFalse> queryPrintFalseList(PrintFalse printFalse);

    public void savePrintFalse(PrintFalse printFalse);

    public void updatePrintFalse(PrintFalse printFalse);

    public void deletePrintFalse(PrintFalse printFalse);

    public List<PrintFalse> queryPrintFalsePage(Map map);

    public Integer queryPrintFalsePageCount(Map map);
}
