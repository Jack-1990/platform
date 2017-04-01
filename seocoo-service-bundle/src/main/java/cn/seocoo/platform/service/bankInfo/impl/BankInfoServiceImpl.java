package cn.seocoo.platform.service.bankInfo.impl;

import java.util.List;
import java.util.Map;

import cn.seocoo.platform.dao.bankInfo.inf.BankInfoDao;
import cn.seocoo.platform.model.BankInfo;
import cn.seocoo.platform.service.bankInfo.inf.BankInfoService;

public class BankInfoServiceImpl  implements BankInfoService{

	  private BankInfoDao bankInfoDao;

    public BankInfoDao getBankInfo() {
        return bankInfoDao;
    }
    public void setBankInfoDao(BankInfoDao bankInfoDao) {
         this.bankInfoDao = bankInfoDao;
    }

    @Override
    public BankInfo queryBankInfo(BankInfo bankInfo){
        return bankInfoDao.queryBankInfo(bankInfo);
    }

    @Override
    public List<BankInfo> queryBankInfoList(BankInfo bankInfo){
        return bankInfoDao.queryBankInfoList(bankInfo);
    }
    @Override
    public void saveBankInfo(BankInfo bankInfo){
          bankInfoDao.saveBankInfo(bankInfo);
    }
    @Override
    public void updateBankInfo(BankInfo bankInfo){
        bankInfoDao.updateBankInfo(bankInfo);
    }
    @Override
    public void deleteBankInfo(BankInfo bankInfo){
        bankInfoDao.deleteBankInfo(bankInfo);
    }
    @Override
    public List<BankInfo> queryBankInfoPage(Map map){
        return bankInfoDao.queryBankInfoPage(map);
    }
    @Override
    public Integer queryBankInfoPageCount(Map map){
        return bankInfoDao.queryBankInfoPageCount(map);
    }
}
