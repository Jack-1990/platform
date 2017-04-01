package cn.seocoo.platform.dao.bankInfo.impl;

import java.util.List;
import java.util.Map;

import cn.seocoo.platform.dao.bankInfo.inf.BankInfoDao;
import cn.seocoo.platform.model.BankInfo;

import com.tydic.framework.base.dao.EntityManagerImpl;

public class BankInfoDaoImpl extends EntityManagerImpl<BankInfo, Integer> implements BankInfoDao{

    @Override
    public BankInfo queryBankInfo(BankInfo bankInfo){
        return entityDao.findObj("BankInfo.queryBankInfo", bankInfo);
    }

    @Override
    public List<BankInfo> queryBankInfoList(BankInfo bankInfo){
        return entityDao.find("BankInfo.queryBankInfo", bankInfo);
    }
    @Override
    public void saveBankInfo(BankInfo bankInfo){
         entityDao.save("BankInfo.saveBankInfo", bankInfo);
    }
    @Override
    public void updateBankInfo(BankInfo bankInfo){
         entityDao.update("BankInfo.updateBankInfo", bankInfo);
    }
    @Override
    public void deleteBankInfo(BankInfo bankInfo){
         entityDao.remove("BankInfo.deleteBankInfo", bankInfo);
    }
    @Override
    public List<BankInfo> queryBankInfoPage(Map map){
        return entityDao.find("BankInfo.queryBankInfoPage", map);
    }
    @Override
    public Integer queryBankInfoPageCount(Map map){
        return (Integer) entityDao.find("BankInfo.queryBankInfoPageCount", map).get(0);
    }
}
