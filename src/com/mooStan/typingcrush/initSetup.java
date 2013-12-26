package com.mooStan.typingcrush;

import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.StrictMode;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mooStan.typingcrush.R;
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
	
	public RelativeLayout slashScreen;
	public RelativeLayout mainMenu;
	
	public ImageView fb_fanpage_btn;
	public ImageView trophy_btn;
	public ImageView ic_play_btn;
	
	Timer WFT = new Timer();

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
	 
	    slashScreen = (RelativeLayout) myActivity.findViewById(R.id.slashScreen);
		mainMenu = (RelativeLayout) myActivity.findViewById(R.id.mainMenu);
		fb_fanpage_btn = (ImageView) myActivity.findViewById(R.id.fb_fanpage_btn);
		trophy_btn = (ImageView) myActivity.findViewById(R.id.trophy_btn);
		ic_play_btn = (ImageView) myActivity.findViewById(R.id.ic_play_btn);
	    
	    uiSetup();
	}

	private void uiSetup(){
		stageController(slashScreen);
		setStageBackground(slashScreen,"backgrounds/slashScreen.jpg");
		
		setWFT_bgSound();
	}
	
	private void stageController(RelativeLayout thisStage){
		slashScreen.setVisibility(View.INVISIBLE);
		mainMenu.setVisibility(View.INVISIBLE);
		
		thisStage.setVisibility(View.VISIBLE);
	}
	
	@SuppressLint("NewApi")
	private void setStageBackground(RelativeLayout thisStage, String fileName){
		double setNewHeight = screenHeight;
		double setNewWidth = screenWidth;
		
		try 
		{
			InputStream ims = myActivity.getAssets().open(fileName);
		    Drawable d = Drawable.createFromStream(ims, null);
		    
		    if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
		    	thisStage.setBackgroundDrawable(d);
		    } else {
		    	thisStage.setBackground(d);
		    }
		    
		    ims = null;
		    d = null;

		}
		catch(IOException ex) 
		{
		    return;
		}
		
	}
	
	private void setWFT_bgSound() {
        WFT.schedule(new TimerTask() {          
            @Override
            public void run() {
                WFTTimerMethod_bgSound();
            }
        }, 500); // 4 seconds delay
    }
    private void WFTTimerMethod_bgSound() {
    	myActivity.runOnUiThread(play_bgSound);
    }
    private Runnable play_bgSound = new Runnable() {
        public void run() {
        	soundsController.playBgMusic("sounds/splashScreen_sound.mp3",false);
        	setWFT_remove_splashScreen();
        }
    };
    
    private void setWFT_remove_splashScreen() {
        WFT.schedule(new TimerTask() {          
            @Override
            public void run() {
                WFTTimerMethod_remove_splashScreen();
            }
        }, 2000); // 4 seconds delay
    }
    private void WFTTimerMethod_remove_splashScreen() {
    	myActivity.runOnUiThread(remove_splashScreen);
    }
    private Runnable remove_splashScreen = new Runnable() {
        public void run() {
        	stageController(mainMenu);
        	setStageBackground(mainMenu,"backgrounds/main_menu.jpg");
        	
        	serverComm.checkInternetConnection();
        	
        	setEffectListeners();
        }
    };
	
    private void setEffectListeners(){
	    fb_fanpage_btn.setOnTouchListener(new View.OnTouchListener() {
	        @Override
	        public boolean onTouch(View arg0, MotionEvent arg1) {
	            switch (arg1.getAction()) {
		            case MotionEvent.ACTION_DOWN: {
		            	soundsController.shortSoundClip("sounds/buttons_clicked.mp3");
		            	fb_fanpage_btn.setAlpha(180);
		            	
		            	Uri uri = Uri.parse("https://www.facebook.com/typingcrush");
			       		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			       		myActivity.startActivity(intent);
			       		 
		                break;
		            }
		            case MotionEvent.ACTION_CANCEL:{
		            	fb_fanpage_btn.setAlpha(255);
		                break;
		            }
		            case MotionEvent.ACTION_UP:{
		            	fb_fanpage_btn.setAlpha(255);
		                break;
		            }
	            }
	            return true;
	        }
	    });
	    
	    trophy_btn.setOnTouchListener(new View.OnTouchListener() {
	        @Override
	        public boolean onTouch(View arg0, MotionEvent arg1) {
	            switch (arg1.getAction()) {
		            case MotionEvent.ACTION_DOWN: {
		            	soundsController.shortSoundClip("sounds/buttons_clicked.mp3");
		            	trophy_btn.setAlpha(180);

		                break;
		            }
		            case MotionEvent.ACTION_CANCEL:{
		            	trophy_btn.setAlpha(255);
		                break;
		            }
		            case MotionEvent.ACTION_UP:{
		            	trophy_btn.setAlpha(255);
		                break;
		            }
	            }
	            return true;
	        }
	    });
	    
	    ic_play_btn.setOnTouchListener(new View.OnTouchListener() {
	        @Override
	        public boolean onTouch(View arg0, MotionEvent arg1) {
	            switch (arg1.getAction()) {
		            case MotionEvent.ACTION_DOWN: {
		            	soundsController.shortSoundClip("sounds/buttons_clicked.mp3");
		            	ic_play_btn.setAlpha(180);

		                break;
		            }
		            case MotionEvent.ACTION_CANCEL:{
		            	ic_play_btn.setAlpha(255);
		                break;
		            }
		            case MotionEvent.ACTION_UP:{
		            	ic_play_btn.setAlpha(255);
		                break;
		            }
	            }
	            return true;
	        }
	    });
    }

}
