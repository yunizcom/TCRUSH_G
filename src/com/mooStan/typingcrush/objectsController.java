package com.mooStan.typingcrush;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.mooStan.typingcrush.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.Log;
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
		System.gc();

		curGloLevel = curLevel;
		
		int maxLevel = curLevel + 3;

		//-----retrieve all saved scores-----
		String curScores = globalVariable.getYunizScores();
		String[] levelScores = curScores.split("[|]");
		
		List<String> list = new ArrayList<String>();
		Collections.addAll(list, levelScores);

		for(int i = 0; i <= maxLevel; i++){			
			if(i < levelScores.length){
				if(levelScores[i].equals("")){
					list.add(i, "0");
				}else{
					list.add(i, levelScores[i]);
				}
			}else{
				list.add(i, "0");
			}
		}
		levelScores = list.toArray(new String[list.size()]);
		//-----retrieve all saved scores-----
		
		for(int i = 1;i <= maxLevel;i++){
			ImageView levelEgg = new ImageView(myActivity);
			LinearLayout levelBox = new LinearLayout(myActivity);
			LinearLayout levelDetails = new LinearLayout(myActivity);
			TextView levelText = new TextView(myActivity);
			TextView scoresText = new TextView(myActivity);
		
			levelBox.setGravity(Gravity.LEFT);
			levelDetails.setOrientation(LinearLayout.VERTICAL);
			
			Typeface tf = Typeface.createFromAsset(myActivity.getAssets(), "fonts/Cookies.ttf");
			levelText.setTypeface(tf);
			levelText.setTextSize(25);
			levelText.setPadding(20, 10, 20, 0);
			
			levelText.setText("Level " + i);
			
			scoresText.setTypeface(tf);
			scoresText.setTextSize(20);
			scoresText.setPadding(20, 10, 20, 10);
			
			scoresText.setText(levelScores[(i-1)] + " pts");
			
			if( i <= curLevel){
				levelEgg.setImageResource(R.drawable.ic_egg_enable);
				levelText.setTextColor(Color.parseColor("#b60202"));
				scoresText.setTextColor(Color.parseColor("#ff3b3b"));
			}else{
				levelEgg.setImageResource(R.drawable.ic_egg_disable);
				levelText.setTextColor(Color.parseColor("#656060"));
				scoresText.setTextColor(Color.parseColor("#656060"));
			}

			levelBox.setOnClickListener(new OnClickListener(){
		        public void onClick(View v) 
		        {
		        	gotoCurrentLevel(v.getId());
		        }
		    });
			
			levelDetails.addView(levelText);
			levelDetails.addView(scoresText);
			
			levelBox.setId(i);
			levelBox.addView(levelEgg);
			levelBox.addView(levelDetails);
			
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
