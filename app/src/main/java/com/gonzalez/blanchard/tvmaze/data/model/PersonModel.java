package com.gonzalez.blanchard.tvmaze.data.model;

import java.io.Serializable;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class PersonModel implements Serializable {

    @Id(assignable = true)
    private long id;
    private String url;
    private String name;
    private String country;
    private String birthday;
    private String deathday;
    private String gender;
    private String mediumImageUrl;
    private String originalImageUrl;
    private Long updated;

    private PersonModel(){

    }

    public PersonModel(long id, String url, String name, String country, String birthday, String deathday, String gender, String mediumImageUrl, String originalImageUrl, Long updated) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.country = country;
        this.birthday = birthday;
        this.deathday = deathday;
        this.gender = gender;
        this.mediumImageUrl = mediumImageUrl;
        this.originalImageUrl = originalImageUrl;
        this.updated = updated;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getDeathday() {
        return deathday;
    }

    public void setDeathday(String deathday) {
        this.deathday = deathday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public Long getUpdated() {
        return updated;
    }

    public void setUpdated(Long updated) {
        this.updated = updated;
    }
}
