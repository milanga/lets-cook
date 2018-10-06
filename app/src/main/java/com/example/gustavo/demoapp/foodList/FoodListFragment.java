package com.example.gustavo.demoapp.foodList;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gustavo.demoapp.R;
import com.example.gustavo.demoapp.foodList.model.Food;
import com.example.gustavo.demoapp.foodList.presenter.FoodListContract;
import com.example.gustavo.demoapp.foodList.presenter.FoodListPresenter;
import com.example.gustavo.demoapp.recyclerViewDecoration.DoubleColumnSpaceDecoration;
import com.example.gustavo.demoapp.recyclerViewDecoration.SpaceItemDecoration;
import com.pnikosis.materialishprogress.ProgressWheel;


import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gustavo on 04/04/18.
 */

public class FoodListFragment extends Fragment implements FoodListContract.View{
    private final String PRESENTER_KEY = "presenter_key";

    @BindView(R.id.rvFood)
    RecyclerView rvFood;
    @BindView(R.id.progressWheel)
    ProgressWheel progressWheel;
    @BindView(R.id.ivError)
    ImageView ivError;
    @BindView(R.id.tvError)
    TextView tvError;

    private FoodListPresenter foodListPresenter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(PRESENTER_KEY, Parcels.wrap(foodListPresenter));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.simple_list, container, false);
        ButterKnife.bind(this, view);

        if (savedInstanceState==null) {
            foodListPresenter = new FoodListPresenter();
            foodListPresenter.attach(this);
        }else {
            foodListPresenter = Parcels.unwrap(savedInstanceState.getParcelable(PRESENTER_KEY));
            foodListPresenter.attach(this);
        }
        foodListPresenter.start();
        initializeRV();

        return view;
    }

    private void initializeRV() {
        int space = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 14, getResources().getDisplayMetrics()));
        if (getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE){
            mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            rvFood.addItemDecoration(new DoubleColumnSpaceDecoration(space));
        }else {
            mLayoutManager = new LinearLayoutManager(getContext());
            rvFood.addItemDecoration(new SpaceItemDecoration(space));
        }
        rvFood.setLayoutManager(mLayoutManager);
    }

    @Override
    public void startLoading() {
        progressWheel.setVisibility(View.VISIBLE);
        rvFood.setVisibility(View.GONE);
    }

    @Override
    public void stopLoading() {
        progressWheel.setVisibility(View.GONE);
        rvFood.setVisibility(View.VISIBLE);
    }

    @Override
    public void showConnectionError() {
        ivError.setVisibility(View.VISIBLE);
        tvError.setVisibility(View.VISIBLE);
        tvError.setText(R.string.internet_error);
    }

    @Override
    public void showFoodList(List<Food> foodList) {
        FoodAdapter adapter = new FoodAdapter(foodList, getActivity());
        rvFood.setAdapter(adapter);
    }

    @Override
    public void showApiError() {
        ivError.setVisibility(View.VISIBLE);
        tvError.setVisibility(View.VISIBLE);
        tvError.setText(R.string.api_error);
    }

}
