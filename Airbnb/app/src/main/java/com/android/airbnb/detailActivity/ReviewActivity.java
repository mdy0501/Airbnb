package com.android.airbnb.detailActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.airbnb.R;
import com.android.airbnb.adapter.ReviewAdapter;
import com.android.airbnb.domain.airbnb.Host;
import com.android.airbnb.util.Remote.ITask;
import com.android.airbnb.util.Remote.Loader;

import java.util.List;

public class ReviewActivity extends AppCompatActivity implements ITask.totalHostList {


    private TextView reviewCount;
    private RatingBar ratingBar3;
    private ReviewAdapter adapter;
    private RecyclerView reviewRecycler;
    private List<Host> dummyHostList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        initView();
        Loader.getTotalHost(this);
    }

    private void initView() {
        reviewCount = (TextView) findViewById(R.id.review_count);
        ratingBar3 = (RatingBar) findViewById(R.id.ratingBar3);
        reviewRecycler = (RecyclerView) findViewById(R.id.review_recycler);
    }

    private void setAdapter() {
        adapter = new ReviewAdapter(dummyHostList, this);
        reviewRecycler.setAdapter(adapter);
        reviewRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void doTotalHostList(List<Host> hostList) {
        this.dummyHostList = hostList;
        setAdapter();
    }
}
