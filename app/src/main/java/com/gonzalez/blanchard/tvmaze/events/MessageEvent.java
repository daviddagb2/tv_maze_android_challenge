package com.gonzalez.blanchard.tvmaze.events;

public class MessageEvent {

    public final String message;
    public final boolean success;

    public MessageEvent(boolean success, String message) {
        this.message = message;
        this.success = success;
    }
}