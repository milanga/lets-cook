package com.example.gustavo.demoapp.recyclerViewDecoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private final int space;

    public SpaceItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        outRect.bottom = space;
        outRect.left = space;
        outRect.right = space;
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = space;
        }
    }
}