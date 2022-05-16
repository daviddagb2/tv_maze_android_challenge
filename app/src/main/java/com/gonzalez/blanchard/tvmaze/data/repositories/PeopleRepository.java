package com.gonzalez.blanchard.tvmaze.data.repositories;

import com.gonzalez.blanchard.tvmaze.config.config;
import com.gonzalez.blanchard.tvmaze.data.api.ApiService;
import com.gonzalez.blanchard.tvmaze.data.model.PersonModel;
import com.gonzalez.blanchard.tvmaze.data.model.SeasonModel;
import com.gonzalez.blanchard.tvmaze.data.model.TvShowModel;
import com.gonzalez.blanchard.tvmaze.data.pojo.CastCredit;
import com.gonzalez.blanchard.tvmaze.data.pojo.Person;
import com.gonzalez.blanchard.tvmaze.data.pojo.SearchPeopleRequest;
import com.gonzalez.blanchard.tvmaze.data.pojo.Season;
import com.gonzalez.blanchard.tvmaze.data.pojo.TvShow;
import com.gonzalez.blanchard.tvmaze.events.CastCreditRequestEvent;
import com.gonzalez.blanchard.tvmaze.events.PersonRequestEvent;
import com.gonzalez.blanchard.tvmaze.events.SeasonRequestEvent;
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

public class PeopleRepository {

    Retrofit retrofit;
    public PeopleRepository(){
        OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();

        retrofit = new Retrofit.Builder()
                .baseUrl(config.API_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void searchPeople(String query){
        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<SearchPeopleRequest>> call = apiService.SearchPeople(query);
        call.enqueue(new Callback<List<SearchPeopleRequest>>() {
            @Override
            public void onResponse(Call<List<SearchPeopleRequest>> call, Response<List<SearchPeopleRequest>> response) {
                if(!response.isSuccessful()){
                    //add message of failure
                    EventBus.getDefault().post(new PersonRequestEvent(false, "failed searching TV shows", new ArrayList<>()));
                    return;
                }

                List<SearchPeopleRequest> peopleRequests = response.body();
                List<PersonModel> personlist = new ArrayList<>();
                for (SearchPeopleRequest searchitem : peopleRequests){
                    if(searchitem != null){
                        Person personpojo = searchitem.getPerson();
                        personlist.add(
                                new PersonModel(
                                        personpojo.getId(),
                                        personpojo.getUrl(),
                                        personpojo.getName(),
                                        personpojo.getCountry() != null ? personpojo.getCountry().getName() : "",
                                        personpojo.getBirthday(),
                                        personpojo.getDeathday() != null ? personpojo.getDeathday() : "",
                                        personpojo.getGender(),
                                        personpojo.getImage() != null ? personpojo.getImage().getMedium() : "",
                                        personpojo.getImage() != null ? personpojo.getImage().getOriginal() : "",
                                        personpojo.getUpdated()
                                )
                        );

                    }
                }
                EventBus.getDefault().post(new PersonRequestEvent(true, "Request complete", personlist));
            }

            @Override
            public void onFailure(Call<List<SearchPeopleRequest>> call, Throwable t) {
                EventBus.getDefault().post(new PersonRequestEvent(false, "failed retrieving people", new ArrayList<>()));
            }
        });
    }



    public void getPeopleCastCredits(long personId){
        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<CastCredit>> call = apiService.ListCastCredits(personId, "show");
        call.enqueue(new Callback<List<CastCredit>>() {

            @Override
            public void onResponse(Call<List<CastCredit>> call, Response<List<CastCredit>> response) {
                if(!response.isSuccessful()){
                    //add message of failure
                    EventBus.getDefault().post(new CastCreditRequestEvent(false, "failed retrieving TV shows", new ArrayList<>()));
                    return;
                }

                List<CastCredit> creditsresponse = response.body();
                List<TvShowModel> shows = new ArrayList<>();

                for (CastCredit credits : creditsresponse){
                    String genre_detail = "";

                    if(credits.getEmbedded() != null){

                        if(credits.getEmbedded().getShow().getGenres() != null){
                            for(int i=0; i< credits.getEmbedded().getShow().getGenres().size(); i++){
                                genre_detail += credits.getEmbedded().getShow().getGenres().get(i);
                                if(i < credits.getEmbedded().getShow().getGenres().size()-1){
                                    genre_detail += " ● ";
                                }
                            }
                        }

                        TvShowModel show = new TvShowModel(
                            credits.getEmbedded().getShow().getId(),
                            credits.getEmbedded().getShow().getUrl(),
                            credits.getEmbedded().getShow().getName(),
                            credits.getEmbedded().getShow().getType(),
                            credits.getEmbedded().getShow().getLanguage(),
                            credits.getEmbedded().getShow().getStatus(),
                            credits.getEmbedded().getShow().getRuntime(),
                            credits.getEmbedded().getShow().getAverageRuntime(),
                            credits.getEmbedded().getShow().getPremiered(),
                            credits.getEmbedded().getShow().getEnded(),
                            credits.getEmbedded().getShow().getOfficialSite(),
                            credits.getEmbedded().getShow().getSummary(),
                            credits.getEmbedded().getShow().getImage() != null ? credits.getEmbedded().getShow().getImage().getMedium() : "",
                            credits.getEmbedded().getShow().getImage() != null ? credits.getEmbedded().getShow().getImage().getOriginal() : "",
                            credits.getEmbedded().getShow().getWeight(),
                            credits.getEmbedded().getShow().getUpdated(),
                            genre_detail,
                            false
                        );
                        shows.add(show);
                    }
                }
                EventBus.getDefault().post(new CastCreditRequestEvent(true, "Request complete", shows));
            }

            @Override
            public void onFailure(Call<List<CastCredit>> call, Throwable t) {
                EventBus.getDefault().post(new CastCreditRequestEvent(false, "failed retrieving casting", new ArrayList<>()));
            }
        });
    }

}
