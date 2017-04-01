package com.odchina.micro.shiro;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

	/**
	 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
	 */
	public class ShiroUser implements Serializable {
		private static final long serialVersionUID = -1373760761780840081L;
		private String loginName;
		private String name;
		private Long id;
		private Long areaId;

		public String getName() {
			return name;
		}

		public Long getId() {
			return id;
		}
		
		public String getLoginName() {
			return loginName;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Long getAreaId() {
			return areaId;
		}

		public void setAreaId(Long areaId) {
			this.areaId = areaId;
		}

		public void setLoginName(String loginName) {
			this.loginName = loginName;
		}

		public void setName(String name) {
			this.name = name;
		}

		/**
		 * 本函数输出将作为默认的<shiro:principal/>输出.
		 */
		@Override
		public String toString() {
			return loginName;
		}

		/**
		 * 重载equals,只计算loginName;
		 */
		@Override
		public int hashCode() {
			return this.loginName.hashCode();
		}

		/**
		 * 重载equals,只比较loginName
		 */
		@Override
		public boolean equals(Object obj) {
			if (obj == null || !(obj instanceof ShiroUser))
				return false;
			ShiroUser o = (ShiroUser) obj;
			return StringUtils.equals(loginName, o.loginName);
		}
	}