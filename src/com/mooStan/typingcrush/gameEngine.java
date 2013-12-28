package com.mooStan.typingcrush;

import java.io.IOException;
import java.io.InputStream;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class gameEngine extends Activity {

	private Context myContext;
	private Activity myActivity;
	
	private TextView stageLevelShow, stageTimeShow;
	private RelativeLayout slashScreen, mainMenu, sub_menu, gameStage;
	private ImageView ic_time_bar;
	
	private GlobalVars globalVariable;
	
	private boolean stopCounter = false;
	
	private int currentLevel = 1, screenWidth = 0, screenHeight = 0, sdk = 0, countDowns = 0, countTotalDowns = 0;
	
	gameEngine(Context context, Activity myActivityReference) {
		myContext = context;
		myActivity = myActivityReference;
		
		globalVariable = (GlobalVars) myActivity.getApplicationContext();
		
		stageLevelShow = (TextView) myActivity.findViewById(R.id.stageLevelShow);
		stageTimeShow = (TextView) myActivity.findViewById(R.id.stageTimeShow);
		
		ic_time_bar = (ImageView) myActivity.findViewById(R.id.ic_time_bar);
		
		slashScreen = (RelativeLayout) myActivity.findViewById(R.id.slashScreen);
		mainMenu = (RelativeLayout) myActivity.findViewById(R.id.mainMenu);
		sub_menu = (RelativeLayout) myActivity.findViewById(R.id.sub_menu);
		gameStage = (RelativeLayout) myActivity.findViewById(R.id.gameStage);
	}

	public void startGameEngine(int level, int p_sdk){
		sdk = p_sdk;
		currentLevel = level;
		stageLevelShow.setText("Lvl " + currentLevel);
		
		countDowns = 30 + (level * 5);
		countTotalDowns = countDowns;
		
		stageTimeShow.setText("Time : " + secToString(countDowns));
		
		stageController(gameStage);
    	setStageBackground(gameStage,"backgrounds/sub_menu.jpg");
    	
    	setCountDownTimerBar(countDowns);
    	
    	stopCounter = false;
    	timerCountDown();
	}
	
	public void stopGameStage(){
		ic_time_bar.clearAnimation();
		stopCounter = true;
	}
	
	private void setCountDownTimerBar(int size){
		//ic_time_bar.getLayoutParams().width = size;
		
		Animation animationScale = new ScaleAnimation(1, 0, 1, 1);
    	animationScale.setDuration((size + 2) * 1000);

		AnimationSet animSet = new AnimationSet(true);
		animSet.setFillAfter(true);
		animSet.addAnimation(animationScale);
		
		ic_time_bar.startAnimation(animSet);
	}
	
	private void timerCountDown(){
		
		if(stopCounter == true){
			return;
		}
		
		//int barSize = globalVariable.gameStage_TimeBar_Width * (int)(countDowns * 100 / countTotalDowns) / 100;

		ic_time_bar.postDelayed(new Runnable() {
	        @Override
	        public void run() {
	        	countDowns--;
	        	
	        	stageTimeShow.setText("Time : " + secToString(countDowns));
	        	
	        	if(countDowns > 0){
	        		timerCountDown();
	        	}
	        }
	    }, 1000);
	}
	
	private String secToString(int secStr){
		String returnStr = "00:00";
		
		int minVal = (int)(countDowns / 60);
    	int secVal = (int)(countDowns % 60);
    	
    	String minValStr = minVal + "";
    	String secValStr = secVal + "";
    	
    	if(minVal < 10)
    		minValStr = "0" + minValStr;
    	
    	if(secVal < 10)
    		secValStr = "0" + secValStr;
    	
    	returnStr = minValStr + ":" + secValStr;
		
		return returnStr;
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
	
	@SuppressLint("NewApi")
	public void setStageBackground(RelativeLayout thisStage, String fileName){

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
	
}
