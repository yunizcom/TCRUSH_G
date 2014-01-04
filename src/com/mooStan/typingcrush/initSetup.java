package com.mooStan.typingcrush;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.StrictMode;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mooStan.typingcrush.R;
import com.mooStan.typingcrush.serverComm;
import com.mooStan.typingcrush.soundsController;
import com.mooStan.typingcrush.objectsController;
import com.mooStan.typingcrush.popupBox;
import com.mooStan.typingcrush.gameEngine;

public class initSetup {
	
	

	public serverComm serverComm;
	public soundsController soundsController;
	public objectsController objectsController;
	public popupBox popupBox;
	public gameEngine gameEngine;
	
	private GlobalVars globalVariable;

	private Context myContext;
	private Activity myActivity;
	
	private int screenWidth = 0, screenHeight = 0, sdk = 0;
	private boolean smallScreen = false;
	
	private RelativeLayout slashScreen, mainMenu, sub_menu, gameStage;
	private LinearLayout keypadBg;
	
	private ImageView fb_fanpage_btn, trophy_btn, ic_play_btn, ic_continue_btn;
	
	private TextView stageLevelShow,stageTimeShow,keyPadInputed,ic_trophy_game_scores;

	initSetup(Context context, Activity myActivityReference) {
		myContext = context;
		myActivity = myActivityReference;
		
		serverComm = new serverComm(myContext,myActivity);
		soundsController = new soundsController(myContext,myActivity);
		objectsController = new objectsController(myContext,myActivity);
		popupBox = new popupBox(myContext,myActivity);
		gameEngine = new gameEngine(myContext,myActivity);
		
		globalVariable = (GlobalVars) myActivity.getApplicationContext();
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
		gameStage = (RelativeLayout) myActivity.findViewById(R.id.gameStage);
		
		keypadBg = (LinearLayout) myActivity.findViewById(R.id.keypadBg);
		
		fb_fanpage_btn = (ImageView) myActivity.findViewById(R.id.fb_fanpage_btn);
		trophy_btn = (ImageView) myActivity.findViewById(R.id.trophy_btn);
		ic_play_btn = (ImageView) myActivity.findViewById(R.id.ic_play_btn);
		ic_continue_btn = (ImageView) myActivity.findViewById(R.id.ic_continue_btn);
		
		stageLevelShow = (TextView) myActivity.findViewById(R.id.stageLevelShow);
		stageTimeShow = (TextView) myActivity.findViewById(R.id.stageTimeShow);
		keyPadInputed = (TextView) myActivity.findViewById(R.id.keyPadInputed);
		ic_trophy_game_scores = (TextView) myActivity.findViewById(R.id.ic_trophy_game_scores);
	    
		objectsController.setWindowDetails(screenWidth,screenHeight,smallScreen,sdk);
		
	    uiSetup();
	}

	private void uiSetup(){
		stageController(slashScreen);
		objectsController.setStageBackground(slashScreen,"backgrounds/slashScreen.jpg");
		objectsController.setStageBackground_linear(keypadBg,"backgrounds/keypad_bg.png");
		globalVariable.curentStage = 0;
		
		Typeface tf = Typeface.createFromAsset(myActivity.getAssets(), "fonts/Cookies.ttf");
		Typeface tf2 = Typeface.createFromAsset(myActivity.getAssets(), "fonts/GROBOLD.ttf");
		stageLevelShow.setTextSize(20f);
		stageLevelShow.setTypeface(tf);
		stageLevelShow.setTextColor(Color.parseColor("#ff7979"));
		
		stageTimeShow.setTextSize(20f);
		stageTimeShow.setTypeface(tf);
		stageTimeShow.setTextColor(Color.parseColor("#ff7979"));
		
		ic_trophy_game_scores.setTextSize(24f);
		ic_trophy_game_scores.setTypeface(tf);
		ic_trophy_game_scores.setTextColor(Color.parseColor("#ff3b3b"));
		
		keyPadInputed.setTextSize(14f);
		keyPadInputed.setTypeface(tf2);
		keyPadInputed.setTextColor(Color.parseColor("#333333"));
		
		slashScreen.postDelayed(new Runnable() {
	        @Override
	        public void run() {
	        	soundsController.playBgMusic("sounds/splashScreen_sound.mp3",false);
	        	slashScreen.postDelayed(new Runnable() {
	    	        @Override
	    	        public void run() {
	    	        	stageController(mainMenu);
	    	        	objectsController.setStageBackground(mainMenu,"backgrounds/main_menu.jpg");
	    	        	globalVariable.curentStage = 1;
	    	        	
	    	        	serverComm.checkInternetConnection();
	    	        	
	    	        	setEffectListeners();
	    	        }
	    	    }, 2000);
	        }
	    }, 500);
		
	}
	
	private void stageController(RelativeLayout thisStage){
		slashScreen.setVisibility(View.INVISIBLE);
		mainMenu.setVisibility(View.INVISIBLE);
		sub_menu.setVisibility(View.INVISIBLE);
		gameStage.setVisibility(View.INVISIBLE);
		
		slashScreen.setClickable(false);
		mainMenu.setClickable(false);
		sub_menu.setClickable(false);
		gameStage.setClickable(false);
		
		thisStage.setVisibility(View.VISIBLE);
		thisStage.setClickable(true);
		
		System.gc();
	}

