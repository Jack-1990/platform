package cn.seocoo.platform.model;

import java.io.Serializable;

public class SystemConfig  implements Serializable{
	private int id;
	private String propertyKey;
	private String propertyValue;
	private String propertyDesc;
	private int propertyIndex;
	private String systemId;
	
	
	@Override
	public String toString() {
		return "SystemConfigEntity [id=" + id + ", propertyKey=" + propertyKey
				+ ", propertyValue=" + propertyValue + ", propertyDesc="
				+ propertyDesc + ", propertyIndex=" + propertyIndex
				+ ", systemId=" + systemId + "]";
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPropertyKey() {
		return propertyKey;
	}
	public void setPropertyKey(String propertyKey) {
		this.propertyKey = propertyKey;
	}
	public String getPropertyValue() {
		return propertyValue;
	}
	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}
	public String getPropertyDesc() {
		return propertyDesc;
	}
	public void setPropertyDesc(String propertyDesc) {
		this.propertyDesc = propertyDesc;
	}
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	public int getPropertyIndex() {
		return propertyIndex;
	}
	public void setPropertyIndex(int propertyIndex) {
		this.propertyIndex = propertyIndex;
	}

}
