package bao.women.mactivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import bao.utils.FileUtils;
import bao.women.model.AppConstant;


import net.ting.sliding.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{
	private SlideMenu slideMenu;
	
	private ViewPager viewPager; // android-support-v4中的滑动组件
	private List<ImageView> imageViews; // 滑动的图片集合

	private String[] titles; // 图片标题
	private int[] imageResId; // 图片ID
	private List<View> dots; // 图片标题正文的那些点

	private TextView tv_title;
	private int currentItem = 0; // 当前图片的索引号
	private ImageButton imageButton;

	// An ExecutorService that can schedule commands to run after a given delay,
	// or to execute periodically.
	private ScheduledExecutorService scheduledExecutorService;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//创建资源存放的目录
		FileUtils fileUtils = new FileUtils();
		fileUtils.creatSDDir(AppConstant.PATH.PATH_WITHOUT_SEPARATE);
		
		slideMenu = (SlideMenu) findViewById(R.id.slide_menu);
		View item1 = findViewById(R.id.tv1);
		ImageView menuImg = (ImageView) findViewById(R.id.title_bar_menu_btn);
		menuImg.setOnClickListener(this);
		item1.setOnClickListener(this);
		
		

		imageResId = new int[] { R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e };
		titles = new String[imageResId.length];
		titles[0] = "清风涤荡你的肺腑，天籁拨动你的心音";
		titles[1] = "Eternity is not a distance but a decision.";
		titles[2] = "即使变成笨蛋我也不愿意失去你";
		titles[3] = "人生就像骑单车，想保持平衡就得往前进";
		titles[4] = "最大的愉悦就是看到你和阳光都在";

		imageViews = new ArrayList<ImageView>();

		// 初始化图片资源
		for (int i = 0; i < imageResId.length; i++) {
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(imageResId[i]);
		imageView.setScaleType(ScaleType.FIT_XY);
		imageView.setOnClickListener(this);
		imageViews.add(imageView);
		}
		dots = new ArrayList<View>();
		dots.add(findViewById(R.id.v_dot0));
		dots.add(findViewById(R.id.v_dot1));
		dots.add(findViewById(R.id.v_dot2));
		dots.add(findViewById(R.id.v_dot3));
		dots.add(findViewById(R.id.v_dot4));

		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText(titles[0]);//

		viewPager = (ViewPager) findViewById(R.id.vp);
		viewPager.setAdapter(new MyAdapter());// 设置填充ViewPager页面的适配器
		// 设置一个监听器，当ViewPager中的页面改变时调用
		viewPager.setOnPageChangeListener(new MyPageChangeListener());
		imageButton = (ImageButton) findViewById(R.id.imagebtn_alpha);
		float f=0;
		imageButton.setAlpha(f);
		imageButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_bar_menu_btn:
			if (slideMenu.isMainScreenShowing()) {
				slideMenu.openMenu();				
			} else {
				slideMenu.closeMenu();
			}
			//Toast.makeText(this, ""+titles[viewPager.getCurrentItem()], Toast.LENGTH_SHORT).show();
			break;
		//点击了推荐列表
		case R.id.imagebtn_alpha:
			Toast.makeText(this, ""+titles[viewPager.getCurrentItem()], Toast.LENGTH_SHORT).show();
			break;
		case R.id.tv1:
			
			Intent intent = new Intent();
			intent.setClass(MainActivity.this,	ItemListActivity.class);
			startActivity(intent);
			slideMenu.closeMenu();
		}
	}
	
	// 切换当前显示的图片
	private Handler handler = new Handler() {
	  public void handleMessage(android.os.Message msg) {
	   viewPager.setCurrentItem(currentItem);// 切换当前显示的图片
	  }
	 };
	 
	
	 @Override
	 protected void onStart() {
	  scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
	  // 当Activity显示出来后，每两秒钟切换一次图片显示
	  scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 3, 4, TimeUnit.SECONDS);
	  super.onStart();
	 }

	 @Override
	 protected void onStop() {
	  // 当Activity不可见的时候停止切换
	  scheduledExecutorService.shutdown();
	  super.onStop();
	 }

	 /**
	  * 换行切换任务
	  *
	  * @author Administrator
	  *
	  */
	 private class ScrollTask implements Runnable {

	  public void run() {
	   synchronized (viewPager) {
	    System.out.println("currentItem: " + currentItem);
	    currentItem = (currentItem + 1) % imageViews.size();
	    handler.obtainMessage().sendToTarget(); // 通过Handler切换图片
	   }
	  }

	 }

	 /**
	  * 当ViewPager中页面的状态发生改变时调用
	  *
	  * @author Administrator
	  *
	  */
	 private class MyPageChangeListener implements OnPageChangeListener {
	  private int oldPosition = 0;

	  /**
	   * This method will be invoked when a new page becomes selected.
	   * position: Position index of the new selected page.
	   */
	  public void onPageSelected(int position) {
	   currentItem = position;
	   tv_title.setText(titles[position]);
	   dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
	   dots.get(position).setBackgroundResource(R.drawable.dot_focused);
	   oldPosition = position;
	  }

	  public void onPageScrollStateChanged(int arg0) {

	  }

	  public void onPageScrolled(int arg0, float arg1, int arg2) {

	  }
	 }

	 /**
	  * 填充ViewPager页面的适配器
	  *
	  * @author Administrator
	  *
	  */
	 private class MyAdapter extends PagerAdapter {

	  @Override
	  public int getCount() {
	   return imageResId.length;
	  }

	  @Override
	  public Object instantiateItem(View arg0, int arg1) {
	   ((ViewPager) arg0).addView(imageViews.get(arg1));
	   return imageViews.get(arg1);
	  }

	  @Override
	  public void destroyItem(View arg0, int arg1, Object arg2) {
	   ((ViewPager) arg0).removeView((View) arg2);
	  }

	  @Override
	  public boolean isViewFromObject(View arg0, Object arg1) {
	   return arg0 == arg1;
	  }

	  @Override
	  public void restoreState(Parcelable arg0, ClassLoader arg1) {

	  }

	  @Override
	  public Parcelable saveState() {
	   return null;
	  }

	  @Override
	  public void startUpdate(View arg0) {

	  }

	  @Override
	  public void finishUpdate(View arg0) {

	  }
	}

}

