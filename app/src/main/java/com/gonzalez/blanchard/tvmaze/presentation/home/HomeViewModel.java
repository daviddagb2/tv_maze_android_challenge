package com.gonzalez.blanchard.tvmaze.presentation.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gonzalez.blanchard.tvmaze.data.pojo.TvShow;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<List<TvShow>> tvshowslist;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");

        List<TvShow> listalocal = new ArrayList<TvShow>();
        //listalocal.add()

        tvshowslist = new MutableLiveData<>();
        tvshowslist.setValue(listalocal);

    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<List<TvShow>> getTvShowList(){
        return tvshowslist;
    }
}