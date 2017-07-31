package com.coderschool.rxthinking;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

import static android.support.v7.widget.RecyclerView.NO_POSITION;

class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private final NotePresenter notePresenter;
    private final BehaviorSubject<Integer> selectedPos;

    NoteAdapter(NotePresenter notePresenter) {
        this.notePresenter = notePresenter;
        this.selectedPos = BehaviorSubject.createDefault(NO_POSITION);
    }

    Observable<Integer> selectedPos() {
        return selectedPos.hide();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_note, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NoteViewModel noteViewModel = notePresenter.getViewModels().get(position);
        holder.radioButton.setChecked(noteViewModel.isSelected());
        holder.tvNote.setText(noteViewModel.decorateContent());
    }

    @Override
    public int getItemCount() {
        return notePresenter.getViewModels().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvNote)
        TextView tvNote;
        @BindView(R.id.rbSelecting)
        RadioButton radioButton;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.itemView)
        void onItemClick() {
            selectedPos.onNext(getAdapterPosition());
        }
    }
}
