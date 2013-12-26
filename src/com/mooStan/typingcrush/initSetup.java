package com.mooStan.typingcrush;

import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
	
	public static final String PREFS_SETTINGS = "YunizSaved";
	
	private int curentStage = 0;
	
	public serverComm serverComm;
	public soundsController soundsController;

	private Context myContext;
	private Activity myActivity;
	
	private int screenWidth = 0;
	private int screenHeight = 0;
	private boolean smallScreen = false;
	private int sdk = 0;
	
	private RelativeLayout slashScreen;
	private RelativeLayout mainMenu;
	private RelativeLayout sub_menu;
	
	private ImageView fb_fanpage_btn;
	private ImageView trophy_btn;
	private ImageView ic_play_btn;
	
	private Timer WFT = new Timer();

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
		sub_menu = (RelativeLayout) myActivity.findViewById(R.id.sub_menu);
		fb_fanpage_btn = (ImageView) myActivity.findViewById(R.id.fb_fanpage_btn);
		trophy_btn = (ImageView) myActivity.findViewById(R.id.trophy_btn);
		ic_play_btn = (ImageView) myActivity.findViewById(R.id.ic_play_btn);
	    
	    uiSetup();
	}

	private void uiSetup(){
		stageController(slashScreen);
		setStageBackground(slashScreen,"backgrounds/slashScreen.jpg");
		curentStage = 0;
		
		setWFT_bgSound();
	}
	
	private void stageController(RelativeLayout thisStage){
		slashScreen.setVisibility(View.INVISIBLE);
		mainMenu.setVisibility(View.INVISIBLE);
		sub_menu.setVisibility(View.INVISIBLE);
		
		slashScreen.setClickable(false);
		mainMenu.setClickable(false);
		sub_menu.setClickable(false);
		
		thisStage.setVisibility(View.VISIBLE);
		thisStage.setClickable(false);
		
		System.gc();
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
        	curentStage++;
        	
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

		            	stageController(sub_menu);
		            	setStageBackground(sub_menu,"backgrounds/sub_menu.jpg");
		            	curentStage++;
		            	
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

    public void saveYunizSaved(int level, int bombs, String playername, int shares){ // usage : saveYunizSaved(10, 2, "Stanly", 1);
		  SharedPreferences settings = myActivity.getSharedPreferences(PREFS_SETTINGS, 0);
	      SharedPreferences.Editor editor = settings.edit();
	      editor.putInt("YunizCurLevel", level);
	      editor.putInt("YunizCurBombs", bombs);
	      editor.putString("YunizPlayerName", playername);
	      editor.putInt("YunizShared", shares);

	      editor.commit();
	}
	
	public JSONObject retriveScoreValue() throws JSONException{ // usage : retriveScoreValue().getString("YunizCurLevel") + " | " + retriveScoreValue().getString("YunizCurBombs") + " | " + retriveScoreValue().getString("YunizPlayerName") + " | " + retriveScoreValue().getString("YunizShared")
		String returnString = "";
		SharedPreferences settings = myActivity.getSharedPreferences(PREFS_SETTINGS, 0);
		returnString = "{'YunizCurLevel' : " + settings.getInt("YunizCurLevel", 0) + ",'YunizCurBombs' : " + settings.getInt("YunizCurBombs", 0) + ",'YunizPlayerName' : " + settings.getString("YunizPlayerName", "New Player") + ",'YunizShared' : " + settings.getInt("YunizShared", 0) + "}";
		
		JSONObject json = null;
		try {
			json = new JSONObject(returnString);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return json;
	}
    
	public void handleNativeBackAction(){
		soundsController.shortSoundClip("sounds/buttons_clicked.mp3");
		
		curentStage--;
	
		switch(curentStage){
			case 1 : {
				
				stageController(mainMenu);
            	setStageBackground(mainMenu,"backgrounds/main_menu.jpg");
				
				break;
			}
			case 2 : {
				
				stageController(sub_menu);
            	setStageBackground(sub_menu,"backgrounds/sub_menu.jpg");
				
				break;
			}
			case 0 : {
				
				myActivity.finish();
				
				break;
			}
		}
	}
	
}
