package cn.seocoo.platform.dao.validatecode.impl;


import java.util.List;
import java.util.Map;

import cn.seocoo.platform.dao.validatecode.inf.ValidatecodeDao;
import cn.seocoo.platform.model.Validatecode;

import com.tydic.framework.base.dao.EntityManagerImpl;

public class ValidatecodeDaoImpl extends EntityManagerImpl<Validatecode, Integer> implements ValidatecodeDao{

    @Override
    public Validatecode queryValidatecode(Validatecode validatecode){
        return entityDao.findObj("Validatecode.queryValidatecode", validatecode);
    }

    @Override
    public List<Validatecode> queryValidatecodeList(Validatecode validatecode){
        return entityDao.find("Validatecode.queryValidatecode", validatecode);
    }
    @Override
    public void saveValidatecode(Validatecode validatecode){
         entityDao.save("Validatecode.saveValidatecode", validatecode);
    }
    @Override
    public void updateValidatecode(Validatecode validatecode){
         entityDao.update("Validatecode.updateValidatecode", validatecode);
    }
    @Override
    public void deleteValidatecode(Validatecode validatecode){
         entityDao.remove("Validatecode.deleteValidatecode", validatecode);
    }
    @Override
    public List<Validatecode> queryValidatecodePage(Map map){
        return entityDao.find("Validatecode.queryValidatecodePage", map);
    }
    @Override
    public Integer queryValidatecodePageCount(Map map){
        return (Integer) entityDao.find("Validatecode.queryValidatecodePageCount", map).get(0);
    }
}
