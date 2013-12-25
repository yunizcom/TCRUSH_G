package com.mooStan.typingcrush;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

public class soundsController {

	private Context myContext;
	private Activity myActivity;
	
	MediaPlayer splashScreenMusic = new MediaPlayer();
	
	soundsController(Context context, Activity myActivityReference) {
		myContext = context;
		myActivity = myActivityReference;
	}
	
	public void playBgMusic(String filename, boolean looping){
		splashScreenMusic.reset();
		splashScreenMusic.release();
		splashScreenMusic = null;
    	
		AssetFileDescriptor descriptor = null;
		try {
			descriptor = myActivity.getAssets().openFd(filename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long start = descriptor.getStartOffset();
		long end = descriptor.getLength();
		
		splashScreenMusic = new MediaPlayer();
		try {
			splashScreenMusic.setDataSource(descriptor.getFileDescriptor(), start, end);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			descriptor.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			splashScreenMusic.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		splashScreenMusic.setVolume(1.0f,1.0f);
		
		splashScreenMusic.setLooping(looping);
		splashScreenMusic.start();
	}
	
}
