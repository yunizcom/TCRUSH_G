package com.mooStan.typingcrush;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

public class serverComm {

	private Context myContext;

	serverComm(Context context) {
		myContext = context;
	}
	
	public void checkInternetConnection(){
		if(!isNetworkAvailable()){
			Toast.makeText(myContext.getApplicationContext(), "You need a smooth internet connection to play this game." , Toast.LENGTH_LONG).show();
		}
	}
	
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) myContext.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	
}
