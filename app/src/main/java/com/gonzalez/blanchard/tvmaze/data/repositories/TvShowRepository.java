package com.gonzalez.blanchard.tvmaze.data.repositories;

import com.gonzalez.blanchard.tvmaze.config.config;
import com.gonzalez.blanchard.tvmaze.data.api.ApiService;
import com.gonzalez.blanchard.tvmaze.data.model.TvShowModel;
import com.gonzalez.blanchard.tvmaze.data.pojo.TvShow;
import com.gonzalez.blanchard.tvmaze.events.TvShowRequestEvent;
import com.gonzalez.blanchard.tvmaze.utils.UnsafeOkHttpClient;
import org.greenrobot.eventbus.EventBus;
import java.util.ArrayList;
import java.util.List;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TvShowRepository {

    Retrofit retrofit;
    public TvShowRepository(){
        OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();

        retrofit = new Retrofit.Builder()
                .baseUrl(config.API_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
                //.create(ApiService.class);
    }

    public void getTvShows(){
        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<TvShow>> call = apiService.ListsShows();
        call.enqueue(new Callback<List<TvShow>>() {
            @Override
            public void onResponse(Call<List<TvShow>> call, Response<List<TvShow>> response) {
                if(!response.isSuccessful()){
                    //add message of failure
                    EventBus.getDefault().post(new TvShowRequestEvent(false, "failed retrieving TV shows", new ArrayList<>()));
                    return;
                }
                List<TvShow> tvshows = response.body();
                List<TvShowModel> showlist = new ArrayList<>();
                for (TvShow show : tvshows){
                    //save on db or do something
                    TvShowModel newmodel = new TvShowModel(
                          show.getId(),
                          show.getUrl(),
                          show.getName(),
                          show.getType(),
                          show.getLanguage(),
                          show.getStatus(),
                          show.getRuntime(),
                          show.getAverageRuntime(),
                          show.getPremiered(),
                          show.getEnded(),
                          show.getOfficialSite(),
                          show.getSummary(),
                          show.getImage().getMedium(),
                          show.getImage().getOriginal(),
                          show.getWeight(),
                          show.getUpdated());
                    showlist.add(newmodel);
                }
                EventBus.getDefault().post(new TvShowRequestEvent(true, "Request complete", showlist));
            }

            @Override
            public void onFailure(Call<List<TvShow>> call, Throwable t) {
                EventBus.getDefault().post(new TvShowRequestEvent(false, "failed retrieving TV shows", new ArrayList<>()));
            }
        });
    }

}
