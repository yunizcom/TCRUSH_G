package com.mooStan.typingcrush;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

public class soundsController {

	private Context myContext;
	private Activity myActivity;
	
	private GlobalVars globalVariable;
	
	soundsController(Context context, Activity myActivityReference) {
		myContext = context;
		myActivity = myActivityReference;
		
		globalVariable = (GlobalVars) myActivity.getApplicationContext();
	}
	
	//-----------app activity life cycle handling---------------
	public void destroyBgMusic(){
		globalVariable.bgMusic.stop();
		globalVariable.bgMusic.release();
		//globalVariable.bgMusic = null;
		 
		globalVariable.shortMusic.stop();
		globalVariable.shortMusic.release();
		//globalVariable.shortMusic = null;
		 
		globalVariable.objMusic.stop();
		globalVariable.objMusic.release();
		//globalVariable.objMusic = null;
	}
	
	public void pauseBgMusic(){
		if(globalVariable.bgMusic.isPlaying()){
			globalVariable.bgMusic.pause();
		}
		globalVariable.objMusic.setVolume(0.0f, 0.0f);
		globalVariable.minimize = 1;
	}
	
	public void resumeBgMusic(){
		try {
			globalVariable.bgMusic.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(!globalVariable.bgMusic.isPlaying()){
			globalVariable.bgMusic.start();
		}
		
		globalVariable.objMusic.setVolume(1.0f, 1.0f);
		globalVariable.minimize = 0;
	}
	//-----------app activity life cycle handling---------------
	
	public void playBgMusic(String filename, boolean looping){
		globalVariable.bgMusic.reset();
		globalVariable.bgMusic.release();
		//globalVariable.bgMusic = null;
    	
		AssetFileDescriptor descriptor = null;
		try {
			descriptor = myActivity.getAssets().openFd(filename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long start = descriptor.getStartOffset();
		long end = descriptor.getLength();
		
		globalVariable.bgMusic = new MediaPlayer();
		try {
			globalVariable.bgMusic.setDataSource(descriptor.getFileDescriptor(), start, end);
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
			globalVariable.bgMusic.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		globalVariable.bgMusic.setVolume(1.0f,1.0f);
		
		globalVariable.bgMusic.setLooping(looping);
		globalVariable.bgMusic.start();
	}
	
	public void shortSoundClip(String filename){
		globalVariable.shortMusic.reset();
		globalVariable.shortMusic.release();
		//globalVariable.shortMusic = null;
    	
		AssetFileDescriptor descriptor = null;
		try {
			descriptor = myActivity.getAssets().openFd(filename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long start = descriptor.getStartOffset();
		long end = descriptor.getLength();
		
		globalVariable.shortMusic = new MediaPlayer();
		try {
			globalVariable.shortMusic.setDataSource(descriptor.getFileDescriptor(), start, end);
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
			globalVariable.shortMusic.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		globalVariable.shortMusic.start();
	}
	
	public void objSoundClip(String filename){
		globalVariable.objMusic.reset();
		globalVariable.objMusic.release();
		//globalVariable.objMusic = null;

		AssetFileDescriptor descriptor = null;
		try {
			descriptor = myActivity.getAssets().openFd(filename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long start = descriptor.getStartOffset();
		long end = descriptor.getLength();
		
		globalVariable.objMusic = new MediaPlayer();
		try {
			globalVariable.objMusic.setDataSource(descriptor.getFileDescriptor(), start, end);
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
			globalVariable.objMusic.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(globalVariable.minimize == 1){
			globalVariable.objMusic.setVolume(0.0f, 0.0f);
		}
		
		globalVariable.objMusic.start();
	}
	
}
