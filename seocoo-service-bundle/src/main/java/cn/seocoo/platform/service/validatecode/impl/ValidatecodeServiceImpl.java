package cn.seocoo.platform.service.validatecode.impl;

import java.util.List;
import java.util.Map;

import cn.seocoo.platform.dao.validatecode.inf.ValidatecodeDao;
import cn.seocoo.platform.model.Validatecode;
import cn.seocoo.platform.service.validatecode.inf.ValidatecodeService;

public class ValidatecodeServiceImpl  implements ValidatecodeService{

	  private ValidatecodeDao validatecodeDao;

    public ValidatecodeDao getValidatecode() {
        return validatecodeDao;
    }
    public void setValidatecodeDao(ValidatecodeDao validatecodeDao) {
         this.validatecodeDao = validatecodeDao;
    }

    @Override
    public Validatecode queryValidatecode(Validatecode validatecode){
        return validatecodeDao.queryValidatecode(validatecode);
    }

    @Override
    public List<Validatecode> queryValidatecodeList(Validatecode validatecode){
        return validatecodeDao.queryValidatecodeList(validatecode);
    }
    @Override
    public void saveValidatecode(Validatecode validatecode){
          validatecodeDao.saveValidatecode(validatecode);
    }
    @Override
    public void updateValidatecode(Validatecode validatecode){
        validatecodeDao.updateValidatecode(validatecode);
    }
    @Override
    public void deleteValidatecode(Validatecode validatecode){
        validatecodeDao.deleteValidatecode(validatecode);
    }
    @Override
    public List<Validatecode> queryValidatecodePage(Map map){
        return validatecodeDao.queryValidatecodePage(map);
    }
    @Override
    public Integer queryValidatecodePageCount(Map map){
        return validatecodeDao.queryValidatecodePageCount(map);
    }
}
