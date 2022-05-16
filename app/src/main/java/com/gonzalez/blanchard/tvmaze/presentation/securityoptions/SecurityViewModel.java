package com.gonzalez.blanchard.tvmaze.presentation.securityoptions;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SecurityViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public SecurityViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}