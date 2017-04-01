package cn.seocoo.platform.dao.userBank.impl;

import java.util.List;
import java.util.Map;

import cn.seocoo.platform.dao.userBank.inf.UserBankDao;
import cn.seocoo.platform.model.UserBank;

import com.tydic.framework.base.dao.EntityManagerImpl;

public class UserBankDaoImpl extends EntityManagerImpl<UserBank, Integer> implements UserBankDao{

    @Override
    public UserBank queryUserBank(UserBank userBank){
        return entityDao.findObj("UserBank.queryUserBank", userBank);
    }

    @Override
    public List<UserBank> queryUserBankList(UserBank userBank){
        return entityDao.find("UserBank.queryUserBank", userBank);
    }
    @Override
    public void saveUserBank(UserBank userBank){
         entityDao.save("UserBank.saveUserBank", userBank);
    }
    @Override
    public void updateUserBank(UserBank userBank){
         entityDao.update("UserBank.updateUserBank", userBank);
    }
    @Override
    public void deleteUserBank(UserBank userBank){
         entityDao.remove("UserBank.deleteUserBank", userBank);
    }
    @Override
    public List<UserBank> queryUserBankPage(Map map){
        return entityDao.find("UserBank.queryUserBankPage", map);
    }
    @Override
    public Integer queryUserBankPageCount(Map map){
        return (Integer) entityDao.find("UserBank.queryUserBankPageCount", map).get(0);
    }
    
    public void updateUser(UserBank userBank){
        entityDao.update("UserBank.updateUser", userBank);
   }
}
