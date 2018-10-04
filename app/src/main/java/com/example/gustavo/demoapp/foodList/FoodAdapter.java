package com.example.gustavo.demoapp.foodList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gustavo.demoapp.foodDetail.FoodDetailActivity;
import com.example.gustavo.demoapp.R;
import com.example.gustavo.demoapp.views.CheckableImageView;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gustavo on 04/04/18.
 */

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.foodImage)
        ImageView imageView;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.cardView)
        CardView cardView;
        @BindView(R.id.favorite)
        CheckableImageView checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private List<Food> foodList;
    private Activity activity;

    public FoodAdapter(List<Food> food, Activity activity) {
        foodList = food;
        this.activity = activity;
    }


    @Override
    public FoodAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View foodView = inflater.inflate(R.layout.card_item, parent, false);
        ((CardView)foodView.findViewById(R.id.cardView)).setPreventCornerOverlap(false);
        return new ViewHolder(foodView);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodAdapter.ViewHolder viewHolder, int position) {
        final Food food = foodList.get(position);
        viewHolder.title.setText(food.getName());
        initializeCheckbox(viewHolder, food);
        initializeImage(viewHolder, food);
        initializeCardView(viewHolder, food);
    }

    private void initializeCardView(@NonNull final ViewHolder viewHolder, final Food food) {
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), FoodDetailActivity.class);
                intent.putExtra(Food.FOOD_KEY, Parcels.wrap(food));
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(activity, viewHolder.imageView, "image");
                view.getContext().startActivity(intent, options.toBundle());
//                todo remove clicked view from transition and add picasso listeners to avoid weird blik
                //todo animate the gray bar and transition text
            }
        });
    }

    private void initializeImage(@NonNull ViewHolder viewHolder, Food food) {
        Picasso.get()
                .load(food.getImageUrl())
                .into(viewHolder.imageView);
    }

    private void initializeCheckbox(@NonNull ViewHolder viewHolder, final Food food) {
        viewHolder.checkBox.setOnStateChangedListener(new CheckableImageView.OnStateChangedListener() {
            @Override
            public void onStateChanged(boolean checked) {
                food.setFavorite(checked);
            }
        });
        viewHolder.checkBox.setChecked(food.isFavorite());
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }
}
