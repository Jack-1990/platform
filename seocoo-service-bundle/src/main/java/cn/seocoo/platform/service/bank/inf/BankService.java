package cn.seocoo.platform.service.bank.inf;

import java.util.List;
import java.util.Map;

import cn.seocoo.platform.model.Bank;

public interface BankService {

    public Bank queryBank(Bank bank);

    public List<Bank> queryBankList(Bank bank);

    public void saveBank(Bank bank);

    public void updateBank(Bank bank);

    public void deleteBank(Bank bank);

    public List<Bank> queryBankPage(Map map);

    public Integer queryBankPageCount(Map map);
}
