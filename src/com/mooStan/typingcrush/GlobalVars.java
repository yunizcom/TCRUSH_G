package com.mooStan.typingcrush;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Application;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.util.Log;

public class GlobalVars extends Application {
	public static final String PREFS_SETTINGS = "YunizSaved", PREFS_SETTINGS_SCORES = "YunizSCores", gameServerAPI_URL = "http://www.yuniz.com/apps/tyc/";
	public boolean isBlockOpen = false, isResultOpen = false, isPopUpOpen = false, stopCounter = false;
	public int minimize = 0, scores = 0, currentObjDelayed = 0, currentToDelayed = 0, currentLevels = 0, currentTill = 0, curentStage = 0, countTotalDowns = 0, countDowns = 0, gameStage_TimeBar_Width = 0, curShownObject = 0, currArrayItemIndex = 0;
	public String curTypedWord;
	public String[] currentLevelChallenge;
	public MediaPlayer bgMusic = new MediaPlayer(), shortMusic = new MediaPlayer(), objMusic = new MediaPlayer();
	
    public void saveYunizSaved(int level, int bombs, String playername, int shares){ // usage : saveYunizSaved(10, 2, "Stanly", 1);
		  SharedPreferences settings = this.getSharedPreferences(PREFS_SETTINGS, 0);
	      SharedPreferences.Editor editor = settings.edit();
	      editor.putInt("YunizCurLevel", level);
	      editor.putInt("YunizCurBombs", bombs);
	      editor.putString("YunizPlayerName", playername);
	      editor.putInt("YunizShared", shares);

	      editor.commit();
	}
    
	public JSONObject retriveScoreValue() throws JSONException{ // usage : retriveScoreValue().getString("YunizCurLevel") + " | " + retriveScoreValue().getString("YunizCurBombs") + " | " + retriveScoreValue().getString("YunizPlayerName") + " | " + retriveScoreValue().getString("YunizShared")
		String returnString = "";
		SharedPreferences settings = this.getSharedPreferences(PREFS_SETTINGS, 0);
		returnString = "{'YunizCurLevel' : " + settings.getInt("YunizCurLevel", 0) + ",'YunizCurBombs' : " + settings.getInt("YunizCurBombs", 0) + ",'YunizPlayerName' : '" + settings.getString("YunizPlayerName", "New Player") + "','YunizShared' : " + settings.getInt("YunizShared", 0) + "}";
		
		JSONObject json = new JSONObject(returnString);

		return json;
	}
	
	public void saveYunizScores(int level, int scores){ // usage : saveYunizScores(10, 200);
		  SharedPreferences settings = this.getSharedPreferences(PREFS_SETTINGS_SCORES, 0);
	      SharedPreferences.Editor editor = settings.edit();
	      
	      String curStr = getYunizScores();

	      String levelScoresStr = "";
	      String[] levelScores = curStr.split("[|]");
      
	    //-----retrieve all saved scores-----
		List<String> list = new ArrayList<String>();
		Collections.addAll(list, levelScores);
	
		for(int i = list.size(); i < level; i++){
			list.add(i, "0");
		}
		levelScores = list.toArray(new String[list.size()]);
		//-----retrieve all saved scores-----
      
	      int index = 0;
	      for(String s : levelScores)
	      {
	    	  if(level == (index + 1)){
	    		  levelScoresStr = levelScoresStr + scores + "|";
	    	  }else{
	    		  levelScoresStr = levelScoresStr + levelScores[index] + "|";
	    	  }
	    	  index++;
	      }
	      
	      editor.putString("scores", levelScoresStr);

	      editor.commit();
	}
	
	public String getYunizScores(){ // usage : getYunizScores();
		String curScoresList = "";
		
		SharedPreferences scores = this.getSharedPreferences(PREFS_SETTINGS_SCORES, 0);
		curScoresList = scores.getString("scores", "");
		
		return curScoresList;
	}
	
	public String getSelectedYunizScores(int index){ // usage : getSelectedYunizScores(0);
		String curScoresList = "";
		
		SharedPreferences scores = this.getSharedPreferences(PREFS_SETTINGS_SCORES, 0);
		curScoresList = scores.getString("scores", "");
		
		String[] levelScores = curScoresList.split("[|]");
		
		if(levelScores.length < index){
			return "0";
		}else{
			return levelScores[(index - 1)];
		}
	}
	
	public int getLevel(){
		int curValue = 0;
		
		try {
			curValue = Integer.valueOf(retriveScoreValue().getString("YunizCurLevel"));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return curValue;
	}
	
	public int getBomb(){
		int curValue = 0;
		
		try {
			curValue = Integer.valueOf(retriveScoreValue().getString("YunizCurBombs"));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return curValue;
	}
	
	public int getShare(){
		int curValue = 0;
		
		try {
			curValue = Integer.valueOf(retriveScoreValue().getString("YunizShared"));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return curValue;
	}
	
	public String getPlayerName(){
		String curValue = "New Player";
		
		try {
			curValue = retriveScoreValue().getString("YunizPlayerName");
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return curValue;
	}
	
	public static JSONObject getJSONfromURL(String url,List<NameValuePair> postDatas ){

		//initialize
		InputStream is = null;
		String result = "";
		JSONObject jArray = null;

		//http post
		try{
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);
			
	        httppost.setEntity(new UrlEncodedFormEntity(postDatas));
			
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

		}catch(Exception e){
			Log.e("log_tag", "Error in http connection "+e.toString());
		}

		//convert response to string
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result=sb.toString();
		}catch(Exception e){
			Log.e("log_tag", "Error converting result "+e.toString());
		}

		//try parse the string to a JSON object
		try{
	        	jArray = new JSONObject(result);
		}catch(JSONException e){
			Log.e("log_tag", "Error parsing data "+e.toString());
		}

		return jArray;
	} 
}
