package com.gonzalez.blanchard.tvmaze.events;

import com.gonzalez.blanchard.tvmaze.data.model.PersonModel;
import java.util.ArrayList;
import java.util.List;

public class PersonRequestEvent extends MessageEvent {

    List<PersonModel> list = new ArrayList<>();

    public PersonRequestEvent(boolean success, String message, List<PersonModel> list) {
        super(success, message);
        this.list = list;
    }

    public List<PersonModel> getList() {
        return list;
    }

    public void setList(List<PersonModel> list) {
        this.list = list;
    }
}
