package com.mooStan.typingcrush;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.mooStan.typingcrush.soundsController;
import com.revmob.RevMob;
import com.revmob.RevMobTestingMode;

public class gameEngine extends Activity {

	private Context myContext;
	private Activity myActivity;
	
	private TextView stageLevelShow, stageTimeShow, keyPadInputed, ic_trophy_game_scores,ic_mybomb_txt;
	private RelativeLayout slashScreen, mainMenu, sub_menu, gameStage,leaderBoard;
	private LinearLayout gameObjects;
	private ImageView ic_mybomb,ic_time_bar,ic_key_q,ic_key_w,ic_key_e,ic_key_r,ic_key_t,ic_key_y,ic_key_u,ic_key_i,ic_key_o,ic_key_p,ic_key_a,ic_key_s,ic_key_d,ic_key_f,ic_key_g,ic_key_h,ic_key_j,ic_key_k,ic_key_l,ic_key_z,ic_key_x,ic_key_c,ic_key_v,ic_key_b,ic_key_n,ic_key_m,ic_key_del,ic_key_clr;
	
	public soundsController soundsController;
	public popupBox popupBox;
	
	private GlobalVars globalVariable;
	
	private int currentLevel = 1, screenWidth = 0, screenHeight = 0, sdk = 0;
	
