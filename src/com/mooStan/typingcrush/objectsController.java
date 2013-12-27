package com.mooStan.typingcrush;

import com.mooStan.typingcrush.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.mooStan.typingcrush.soundsController;

public class objectsController {

	private Context myContext;
	private Activity myActivity;
	
	private LinearLayout level_list;
	private ScrollView scrollList;
	
	private int curGloLevel = 0;
	
	public soundsController soundsController;

	objectsController(Context context, Activity myActivityReference) {
		myContext = context;
		myActivity = myActivityReference;
		
		soundsController = new soundsController(myContext,myActivity);
		
		level_list = (LinearLayout) myActivity.findViewById(R.id.level_list);
		scrollList = (ScrollView) myActivity.findViewById(R.id.scrollList);
	}
	
	public void createLevelOptions(int curLevel){
		level_list.removeAllViewsInLayout();

		curGloLevel = curLevel;
		
		int maxLevel = curLevel + 3;
		
		for(int i = 1;i <= maxLevel;i++){
			ImageView levelEgg = new ImageView(myActivity);
			LinearLayout levelBox = new LinearLayout(myActivity);
			TextView levelText = new TextView(myActivity);
			
			Typeface tf = Typeface.createFromAsset(myActivity.getAssets(), "fonts/Cookies.ttf");
			levelText.setTypeface(tf);
			levelText.setGravity(Gravity.BOTTOM);
			levelText.setTextSize(30f);
			levelText.setPadding(20, 25, 20, 20);
			
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
		}else{
			Toast.makeText(myContext.getApplicationContext(), "You are not allow to jump across levels, please play them one by one." , Toast.LENGTH_LONG).show();
		}
		
	}
	
}
