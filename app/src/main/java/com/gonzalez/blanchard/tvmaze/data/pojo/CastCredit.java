package com.gonzalez.blanchard.tvmaze.data.pojo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CastCredit {

    @SerializedName("self")
    @Expose
    private Boolean self;
    @SerializedName("voice")
    @Expose
    private Boolean voice;
    @SerializedName("_links")
    @Expose
    private Links links;
    @SerializedName("_embedded")
    @Expose
    private Embedded embedded;

    public Boolean getSelf() {
        return self;
    }

    public void setSelf(Boolean self) {
        this.self = self;
    }

    public Boolean getVoice() {
        return voice;
    }

    public void setVoice(Boolean voice) {
        this.voice = voice;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public Embedded getEmbedded() {
        return embedded;
    }

    public void setEmbedded(Embedded embedded) {
        this.embedded = embedded;
    }

}
