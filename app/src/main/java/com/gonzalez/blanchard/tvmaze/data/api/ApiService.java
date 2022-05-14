package com.gonzalez.blanchard.tvmaze.data.api;

import com.gonzalez.blanchard.tvmaze.data.pojo.TvShow;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("/shows")
    Call<List<TvShow>> ListsShows();
}
