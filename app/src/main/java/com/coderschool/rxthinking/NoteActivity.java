package com.coderschool.rxthinking;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.jakewharton.rxbinding2.widget.RxTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoteActivity extends AppCompatActivity implements NoteScreen {
    private NotePresenter notePresenter;
    private NoteAdapter noteAdapter;

    @BindView(R.id.edtSearch)
    EditText edtSearch;
    @BindView(R.id.listNotes)
    RecyclerView listNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        notePresenter = new NotePresenter(this);
        noteAdapter = new NoteAdapter(notePresenter);
        listNotes.setAdapter(noteAdapter);
        listNotes.setLayoutManager(new LinearLayoutManager(this));
        notePresenter.init(RxTextView.textChanges(edtSearch), noteAdapter.selectedPos());
    }

    @Override
    protected void onDestroy() {
        notePresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void notifyChange() {
        noteAdapter.notifyDataSetChanged();
    }
}
