package cn.seocoo.platform.dao.withdrawCash.impl;

import java.util.List;
import java.util.Map;
import cn.seocoo.platform.model.WithdrawCash;
import cn.seocoo.platform.dao.withdrawCash.inf.WithdrawCashDao;
import com.tydic.framework.base.dao.EntityManagerImpl;

public class WithdrawCashDaoImpl extends EntityManagerImpl<WithdrawCash, Integer> implements WithdrawCashDao{

    @Override
    public WithdrawCash queryWithdrawCash(WithdrawCash withdrawCash){
        return entityDao.findObj("WithdrawCash.queryWithdrawCash", withdrawCash);
    }

    @Override
    public List<WithdrawCash> queryWithdrawCashList(WithdrawCash withdrawCash){
        return entityDao.find("WithdrawCash.queryWithdrawCash", withdrawCash);
    }
    @Override
    public void saveWithdrawCash(WithdrawCash withdrawCash){
         entityDao.save("WithdrawCash.saveWithdrawCash", withdrawCash);
    }
    @Override
    public void updateWithdrawCash(WithdrawCash withdrawCash){
         entityDao.update("WithdrawCash.updateWithdrawCash", withdrawCash);
    }
    @Override
    public void deleteWithdrawCash(WithdrawCash withdrawCash){
         entityDao.remove("WithdrawCash.deleteWithdrawCash", withdrawCash);
    }
    @Override
    public List<WithdrawCash> queryWithdrawCashPage(Map map){
        return entityDao.find("WithdrawCash.queryWithdrawCashPage", map);
    }
    @Override
    public Integer queryWithdrawCashPageCount(Map map){
        return (Integer) entityDao.find("WithdrawCash.queryWithdrawCashPageCount", map).get(0);
    }
}
