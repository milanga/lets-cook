package com.example.gustavo.demoapp;

import android.animation.Animator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.ImageView;

import com.example.gustavo.demoapp.foodList.Food;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FoodDetailActivity extends AppCompatActivity{

    @BindView(R.id.foodImage)
    ImageView foodImage;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.favorite)
    FloatingActionButton favorite;

    //used to add circular enter animation
    private Transition.TransitionListener mEnterTransitionListener;

    private Food food;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_detail);
        ButterKnife.bind(this);

        addEnterAnimation();

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState==null) {
            food = Parcels.unwrap(getIntent().getParcelableExtra(Food.FOOD_KEY));
        }

        Picasso.get()
                .load(food.getImageUrl())
                .into(foodImage);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            exitAnimation();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        exitAnimation();
    }

    // animatinos
    private void addEnterAnimation() {
        mEnterTransitionListener = new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                enterAnimation();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        };
        getWindow().getEnterTransition().addListener(mEnterTransitionListener);
    }

    private void enterAnimation() {

        // get the center for the clipping circle
        int cx = favorite.getMeasuredWidth() / 2;
        int cy = favorite.getMeasuredHeight() / 2;

        // get the final radius for the clipping circle
        int finalRadius = Math.max(favorite.getWidth(), favorite.getHeight()) / 2;
        Animator anim = ViewAnimationUtils.createCircularReveal(favorite, cx, cy, 0, finalRadius);
        favorite.setVisibility(View.VISIBLE);
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                getWindow().getEnterTransition().removeListener(mEnterTransitionListener);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        anim.start();
    }

    private void exitAnimation() {
        favorite.animate().alpha(0).setDuration(100).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                favorite.setVisibility(View.INVISIBLE);
                finishAfterTransition();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).start();
    }
}
