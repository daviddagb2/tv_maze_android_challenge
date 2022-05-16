package com.gonzalez.blanchard.tvmaze.data.api;

import com.gonzalez.blanchard.tvmaze.data.pojo.CastCredit;
import com.gonzalez.blanchard.tvmaze.data.pojo.Episode;
import com.gonzalez.blanchard.tvmaze.data.pojo.SearchPeopleRequest;
import com.gonzalez.blanchard.tvmaze.data.pojo.SearchTvShowRequest;
import com.gonzalez.blanchard.tvmaze.data.pojo.Season;
import com.gonzalez.blanchard.tvmaze.data.pojo.TvShow;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("/shows")
    Call<List<TvShow>> ListsShows();

    @GET("/search/shows")
    Call<List<SearchTvShowRequest>> SearchShows(
            @Query("q") String search);

    @GET("/shows/{showid}/seasons")
    Call<List<Season>> ListSeasons(
            @Path("showid") long showId);

    @GET("/seasons/{episodeid}/episodes")
    Call<List<Episode>> ListEpisodes(
            @Path("episodeid") long showId);

    @GET("/search/people")
    Call<List<SearchPeopleRequest>> SearchPeople(
            @Query("q") String search);

    @GET("/people/{personId}/castcredits")
    Call<List<CastCredit>> ListCastCredits(
            @Path("personId") long personId,
            @Query("embed") String embed);
}
