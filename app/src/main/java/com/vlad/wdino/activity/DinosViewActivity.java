package com.vlad.wdino.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageButton;

import com.vlad.wdino.R;
import com.vlad.wdino.adapter.RecyclerAdapter;
import com.vlad.wdino.background.BackgroundManager;
import com.vlad.wdino.background.DefaultBackgroundCallback;
import com.vlad.wdino.background.IBackgroundTask;
import com.vlad.wdino.manager.RetrofitManager;
import com.vlad.wdino.model.Dino;
import com.vlad.wdino.utils.Constants;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import me.dkzwm.widget.srl.SmoothRefreshLayout;

public class DinosViewActivity extends AppCompatActivity {
    private ImageButton mAddNewDino;
    private RecyclerView mRecyclerView;
    private SmoothRefreshLayout tapRefresh;
    private List<Dino> dinoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinos_view);

        initWidgets();
        getDataSet();
    }

    private void initWidgets() {
        mAddNewDino = (ImageButton) findViewById(R.id.add_new_dino_button);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        tapRefresh = (SmoothRefreshLayout) findViewById(R.id.smoothRefreshLayout);
    }

    private void getDataSet() {
        RetrofitManager.getInstance().getDinos(new DefaultBackgroundCallback<List<Dino>>() {
            @Override
            public void doOnSuccess(List<Dino> result) {
                if (dinoList != null) {
                    dinoList.clear();
                    dinoList.addAll(result);
                    refreshRecyclerView();
                } else {
                    dinoList = result;
                    initRecyclerView(result);
                }
                loadDinoImages();
            }
        });
    }

    private void loadDinoImages() {
        if (!dinoList.isEmpty()) {
            for (final Dino dino : dinoList) {
                final String url = dino.getDino().getDinoImage().getSrc();
                BackgroundManager.getInstance().doBackgroundTask(new IBackgroundTask<Bitmap>() {
                    @Override
                    public Bitmap execute() {
                        try {
                            URL urlConnection = new URL(url);
                            HttpURLConnection connection = (HttpURLConnection) urlConnection
                                    .openConnection();
                            connection.setDoInput(true);
                            connection.connect();
                            InputStream input = connection.getInputStream();
                            Bitmap myBitmap = BitmapFactory.decodeStream(input);
                            return myBitmap;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                }, new DefaultBackgroundCallback<Bitmap>() {
                    @Override
                    public void doOnSuccess(Bitmap result) {
                        dino.getDino().getDinoImage().setImage(result);
                        refreshRecyclerView();
                    }
                });
            }
        }
    }

    private void initRecyclerView(List<Dino> dinoList) {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        RecyclerAdapter rvAdapter = new RecyclerAdapter(dinoList);
        mRecyclerView.setAdapter(rvAdapter);
        mRecyclerView.setHasFixedSize(true);
    }

    private void refreshRecyclerView() {
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }
}