	private String[] lettersLib = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};

	private RevMob revmob;
	
	gameEngine(Context context, Activity myActivityReference) {
		myContext = context;
		myActivity = myActivityReference;
		
		globalVariable = (GlobalVars) myActivity.getApplicationContext();
		
		soundsController = new soundsController(myContext,myActivity);
		popupBox = new popupBox(myContext,myActivity);
		
		stageLevelShow = (TextView) myActivity.findViewById(R.id.stageLevelShow);
		stageTimeShow = (TextView) myActivity.findViewById(R.id.stageTimeShow);
		keyPadInputed = (TextView) myActivity.findViewById(R.id.keyPadInputed);
		ic_trophy_game_scores = (TextView) myActivity.findViewById(R.id.ic_trophy_game_scores);
		ic_mybomb_txt = (TextView) myActivity.findViewById(R.id.ic_mybomb_txt);
		
		ic_time_bar = (ImageView) myActivity.findViewById(R.id.ic_time_bar);
		
		ic_mybomb = (ImageView) myActivity.findViewById(R.id.ic_mybomb);
		
		ic_key_q = (ImageView) myActivity.findViewById(R.id.ic_key_q);
		ic_key_w = (ImageView) myActivity.findViewById(R.id.ic_key_w);
		ic_key_e = (ImageView) myActivity.findViewById(R.id.ic_key_e);
		ic_key_r = (ImageView) myActivity.findViewById(R.id.ic_key_r);
		ic_key_t = (ImageView) myActivity.findViewById(R.id.ic_key_t);
		ic_key_y = (ImageView) myActivity.findViewById(R.id.ic_key_y);
		ic_key_u = (ImageView) myActivity.findViewById(R.id.ic_key_u);
		ic_key_i = (ImageView) myActivity.findViewById(R.id.ic_key_i);
		ic_key_o = (ImageView) myActivity.findViewById(R.id.ic_key_o);
		ic_key_p = (ImageView) myActivity.findViewById(R.id.ic_key_p);
		ic_key_a = (ImageView) myActivity.findViewById(R.id.ic_key_a);
		ic_key_s= (ImageView) myActivity.findViewById(R.id.ic_key_s);
		ic_key_d = (ImageView) myActivity.findViewById(R.id.ic_key_d);
		ic_key_f = (ImageView) myActivity.findViewById(R.id.ic_key_f);
		ic_key_g = (ImageView) myActivity.findViewById(R.id.ic_key_g);
		ic_key_h = (ImageView) myActivity.findViewById(R.id.ic_key_h);
		ic_key_j = (ImageView) myActivity.findViewById(R.id.ic_key_j);
		ic_key_k = (ImageView) myActivity.findViewById(R.id.ic_key_k);
		ic_key_l = (ImageView) myActivity.findViewById(R.id.ic_key_l);
		ic_key_z = (ImageView) myActivity.findViewById(R.id.ic_key_z);
		ic_key_x = (ImageView) myActivity.findViewById(R.id.ic_key_x);
		ic_key_c = (ImageView) myActivity.findViewById(R.id.ic_key_c);
		ic_key_v = (ImageView) myActivity.findViewById(R.id.ic_key_v);
		ic_key_b = (ImageView) myActivity.findViewById(R.id.ic_key_b);
		ic_key_n = (ImageView) myActivity.findViewById(R.id.ic_key_n);
		ic_key_m = (ImageView) myActivity.findViewById(R.id.ic_key_m);
		ic_key_del = (ImageView) myActivity.findViewById(R.id.ic_key_del);
		ic_key_clr = (ImageView) myActivity.findViewById(R.id.ic_key_clr);
		
		slashScreen = (RelativeLayout) myActivity.findViewById(R.id.slashScreen);
		mainMenu = (RelativeLayout) myActivity.findViewById(R.id.mainMenu);
		sub_menu = (RelativeLayout) myActivity.findViewById(R.id.sub_menu);
		gameStage = (RelativeLayout) myActivity.findViewById(R.id.gameStage);
		gameObjects = (LinearLayout) myActivity.findViewById(R.id.gameObjects);
		leaderBoard = (RelativeLayout) myActivity.findViewById(R.id.leaderBoard);

		initAllKeysPress();
		
		/*----RevMob Ads----*/
		revmob = RevMob.start(myActivity);
//revmob.setTestingMode(RevMobTestingMode.WITH_ADS);
        /*----RevMob Ads----*/
	}

	private void setTimeBarGraphic(boolean style){

		RelativeLayout.LayoutParams params;
		
		if(style == true){
			float f_CD = globalVariable.countDowns;
			
			if(f_CD < 0){
				f_CD = 0;
			}
			
			float f_TCD = globalVariable.countTotalDowns;
			int currentTimePercent = (int)(f_CD / f_TCD * 100);
			float c_TBW = globalVariable.timerBarOriWidth;
			int curWidthIs = (int)(c_TBW / 100 * currentTimePercent);
			
			params = new RelativeLayout.LayoutParams
			          (curWidthIs,(int) LayoutParams.WRAP_CONTENT);
			
			params.setMargins(0, 0, (globalVariable.timerBarOriWidth - curWidthIs), 0);
		}else{
			params = new RelativeLayout.LayoutParams
			          ((int) LayoutParams.WRAP_CONTENT,(int) LayoutParams.WRAP_CONTENT);
		}
		
		params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, ic_time_bar.getId());
		params.addRule(RelativeLayout.BELOW, stageTimeShow.getId());
		
		ic_time_bar.setLayoutParams(params);
		
		if(style == false && globalVariable.timerBarOriWidth == 0){
	    	globalVariable.timerBarOriWidth = ic_time_bar.getWidth();
		}
		
	}
	
	public void gameActivity(boolean status){
		if(globalVariable.inGameMode == false){
			return;
		}
		
		if(status == true){
			globalVariable.stopCounter = false;
			
			setTimeBarGraphic(true);

			setCountDownTimerBar(globalVariable.countDowns);
			
			timerCountDown();
	    	timerObjectCreate();
		}else{
			//ic_time_bar.clearAnimation();
			setTimeBarGraphic(true);
			globalVariable.stopCounter = true;
		}
		
	}
	
	public void startGameEngine(int level, int p_sdk){
		setTimeBarGraphic(false);
		gameObjects.removeAllViewsInLayout();
		System.gc();
		
		sdk = p_sdk;
		currentLevel = level;
		stageLevelShow.setText("Lvl " + currentLevel);
		
		globalVariable.scores = 0;
		
		globalVariable.curTypedWord = "";
		keyPadInputed.setText("* start typing now *");
		
		ic_trophy_game_scores.setText(globalVariable.scores + "");
		ic_mybomb_txt.setText("x" + globalVariable.getBomb());
		
		stageController(gameStage);
    	setStageBackground(gameStage,"backgrounds/sub_menu.jpg");
    	
    	if(currentLevel > 2){
    		accessWordsLib(currentLevel,(70 + currentLevel));
    	}else{
    		generateLevelChallenge(currentLevel, (70 + currentLevel));
    	}
    	
    	setBonuses();
    	globalVariable.currentLevels = currentLevel;
    	globalVariable.currentToDelayed = (int)(currentLevel / 2);
    	globalVariable.countDowns = (50 + currentLevel) * globalVariable.currentToDelayed;
    	globalVariable.currentToDelayed = globalVariable.currentToDelayed * 1000;
    	if(globalVariable.currentToDelayed < 1){
    		globalVariable.currentToDelayed = 1000;
    		globalVariable.countDowns = (50 + currentLevel) * 1;
    	}
    	
    	//globalVariable.countDowns = 30 + (currentLevel * 5);
		globalVariable.countTotalDowns = globalVariable.countDowns;
		stageTimeShow.setText("Time : " + secToString(globalVariable.countDowns));
		setCountDownTimerBar(globalVariable.countDowns);
    	
    	globalVariable.curShownObject = 0;
    	globalVariable.currentTill = 0;
    	createGameObjects(globalVariable.curShownObject);
    	
    	globalVariable.inGameMode = true;
   	
    	globalVariable.stopCounter = false;
    	timerCountDown();
    	timerObjectCreate();
    	
    	globalVariable.saveYunizSaved(globalVariable.getLevel(), globalVariable.getBomb(), globalVariable.getPlayerName(), 0);
	}
	
	private void createGameObjects(int curObject){
		
		soundsController.objSoundClip("sounds/createWords.mp3");
		
		globalVariable.curShownObject++;
		
		int wordPos = randomNumber(0,2), paddings = randomNumber(0,50) + 2;

		LinearLayout levelBox = new LinearLayout(myActivity);
		TextView levelText = new TextView(myActivity);
		ImageView levelEgg = new ImageView(myActivity);
	
		levelBox.setOrientation(LinearLayout.HORIZONTAL);
		
		switch(wordPos){
			case 0:{
				levelBox.setGravity(Gravity.LEFT);
				break;
			}
			case 1:{
				levelBox.setGravity(Gravity.CENTER);
				break;
			}
			case 2:{
				levelBox.setGravity(Gravity.RIGHT);
				break;
			}
		}
		
		//setStageBackground_text(levelText,"backgrounds/objects_bg.png");
		
		Typeface tf = Typeface.createFromAsset(myActivity.getAssets(), "fonts/GROBOLD.ttf");
		levelText.setTypeface(tf);
		levelText.setTextSize(20f);
		
		if(isStringArrayExist(globalVariable.bonus3,3,globalVariable.currentLevelChallenge[curObject],false)){
			levelText.setTextColor(Color.parseColor("#c92121"));
			levelEgg.setImageResource(R.drawable.ic_bonus_special);
		}else if(isStringArrayExist(globalVariable.bonus2,2,globalVariable.currentLevelChallenge[curObject],false)){
			levelText.setTextColor(Color.parseColor("#4d4d4d"));
			levelEgg.setImageResource(R.drawable.ic_bonus_double);
		}else if(isStringArrayExist(globalVariable.bonus1,1,globalVariable.currentLevelChallenge[curObject],false)){
			levelText.setTextColor(Color.parseColor("#348ca5"));
			levelEgg.setImageResource(R.drawable.ic_bonus_triple);
		}else{
			levelText.setTextColor(Color.parseColor("#333333"));
		}

		levelBox.setPadding(paddings, 2, paddings, 2);
		
		levelText.setText(globalVariable.currentLevelChallenge[curObject]);
		
		levelBox.setId(curObject);
		levelBox.addView(levelEgg);
		levelBox.addView(levelText);
		gameObjects.addView(levelBox,0);

		if(gameObjects.getChildCount() > 9){
			forfeitObject(0);
		}
		
	}
	
	private boolean isStringArrayExist(String[] strArray, int type, String strCheck, boolean removeNow){
		boolean val = false;
		
		if(strCheck == null || strCheck.length() < 1 || strCheck.equals(null) || strCheck.equals("")){
			return val;
		}
		
		switch(type){
			case 1 : {
				if(globalVariable.bonus1.length < 1){
					return val;
				}
				
				strArray = globalVariable.bonus1;
				
				String[] arraylist1 = globalVariable.bonus1;
				List<String> list1 = new ArrayList<String>();
				Collections.addAll(list1, arraylist1);
				
				globalVariable.currArrayBonusItemIndex1 = 0;
				
				for(String s : strArray)
			    {
			        if(strCheck.equals(s))
			        {
			        	if(removeNow == true){
			        		list1.remove(globalVariable.currArrayBonusItemIndex1);
			        	}
			        	val = true;
			        	break;
			        }
			        globalVariable.currArrayBonusItemIndex1++;
			    }
				
				globalVariable.bonus1 = list1.toArray(new String[list1.size()]);
				
			}
			case 2 : {
				if(globalVariable.bonus2.length < 1){
					return val;
				}
				
				strArray = globalVariable.bonus2;
				
				String[] arraylist2 = globalVariable.bonus2;
				List<String> list2 = new ArrayList<String>();
				Collections.addAll(list2, arraylist2);
				
				globalVariable.currArrayBonusItemIndex2 = 0;
				
				for(String s : strArray)
			    {
			        if(strCheck.equals(s))
			        {
			        	if(removeNow == true){
			        		list2.remove(globalVariable.currArrayBonusItemIndex2);
			        	}
			        	val = true;
			        	break;
			        }
			        globalVariable.currArrayBonusItemIndex2++;
			    }
				
				globalVariable.bonus2 = list2.toArray(new String[list2.size()]);
			}
			case 3 : {
				if(globalVariable.bonus3.length < 1){
					return val;
				}
				
				strArray = globalVariable.bonus3;
				
				String[] arraylist3 = globalVariable.bonus3;
				List<String> list3 = new ArrayList<String>();
				Collections.addAll(list3, arraylist3);
				
				globalVariable.currArrayBonusItemIndex3 = 0;
				
				for(String s : strArray)
			    {
			        if(strCheck.equals(s))
			        {
			        	if(removeNow == true){
			        		list3.remove(globalVariable.currArrayBonusItemIndex3);
			        	}
			        	val = true;
			        	break;
			        }
			        globalVariable.currArrayBonusItemIndex3++;
			    }
				
				globalVariable.bonus3 = list3.toArray(new String[list3.size()]);
			}
		}	
		
		return val;
	}
	
	private void setBonuses(){
		int randomTo = globalVariable.currentLevelChallenge.length - 10, totolBonus1 = (10 + globalVariable.currentLevels), totolBonus2 = (5 + globalVariable.currentLevels), totolBonus3 = (2 + globalVariable.currentLevels);
		globalVariable.bonus1 = new String[totolBonus1];
		globalVariable.bonus2 = new String[totolBonus2];
		globalVariable.bonus3 = new String[totolBonus3];

		for(int i=0;i<totolBonus1;i++){
			globalVariable.bonus1[i] = globalVariable.currentLevelChallenge[randomNumber(0,randomTo)];
		}

		for(int i=0;i<totolBonus2;i++){
			//bonus2[i] = globalVariable.currentLevelChallenge[randomNumber(0,randomTo)];
			
			String curStr = globalVariable.currentLevelChallenge[randomNumber(0,randomTo)];
			for(String s : globalVariable.bonus1)
		    {
		        if(!curStr.equals(s))
		        {
		        	globalVariable.bonus2[i] = curStr;
		        	break;
		        }
		    }
		}
		
		for(int i=0;i<totolBonus3;i++){
			//bonus2[i] = globalVariable.currentLevelChallenge[randomNumber(0,randomTo)];
			
			String curStr = globalVariable.currentLevelChallenge[randomNumber(0,randomTo)];
			for(String s : globalVariable.bonus1)
		    {
		        if(!curStr.equals(s))
		        {
		        	for(String r : globalVariable.bonus2)
				    {
				        if(!curStr.equals(r))
				        {
				        	globalVariable.bonus3[i] = curStr;
				        	break;
				        }
				    }
		        }
		    }
		}
	}
	
	private void generateLevelChallenge(int difficulty, int totalList){
		int increaseEmpty = 9;
		globalVariable.currentLevelChallenge = new String[totalList + increaseEmpty];
		int untilIndex = 0;
		for(int i=0;i<totalList;i++){
			globalVariable.currentLevelChallenge[i] = randomStringFromArray(difficulty,lettersLib);
			untilIndex++;
		}
		for(int i=untilIndex;i<=increaseEmpty;i++){
			globalVariable.currentLevelChallenge[i] = "";
		}
	}
	
	private String randomStringFromArray(int total, String[] library){
		String randomString = "";
		
		for(int i=0;i<total;i++){
			randomString = randomString + library[randomNumber(0,25)];
		}
		
		return randomString;
	}
	
	private int randomNumber(int min, int max){
		Random r = new Random();
    	int i1 = r.nextInt(max - min + 1) + min;
    	return i1;
	}
	
	private void typingHit(String curKeys){
		/*if( globalVariable.currentLevelChallenge.length <= globalVariable.currentTill ){
			return;
		}
		
		if(globalVariable.currentLevelChallenge[globalVariable.currentTill].equals(curKeys)){
			soundsController.shortSoundClip("sounds/word_hit.mp3");
			globalVariable.currentTill++;
			globalVariable.curTypedWord = "";
			keyPadInputed.setText(globalVariable.curTypedWord);
			gameObjects.removeViewAt(gameObjects.getChildCount() - 1);
			
			if( globalVariable.currentLevelChallenge.length <= globalVariable.currentTill ){
				gameOver(0);
			}
		}*/
		
		if( globalVariable.currentLevelChallenge.length <= 9 || curKeys.equals("")){
			return;
		}
		
		globalVariable.currArrayItemIndex = 0;
		
		String[] arraylist = globalVariable.currentLevelChallenge;
		List<String> list = new ArrayList<String>();
		Collections.addAll(list, arraylist);

		for(String s : globalVariable.currentLevelChallenge)
	    {
	        if(curKeys.equals(s))
	        {//Log.v("debug",gameObjects.getChildCount() + "|" + ((gameObjects.getChildCount() - 1) - globalVariable.currArrayItemIndex) + "|" + globalVariable.currArrayItemIndex + "|" + s);
	        	if(((gameObjects.getChildCount() - 1) - globalVariable.currArrayItemIndex) < 0){
	        		return;
	        	}
	        	
	        	list.remove(globalVariable.currArrayItemIndex);
	        	
	        	globalVariable.curTypedWord = "";
				keyPadInputed.setText(globalVariable.curTypedWord);
				gameObjects.removeViewAt((gameObjects.getChildCount() - 1) - globalVariable.currArrayItemIndex);
				globalVariable.curShownObject--;

				if(isStringArrayExist(globalVariable.bonus3,3,curKeys,true)){
					showBonusHits(0);
				}else if(isStringArrayExist(globalVariable.bonus2,2,curKeys,true)){
					globalVariable.scores = globalVariable.scores + 3;
					showBonusHits(3);
					soundsController.shortSoundClip("sounds/bonus_scored.mp3");
				}else if(isStringArrayExist(globalVariable.bonus1,1,curKeys,true)){
					globalVariable.scores = globalVariable.scores + 2;
					showBonusHits(2);
					soundsController.shortSoundClip("sounds/bonus_scored.mp3");
				}else{
					globalVariable.scores = globalVariable.scores + 1;
					soundsController.shortSoundClip("sounds/word_hit.mp3");
				}
				
				ic_trophy_game_scores.setText(globalVariable.scores + "");

				if( globalVariable.currentLevelChallenge.length <= 9 ){
					gameOver(0);
				}
				
	        	break;
	        }
	        
	        globalVariable.currArrayItemIndex++;
	    }
		
		globalVariable.currentLevelChallenge = list.toArray(new String[list.size()]);

		
	}
	
	private void showBombEffect(){
		if(gameStage.getChildAt(gameStage.getChildCount() - 1).getId() == -1){
			gameStage.removeView(gameStage.getChildAt(gameStage.getChildCount() - 1));
		}
		
		RelativeLayout bonusShowBox = new RelativeLayout(myActivity);
		TextView ptsTxt = new TextView(myActivity);
		ImageView levelEgg = new ImageView(myActivity);
		
		Typeface tf = Typeface.createFromAsset(myActivity.getAssets(), "fonts/Cookies.ttf");
		ptsTxt.setTypeface(tf);
		ptsTxt.setTextSize(15);
		
		ptsTxt.setText("Blew a word away...");
	
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
		          ((int) LayoutParams.WRAP_CONTENT, (int) LayoutParams.WRAP_CONTENT);
		//params.addRule(RelativeLayout.CENTER_VERTICAL);
		params.addRule(RelativeLayout.CENTER_HORIZONTAL);

		params.topMargin = 100;
		
		bonusShowBox.setLayoutParams(params);
		bonusShowBox.setGravity(Gravity.CENTER);
		
		ptsTxt.setGravity(Gravity.CENTER);
		
		levelEgg.setImageResource(R.drawable.ic_bomb_effect);
		ptsTxt.setTextColor(Color.parseColor("#c92121"));
		
		bonusShowBox.setId(-1);
		
		bonusShowBox.addView(levelEgg);
		bonusShowBox.addView(ptsTxt);

		gameStage.addView(bonusShowBox);

		//------add fadeIn-out animation------//
		AnimationSet set = new AnimationSet(true);

		Animation fadeIn = new AlphaAnimation(1.0f, 0.0f);
		fadeIn.setDuration(1000);
		
		Animation animationScale = new ScaleAnimation(0,1,0,1,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.9f);
		animationScale.setDuration(1000);

		animationScale.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				if(gameStage.getChildAt(gameStage.getChildCount() - 1).getId() == -1){
					gameStage.removeView(gameStage.getChildAt(gameStage.getChildCount() - 1));
				}
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			
		});
		
		set.addAnimation(animationScale);

		bonusShowBox.startAnimation(set);
		//------add fadeIn-out animation------//
		
	}
	
	private void showBonusHits(int points){
		if(gameStage.getChildAt(gameStage.getChildCount() - 1).getId() == -1){
			gameStage.removeView(gameStage.getChildAt(gameStage.getChildCount() - 1));
		}
		
		RelativeLayout bonusShowBox = new RelativeLayout(myActivity);
		LinearLayout alignBox = new LinearLayout(myActivity);
		TextView ptsTxt = new TextView(myActivity);
		ImageView levelEgg = new ImageView(myActivity);
		
		alignBox.setGravity(Gravity.CENTER);
		alignBox.setOrientation(LinearLayout.HORIZONTAL);
		
		Typeface tf = Typeface.createFromAsset(myActivity.getAssets(), "fonts/Cookies.ttf");
		ptsTxt.setTypeface(tf);
		ptsTxt.setTextSize(35);
		
		ptsTxt.setText("+" + points + " points");
		
		switch(points){
			case 0 : {
				levelEgg.setImageResource(R.drawable.ic_bonus_special);
				ptsTxt.setTextColor(Color.parseColor("#c92121"));
				
				int specialId = randomNumber(0,4);
				int luckyPoints = randomNumber(3,10);
				
				switch(specialId){
					case 0:{
						globalVariable.scores = globalVariable.scores + luckyPoints;
						ptsTxt.setText("+" + luckyPoints + " points");
						soundsController.shortSoundClip("sounds/bonus_scored.mp3");
						break;
					}
					case 1:{
						globalVariable.scores = globalVariable.scores - luckyPoints;
						ptsTxt.setText("-" + luckyPoints + " points");
						soundsController.shortSoundClip("sounds/levelFailed.mp3");
						break;
					}
					case 2:{
						globalVariable.countDowns = globalVariable.countDowns + luckyPoints;
						ptsTxt.setText("+" + luckyPoints + " seconds");
						soundsController.shortSoundClip("sounds/bonus_scored.mp3");
						break;
					}
					case 3:{
						globalVariable.countDowns = globalVariable.countDowns - luckyPoints;
						ptsTxt.setText("-" + luckyPoints + " seconds");
						soundsController.shortSoundClip("sounds/levelFailed.mp3");
						break;
					}case 4:{
						int curBombUnits = globalVariable.getBomb();
						curBombUnits++;
						globalVariable.saveYunizSaved(globalVariable.getLevel(), curBombUnits, globalVariable.getPlayerName(), globalVariable.getShare());
						
						ic_mybomb_txt.setText("x" + curBombUnits);
						ptsTxt.setText("+1 bomb");
						soundsController.shortSoundClip("sounds/bonus_scored.mp3");
						break;
					}
				}

				break;
			}
			case 2 : {
				levelEgg.setImageResource(R.drawable.ic_bonus_triple);
				ptsTxt.setTextColor(Color.parseColor("#348ca5"));
				
				break;
			}
			case 3 : {
				levelEgg.setImageResource(R.drawable.ic_bonus_double);
				ptsTxt.setTextColor(Color.parseColor("#4d4d4d"));
				
				break;
			}
		}
		
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
		          ((int) LayoutParams.WRAP_CONTENT, (int) LayoutParams.WRAP_CONTENT);
		//params.addRule(RelativeLayout.CENTER_VERTICAL);
		params.addRule(RelativeLayout.CENTER_HORIZONTAL);

		params.topMargin = 100;
		
		bonusShowBox.setLayoutParams(params);
		bonusShowBox.setGravity(Gravity.CENTER);
		
		bonusShowBox.setId(-1);
		
		alignBox.addView(levelEgg);
		alignBox.addView(ptsTxt);
		
		bonusShowBox.addView(alignBox);

		gameStage.addView(bonusShowBox);

		//------add fadeIn-out animation------//
		AnimationSet set = new AnimationSet(true);

		Animation fadeIn = new AlphaAnimation(1.0f, 0.0f);
		fadeIn.setDuration(1000);
		
		Animation animationScale = new ScaleAnimation(0,1,0,1,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.9f);
		animationScale.setDuration(1000);

		animationScale.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				if(gameStage.getChildAt(gameStage.getChildCount() - 1).getId() == -1){
					gameStage.removeView(gameStage.getChildAt(gameStage.getChildCount() - 1));
				}
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			
		});
		
		set.addAnimation(animationScale);

		bonusShowBox.startAnimation(set);
		//------add fadeIn-out animation------//
		
	}
	
	private void forfeitObject(int index){
		String[] arraylist = globalVariable.currentLevelChallenge;
		List<String> list = new ArrayList<String>();
		Collections.addAll(list, arraylist);
		
		list.remove(index);
		gameObjects.removeViewAt((gameObjects.getChildCount() - 1) - index);
		globalVariable.curShownObject--;
		
		globalVariable.currentLevelChallenge = list.toArray(new String[list.size()]);
	}
	
	private void gameOver(int types){
		stopGameStage();
		
		switch(types) {
			case 0: {
				globalVariable.scores = (globalVariable.scores * globalVariable.currentLevels) + globalVariable.countDowns;
				break;
			}
			case 1: {
				globalVariable.scores = (globalVariable.scores * globalVariable.currentLevels);
				break;
			}
		}
		
		ic_trophy_game_scores.setText(globalVariable.scores + "");
		
		int topScore = Integer.valueOf(globalVariable.getSelectedYunizScores(globalVariable.currentLevels));
		
		if(topScore >= globalVariable.scores){
			
			popupBox.showPopBox("",2);
			
		}else if(globalVariable.scores < ((globalVariable.currentLevels * 10) + globalVariable.currentLevels) * 2){
			
			popupBox.showPopBox("",2);
			
		}else{

			if(globalVariable.currentLevels > globalVariable.getLevel()){
				globalVariable.saveYunizSaved(globalVariable.currentLevels, globalVariable.getBomb(), globalVariable.getPlayerName(), globalVariable.getShare());
			}else{
				globalVariable.saveYunizSaved(globalVariable.getLevel(), globalVariable.getBomb(), globalVariable.getPlayerName(), globalVariable.getShare());
			}
		
			globalVariable.saveYunizScores(globalVariable.currentLevels,globalVariable.scores);
			
			popupBox.showPopBox("",1);
		}

		revmob.showFullscreen(myActivity);
		
	}
	
	public void stopGameStage(){
		globalVariable.inGameMode = false;
		//ic_time_bar.clearAnimation();
		setTimeBarGraphic(true);
		globalVariable.stopCounter = true;
	}
	
	private void setCountDownTimerBar(int size){
		/*// removed because no longer needed animation to animate the timebar
		Animation animationScale = new ScaleAnimation(1, 0, 1, 1);
    	animationScale.setDuration((size + 2) * 1000);

		AnimationSet animSet = new AnimationSet(true);
		animSet.setFillAfter(true);
		animSet.addAnimation(animationScale);
		
		ic_time_bar.startAnimation(animSet);
		*/
	}
	
	private void timerCountDown(){
		if(globalVariable.stopCounter == true){
			return;
		}

		ic_time_bar.postDelayed(new Runnable() {
	        @Override
	        public void run() {
        	
	        	if(globalVariable.stopCounter == true){
	    			return;
	    		}
	        	
	        	globalVariable.countDowns--;
      	
	        	stageTimeShow.setText("Time : " + secToString(globalVariable.countDowns));
	        	
	        	setTimeBarGraphic(true);

        		if(globalVariable.countDowns > 0){
	        		timerCountDown();
	        	}else{
	        		gameOver(1);
	        	}

	        }
	    }, 1000);
	}
	
	private void timerObjectCreate(){
		if(globalVariable.stopCounter == true){
			return;
		}

		ic_trophy_game_scores.postDelayed(new Runnable() {
	        @Override
	        public void run() {
        	
	        	if(globalVariable.stopCounter == true){
	    			return;
	    		}
	        	
	        	if(globalVariable.curShownObject < globalVariable.currentLevelChallenge.length){

	        		createGameObjects(globalVariable.curShownObject);

	        	}	
	        	
	        	if( globalVariable.currentLevelChallenge.length <= 9 ){
					gameOver(0);
				}else{
					timerObjectCreate();
				}

	        }
	    }, globalVariable.currentToDelayed);
	}
	
	private String secToString(int secStr){
		String returnStr = "00:00";
		
		int minVal = (int)(globalVariable.countDowns / 60);
    	int secVal = (int)(globalVariable.countDowns % 60);
    	
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
		leaderBoard.setVisibility(View.INVISIBLE);
		
		slashScreen.setClickable(false);
		mainMenu.setClickable(false);
		sub_menu.setClickable(false);
		gameStage.setClickable(false);
		leaderBoard.setClickable(false);
		
		thisStage.setVisibility(View.VISIBLE);
		thisStage.setClickable(true);
		
		System.gc();
	}
	
	@SuppressLint("NewApi")
	public void setStageBackground_text(TextView thisStage, String fileName){
		if(thisStage.getBackground() != null){
			return;
		}
		
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
		if(thisStage.getBackground() != null){
			return;
		}
		
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
	
	private void accessWordsLib(int level, int totaOutput){
		String rawList = null;
		BufferedReader isr = null;
		String[] wordsList = null;
		
		if(level < 3){
			level = 3;
		}

		InputStream ims = null;
		try {
			ims = myActivity.getAssets().open("wordslib/textlib_" + level + ".txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		isr = new BufferedReader(new InputStreamReader(ims));
		try {
			rawList = isr.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rawList = rawList.substring(0, rawList.length() - 1);
		wordsList = rawList.split(",");
		
		ArrayList<String> cards = new ArrayList<String>(), finals = new ArrayList<String>();
		Collections.addAll(cards, wordsList);
		Collections.shuffle(cards);

		for(int i = 0; i < totaOutput; i++){
			finals.add(cards.get(i));
		}
		
		wordsList = finals.toArray(new String[finals.size()]);
		
		/*load into the curent level words challenge list*/
		int increaseEmpty = 9;
		globalVariable.currentLevelChallenge = new String[wordsList.length + increaseEmpty];
		int untilIndex = 0;
		for(int i=0;i<wordsList.length;i++){
			globalVariable.currentLevelChallenge[i] = wordsList[i];
			untilIndex++;
		}
		for(int i=untilIndex;i<=increaseEmpty;i++){
			globalVariable.currentLevelChallenge[i] = "";
		}
		/*load into the curent level words challenge list*/
		
		/*rawList = "";
		for(int i = 0; i < wordsList.length; i++){
			rawList = rawList + wordsList[i] + ",";
		}
		
	    Log.v("debug",wordsList.length + " - " + rawList);*/
	}
	
	private void keyPressReceiver(String keycode){
		if(globalVariable.stopCounter == true){
			return;
		}
		
		if(keycode == "del"){
			if(globalVariable.curTypedWord.length() > 0){
				globalVariable.curTypedWord = globalVariable.curTypedWord.substring(0, globalVariable.curTypedWord.length() - 1);
			}
		}else if(keycode == "clr"){
			globalVariable.curTypedWord = "";
		}else{
			globalVariable.curTypedWord = globalVariable.curTypedWord + keycode;
		}

		keyPadInputed.setText(globalVariable.curTypedWord);
		typingHit(globalVariable.curTypedWord);
	}
	
	//-----------all keys press-------------
	private void initAllKeysPress(){
		
		ic_mybomb.setOnTouchListener(new View.OnTouchListener() {
	        @Override
	        public boolean onTouch(View arg0, MotionEvent arg1) {
	            switch (arg1.getAction()) {
		            case MotionEvent.ACTION_DOWN: {
		            	soundsController.shortSoundClip("sounds/keypad_click.mp3");
		            	ic_mybomb.setAlpha(180);
		            	
		                break;
		            }
		            case MotionEvent.ACTION_UP:{
		            	ic_mybomb.setAlpha(255);
		            	
		            	if(globalVariable.currentLevelChallenge.length > 0 && gameObjects.getChildCount() > 0 && globalVariable.getBomb() > 0){
		            		//showBombEffect();
		            		int curBombUnits = globalVariable.getBomb();
		            		curBombUnits--;
		            		
		            		globalVariable.saveYunizSaved(globalVariable.getLevel(), curBombUnits, globalVariable.getPlayerName(), globalVariable.getShare());
		            		
		            		ic_mybomb_txt.setText("x" + curBombUnits);
		            		
		            		typingHit(globalVariable.currentLevelChallenge[0]);
		            	}
		            	
		                break;
		            }
	            }
	            return true;
	        }
	    });
		
		ic_key_q.setOnTouchListener(new View.OnTouchListener() {
	        @Override
	        public boolean onTouch(View arg0, MotionEvent arg1) {
	            switch (arg1.getAction()) {
		            case MotionEvent.ACTION_DOWN: {
		            	soundsController.shortSoundClip("sounds/keypad_click.mp3");
		            	ic_key_q.setAlpha(180);
		            	
		                break;
		            }
		            case MotionEvent.ACTION_UP:{
		            	ic_key_q.setAlpha(255);
		            	
		            	keyPressReceiver("q");
		            	
		                break;
		            }
	            }
	            return true;
	        }
	    });
		
		ic_key_w.setOnTouchListener(new View.OnTouchListener() {
	        @Override
	        public boolean onTouch(View arg0, MotionEvent arg1) {
	            switch (arg1.getAction()) {
		            case MotionEvent.ACTION_DOWN: {
		            	soundsController.shortSoundClip("sounds/keypad_click.mp3");
		            	ic_key_w.setAlpha(180);
		            	
		                break;
		            }
		            case MotionEvent.ACTION_UP:{
		            	ic_key_w.setAlpha(255);
		            	
		            	keyPressReceiver("w");
		            	
		                break;
		            }
	            }
	            return true;
	        }
	    });
		
		ic_key_e.setOnTouchListener(new View.OnTouchListener() {
	        @Override
	        public boolean onTouch(View arg0, MotionEvent arg1) {
	            switch (arg1.getAction()) {
		            case MotionEvent.ACTION_DOWN: {
		            	soundsController.shortSoundClip("sounds/keypad_click.mp3");
		            	ic_key_e.setAlpha(180);
		            	
		                break;
		            }
		            case MotionEvent.ACTION_UP:{
		            	ic_key_e.setAlpha(255);
		            	
		            	keyPressReceiver("e");
		            	
		                break;
		            }
	            }
	            return true;
	        }
	    });
		
		ic_key_r.setOnTouchListener(new View.OnTouchListener() {
	        @Override
	        public boolean onTouch(View arg0, MotionEvent arg1) {
	            switch (arg1.getAction()) {
		            case MotionEvent.ACTION_DOWN: {
		            	soundsController.shortSoundClip("sounds/keypad_click.mp3");
		            	ic_key_r.setAlpha(180);
		            	
		                break;
		            }
		            case MotionEvent.ACTION_UP:{
		            	ic_key_r.setAlpha(255);
		            	
		            	keyPressReceiver("r");
		            	
		                break;
		            }
	            }
	            return true;
	        }
	    });
		
		ic_key_t.setOnTouchListener(new View.OnTouchListener() {
	        @Override
	        public boolean onTouch(View arg0, MotionEvent arg1) {
	            switch (arg1.getAction()) {
		            case MotionEvent.ACTION_DOWN: {
		            	soundsController.shortSoundClip("sounds/keypad_click.mp3");
		            	ic_key_t.setAlpha(180);
		            	
		                break;
		            }
		            case MotionEvent.ACTION_UP:{
		            	ic_key_t.setAlpha(255);
		            	
		            	keyPressReceiver("t");
		            	
		                break;
		            }
	            }
	            return true;
	        }
	    });
		
		ic_key_y.setOnTouchListener(new View.OnTouchListener() {
	        @Override
	        public boolean onTouch(View arg0, MotionEvent arg1) {
	            switch (arg1.getAction()) {
		            case MotionEvent.ACTION_DOWN: {
		            	soundsController.shortSoundClip("sounds/keypad_click.mp3");
		            	ic_key_y.setAlpha(180);
		            	
		                break;
		            }
		            case MotionEvent.ACTION_UP:{
		            	ic_key_y.setAlpha(255);
		            	
		            	keyPressReceiver("y");
		            	
		                break;
		            }
	            }
	            return true;
	        }
	    });
		
		ic_key_u.setOnTouchListener(new View.OnTouchListener() {
	        @Override
	        public boolean onTouch(View arg0, MotionEvent arg1) {
	            switch (arg1.getAction()) {
		            case MotionEvent.ACTION_DOWN: {
		            	soundsController.shortSoundClip("sounds/keypad_click.mp3");
		            	ic_key_u.setAlpha(180);
		            	
		                break;
		            }
		            case MotionEvent.ACTION_UP:{
		            	ic_key_u.setAlpha(255);
		            	
		            	keyPressReceiver("u");
		            	
		                break;
		            }
	            }
	            return true;
	        }
	    });
		
		ic_key_i.setOnTouchListener(new View.OnTouchListener() {
	        @Override
	        public boolean onTouch(View arg0, MotionEvent arg1) {
	            switch (arg1.getAction()) {
		            case MotionEvent.ACTION_DOWN: {
		            	soundsController.shortSoundClip("sounds/keypad_click.mp3");
		            	ic_key_i.setAlpha(180);
		            	
		                break;
		            }
		            case MotionEvent.ACTION_UP:{
		            	ic_key_i.setAlpha(255);
		            	
		            	keyPressReceiver("i");
		            	
		                break;
		            }
	            }
	            return true;
	        }
	    });
		
		ic_key_o.setOnTouchListener(new View.OnTouchListener() {
	        @Override
	        public boolean onTouch(View arg0, MotionEvent arg1) {
	            switch (arg1.getAction()) {
		            case MotionEvent.ACTION_DOWN: {
		            	soundsController.shortSoundClip("sounds/keypad_click.mp3");
		            	ic_key_o.setAlpha(180);
		            	
		                break;
		            }
		            case MotionEvent.ACTION_UP:{
		            	ic_key_o.setAlpha(255);
		            	
		            	keyPressReceiver("o");
		            	
		                break;
		            }
	            }
	            return true;
	        }
	    });
		
		ic_key_p.setOnTouchListener(new View.OnTouchListener() {
	        @Override
	        public boolean onTouch(View arg0, MotionEvent arg1) {
	            switch (arg1.getAction()) {
		            case MotionEvent.ACTION_DOWN: {
		            	soundsController.shortSoundClip("sounds/keypad_click.mp3");
		            	ic_key_p.setAlpha(180);
		            	
		                break;
		            }
		            case MotionEvent.ACTION_UP:{
		            	ic_key_p.setAlpha(255);
		            	
		            	keyPressReceiver("p");
		            	
		                break;
		            }
	            }
	            return true;
	        }
	    });
		
		ic_key_a.setOnTouchListener(new View.OnTouchListener() {
	        @Override
	        public boolean onTouch(View arg0, MotionEvent arg1) {
	            switch (arg1.getAction()) {
		            case MotionEvent.ACTION_DOWN: {
		            	soundsController.shortSoundClip("sounds/keypad_click.mp3");
		            	ic_key_a.setAlpha(180);
		            	
		                break;
		            }
		            case MotionEvent.ACTION_UP:{
		            	ic_key_a.setAlpha(255);
		            	
		            	keyPressReceiver("a");
		            	
		                break;
		            }
	            }
	            return true;
	        }
	    });
		
		ic_key_s.setOnTouchListener(new View.OnTouchListener() {
	        @Override
	        public boolean onTouch(View arg0, MotionEvent arg1) {
	            switch (arg1.getAction()) {
		            case MotionEvent.ACTION_DOWN: {
		            	soundsController.shortSoundClip("sounds/keypad_click.mp3");
		            	ic_key_s.setAlpha(180);
		            	
		                break;
		            }
		            case MotionEvent.ACTION_UP:{
		            	ic_key_s.setAlpha(255);
		            	
		            	keyPressReceiver("s");
		            	
		                break;
		            }
	            }
	            return true;
	        }
	    });
		
		ic_key_d.setOnTouchListener(new View.OnTouchListener() {
	        @Override
	        public boolean onTouch(View arg0, MotionEvent arg1) {
	            switch (arg1.getAction()) {
		            case MotionEvent.ACTION_DOWN: {
		            	soundsController.shortSoundClip("sounds/keypad_click.mp3");
		            	ic_key_d.setAlpha(180);
		            	
		                break;
		            }
		            case MotionEvent.ACTION_UP:{
		            	ic_key_d.setAlpha(255);
		            	
		            	keyPressReceiver("d");
		            	
		                break;
		            }
	            }
	            return true;
	        }
	    });
		
		ic_key_f.setOnTouchListener(new View.OnTouchListener() {
	        @Override
	        public boolean onTouch(View arg0, MotionEvent arg1) {
	            switch (arg1.getAction()) {
		            case MotionEvent.ACTION_DOWN: {
		            	soundsController.shortSoundClip("sounds/keypad_click.mp3");
		            	ic_key_f.setAlpha(180);
		            	
		                break;
		            }
		            case MotionEvent.ACTION_UP:{
		            	ic_key_f.setAlpha(255);
		            	
		            	keyPressReceiver("f");
		            	
		                break;
		            }
	            }
	            return true;
	        }
	    });
		
		ic_key_g.setOnTouchListener(new View.OnTouchListener() {
	        @Override
	        public boolean onTouch(View arg0, MotionEvent arg1) {
	            switch (arg1.getAction()) {
		            case MotionEvent.ACTION_DOWN: {
		            	soundsController.shortSoundClip("sounds/keypad_click.mp3");
		            	ic_key_g.setAlpha(180);
		            	
		                break;
		            }
		            case MotionEvent.ACTION_UP:{
		            	ic_key_g.setAlpha(255);
		            	
		            	keyPressReceiver("g");
		            	
		                break;
		            }
	            }
	            return true;
	        }
	    });
		
		ic_key_h.setOnTouchListener(new View.OnTouchListener() {
	        @Override
	        public boolean onTouch(View arg0, MotionEvent arg1) {
	            switch (arg1.getAction()) {
		            case MotionEvent.ACTION_DOWN: {
		            	soundsController.shortSoundClip("sounds/keypad_click.mp3");
		            	ic_key_h.setAlpha(180);
		            	
		                break;
		            }
		            case MotionEvent.ACTION_UP:{
		            	ic_key_h.setAlpha(255);
		            	
		            	keyPressReceiver("h");
		            	
		                break;
		            }
	            }
	            return true;
	        }
	    });
		
		ic_key_j.setOnTouchListener(new View.OnTouchListener() {
	        @Override
	        public boolean onTouch(View arg0, MotionEvent arg1) {
	            switch (arg1.getAction()) {
		            case MotionEvent.ACTION_DOWN: {
		            	soundsController.shortSoundClip("sounds/keypad_click.mp3");
		            	ic_key_j.setAlpha(180);
		            	
		                break;
		            }
		            case MotionEvent.ACTION_UP:{
		            	ic_key_j.setAlpha(255);
		            	
		            	keyPressReceiver("j");
		            	
		                break;
		            }
	            }
	            return true;
	        }
	    });
		
		ic_key_k.setOnTouchListener(new View.OnTouchListener() {
	        @Override
	        public boolean onTouch(View arg0, MotionEvent arg1) {
	            switch (arg1.getAction()) {
		            case MotionEvent.ACTION_DOWN: {
		            	soundsController.shortSoundClip("sounds/keypad_click.mp3");
		            	ic_key_k.setAlpha(180);
		            	
		                break;
		            }
		            case MotionEvent.ACTION_UP:{
		            	ic_key_k.setAlpha(255);
		            	
		            	keyPressReceiver("k");
		            	
		                break;
		            }
	            }
	            return true;
	        }
	    });
		
		ic_key_l.setOnTouchListener(new View.OnTouchListener() {
	        @Override
	        public boolean onTouch(View arg0, MotionEvent arg1) {
	            switch (arg1.getAction()) {
		            case MotionEvent.ACTION_DOWN: {
		            	soundsController.shortSoundClip("sounds/keypad_click.mp3");
		            	ic_key_l.setAlpha(180);
		            	
		                break;
		            }
		            case MotionEvent.ACTION_UP:{
		            	ic_key_l.setAlpha(255);
		            	
		            	keyPressReceiver("l");
		            	
		                break;
		            }
	            }
	            return true;
	        }
	    });
		
		ic_key_z.setOnTouchListener(new View.OnTouchListener() {
	        @Override
	        public boolean onTouch(View arg0, MotionEvent arg1) {
	            switch (arg1.getAction()) {
		            case MotionEvent.ACTION_DOWN: {
		            	soundsController.shortSoundClip("sounds/keypad_click.mp3");
		            	ic_key_z.setAlpha(180);
		            	
		                break;
		            }
		            case MotionEvent.ACTION_UP:{
		            	ic_key_z.setAlpha(255);
		            	
		            	keyPressReceiver("z");
		            	
		                break;
		            }
	            }
	            return true;
	        }
	    });
		
		ic_key_x.setOnTouchListener(new View.OnTouchListener() {
	        @Override
	        public boolean onTouch(View arg0, MotionEvent arg1) {
	            switch (arg1.getAction()) {
		            case MotionEvent.ACTION_DOWN: {
		            	soundsController.shortSoundClip("sounds/keypad_click.mp3");
		            	ic_key_x.setAlpha(180);
		            	
		                break;
		            }
		            case MotionEvent.ACTION_UP:{
		            	ic_key_x.setAlpha(255);
		            	
		            	keyPressReceiver("x");
		            	
		                break;
		            }
	            }
	            return true;
	        }
	    });
		
		ic_key_c.setOnTouchListener(new View.OnTouchListener() {
	        @Override
	        public boolean onTouch(View arg0, MotionEvent arg1) {
	            switch (arg1.getAction()) {
		            case MotionEvent.ACTION_DOWN: {
		            	soundsController.shortSoundClip("sounds/keypad_click.mp3");
		            	ic_key_c.setAlpha(180);
		            	
		                break;
		            }
		            case MotionEvent.ACTION_UP:{
		            	ic_key_c.setAlpha(255);
		            	
		            	keyPressReceiver("c");
		            	
		                break;
		            }
	            }
	            return true;
	        }
	    });
		
		ic_key_v.setOnTouchListener(new View.OnTouchListener() {
	        @Override
	        public boolean onTouch(View arg0, MotionEvent arg1) {
	            switch (arg1.getAction()) {
		            case MotionEvent.ACTION_DOWN: {
		            	soundsController.shortSoundClip("sounds/keypad_click.mp3");
		            	ic_key_v.setAlpha(180);
		            	
		                break;
		            }
		            case MotionEvent.ACTION_UP:{
		            	ic_key_v.setAlpha(255);
		            	
		            	keyPressReceiver("v");
		            	
		                break;
		            }
	            }
	            return true;
	        }
	    });
		
		ic_key_b.setOnTouchListener(new View.OnTouchListener() {
	        @Override
	        public boolean onTouch(View arg0, MotionEvent arg1) {
	            switch (arg1.getAction()) {
		            case MotionEvent.ACTION_DOWN: {
		            	soundsController.shortSoundClip("sounds/keypad_click.mp3");
		            	ic_key_b.setAlpha(180);
		            	
		                break;
		            }
		            case MotionEvent.ACTION_UP:{
		            	ic_key_b.setAlpha(255);
		            	
		            	keyPressReceiver("b");
		            	
		                break;
		            }
	            }
	            return true;
	        }
	    });
		
		ic_key_n.setOnTouchListener(new View.OnTouchListener() {
	        @Override
	        public boolean onTouch(View arg0, MotionEvent arg1) {
	            switch (arg1.getAction()) {
		            case MotionEvent.ACTION_DOWN: {
		            	soundsController.shortSoundClip("sounds/keypad_click.mp3");
		            	ic_key_n.setAlpha(180);
		            	
		                break;
		            }
		            case MotionEvent.ACTION_UP:{
		            	ic_key_n.setAlpha(255);
		            	
		            	keyPressReceiver("n");
		            	
		                break;
		            }
	            }
	            return true;
	        }
	    });
		
		ic_key_m.setOnTouchListener(new View.OnTouchListener() {
	        @Override
	        public boolean onTouch(View arg0, MotionEvent arg1) {
	            switch (arg1.getAction()) {
		            case MotionEvent.ACTION_DOWN: {
		            	soundsController.shortSoundClip("sounds/keypad_click.mp3");
		            	ic_key_m.setAlpha(180);
		            	
		                break;
		            }
		            case MotionEvent.ACTION_UP:{
		            	ic_key_m.setAlpha(255);
		            	
		            	keyPressReceiver("m");
		            	
		                break;
		            }
	            }
	            return true;
	        }
	    });
		
		ic_key_del.setOnTouchListener(new View.OnTouchListener() {
	        @Override
	        public boolean onTouch(View arg0, MotionEvent arg1) {
	            switch (arg1.getAction()) {
		            case MotionEvent.ACTION_DOWN: {
		            	soundsController.shortSoundClip("sounds/keypad_click.mp3");
		            	ic_key_del.setAlpha(180);
		            	
		                break;
		            }
		            case MotionEvent.ACTION_UP:{
		            	ic_key_del.setAlpha(255);
		            	
		            	keyPressReceiver("del");
		            	
		                break;
		            }
	            }
	            return true;
	        }
	    });
		
		ic_key_clr.setOnTouchListener(new View.OnTouchListener() {
	        @Override
	        public boolean onTouch(View arg0, MotionEvent arg1) {
	            switch (arg1.getAction()) {
		            case MotionEvent.ACTION_DOWN: {
		            	soundsController.shortSoundClip("sounds/keypad_click.mp3");
		            	ic_key_clr.setAlpha(180);
		            	
		                break;
		            }
		            case MotionEvent.ACTION_UP:{
		            	ic_key_clr.setAlpha(255);
		            	
		            	keyPressReceiver("clr");
		            	
		                break;
		            }
	            }
	            return true;
	        }
	    });
		
	}
	//-----------all keys press-------------
	
}
