package bao.xml;

import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import bao.women.model.ItemInfo;


public class ItemInfoListContentHandler extends DefaultHandler {
	private List<ItemInfo> itemInfos=null;
	private ItemInfo itemInfo =null;
	private String tagName=null;
	
	public ItemInfoListContentHandler(List<ItemInfo> itemInfos) {
		super();
		this.itemInfos = itemInfos;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
	}

	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		tagName=localName;
		if(tagName.equals("resource")){
			itemInfo = new ItemInfo();
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if(qName.equals("resource")){
			itemInfos.add(itemInfo);			
		}
		tagName="";
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String data=new String(ch,start,length);
		if(tagName.equals("fromAndTime")){
			itemInfo.setFromAndTime(data);
		}
		else if(tagName.equals("titleString")){
			itemInfo.setTitleString(data);
		}
		else if(tagName.equals("titleImageName")){
			itemInfo.setTitleImageName(data);
		}
		else if(tagName.equals("contentHTMLName")){
			itemInfo.setContentHTMLName(data);
		}
		else if(tagName.equals("id")){
			itemInfo.setId(Integer.parseInt(data));
		}
	}

	public List<ItemInfo> getItemInfos() {
		return itemInfos;
	}

	public void setMp3infos(List<ItemInfo> itemInfos) {
		this.itemInfos = itemInfos;
	}

}
