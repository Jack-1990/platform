package cn.seocoo.platform.service.validatecode.inf;

import java.util.List;
import java.util.Map;

import cn.seocoo.platform.model.Validatecode;

public interface ValidatecodeService {

    public Validatecode queryValidatecode(Validatecode validatecode);

    public List<Validatecode> queryValidatecodeList(Validatecode validatecode);

    public void saveValidatecode(Validatecode validatecode);

    public void updateValidatecode(Validatecode validatecode);

    public void deleteValidatecode(Validatecode validatecode);

    public List<Validatecode> queryValidatecodePage(Map map);

    public Integer queryValidatecodePageCount(Map map);
}
