package com.mooStan.typingcrush;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Application;
import android.content.SharedPreferences;

public class GlobalVars extends Application {
	public static final String PREFS_SETTINGS = "YunizSaved";
	public boolean isPopUpOpen = false, stopCounter = false;
	public int scores = 0, currentObjDelayed = 0, currentToDelayed = 0, currentLevels = 0, currentTill = 0, curentStage = 0, countTotalDowns = 0, countDowns = 0, gameStage_TimeBar_Width = 0, curShownObject = 0, currArrayItemIndex = 0;
	public String curTypedWord;
	public String[] currentLevelChallenge;

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
}
