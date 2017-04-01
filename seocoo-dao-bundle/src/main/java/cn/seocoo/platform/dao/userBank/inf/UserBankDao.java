package cn.seocoo.platform.dao.userBank.inf;

import java.util.List;
import java.util.Map;

import cn.seocoo.platform.model.UserBank;

public interface UserBankDao {

    public UserBank queryUserBank(UserBank userBank);

    public List<UserBank> queryUserBankList(UserBank userBank);

    public void saveUserBank(UserBank userBank);

    public void updateUserBank(UserBank userBank);

    public void deleteUserBank(UserBank userBank);

    public List<UserBank> queryUserBankPage(Map map);

    public Integer queryUserBankPageCount(Map map);

	public void updateUser(UserBank userBank);
}
