package com.gonzalez.blanchard.tvmaze.events;

import com.gonzalez.blanchard.tvmaze.data.model.TvShowModel;
import java.util.ArrayList;
import java.util.List;

public class TvShowRequestEvent extends MessageEvent {

    List<TvShowModel> list = new ArrayList<>();

    public TvShowRequestEvent(boolean success, String message, List<TvShowModel> list) {
        super(success, message);
        this.list = list;
    }

    public List<TvShowModel> getList() {
        return list;
    }

    public void setList(List<TvShowModel> list) {
        this.list = list;
    }
}
