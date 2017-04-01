package cn.seocoo.platform.service.userBank.impl;

import java.util.List;
import java.util.Map;

import cn.seocoo.platform.dao.userBank.inf.UserBankDao;
import cn.seocoo.platform.model.UserBank;
import cn.seocoo.platform.service.userBank.inf.UserBankService;

public class UserBankServiceImpl  implements UserBankService{

	  private UserBankDao userBankDao;

    public UserBankDao getUserBank() {
        return userBankDao;
    }
    public void setUserBankDao(UserBankDao userBankDao) {
         this.userBankDao = userBankDao;
    }

    @Override
    public UserBank queryUserBank(UserBank userBank){
        return userBankDao.queryUserBank(userBank);
    }

    @Override
    public List<UserBank> queryUserBankList(UserBank userBank){
        return userBankDao.queryUserBankList(userBank);
    }
    @Override
    public void saveUserBank(UserBank userBank){
          userBankDao.saveUserBank(userBank);
    }
    @Override
    public void updateUserBank(UserBank userBank){
        userBankDao.updateUserBank(userBank);
    }
    @Override
    public void deleteUserBank(UserBank userBank){
        userBankDao.deleteUserBank(userBank);
    }
    @Override
    public List<UserBank> queryUserBankPage(Map map){
        return userBankDao.queryUserBankPage(map);
    }
    @Override
    public Integer queryUserBankPageCount(Map map){
        return userBankDao.queryUserBankPageCount(map);
    }
    
    public void updateUser(UserBank userBank){
        userBankDao.updateUser(userBank);
    }
}
