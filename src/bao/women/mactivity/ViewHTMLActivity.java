package bao.women.mactivity;

import net.ting.sliding.R;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import bao.download.HttpDownloader;
import bao.utils.FileUtils;
import bao.women.model.AppConstant;
import bao.women.model.ItemInfo;

public class ViewHTMLActivity extends Activity {
	private static final int FINISH = 0;
	private FileUtils fileUtils = null;
	private ProgressBar progressBar = null;
	private ProgressBar progressBar2 = null;
	private String filePath = null;
	private TextView htmlView;
	private ItemInfo itemInfo = null;
	private Handler mHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			switch (msg.arg1)
			{
			case FINISH:
				// 把获取到的新闻显示到界面上
				progressBar2.setVisibility(View.INVISIBLE);
				htmlView.setText(Html.fromHtml(msg.obj.toString()));
				break;
			}
		}
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_iteminfo_details);
		//取出对象
		itemInfo = (ItemInfo) this.getIntent().getSerializableExtra("itemInfo");
		//设置标题以及来源
		TextView textViewTitle = (TextView) findViewById(R.id.details_title);
		textViewTitle.setText(itemInfo.getTitleString());
		TextView textViewFAT = (TextView)findViewById(R.id.details_fromAndTime);
		textViewFAT.setText(itemInfo.getFromAndTime());
		htmlView = (TextView) findViewById(R.id.details_html);
		progressBar = (ProgressBar) findViewById(R.id.loadhtml_progress);
		progressBar2 = (ProgressBar) findViewById(R.id.loadhtml_progress2);
		progressBar2.setVisibility(View.VISIBLE);
		
		// 启动线程
		new UpdateHTMLThread().start();
	}
	
	/**
	 * 在网上获取HTML文件及其内容
	 */
	private class UpdateHTMLThread extends Thread
	{
		@Override
		public void run()
		{
			// 从本地或者网络上获取新闻
			String HTMLBody = getHTMLBody();
			Message msg = mHandler.obtainMessage();
			msg.arg1 = FINISH;
			msg.obj = HTMLBody;
			mHandler.sendMessage(msg);
			
		}
	}
	/**
	 * 获取HTML的内容，从本地或者网络
	 * @return
	 */
	private String getHTMLBody() {
		String hTMLBody = null;
		filePath = AppConstant.PATH.COMPLETE_PATH+itemInfo.getContentHTMLName();
		fileUtils = new FileUtils();
		//根据本地文件是否存在来判断执行方式
		if(fileUtils.isFileExist(filePath)){
			hTMLBody = FileUtils.getStringFromFile(filePath);
		}else{
			try {
				HttpDownloader.getFileFromServer(AppConstant.URL.BASE_URL+itemInfo.getContentHTMLName(), AppConstant.PATH.COMPLETE_PATH+itemInfo.getContentHTMLName() , progressBar);
			} catch (Exception e) {
				
			}
			hTMLBody = FileUtils.getStringFromFile(filePath);
		}
		
		return hTMLBody;
	}
}
