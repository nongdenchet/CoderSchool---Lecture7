package com.coderschool.rxthinking;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

class NotePresenter {
    private final NoteScreen noteScreen;
    private final NoteApi noteApi;
    private final List<NoteViewModel> viewModels;
    private final CompositeDisposable disposables;

    NotePresenter(NoteScreen noteScreen) {
        this.noteApi = new NoteApi();
        this.viewModels = new ArrayList<>();
        this.disposables = new CompositeDisposable();
        this.noteScreen = noteScreen;
    }

    List<NoteViewModel> getViewModels() {
        return viewModels;
    }

    void init(Observable<CharSequence> keywords, Observable<Integer> selectedPos) {
        disposables.add(Observable.combineLatest(getNotes(), handleKeywords(keywords), handleSelectedPos(selectedPos),
                this::toViewModels)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    viewModels.clear();
                    viewModels.addAll(result);
                    noteScreen.notifyChange();
                }, Throwable::printStackTrace));
    }

    private Observable<Integer> handleSelectedPos(Observable<Integer> selectedPos) {
        return selectedPos.distinctUntilChanged();
    }

    private Observable<String> handleKeywords(Observable<CharSequence> keywords) {
        return keywords.map(CharSequence::toString)
                .map(String::toLowerCase)
                .map(String::trim)
                .debounce(300, TimeUnit.MILLISECONDS)
                .distinctUntilChanged();
    }

    private Observable<List<Note>> getNotes() {
        return noteApi.getNotes().subscribeOn(Schedulers.io());
    }

    private List<NoteViewModel> toViewModels(List<Note> notes, String keyword, int selectedPos) {
        return Observable.fromIterable(notes)
                .map(note -> new NoteViewModel(note, notes.indexOf(note) == selectedPos, keyword))
                .filter(NoteViewModel::found)
                .toList()
                .blockingGet();
    }

    void onDestroy() {
        disposables.clear();
    }
}
