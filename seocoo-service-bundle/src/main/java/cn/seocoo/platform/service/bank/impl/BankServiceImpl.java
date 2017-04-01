package cn.seocoo.platform.service.bank.impl;

import java.util.List;
import java.util.Map;

import cn.seocoo.platform.dao.bank.inf.BankDao;
import cn.seocoo.platform.model.Bank;
import cn.seocoo.platform.service.bank.inf.BankService;

public class BankServiceImpl  implements BankService{

	  private BankDao bankDao;

    public BankDao getBank() {
        return bankDao;
    }
    public void setBankDao(BankDao bankDao) {
         this.bankDao = bankDao;
    }

    @Override
    public Bank queryBank(Bank bank){
        return bankDao.queryBank(bank);
    }

    @Override
    public List<Bank> queryBankList(Bank bank){
        return bankDao.queryBankList(bank);
    }
    @Override
    public void saveBank(Bank bank){
          bankDao.saveBank(bank);
    }
    @Override
    public void updateBank(Bank bank){
        bankDao.updateBank(bank);
    }
    @Override
    public void deleteBank(Bank bank){
        bankDao.deleteBank(bank);
    }
    @Override
    public List<Bank> queryBankPage(Map map){
        return bankDao.queryBankPage(map);
    }
    @Override
    public Integer queryBankPageCount(Map map){
        return bankDao.queryBankPageCount(map);
    }
}
