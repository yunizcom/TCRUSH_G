package com.mooStan.typingcrush;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Application;
import android.content.SharedPreferences;

public class GlobalVars extends Application {
	public static final String PREFS_SETTINGS = "YunizSaved";
	public boolean isPopUpOpen = false;
	public int curentStage = 0;
	public int gameStage_TimeBar_Width = 0;

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
}
