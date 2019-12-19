package com.admaja.maos_aplikasiberita;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class EmptyRecyclerView extends RecyclerView {
    private View mEmptyView;

    final private AdapterDataObserver observer = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            checkIfEmpty();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
           checkIfEmpty();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
           checkIfEmpty();
        }
    };
    public EmptyRecyclerView(@NonNull Context context) {
        super(context);
    }

    public EmptyRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EmptyRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void checkIfEmpty() {
        if (mEmptyView != null && getAdapter() != null){
            final boolean emptyViewVisible =
                    getAdapter().getItemCount() ==0;
            mEmptyView.setVisibility(emptyViewVisible ? VISIBLE : GONE);
            setVisibility(emptyViewVisible ? GONE: VISIBLE);
        }
    }

    @Override
    public void setAdapter(@Nullable Adapter adapter) {
       final  Adapter oldAdapter = getAdapter();
       if (oldAdapter != null){
           adapter.registerAdapterDataObserver(observer);
       }
       checkIfEmpty();
    }
    /**
     * Set an empty view on the EmptyRecyclerView
     * @param emptyView refers to the empty state of the view
     */
    public void setmEmptyView(View emptyView){
        mEmptyView = emptyView;
        checkIfEmpty();
    }

}
