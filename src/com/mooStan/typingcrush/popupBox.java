package com.mooStan.typingcrush;

import java.io.IOException;
import java.io.InputStream;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mooStan.typingcrush.soundsController;

public class popupBox {

	private Context myContext;
	private Activity myActivity;
	
	private RelativeLayout popbox, slashScreen, mainMenu, sub_menu, gameStage;
	private LinearLayout popboxCenter,popboxResult;
	private ImageView popboxOK_btn,popbox_trophy,ic_popbox_close,ic_fb_share,ic_submit_button;
	private TextView popboxMSG,popboxResultPts,popboxResultLvl2,popboxResultLvl,popboxResultPts2;
	private LinearLayout level_list;
	
	public soundsController soundsController;
	private GlobalVars globalVariable;
	
	private int boxType = 0;
	
	popupBox(Context context, Activity myActivityReference) {
		myContext = context;
		myActivity = myActivityReference;
		
		soundsController = new soundsController(myContext,myActivity);
		globalVariable = (GlobalVars) myActivity.getApplicationContext();

		popbox = (RelativeLayout) myActivity.findViewById(R.id.popbox);
		popboxCenter = (LinearLayout) myActivity.findViewById(R.id.popboxCenter);
		popboxResult = (LinearLayout) myActivity.findViewById(R.id.popboxResult);
		popboxOK_btn = (ImageView) myActivity.findViewById(R.id.popboxOK_btn);
		popboxMSG = (TextView) myActivity.findViewById(R.id.popboxMSG);
		popboxResultPts = (TextView) myActivity.findViewById(R.id.popboxResultPts);
		popboxResultPts2 = (TextView) myActivity.findViewById(R.id.popboxResultPts2);
		popboxResultLvl2 = (TextView) myActivity.findViewById(R.id.popboxResultLvl2);
		popboxResultLvl = (TextView) myActivity.findViewById(R.id.popboxResultLvl);
		popbox_trophy = (ImageView) myActivity.findViewById(R.id.popbox_trophy);
		ic_popbox_close = (ImageView) myActivity.findViewById(R.id.ic_popbox_close);
		ic_fb_share = (ImageView) myActivity.findViewById(R.id.ic_fb_share);
		ic_submit_button = (ImageView) myActivity.findViewById(R.id.ic_submit_button);
		
		level_list = (LinearLayout) myActivity.findViewById(R.id.level_list);
		
		slashScreen = (RelativeLayout) myActivity.findViewById(R.id.slashScreen);
		mainMenu = (RelativeLayout) myActivity.findViewById(R.id.mainMenu);
		sub_menu = (RelativeLayout) myActivity.findViewById(R.id.sub_menu);
		gameStage = (RelativeLayout) myActivity.findViewById(R.id.gameStage);
		
		setStageBackground_relative(popbox,"backgrounds/opacity_50_bg.png");
		setStageBackground_linear(popboxCenter,"backgrounds/popUp_bg.png");
		setStageBackground_linear(popboxResult,"backgrounds/popUp_bg2.png");
		
		Typeface tf = Typeface.createFromAsset(myActivity.getAssets(), "fonts/Cookies.ttf");
		popboxMSG.setTypeface(tf);
		popboxMSG.setTextSize(20);
		popboxMSG.setPadding(10, 0, 10, 20);
		popboxMSG.setTextColor(Color.parseColor("#7a7a7a"));
		
		tf = Typeface.createFromAsset(myActivity.getAssets(), "fonts/GROBOLD.ttf");
		popboxResultLvl.setTypeface(tf);
		popboxResultLvl.setTextSize(30);
		popboxResultLvl.setTextColor(Color.parseColor("#fc0000"));
		popboxResultLvl2.setTypeface(tf);
		popboxResultLvl2.setTextSize(30);
		popboxResultLvl2.setTextColor(Color.parseColor("#373737"));
		
		popboxResultPts.setTypeface(tf);
		popboxResultPts.setTextSize(50);
		popboxResultPts.setTextColor(Color.parseColor("#fd3636"));
		popboxResultPts2.setTypeface(tf);
		popboxResultPts2.setTextSize(50);
		popboxResultPts2.setTextColor(Color.parseColor("#373737"));
		
		popboxOK_btn.setOnTouchListener(new View.OnTouchListener() {
	        @Override
	        public boolean onTouch(View arg0, MotionEvent arg1) {
	            switch (arg1.getAction()) {
		            case MotionEvent.ACTION_DOWN: {
		            	soundsController.shortSoundClip("sounds/buttons_clicked.mp3");
		            	popboxOK_btn.setAlpha(180);

		                break;
		            }
		            case MotionEvent.ACTION_UP:{
		            	popboxOK_btn.setAlpha(255);
		            	
		            	closePopBox();
		            	
		                break;
		            }
	            }
	            return true;
	        }
	    });
		
		ic_popbox_close.setOnTouchListener(new View.OnTouchListener() {
	        @Override
	        public boolean onTouch(View arg0, MotionEvent arg1) {
	            switch (arg1.getAction()) {
		            case MotionEvent.ACTION_DOWN: {
		            	soundsController.shortSoundClip("sounds/buttons_clicked.mp3");
		            	ic_popbox_close.setAlpha(180);

		                break;
		            }
		            case MotionEvent.ACTION_UP:{
		            	ic_popbox_close.setAlpha(255);

		            	closePopBox();
		            	
		            	//updateResultBoard(globalVariable.currentLevels,200);
		            	
		            	stageController(mainMenu);

		                break;
		            }
	            }
	            return true;
	        }
	    });
		
		ic_fb_share.setOnTouchListener(new View.OnTouchListener() {
	        @Override
	        public boolean onTouch(View arg0, MotionEvent arg1) {
	            switch (arg1.getAction()) {
		            case MotionEvent.ACTION_DOWN: {
		            	soundsController.shortSoundClip("sounds/buttons_clicked.mp3");
		            	ic_fb_share.setAlpha(180);

		                break;
		            }
		            case MotionEvent.ACTION_UP:{
		            	ic_fb_share.setAlpha(255);
		            	

		            	
		                break;
		            }
	            }
	            return true;
	        }
	    });
		
		ic_submit_button.setOnTouchListener(new View.OnTouchListener() {
	        @Override
	        public boolean onTouch(View arg0, MotionEvent arg1) {
	            switch (arg1.getAction()) {
		            case MotionEvent.ACTION_DOWN: {
		            	soundsController.shortSoundClip("sounds/buttons_clicked.mp3");
		            	ic_submit_button.setAlpha(180);

		                break;
		            }
		            case MotionEvent.ACTION_UP:{
		            	ic_submit_button.setAlpha(255);
		            	

		            	
		                break;
		            }
	            }
	            return true;
	        }
	    });
	}
	
