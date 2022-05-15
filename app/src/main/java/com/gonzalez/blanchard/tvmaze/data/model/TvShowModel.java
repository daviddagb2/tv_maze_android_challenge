package com.gonzalez.blanchard.tvmaze.data.model;

import java.io.Serializable;
import java.time.LocalDate;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class TvShowModel implements Serializable {

    @Id(assignable = true)
    private long id;
    private String url;
    private String name;
    private String type;
    private String language;
    private String status;
    private Integer runtime;
    private Integer averageRuntime;
    private String premiered;
    private String ended;
    private String officialSite;
    private String summary;
    private String mediumImageUrl;
    private String originalImageUrl;
    private Integer weight;
    private Integer updated;
    private String genres;

    public  TvShowModel(){

    }

    public TvShowModel(long id, String url, String name, String type, String language, String status, Integer runtime, Integer averageRuntime, String premiered, String ended, String officialSite, String summary, String mediumImageUrl, String originalImageUrl, Integer weight, Integer updated, String genres) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.type = type;
        this.language = language;
        this.status = status;
        this.runtime = runtime;
        this.averageRuntime = averageRuntime;
        this.premiered = premiered;
        this.ended = ended;
        this.officialSite = officialSite;
        this.summary = summary;
        this.mediumImageUrl = mediumImageUrl;
        this.originalImageUrl = originalImageUrl;
        this.weight = weight;
        this.updated = updated;
        this.genres = genres;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public Integer getAverageRuntime() {
        return averageRuntime;
    }

    public void setAverageRuntime(Integer averageRuntime) {
        this.averageRuntime = averageRuntime;
    }

    public String getPremiered() {
        return premiered;
    }

    public void setPremiered(String premiered) {
        this.premiered = premiered;
    }

    public String getEnded() {
        return ended;
    }

    public void setEnded(String ended) {
        this.ended = ended;
    }

    public String getOfficialSite() {
        return officialSite;
    }

    public void setOfficialSite(String officialSite) {
        this.officialSite = officialSite;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getUpdated() {
        return updated;
    }

    public void setUpdated(Integer updated) {
        this.updated = updated;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getYear(){
        LocalDate date = LocalDate.parse(this.premiered);
        return "" + date.getYear();
    }
}
