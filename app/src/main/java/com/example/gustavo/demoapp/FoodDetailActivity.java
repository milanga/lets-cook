package com.example.gustavo.demoapp;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.gustavo.demoapp.foodList.Food;
import com.example.gustavo.demoapp.glide.GlideApp;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FoodDetailActivity extends AppCompatActivity{

    @BindView(R.id.foodImage)
    ImageView foodImage;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private Food food;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState==null) {
            food = Parcels.unwrap(getIntent().getParcelableExtra(Food.FOOD_KEY));
        }

        Picasso.with(this)
                .load(food.getImageUrl())
                .into(foodImage);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return false;
    }
}
