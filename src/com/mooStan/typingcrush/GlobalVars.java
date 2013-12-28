package com.mooStan.typingcrush;

import android.app.Application;

public class GlobalVars extends Application {
	boolean isPopUpOpen = false;

    public boolean isPopUpOpen() {
        return isPopUpOpen;
    }

    public boolean setIsPopUpOpen(boolean option) {
        this.isPopUpOpen = option;
        return isPopUpOpen;
    }
}
