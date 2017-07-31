package com.coderschool.rxthinking;

import java.util.UUID;

public class Note {
    private final String id;
    private final String note;

    public Note(String note) {
        this.id = UUID.randomUUID().toString();
        this.note = note;
    }

    public String getId() {
        return id;
    }

    public String getNote() {
        return note;
    }
}
