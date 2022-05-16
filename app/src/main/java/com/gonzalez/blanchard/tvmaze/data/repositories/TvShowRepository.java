package com.gonzalez.blanchard.tvmaze.data.repositories;

import com.gonzalez.blanchard.tvmaze.config.config;
import com.gonzalez.blanchard.tvmaze.data.api.ApiService;
import com.gonzalez.blanchard.tvmaze.data.database.TvShowDatabaseRepository;
import com.gonzalez.blanchard.tvmaze.data.model.EpisodeModel;
import com.gonzalez.blanchard.tvmaze.data.model.SeasonModel;
import com.gonzalez.blanchard.tvmaze.data.model.TvShowModel;
import com.gonzalez.blanchard.tvmaze.data.pojo.Episode;
import com.gonzalez.blanchard.tvmaze.data.pojo.SearchTvShowRequest;
import com.gonzalez.blanchard.tvmaze.data.pojo.Season;
import com.gonzalez.blanchard.tvmaze.data.pojo.TvShow;
import com.gonzalez.blanchard.tvmaze.events.EpisodeRequestEvent;
import com.gonzalez.blanchard.tvmaze.events.SeasonRequestEvent;
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

    TvShowDatabaseRepository dbrepository;
    Retrofit retrofit;
    public TvShowRepository(){
        dbrepository = new TvShowDatabaseRepository();
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
                    TvShowModel model = convertTvShowPojoToModel(show);
                    showlist.add(model);
                    dbrepository.saveTvShow(model);
                }
                EventBus.getDefault().post(new TvShowRequestEvent(true, "Request complete", showlist));
            }

            @Override
            public void onFailure(Call<List<TvShow>> call, Throwable t) {
                EventBus.getDefault().post(new TvShowRequestEvent(false, "failed retrieving TV shows", new ArrayList<>()));
            }
        });
    }

    public void getSeasons(long showId){
        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<Season>> call = apiService.ListSeasons(showId);
        call.enqueue(new Callback<List<Season>>() {

            @Override
            public void onResponse(Call<List<Season>> call, Response<List<Season>> response) {
                if(!response.isSuccessful()){
                    //add message of failure
                    EventBus.getDefault().post(new SeasonRequestEvent(false, "failed retrieving TV shows", new ArrayList<>()));
                    return;
                }

                List<Season> seasonsresponse = response.body();
                List<SeasonModel> seasons = new ArrayList<>();
                for (Season season : seasonsresponse){
                    seasons.add(
                            new SeasonModel(
                                    season.getId(),
                                    season.getUrl(),
                                    season.getNumber(),
                                    season.getName(),
                                    season.getEpisodeOrder(),
                                    season.getPremiereDate(),
                                    season.getEndDate(),
                                    season.getImage() != null ? season.getImage().getMedium() : "",
                                    season.getImage() != null ? season.getImage().getOriginal() : "",
                                    season.getSummary()
                            )
                    );
                }
                EventBus.getDefault().post(new SeasonRequestEvent(true, "Request complete", seasons));
            }

            @Override
            public void onFailure(Call<List<Season>> call, Throwable t) {
                EventBus.getDefault().post(new SeasonRequestEvent(false, "failed retrieving TV shows", new ArrayList<>()));
            }
        });
    }

    public void searchTvShows(String query){
        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<SearchTvShowRequest>> call = apiService.SearchShows(query);
        call.enqueue(new Callback<List<SearchTvShowRequest>>() {
            @Override
            public void onResponse(Call<List<SearchTvShowRequest>> call, Response<List<SearchTvShowRequest>> response) {
                if(!response.isSuccessful()){
                    //add message of failure
                    EventBus.getDefault().post(new TvShowRequestEvent(false, "failed searching TV shows", new ArrayList<>()));
                    return;
                }

                List<SearchTvShowRequest> tvshowrequests = response.body();
                List<TvShowModel> showlist = new ArrayList<>();
                for (SearchTvShowRequest searchmd : tvshowrequests){
                    if(searchmd != null){
                        TvShow tvshowpojo = searchmd.getShow();
                        showlist.add(convertTvShowPojoToModel(tvshowpojo));
                    }
                }
                EventBus.getDefault().post(new TvShowRequestEvent(true, "Request complete", showlist));
            }

            @Override
            public void onFailure(Call<List<SearchTvShowRequest>> call, Throwable t) {
                EventBus.getDefault().post(new TvShowRequestEvent(false, "failed retrieving TV shows", new ArrayList<>()));
            }
        });
    }

    public void getEpisodes(long seasonId){
        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<Episode>> call = apiService.ListEpisodes(seasonId);
        call.enqueue(new Callback<List<Episode>>() {
            @Override
            public void onResponse(Call<List<Episode>> call, Response<List<Episode>> response) {
                if(!response.isSuccessful()){
                    //add message of failure
                    EventBus.getDefault().post(new EpisodeRequestEvent(false, "failed searching TV shows", new ArrayList<>()));
                    return;
                }

                List<Episode> episodeslist = response.body();
                List<EpisodeModel> episodes = new ArrayList<>();
                for (Episode ep : episodeslist){
                    if(ep != null){
                        episodes.add(
                          new EpisodeModel(
                                ep.getId(),
                                ep.getUrl(),
                                ep.getName(),
                                ep.getSeason(),
                                ep.getNumber(),
                                ep.getType(),
                                ep.getAirdate(),
                                ep.getAirtime(),
                                ep.getAirstamp(),
                                ep.getRuntime(),
                                0.0,
                                ep.getImage() != null ? ep.getImage().getMedium() : "",
                                ep.getImage() != null ? ep.getImage().getOriginal() : "",
                                ep.getSummary()
                          )
                        );
                    }
                }
                EventBus.getDefault().post(new EpisodeRequestEvent(true, "Request complete", episodes));
            }

            @Override
            public void onFailure(Call<List<Episode>> call, Throwable t) {

            }
        });

    }
    private TvShowModel convertTvShowPojoToModel(TvShow show){
        String genre_detail = "";

        if(!(show.getGenres() == null)){
            for(int i=0; i< show.getGenres().size(); i++){
                genre_detail += show.getGenres().get(i);
                if(i < show.getGenres().size()-1){
                    genre_detail += " ● ";
                }
            }
        }

        String emptyImage = "https://upload.wikimedia.org/wikipedia/commons/1/14/No_Image_Available.jpg?20200913095930";
        return new TvShowModel(show.getId(),
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
                show.getImage() != null ? show.getImage().getMedium() : emptyImage,
                show.getImage() != null ? show.getImage().getOriginal() : emptyImage,
                show.getWeight(),
                show.getUpdated(),
                genre_detail,
                false);
    }
}
