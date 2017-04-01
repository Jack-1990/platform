package cn.seocoo.platform.model;

import java.util.Date;

public class UserInfo {

    private Long id;
    private String loginName;
    private String trueName;
    private String provinceCode;//所在省编码
    private String cityCode;//所在城市编码
    private String sex;
    private String phone;
    private String email;
    private Integer status;
    private String password;
    private String creator;
    private Date createTime;
    private String updator;
    private Date updateTime;
    private String version;
    private String parentId;
    private String areaCode;

    public String getLoginName() {
        return loginName;
    }
    public void setLoginName(String loginName) {
         this.loginName = loginName;
    }
    public String getTrueName() {
        return trueName;
    }
    public void setTrueName(String trueName) {
         this.trueName = trueName;
    }
     
    public String getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
         this.sex = sex;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
         this.phone = phone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
         this.email = email;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
         this.status = status;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
         this.password = password;
    }
    public String getCreator() {
        return creator;
    }
    public void setCreator(String creator) {
         this.creator = creator;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
         this.createTime = createTime;
    }
    public String getUpdator() {
        return updator;
    }
    public void setUpdator(String updator) {
         this.updator = updator;
    }
    public Date getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(Date updateTime) {
         this.updateTime = updateTime;
    }
    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
         this.version = version;
    }
    public String getParentId() {
        return parentId;
    }
    public void setParentId(String parentId) {
         this.parentId = parentId;
    }
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAreaCode()
	{
		return areaCode;
	}
	public void setAreaCode(String areaCode)
	{
		this.areaCode = areaCode;
	}
    
    
}
