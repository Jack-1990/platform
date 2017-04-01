package com.odchina.micro.auth.entity;

public class MenuInfo  {
    /**目录*/
	public static final int MENU_TYPE_FOLDER = 1;
	/**页面*/
	public static final int MENU_TYPE_PAGE = 2;
	/**操作*/
	public static final int MENU_TYPE_ACTION = 3;

	private String id;
	private String name;
	private String link;
	private Integer menuType;
	private Integer menuOrder;
	private MenuInfo parent;
	private Integer parentId;
	private Boolean visible;
	private String iconUrl;
	private String creator;
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Integer getMenuType() {
		return menuType;
	}

	public void setMenuType(Integer menuType) {
		this.menuType = menuType;
	}

	public Integer getMenuOrder() {
		return menuOrder;
	}

	public void setMenuOrder(Integer menuOrder) {
		this.menuOrder = menuOrder;
	}

	public MenuInfo getParent() {
		return parent;
	}

	public void setParent(MenuInfo parent) {
		this.parent = parent;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int compareTo(Object o) {
		if (o instanceof MenuInfo) {
			MenuInfo mi = (MenuInfo) o;
			int l1 = 0, l2 = 0;
			MenuInfo cursor = this;
			while (cursor != null) {
				cursor = cursor.parent;
				l1++;
			}
			cursor = mi;
			while (cursor != null) {
				cursor = cursor.parent;
				l2++;
			}
			int levelRet = Integer.valueOf(l1).compareTo(Integer.valueOf(l2));
			if (levelRet != 0) {
				return levelRet;
			}
			if ((this.getParent() == null && mi.getParent() == null)
					|| (this.getParent().equals(mi.getParent()))) {
				int orderRet = 0;
				if (this.menuOrder != null && mi.getMenuOrder() != null) {
					orderRet = this.menuOrder.compareTo(mi.getMenuOrder());
				}
				if (orderRet != 0) {
					return orderRet;
				}
				return this.getId().compareTo(mi.getId());
			}
			return this.getParent().compareTo(mi.getParent());
		}
		return 0;
	}
}
