package cn.seocoo.platform.dao.validatecode.inf;

import java.util.List;
import java.util.Map;

import cn.seocoo.platform.model.Validatecode;

public interface ValidatecodeDao {

    public Validatecode queryValidatecode(Validatecode validatecode);

    public List<Validatecode> queryValidatecodeList(Validatecode validatecode);

    public void saveValidatecode(Validatecode validatecode);

    public void updateValidatecode(Validatecode validatecode);

    public void deleteValidatecode(Validatecode validatecode);

    public List<Validatecode> queryValidatecodePage(Map map);

    public Integer queryValidatecodePageCount(Map map);
}
