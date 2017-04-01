package cn.seocoo.platform.dao.bank.impl;

import java.util.List;
import java.util.Map;

import cn.seocoo.platform.dao.bank.inf.BankDao;
import cn.seocoo.platform.model.Bank;

import com.tydic.framework.base.dao.EntityManagerImpl;

public class BankDaoImpl extends EntityManagerImpl<Bank, Integer> implements BankDao{

    @Override
    public Bank queryBank(Bank bank){
        return entityDao.findObj("Bank.queryBank", bank);
    }

    @Override
    public List<Bank> queryBankList(Bank bank){
        return entityDao.find("Bank.queryBank", bank);
    }
    @Override
    public void saveBank(Bank bank){
         entityDao.save("Bank.saveBank", bank);
    }
    @Override
    public void updateBank(Bank bank){
         entityDao.update("Bank.updateBank", bank);
    }
    @Override
    public void deleteBank(Bank bank){
         entityDao.remove("Bank.deleteBank", bank);
    }
    @Override
    public List<Bank> queryBankPage(Map map){
        return entityDao.find("Bank.queryBankPage", map);
    }
    @Override
    public Integer queryBankPageCount(Map map){
        return (Integer) entityDao.find("Bank.queryBankPageCount", map).get(0);
    }
}
