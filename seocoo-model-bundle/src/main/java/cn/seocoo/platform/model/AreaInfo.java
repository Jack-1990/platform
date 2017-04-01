package cn.seocoo.platform.model;

import java.util.List;

/**
 *  提供数据给APP
 * @author wangyuan
 *
 */
public class AreaInfo {
    private String code;
    private String name;
    private List<AreaInfo>  cityList;
    
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<AreaInfo> getCityList() {
		return cityList;
	}
	public void setCityList(List<AreaInfo> cityList) {
		this.cityList = cityList;
	}
    
    
}
