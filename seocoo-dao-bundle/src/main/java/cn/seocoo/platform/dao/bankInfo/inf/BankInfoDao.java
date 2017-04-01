package cn.seocoo.platform.dao.bankInfo.inf;
import java.util.List;
import java.util.Map;

import cn.seocoo.platform.model.BankInfo;

public interface BankInfoDao {

    public BankInfo queryBankInfo(BankInfo bankInfo);

    public List<BankInfo> queryBankInfoList(BankInfo bankInfo);

    public void saveBankInfo(BankInfo bankInfo);

    public void updateBankInfo(BankInfo bankInfo);

    public void deleteBankInfo(BankInfo bankInfo);

    public List<BankInfo> queryBankInfoPage(Map map);

    public Integer queryBankInfoPageCount(Map map);
}
