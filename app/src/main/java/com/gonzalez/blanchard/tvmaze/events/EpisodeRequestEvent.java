package com.gonzalez.blanchard.tvmaze.events;

import com.gonzalez.blanchard.tvmaze.data.model.EpisodeModel;
import com.gonzalez.blanchard.tvmaze.data.model.SeasonModel;

import java.util.ArrayList;
import java.util.List;

public class EpisodeRequestEvent extends MessageEvent {

    List<EpisodeModel> list = new ArrayList<>();

    public EpisodeRequestEvent(boolean success, String message, List<EpisodeModel> list) {
        super(success, message);
        this.list = list;
    }

    public List<EpisodeModel> getList() {
        return list;
    }

    public void setList(List<EpisodeModel> list) {
        this.list = list;
    }
}
