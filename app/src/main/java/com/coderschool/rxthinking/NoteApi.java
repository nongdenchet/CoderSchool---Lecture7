package com.coderschool.rxthinking;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;

class NoteApi {

    public Observable<List<Note>> getNotes() {
        return Observable.just(Arrays.asList(
                new Note("Do final project"),
                new Note("Go to school"),
                new Note("Practice guitar"),
                new Note("Learn Android"),
                new Note("Go swimming"),
                new Note("Buy some apples and grapes"),
                new Note("Go to bed early")
        ));
    }
}
