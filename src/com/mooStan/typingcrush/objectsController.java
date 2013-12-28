package com.mooStan.typingcrush;

import java.io.IOException;
import java.io.InputStream;

import com.mooStan.typingcrush.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.mooStan.typingcrush.soundsController;
import com.mooStan.typingcrush.popupBox;
import com.mooStan.typingcrush.gameEngine;

public class objectsController {

	private Context myContext;
	private Activity myActivity;
	
	private LinearLayout level_list;
	private ScrollView scrollList;
	
	private int curGloLevel = 0;
	
	private int sdk = 0;
	public soundsController soundsController;
	public popupBox popupBox;
	public gameEngine gameEngine;
	
	private GlobalVars globalVariable;

	objectsController(Context context, Activity myActivityReference) {
		myContext = context;
		myActivity = myActivityReference;
		
		soundsController = new soundsController(myContext,myActivity);
		popupBox = new popupBox(myContext,myActivity);
		gameEngine = new gameEngine(myContext,myActivity);
		
		level_list = (LinearLayout) myActivity.findViewById(R.id.level_list);
		scrollList = (ScrollView) myActivity.findViewById(R.id.scrollList);
		
		globalVariable = (GlobalVars) myActivity.getApplicationContext();
	}
	
	public void setWindowDetails(int sw,int sh,boolean sc,int tsdk){
		sdk = tsdk;
	}
	
	public void createLevelOptions(int curLevel){
		level_list.removeAllViewsInLayout();

		curGloLevel = curLevel;
		
		int maxLevel = curLevel + 4;
		
		for(int i = 1;i <= maxLevel;i++){
			ImageView levelEgg = new ImageView(myActivity);
			LinearLayout levelBox = new LinearLayout(myActivity);
			TextView levelText = new TextView(myActivity);
		
			levelBox.setGravity(Gravity.CENTER);
			
			Typeface tf = Typeface.createFromAsset(myActivity.getAssets(), "fonts/Cookies.ttf");
			levelText.setTypeface(tf);
			levelText.setTextSize(35);
			levelText.setPadding(20, 20, 20, 20);
			
			levelText.setText("Level " + i);
			
			if( i <= curLevel){
				levelEgg.setImageResource(R.drawable.ic_egg_enable);
				levelText.setTextColor(Color.parseColor("#b60202"));
			}else{
				levelEgg.setImageResource(R.drawable.ic_egg_disable);
				levelText.setTextColor(Color.parseColor("#656060"));
			}

			levelBox.setOnClickListener(new OnClickListener(){
		        public void onClick(View v) 
		        {
		        	gotoCurrentLevel(v.getId());
		        }
		    });
			
			levelBox.setId(i);
			levelBox.addView(levelEgg);
			levelBox.addView(levelText);
			level_list.addView(levelBox);
		}
		
		
		scrollList.postDelayed(new Runnable() {
	        @Override
	        public void run() {
	        	scrollList.smoothScrollTo(0, level_list.getBottom());
	        }
	    }, 500);

	}
	
	private void gotoCurrentLevel(int level){
		if( level <= (curGloLevel + 1) ){
			soundsController.shortSoundClip("sounds/buttons_clicked.mp3");
			
			gameEngine.startGameEngine(level,sdk);
			
        	globalVariable.curentStage++;
			
		}else{
			popupBox.showPopBox("You are not allow to jump across levels, please play them one by one.");
		}
		
	}
	
	@SuppressLint("NewApi")
	public void setStageBackground_linear(LinearLayout thisStage, String fileName){

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
