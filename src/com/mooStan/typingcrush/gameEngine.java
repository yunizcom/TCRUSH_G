package com.mooStan.typingcrush;

import java.io.IOException;
import java.io.InputStream;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mooStan.typingcrush.soundsController;

public class gameEngine extends Activity {

	private Context myContext;
	private Activity myActivity;
	
	private TextView stageLevelShow, stageTimeShow;
	private RelativeLayout slashScreen, mainMenu, sub_menu, gameStage;
	private ImageView ic_time_bar,ic_key_q,ic_key_w,ic_key_e,ic_key_r,ic_key_t,ic_key_y,ic_key_u,ic_key_i,ic_key_o,ic_key_p,ic_key_a,ic_key_s,ic_key_d,ic_key_f,ic_key_g,ic_key_h,ic_key_j,ic_key_k,ic_key_l,ic_key_z,ic_key_x,ic_key_c,ic_key_v,ic_key_b,ic_key_n,ic_key_m;
	
	public soundsController soundsController;
	
	private GlobalVars globalVariable;
	
	private boolean stopCounter = false;
	
	private int currentLevel = 1, screenWidth = 0, screenHeight = 0, sdk = 0, countDowns = 0, countTotalDowns = 0;
	
	gameEngine(Context context, Activity myActivityReference) {
		myContext = context;
		myActivity = myActivityReference;
		
		globalVariable = (GlobalVars) myActivity.getApplicationContext();
		
		soundsController = new soundsController(myContext,myActivity);
		
		stageLevelShow = (TextView) myActivity.findViewById(R.id.stageLevelShow);
		stageTimeShow = (TextView) myActivity.findViewById(R.id.stageTimeShow);
		
		ic_time_bar = (ImageView) myActivity.findViewById(R.id.ic_time_bar);
		
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
		
		slashScreen = (RelativeLayout) myActivity.findViewById(R.id.slashScreen);
		mainMenu = (RelativeLayout) myActivity.findViewById(R.id.mainMenu);
		sub_menu = (RelativeLayout) myActivity.findViewById(R.id.sub_menu);
		gameStage = (RelativeLayout) myActivity.findViewById(R.id.gameStage);
		
		initAllKeysPress();
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
	
	private void keyPressReceiver(String keycode){
		Log.v("debug",keycode);
	}
	
	//-----------all keys press-------------
	private void initAllKeysPress(){
		
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
		
	}
	//-----------all keys press-------------
	
}