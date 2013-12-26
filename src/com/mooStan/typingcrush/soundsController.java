package com.mooStan.typingcrush;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

public class soundsController {

	private Context myContext;
	private Activity myActivity;
	
	MediaPlayer bgMusic = new MediaPlayer();
	MediaPlayer shortMusic = new MediaPlayer();
	
	soundsController(Context context, Activity myActivityReference) {
		myContext = context;
		myActivity = myActivityReference;
	}
	
	//-----------app activity life cycle handling---------------
	public void destroyBgMusic(){
		 bgMusic.stop();
		 bgMusic.release();
		 
		 shortMusic.stop();
		 shortMusic.release();
	}
	
	public void pauseBgMusic(){
		if(bgMusic.isPlaying()){
			bgMusic.pause();
		}
	}
	
	public void resumeBgMusic(){
		try {
			bgMusic.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(!bgMusic.isPlaying()){
			bgMusic.start();
		}
	}
	//-----------app activity life cycle handling---------------
	
	public void playBgMusic(String filename, boolean looping){
		bgMusic.reset();
		bgMusic.release();
		bgMusic = null;
    	
		AssetFileDescriptor descriptor = null;
		try {
			descriptor = myActivity.getAssets().openFd(filename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long start = descriptor.getStartOffset();
		long end = descriptor.getLength();
		
		bgMusic = new MediaPlayer();
		try {
			bgMusic.setDataSource(descriptor.getFileDescriptor(), start, end);
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
			bgMusic.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		bgMusic.setVolume(1.0f,1.0f);
		
		bgMusic.setLooping(looping);
		bgMusic.start();
	}
	
	public void shortSoundClip(String filename){
		shortMusic.reset();
		shortMusic.release();
		shortMusic = null;
    	
		AssetFileDescriptor descriptor = null;
		try {
			descriptor = myActivity.getAssets().openFd(filename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long start = descriptor.getStartOffset();
		long end = descriptor.getLength();
		
		shortMusic = new MediaPlayer();
		try {
			shortMusic.setDataSource(descriptor.getFileDescriptor(), start, end);
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
			shortMusic.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		shortMusic.start();
	}
	
}