    private void setEffectListeners(){
	    fb_fanpage_btn.setOnTouchListener(new View.OnTouchListener() {
	        @Override
	        public boolean onTouch(View arg0, MotionEvent arg1) {
	            switch (arg1.getAction()) {
		            case MotionEvent.ACTION_DOWN: {
		            	soundsController.shortSoundClip("sounds/buttons_clicked.mp3");
		            	fb_fanpage_btn.setAlpha(180);

		                break;
		            }
		            case MotionEvent.ACTION_UP:{
		            	fb_fanpage_btn.setAlpha(255);
		            	
		            	Uri uri = Uri.parse("https://www.facebook.com/typingcrush");
			       		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			       		myActivity.startActivity(intent);
		            	
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
		            case MotionEvent.ACTION_UP:{
		            	trophy_btn.setAlpha(255);
		            	
		            	retreiveLeaderBoard(1,1);

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
		            case MotionEvent.ACTION_UP:{
		            	ic_play_btn.setAlpha(255);
		            	
		            	stageController(sub_menu);
		            	objectsController.setStageBackground(sub_menu,"backgrounds/sub_menu.jpg");
		            	globalVariable.curentStage = 2;

		            	objectsController.createLevelOptions(globalVariable.getLevel());
		            	
		                break;
		            }
	            }
	            return true;
	        }
	    });
	    
	    ic_continue_btn.setOnTouchListener(new View.OnTouchListener() {
	        @Override
	        public boolean onTouch(View arg0, MotionEvent arg1) {
	            switch (arg1.getAction()) {
		            case MotionEvent.ACTION_DOWN: {
		            	soundsController.shortSoundClip("sounds/buttons_clicked.mp3");
		            	ic_continue_btn.setAlpha(180);
		            	
		                break;
		            }
		            case MotionEvent.ACTION_UP:{
		            	ic_continue_btn.setAlpha(255);
		            	
		            	gameEngine.startGameEngine((globalVariable.getLevel() + 1),sdk);
		            	
		            	globalVariable.curentStage = 3;
		            	
		                break;
		            }
	            }
	            return true;
	        }
	    });
    }

    public void retreiveLeaderBoard(int page,int level){
		popupBox.showPopBox("\n\nProcessing, please hold on...",3);
		
		trophy_btn.postDelayed(new Runnable() {
	        @Override
	        public void run() {
        	
	        	gameScoresAPI("2");

	        }
	    }, 1000);
		
		
	}

	public void gameScoresAPI(String pages){
		String url = globalVariable.gameServerAPI_URL + "?mod=2&page=" + pages;
		//-------load JSON
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        //nameValuePairs.add(new BasicNameValuePair("convo_id", "4546db1fd1"));
        //nameValuePairs.add(new BasicNameValuePair("say", words));
		
		JSONObject json = globalVariable.getJSONfromURL(url, nameValuePairs);
		popupBox.closePopBox();
		try {
			if(json == null){
				popupBox.showPopBox("You need a smooth internet connection to retrieve LeaderBoard.",0);
			}else{
				Log.v("debug",json.getString("breakRecords"));
				//loadBoard(json.getJSONArray("hallOfFame"));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//-------load JSON
	}
    
	public void handleNativeBackAction(){
		soundsController.shortSoundClip("sounds/buttons_clicked.mp3");

		if(globalVariable.isPopUpOpen == false && globalVariable.isResultOpen == false && globalVariable.isBlockOpen == false){

			if(globalVariable.curentStage < 0){
				globalVariable.curentStage = 0;
			}

			switch(globalVariable.curentStage){
				case 2 : {
					globalVariable.curentStage = 1;
					stageController(mainMenu);
					objectsController.setStageBackground(mainMenu,"backgrounds/main_menu.jpg");
					
					break;
				}
				case 3 : {
					globalVariable.curentStage = 2;
					gameEngine.stopGameStage();
					stageController(sub_menu);
					objectsController.setStageBackground(sub_menu,"backgrounds/sub_menu.jpg");
					
					objectsController.createLevelOptions(globalVariable.getLevel());
					
					break;
				}
				case 1 : {
					globalVariable.curentStage = 0;
					myActivity.finish();
					
					break;
				}
			}
		}else{
			if(globalVariable.isBlockOpen == false){
				if(globalVariable.isResultOpen == true){
	
					switch(globalVariable.curentStage){
						case 2 : {
							globalVariable.curentStage = 1;
	
							break;
						}
						case 3 : {
							globalVariable.curentStage = 2;
	
							break;
						}
						case 1 : {
							globalVariable.curentStage = 0;
	
							break;
						}
					}
					
					stageController(sub_menu);
					objectsController.createLevelOptions(globalVariable.getLevel());
				}
				popupBox.closePopBox();
			}
		}
	}
	
}
