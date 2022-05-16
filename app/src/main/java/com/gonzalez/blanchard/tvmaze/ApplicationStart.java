package com.gonzalez.blanchard.tvmaze;

import android.app.Application;

import com.gonzalez.blanchard.tvmaze.config.ObjectBoxMain;

public class ApplicationStart extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        ObjectBoxMain.init(this);
    }
}
