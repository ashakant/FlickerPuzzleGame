package com.flickerpuzzle.ui;

import android.app.Activity;
import android.os.Bundle;
import com.flickerpuzzle.client.AClient;
import com.flickerpuzzle.client.IUserSession;
import com.flickerpuzzle.component.dto.FlickrDTO;

/**
 * (c) Ashakant 2014
 *
 * @author Ashakant <ashakant.kumar@gmail.com>
 * @since 14:03:59 - 18.11.2014
 */
public class BaseActivity extends  Activity{
	protected AClient mAClient ;
	protected IUserSession mSession ;
	protected FlickrDTO mFlickerDTO ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		mAClient=new AClient() ;
		mSession=mAClient.getSession() ;
	}
	
	/**  
	 * Called on activity onStart callback
	 * @param-void
	 * @return-void
	 */
	@Override
	protected void onStart(){
		super.onStart();
	}

	/**  
	 * Called on activity onRestart callback
	 * @param-void
	 * @return-void
	 */
	@Override
	protected void onRestart(){
		super.onRestart();
	}

	/**  
	 * Called on activity onPause callback
	 * @param-void
	 * @return-void
	 */
	@Override
	protected void onPause(){
		super.onPause();
		mSession.onPauseSession();
	}

	/**  
	 * Called on activity onStop callback
	 * @param-void
	 * @return-void
	 */
	@Override
	protected void onStop(){
		super.onStop();
	}

	/**  
	 * Called on activity onResume callback
	 * @param-void
	 * @return-void
	 */
	@Override
	protected void onResume(){
		super.onResume();
		mSession.onResumeSession();
	}

	/**  
	 * Called on activity onDestroy callback
	 * @param-void
	 * @return-void
	 */
	@Override
	public void onDestroy(){
		super.onDestroy();
		mSession.onCloseSession();
	}

	/**  
	 * Called on activity onBackPressed callback
	 * @param-void
	 * @return-void
	 */
	@Override
	public void onBackPressed(){
		super.onBackPressed();
	}
}
