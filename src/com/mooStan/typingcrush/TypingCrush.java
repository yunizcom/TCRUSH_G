package com.mooStan.typingcrush;

import java.io.IOException;
import java.io.InputStream;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.Display;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Point;
import android.graphics.drawable.Drawable;

import com.mooStan.typingcrush.initSetup;
import com.mooStan.typingcrush.serverComm;

public class TypingCrush extends Activity {

	public initSetup initSetup;
	public serverComm serverComm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_typing_crush);

		initSetup = new initSetup(this.getApplicationContext(),this);
		serverComm = new serverComm(this.getApplicationContext());
		
		initSetup.basicDetection();
	}

	
	
}
