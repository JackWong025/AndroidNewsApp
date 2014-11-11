package bao.women.mactivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import net.ting.sliding.R;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import bao.utils.FileUtils;
import bao.women.dao.DBOpenHelper;
import bao.women.dao.ItemInfoDAO;
import bao.women.model.AppConstant;
import bao.women.model.ItemInfo;
import bao.xml.ItemInfoListContentHandler;


public class ItemListActivity extends ListActivity {
	private String filePath = null;
	
	private List<ItemInfo> itemInfos=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.layout_itemlist);
		
		//测试数据库的读写功能
		
		//解析XML
		filePath=AppConstant.PATH.SDCardRoot+"test.xml";
		String fileContent = FileUtils.getStringFromFile(filePath);
		itemInfos=xmlParser(fileContent);
		
		//将itemInfos加入数据库的表
		ItemInfoDAO itemInfoDAO = new ItemInfoDAO(this);
		itemInfoDAO.add(itemInfos, AppConstant.DATABASETABLENAME.TESTTABLE);
		itemInfos.clear();
		
		//读取数据库中的表，结果会根据id倒序排列在listview
		itemInfos = itemInfoDAO.getScrollData(0, itemInfoDAO.getCount(AppConstant.DATABASETABLENAME.TESTTABLE), AppConstant.DATABASETABLENAME.TESTTABLE);
		
		
		setListAdapter(buildSimpleAdapter(itemInfos));
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		ItemInfo itemInfo=itemInfos.get(position);
		Intent intent = new Intent(this, ViewHTMLActivity.class);
		intent.putExtra("itemInfo",itemInfo);
		startActivityForResult(intent, 0);
	}



	//根据哈希表创建适配器
	private SimpleAdapter buildSimpleAdapter(List<ItemInfo> itemInfos){
		List<HashMap<String,Object>> list=buildHashMaps(itemInfos);
		SimpleAdapter simpleAdapter=new SimpleAdapter(this, list, R.layout.layout_eachitem, new String[]{"titleString","fromAndTime","imageBitmap"}, new int[]{R.id.newslist_item_title,R.id.newslist_item_source,R.id.newslist_item_Image});		
		return simpleAdapter;
	}
	
	//动态创建哈希表
	private List<HashMap<String,Object>> buildHashMaps(List<ItemInfo> itemInfos){
		List<HashMap<String,Object>> list=new ArrayList<HashMap<String,Object>>();
			for (Iterator<ItemInfo> iterator = itemInfos.iterator(); iterator.hasNext();) {
				ItemInfo itemInfo=(ItemInfo) iterator.next();
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("titleString", itemInfo.getTitleString());
				map.put("fromAndTime", itemInfo.getFromAndTime());
				map.put("imageBitmap", R.drawable.icon);
				list.add(map);				
			}
		return list;
	}
	
	//根据XMLString解析XML文本，返回List<ItemInfo>
	private List<ItemInfo> xmlParser(String xmlString){
		SAXParserFactory spf=SAXParserFactory.newInstance();
		List<ItemInfo> infos=new ArrayList<ItemInfo>();			
		try {
			XMLReader xmlReader=spf.newSAXParser().getXMLReader();
			ItemInfoListContentHandler handler=new ItemInfoListContentHandler(infos);
			xmlReader.setContentHandler(handler);
			xmlReader.parse(new InputSource(new StringReader(xmlString)));
			for (Iterator<ItemInfo> iterator = infos.iterator(); iterator.hasNext();) {
				ItemInfo itemInfo = (ItemInfo) iterator.next();
				//输出每一个列表内的对象，以便调试
				System.out.println(itemInfo);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}		
		return infos;
	}
	
	
}
