package com.example.gustavo.demoapp.recipe;

import android.animation.Animator;
import android.graphics.drawable.Drawable;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.gustavo.demoapp.R;
import com.example.gustavo.demoapp.recipe.presenter.FoodDetailContract;
import com.example.gustavo.demoapp.recipe.presenter.FoodDetailPresenter;
import com.example.gustavo.demoapp.foodList.Food;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FoodDetailActivity extends AppCompatActivity implements FoodDetailContract.View{

    @BindView(R.id.foodImage)
    ImageView foodImage;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.favorite)
    FloatingActionButton favorite;

    //used to add circular enter animation
    private Transition.TransitionListener enterTransitionListener;

    private final String PRESENTER_KEY = "presenter_key";

    private Food food;
    private FoodDetailPresenter foodDetailPresenter;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(PRESENTER_KEY, Parcels.wrap(foodDetailPresenter));
        outState.putParcelable(Food.FOOD_KEY, Parcels.wrap(food));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_detail);
        ButterKnife.bind(this);
        //postpone the enter transition till glide load the image
        supportPostponeEnterTransition();
        //add listener to know when the transition end
        addTransitionListener();
        //initialize actionbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //initialize
        if (savedInstanceState==null) {
            food = Parcels.unwrap(getIntent().getParcelableExtra(Food.FOOD_KEY));
            foodDetailPresenter = new FoodDetailPresenter(food.getId());
            foodDetailPresenter.attach(this);
        }else{
            food = Parcels.unwrap(savedInstanceState.getParcelable(Food.FOOD_KEY));
            foodDetailPresenter = Parcels.unwrap(savedInstanceState.getParcelable(PRESENTER_KEY));
            foodDetailPresenter.attach(this);
            favorite.setVisibility(View.VISIBLE);
        }

        loadImage(true);
        getSupportActionBar().setTitle(food.getName());
        foodDetailPresenter.start();
    }

    private void loadImage(boolean onlyRetrieveFromCache) {
        Glide.with(this)
                .load(food.getImageUrl())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        supportStartPostponedEnterTransition();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        supportStartPostponedEnterTransition();
                        return false;
                    }
                })
                .apply(new RequestOptions().dontTransform()
                        .onlyRetrieveFromCache(onlyRetrieveFromCache))
                .into(foodImage);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finishWithAnimation();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        finishWithAnimation();
    }


    //view contract

    @Override
    public void startLoading() {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showConnectionError() {

    }

    @Override
    public void showFoodDetail(Recipe recipe) {

    }

    @Override
    public void showApiError() {

    }


    // animations
    private void addTransitionListener() {
        enterTransitionListener = new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                if (foodImage.getDrawable()==null)
                    loadImage(false);
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
        getWindow().getEnterTransition().addListener(enterTransitionListener);
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
                getWindow().getEnterTransition().removeListener(enterTransitionListener);
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

    private void finishWithAnimation() {
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
