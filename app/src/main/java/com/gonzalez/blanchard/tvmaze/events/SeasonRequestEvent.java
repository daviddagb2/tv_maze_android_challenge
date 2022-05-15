package com.gonzalez.blanchard.tvmaze.events;

import com.gonzalez.blanchard.tvmaze.data.model.SeasonModel;
import com.gonzalez.blanchard.tvmaze.data.model.TvShowModel;

import java.util.ArrayList;
import java.util.List;

public class SeasonRequestEvent extends MessageEvent {

    List<SeasonModel> list = new ArrayList<>();

    public SeasonRequestEvent(boolean success, String message, List<SeasonModel> list) {
        super(success, message);
        this.list = list;
    }

    public List<SeasonModel> getList() {
        return list;
    }

    public void setList(List<SeasonModel> list) {
        this.list = list;
    }
}

