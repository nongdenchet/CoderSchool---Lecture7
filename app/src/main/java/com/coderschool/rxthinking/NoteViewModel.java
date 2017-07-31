package com.coderschool.rxthinking;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;

public class NoteViewModel {
    private final Note note;
    private final boolean selected;
    private final int firstMatch;
    private final String keyword;

    public NoteViewModel(Note note, boolean selected, String keyword) {
        this.note = note;
        this.selected = selected;
        this.keyword = keyword;
        this.firstMatch = note.getNote().toLowerCase().indexOf(keyword);
    }

    public boolean found() {
        return firstMatch != -1;
    }

    public String getNote() {
        return note.getNote();
    }

    public String getId() {
        return note.getId();
    }

    public boolean isSelected() {
        return selected;
    }

    public SpannableString decorateContent() {
        SpannableString decoratedContent = new SpannableString(getNote());
        BackgroundColorSpan color = new BackgroundColorSpan(Color.GREEN);
        decoratedContent.setSpan(color, firstMatch, firstMatch + keyword.length(), 0);
        return decoratedContent;
    }
}
