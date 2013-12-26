package com.mooStan.typingcrush;

import android.os.Bundle;
import android.app.Activity;
import com.mooStan.typingcrush.initSetup;
import com.mooStan.typingcrush.soundsController;

public class TypingCrush extends Activity {

	public initSetup initSetup;
	public soundsController soundsController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_typing_crush);

		initSetup = new initSetup(this.getApplicationContext(),this);
		soundsController = new soundsController(this.getApplicationContext(),this);
		
		initSetup.basicDetection();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		soundsController.destroyBgMusic();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		
		soundsController.pauseBgMusic();
	}
	
	protected void onResume() {
		super.onResume();
		
		soundsController.resumeBgMusic();
	}
	
}
