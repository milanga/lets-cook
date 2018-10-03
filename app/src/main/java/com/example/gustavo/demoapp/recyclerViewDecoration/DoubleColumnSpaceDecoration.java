package com.example.gustavo.demoapp.recyclerViewDecoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class DoubleColumnSpaceDecoration extends RecyclerView.ItemDecoration {

    private final int space;

    public DoubleColumnSpaceDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        outRect.bottom = space;

        int position = parent.getChildAdapterPosition(view);
        if (position == 0 || position == 1) {
            outRect.top = space;
        }
        if (position % 2 ==1){
            outRect.right = space;
            outRect.left = space/2;
        }else{
            outRect.right = space/2;
            outRect.left = space;
        }
    }
}