package com.gonzalez.blanchard.tvmaze.data.model;

import com.gonzalez.blanchard.tvmaze.data.pojo.Image;
import com.gonzalez.blanchard.tvmaze.data.pojo.Links;
import com.gonzalez.blanchard.tvmaze.data.pojo.Rating;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class EpisodeModel implements Serializable {

    @Id(assignable = true)
    private long id;
    private String url;
    private String name;
    private int season;
    private int number;
    private String type;
    private String airdate;
    private String airtime;
    private String airstamp;
    private int runtime;
    private Double average;
    private String mediumImageUrl;
    private String originalImageUrl;
    private String summary;

    public EpisodeModel(){

    }

    public EpisodeModel(long id, String url, String name, int season, int number, String type, String airdate, String airtime, String airstamp, int runtime, Double average, String mediumImageUrl, String originalImageUrl, String summary) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.season = season;
        this.number = number;
        this.type = type;
        this.airdate = airdate;
        this.airtime = airtime;
        this.airstamp = airstamp;
        this.runtime = runtime;
        this.average = average;
        this.mediumImageUrl = mediumImageUrl;
        this.originalImageUrl = originalImageUrl;
        this.summary = summary;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAirdate() {
        return airdate;
    }

    public void setAirdate(String airdate) {
        this.airdate = airdate;
    }

    public String getAirtime() {
        return airtime;
    }

    public void setAirtime(String airtime) {
        this.airtime = airtime;
    }

    public String getAirstamp() {
        return airstamp;
    }

    public void setAirstamp(String airstamp) {
        this.airstamp = airstamp;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }

    public String getMediumImageUrl() {
        return mediumImageUrl;
    }

    public void setMediumImageUrl(String mediumImageUrl) {
        this.mediumImageUrl = mediumImageUrl;
    }

    public String getOriginalImageUrl() {
        return originalImageUrl;
    }

    public void setOriginalImageUrl(String originalImageUrl) {
        this.originalImageUrl = originalImageUrl;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