	public void closePopBox(){
		popbox.setVisibility(View.INVISIBLE);
    	popbox.setClickable(false);
    	globalVariable.isPopUpOpen = false;
    	globalVariable.isResultOpen = false;
	}
	
	public void showPopBox(String msg, int cboxType){
		popbox.setVisibility(View.VISIBLE);
		popboxMSG.setText(msg);
		popbox.setClickable(true);
		
		boxType = cboxType;
		
		popboxCenter.setVisibility(View.INVISIBLE);
		popboxResult.setVisibility(View.INVISIBLE);
		popbox_trophy.setVisibility(View.INVISIBLE);
		ic_popbox_close.setVisibility(View.INVISIBLE);
		
		switch(boxType){
			case 0:{
				popboxCenter.setVisibility(View.VISIBLE);
				
				globalVariable.isPopUpOpen = true;
				break;
			}
			case 1:{
				soundsController.shortSoundClip("sounds/splashScreen_sound.mp3");
				
				popboxResultLvl.setText("Level " + globalVariable.currentLevels);
				popboxResultLvl2.setText("Level " + globalVariable.currentLevels);
				
				popboxResultPts.setText(globalVariable.scores + "\npoints");
				popboxResultPts2.setText(globalVariable.scores + "\npoints");
				
				popboxResult.setVisibility(View.VISIBLE);
				popbox_trophy.setVisibility(View.VISIBLE);
				ic_popbox_close.setVisibility(View.VISIBLE);
				
				globalVariable.isResultOpen = true;
				break;
			}
		}

	}
	
	@SuppressLint("NewApi")
	private void setStageBackground_linear(LinearLayout thisStage, String fileName){

		try 
		{
			InputStream ims = myActivity.getAssets().open(fileName);
		    Drawable d = Drawable.createFromStream(ims, null);
		    
		    if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
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
	
	@SuppressLint("NewApi")
	private void setStageBackground_relative(RelativeLayout thisStage, String fileName){

		try 
		{
			InputStream ims = myActivity.getAssets().open(fileName);
		    Drawable d = Drawable.createFromStream(ims, null);
		    
		    if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
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
	
	private void updateResultBoard(int level, int scores){
		level_list.removeViewAt((level - 1));
		
		
		
	}
	
}
