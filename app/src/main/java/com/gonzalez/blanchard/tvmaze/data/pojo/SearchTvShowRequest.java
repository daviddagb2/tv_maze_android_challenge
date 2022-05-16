package com.gonzalez.blanchard.tvmaze.data.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchTvShowRequest {

    @SerializedName("score")
    @Expose
    private Double score;
    @SerializedName("show")
    @Expose
    private TvShow show;

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public TvShow getShow() {
        return show;
    }

    public void setShow(TvShow show) {
        this.show = show;
    }

}