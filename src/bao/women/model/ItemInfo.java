package bao.women.model;

import java.io.Serializable;


public class ItemInfo implements Serializable{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	
	private String fromAndTime = null;
	
	private String titleString = null;
	
	private String titleImageName = null;
	
	private String contentHTMLName = null;

	public ItemInfo() {
		super();
	}

	public ItemInfo(int id,String fromAndTime, String titleString,
			String titleImageName, String contentHTMLName) {
		super();
		this.id = id;
		this.fromAndTime = fromAndTime;
		this.titleString = titleString;
		this.titleImageName = titleImageName;
		this.contentHTMLName = contentHTMLName;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getFromAndTime() {
		return fromAndTime;
	}

	public void setFromAndTime(String fromAndTime) {
		this.fromAndTime = fromAndTime;
	}

	public String getTitleString() {
		return titleString;
	}

	public void setTitleString(String titleString) {
		this.titleString = titleString;
	}

	public String getTitleImageName() {
		return titleImageName;
	}

	public void setTitleImageName(String titleImageName) {
		this.titleImageName = titleImageName;
	}

	public String getContentHTMLName() {
		return contentHTMLName;
	}


	public void setContentHTMLName(String contentHTMLName) {
		this.contentHTMLName = contentHTMLName;
	}

	@Override
	public String toString() {
		return "id = "+id+
		"   fromAndTime = "+fromAndTime+
		"   titleString = "+titleString+
		"   titleImageName = "+titleImageName+
		"   contentHTMLName = "+contentHTMLName;
	}
	
	
}
