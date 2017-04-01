package cn.seocoo.platform.model;

import  java.util.Date;
import java.util.List;

public class Area {

    private Integer id;
    private String code;
    private String name;
    private String pcode;
    private String mergerName;
    private String province;
    private String city;
    private Date create_time;
    private String district;
    private Date last_update_time;
    private Integer operator;
    private String operator_ip;
    private Integer level;

    //接口用
    private List<Area>  cityList;
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
         this.id = id;
    }
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
    public String getPcode() {
        return pcode;
    }
    public void setPcode(String pcode) {
         this.pcode = pcode;
    }
    public String getMergerName() {
        return mergerName;
    }
    public void setMergerName(String mergerName) {
         this.mergerName = mergerName;
    }
    public String getProvince() {
        return province;
    }
    public void setProvince(String province) {
         this.province = province;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
         this.city = city;
    }
    public Date getCreate_time() {
        return create_time;
    }
    public void setCreate_time(Date create_time) {
         this.create_time = create_time;
    }
    public String getDistrict() {
        return district;
    }
    public void setDistrict(String district) {
         this.district = district;
    }
    public Date getLast_update_time() {
        return last_update_time;
    }
    public void setLast_update_time(Date last_update_time) {
         this.last_update_time = last_update_time;
    }
    public Integer getOperator() {
        return operator;
    }
    public void setOperator(Integer operator) {
         this.operator = operator;
    }
    public String getOperator_ip() {
        return operator_ip;
    }
    public void setOperator_ip(String operator_ip) {
         this.operator_ip = operator_ip;
    }
    public Integer getLevel() {
        return level;
    }
    public void setLevel(Integer level) {
         this.level = level;
    }
	public List<Area> getCityList() {
		return cityList;
	}
	public void setCityList(List<Area> cityList) {
		this.cityList = cityList;
	}
    
}
