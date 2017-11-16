package com.flickerpuzzle.ui;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.Toast;

import com.flickerpuzzle.R;
import com.flickerpuzzle.component.api.ASDKError;
import com.flickerpuzzle.component.api.IHttpResponseHandler;
import com.flickerpuzzle.component.api.ResponseObject;
import com.flickerpuzzle.component.cache.cacheverifier.ImageFileCacheManager;
import com.flickerpuzzle.component.dto.FlickrDTO;
import com.flickerpuzzle.core.database.AClientSQLiteHelper;
import com.flickerpuzzle.core.database.DataBaseCallBack;
import com.flickerpuzzle.core.database.SDKClientSqlDb;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * (c) Ashakant 2014
 *
 * @author Ashakant <ashakant.kumar@gmail.com>
 * @since 14:03:59 - 18.11.2014
 */
public class HelloGridView extends BaseActivity {

	private static final int IMAGE_LOAD_TIMER=6000 ;
	private LazyImageLoadAdapter mGridImgAdapter; 
	private GridView mGridview ;  
	private ImageView mSlidingImage;  
	private int mCurrentImageIndex=0 ;   
	private List<Integer> mRandomNumberList;  

	/**  
	 * called on Activity onCreate callback ,first API call to get user photo set information to fetch further 
	 * Information from server
	 * @param-Bundle
	 * @return-void
	 */
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gridview);
		initCacheBthn() ;
		init() ;
		mGridview = (GridView) HelloGridView.this.findViewById(R.id.flickrgridview);
		mSession.getApiCaller(this).getPhotoSetRequest(new IHttpResponseHandler(){
			@Override
			public void onSucess(ResponseObject<?> resObject) {

				HelloGridView.this.mFlickerDTO=(FlickrDTO)resObject.get() ;
				HelloGridView.this.mGridImgAdapter=new LazyImageLoadAdapter(HelloGridView.this,
						mFlickerDTO.getMainPhotUrl() ,mSession);
				HelloGridView.this.mGridview.setAdapter(mGridImgAdapter);


			}
			@Override
			public void onError(ASDKError error) {
				System.out.println("Hi Error");
			}
		}) ;

		/**
		 * =========================Grid item click handler =========================================================
		 */
		mGridview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, final View v, int position,long id) {
				if(mCurrentImageIndex==mRandomNumberList.size()){
					Toast.makeText(HelloGridView.this, 
							"Game is over!!!", 
							Toast.LENGTH_LONG)
							.show();
					return ;
				}
				if(mRandomNumberList.get(mCurrentImageIndex)==position){
					TableLayout tablelayout = (TableLayout) v ;
					ImageView imgview=(ImageView)tablelayout.getChildAt(0) ;
					imgview.setImageResource(R.drawable.ic_launcher);
					initslider() ;
				}
			}
		}) ;
	}

	/**  
	 * initialize the activity logic -wtih 9 random number generation in the list
	 * @param-void
	 * @return-void
	 */
	private void init(){
		mRandomNumberList=new ArrayList<Integer>(26);
		RandomGeneration random=new RandomGeneration(26) ;
		mRandomNumberList=random.getList() ;
	}

	/**  
	 * initialize the  image preview of grid image
	 * @param-void
	 * @return-void
	 */
	private void initslider(){
		mSlidingImage = (ImageView)findViewById(R.id.imagePreview) ;
		mGridImgAdapter.setBitmapbyIndex(mRandomNumberList.get(++mCurrentImageIndex),mSlidingImage) ;
	}

	/**  
	 * called by activity callback - used to free grid adapter
	 * @param-void
	 * @return-void
	 */
	@Override
	public void onDestroy(){
		mGridview.setAdapter(null);
		super.onDestroy();
	}

	/**  
	 * called by activity callback - to handle the logic on activity visible
	 * @param-void
	 * @return-void
	 */
	@Override
	public void onStart(){
		super.onStart();
		startTimer() ;
	}

	/**  
	 * start the timer 
	 * @param-void
	 * @return-void
	 */
	public void startTimer(){
		new CountDownTimer(IMAGE_LOAD_TIMER, IMAGE_LOAD_TIMER){
			@Override
			public void onFinish() {
				mGridImgAdapter.setDefaultImg(true);
				mGridImgAdapter.notifyDataSetChanged() ;
				HelloGridView.this.initslider() ;
				mGridview.invalidate();	
			}
			@Override
			public void onTick(long millisUntilFinished) {
			}
		}.start() ;
	}

	/**  
	 * A helper function to handle the clear cache button events
	 * @param-void
	 * @return-void
	 */
	private void initCacheBthn(){
		Button resetCache=(Button)findViewById(R.id.clearbtn);
		resetCache.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				File cacheDir=new File(android.os.Environment.getExternalStorageDirectory(),"LazyList");
				Resources r=HelloGridView.this.getResources() ;
				ImageFileCacheManager fileCache=new ImageFileCacheManager(HelloGridView.this) ;
				fileCache.deleteRecursive(cacheDir) ;
				int duration = Toast.LENGTH_SHORT;
				Toast toast = Toast.makeText(HelloGridView.this,r.getString(R.string.cache_release_toast_msg) , duration);
				AClientSQLiteHelper db = new AClientSQLiteHelper(HelloGridView.this,
						r.getString(R.string.flick_url_schemaCmd_small),
						r.getString(R.string.flick_url_table_small)) ;
				final SDKClientSqlDb  sqlDb=new SDKClientSqlDb(db) ;
				sqlDb.delete(r.getString(R.string.flick_url_table_small), null, 
						null, new DataBaseCallBack(){
					@Override
					public void exec(long l) {
						System.out.println("db deleted");
					}
				}) ;
				toast.show();
			}
		}) ;
	}
}
