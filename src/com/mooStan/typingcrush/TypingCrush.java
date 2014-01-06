package com.mooStan.typingcrush;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.app.Activity;
import android.content.Intent;

import com.facebook.Session;
import com.mooStan.typingcrush.initSetup;
import com.mooStan.typingcrush.soundsController;

public class TypingCrush extends Activity {

	public initSetup initSetup;
	public soundsController soundsController;

	private GlobalVars globalVariable;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_typing_crush);

		initSetup = new initSetup(this.getApplicationContext(),this);
		soundsController = new soundsController(this.getApplicationContext(),this);
		
		globalVariable = (GlobalVars) getApplicationContext();
		
		initSetup.basicDetection();
		
		/*
		 * PackageInfo info;
		try {
		    info = getPackageManager().getPackageInfo("com.mooStan.typingcrush", PackageManager.GET_SIGNATURES);
		    for (Signature signature : info.signatures) {
		        MessageDigest md;
		        md = MessageDigest.getInstance("SHA");
		        md.update(signature.toByteArray());
		        String something = new String(Base64.encode(md.digest(), 0));
		        //String something = new String(Base64.encodeBytes(md.digest()));
		        Log.e("hash key", something);
		    }
		} catch (NameNotFoundException e1) {
		    Log.e("name not found", e1.toString());
		} catch (NoSuchAlgorithmException e) {
		    Log.e("no such an algorithm", e.toString());
		} catch (Exception e) {
		    Log.e("exception", e.toString());
		}
		 */
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		
		ImageView ic_time_bar = (ImageView) findViewById(R.id.ic_time_bar);
		globalVariable.gameStage_TimeBar_Width = ic_time_bar.getWidth();
	}
	
	@Override
    public void onBackPressed()
    {
		initSetup.handleNativeBackAction();
    }
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		soundsController.destroyBgMusic();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		
		soundsController.pauseBgMusic();
	}
	
	protected void onResume() {
		super.onResume();
		
		soundsController.resumeBgMusic();
	}
	
}
