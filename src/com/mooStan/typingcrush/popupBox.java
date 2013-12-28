package com.mooStan.typingcrush;

import java.io.IOException;
import java.io.InputStream;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mooStan.typingcrush.soundsController;

public class popupBox {

	private Context myContext;
	private Activity myActivity;
	
	private RelativeLayout popbox;
	private LinearLayout popboxCenter;
	private ImageView popboxOK_btn;
	private TextView popboxMSG;
	
	public soundsController soundsController;
	private GlobalVars globalVariable;
	
	popupBox(Context context, Activity myActivityReference) {
		myContext = context;
		myActivity = myActivityReference;
		
		soundsController = new soundsController(myContext,myActivity);
		globalVariable = (GlobalVars) myActivity.getApplicationContext();

		popbox = (RelativeLayout) myActivity.findViewById(R.id.popbox);
		popboxCenter = (LinearLayout) myActivity.findViewById(R.id.popboxCenter);
		popboxOK_btn = (ImageView) myActivity.findViewById(R.id.popboxOK_btn);
		popboxMSG = (TextView) myActivity.findViewById(R.id.popboxMSG);
		
		setStageBackground_relative(popbox,"backgrounds/opacity_50_bg.png");
		setStageBackground_linear(popboxCenter,"backgrounds/popUp_bg.png");
		
		Typeface tf = Typeface.createFromAsset(myActivity.getAssets(), "fonts/Cookies.ttf");
		popboxMSG.setTypeface(tf);
		popboxMSG.setTextSize(20);
		popboxMSG.setPadding(10, 0, 10, 20);
		popboxMSG.setTextColor(Color.parseColor("#7a7a7a"));
		
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
		            	
		            	popbox.setVisibility(View.INVISIBLE);
		            	popbox.setClickable(false);
		            	globalVariable.isPopUpOpen = false;
		            	
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
	}
	
	public void showPopBox(String msg){
		popbox.setVisibility(View.VISIBLE);
		popboxMSG.setText(msg);
		popbox.setClickable(true);
		
		globalVariable.isPopUpOpen = true;
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
	
}
