package cn.seocoo.platform.model;

import java.util.Date;

/**
 * 商户分润金额操作日志类
 * @author Administrator
 *
 */
public class MerchantProfitLog {
		private Integer id;
		private String merchantCode;
		private Double updateProfit;
		private Integer updateStyle;
		private Date countTime;
		private String operator;
		private Date createTime;
		 
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getMerchantCode() {
			return merchantCode;
		}
		public void setMerchantCode(String merchantCode) {
			this.merchantCode = merchantCode;
		}
		public Double getUpdateProfit() {
			return updateProfit;
		}
		public void setUpdateProfit(Double updateProfit) {
			this.updateProfit = updateProfit;
		}
		public Integer getUpdateStyle() {
			return updateStyle;
		}
		public void setUpdateStyle(Integer updateStyle) {
			this.updateStyle = updateStyle;
		}
		public Date getCountTime() {
			return countTime;
		}
		public void setCountTime(Date countTime) {
			this.countTime = countTime;
		}
		public String getOperator() {
			return operator;
		}
		public void setOperator(String operator) {
			this.operator = operator;
		}
		public Date getCreateTime() {
			return createTime;
		}
		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}
		 
}
