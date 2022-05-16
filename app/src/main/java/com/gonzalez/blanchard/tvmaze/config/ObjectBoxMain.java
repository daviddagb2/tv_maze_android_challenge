package com.gonzalez.blanchard.tvmaze.config;

import android.content.Context;

import com.gonzalez.blanchard.tvmaze.data.model.MyObjectBox;

import io.objectbox.BoxStore;

public class ObjectBoxMain {

    private static BoxStore boxStore;

    public static void init(Context context){
        boxStore = MyObjectBox.builder()
                .androidContext(context.getApplicationContext())
                .build();
    }

    public static BoxStore get(){
        return boxStore;
    }
}
