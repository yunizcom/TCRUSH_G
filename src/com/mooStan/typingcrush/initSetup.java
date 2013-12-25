package com.mooStan.typingcrush;

import java.io.IOException;
import java.io.InputStream;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.view.Display;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.mooStan.typingcrush.serverComm;
import com.mooStan.typingcrush.soundsController;

public class initSetup {
	
	public serverComm serverComm;
	public soundsController soundsController;

	private Context myContext;
	private Activity myActivity;
	
	public int screenWidth = 0;
	public int screenHeight = 0;
	public boolean smallScreen = false;
	public int sdk = 0;

	initSetup(Context context, Activity myActivityReference) {
		myContext = context;
		myActivity = myActivityReference;
		
		serverComm = new serverComm(myContext);
		soundsController = new soundsController(myContext,myActivity);
	}
	
	@SuppressLint("NewApi")
	public void basicDetection(){

		myActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		sdk = android.os.Build.VERSION.SDK_INT;
		
		//----------detect device setting and adapt environment
		Display display = myActivity.getWindowManager().getDefaultDisplay();
		Point size = new Point();

		smallScreen = false;
		try
		{ 
			display.getSize(size); 
			screenWidth = size.x; 
			screenHeight = size.y; 
			smallScreen = false;
		} 
		catch (NoSuchMethodError e) 
		{ 
			screenWidth = display.getWidth(); 
			screenHeight = display.getHeight(); 
			smallScreen = true;
		}
	
	    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	    StrictMode.setThreadPolicy(policy);
		//----------detect device setting and adapt environment
	 
	    uiSetup();
	}
	
	@SuppressLint("NewApi")
	public void uiSetup(){
		double setNewHeight = screenHeight;
		double setNewWidth = screenWidth;
		
		RelativeLayout slashScreen = (RelativeLayout) myActivity.findViewById(R.id.slashScreen);
		
		try 
		{
			InputStream ims = myActivity.getAssets().open("backgrounds/slashScreen.jpg");
		    Drawable d = Drawable.createFromStream(ims, null);

		    if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
		    	slashScreen.setBackgroundDrawable(d);
		    } else {
		    	slashScreen.setBackground(d);
		    }
		}
		catch(IOException ex) 
		{
		    return;
		}
		
		soundsController.playBgMusic("sounds/splashScreen_sound.mp3",false);
		serverComm.checkInternetConnection();
	}
	
}
