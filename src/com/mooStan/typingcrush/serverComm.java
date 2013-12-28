package com.mooStan.typingcrush;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.mooStan.typingcrush.popupBox;

public class serverComm {

	private Context myContext;
	private Activity myActivity;
	
	public popupBox popupBox;

	serverComm(Context context, Activity myActivityReference) {
		myContext = context;
		myActivity = myActivityReference;
		
		popupBox = new popupBox(myContext,myActivity);
	}
	
	public void checkInternetConnection(){
		if(!isNetworkAvailable()){
			popupBox.showPopBox("You need a smooth internet connection to play this game.");
		}
	}
	
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) myContext.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	
}
