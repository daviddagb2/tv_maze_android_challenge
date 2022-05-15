package com.gonzalez.blanchard.tvmaze.data.model;

import java.io.Serializable;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class SeasonModel implements Serializable {

    @Id(assignable = true)
    private long id;
    private String url;
    private int number;
    private String name;
    private int episodeOrder;
    private String premiereDate;
    private String endDate;
    private String mediumImageUrl;
    private String originalImageUrl;
    private String summary;

    public SeasonModel(){

    }

    public SeasonModel(long id, String url, int number, String name, int episodeOrder, String premiereDate, String endDate, String mediumImageUrl, String originalImageUrl, String summary) {
        this.id = id;
        this.url = url;
        this.number = number;
        this.name = name;
        this.episodeOrder = episodeOrder;
        this.premiereDate = premiereDate;
        this.endDate = endDate;
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEpisodeOrder() {
        return episodeOrder;
    }

    public void setEpisodeOrder(int episodeOrder) {
        this.episodeOrder = episodeOrder;
    }

    public String getPremiereDate() {
        return premiereDate;
    }

    public void setPremiereDate(String premiereDate) {
        this.premiereDate = premiereDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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
