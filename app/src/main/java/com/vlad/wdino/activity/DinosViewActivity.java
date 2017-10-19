package com.vlad.wdino.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vlad.wdino.R;
import com.vlad.wdino.adapter.RecyclerAdapter;
import com.vlad.wdino.api.model.response.login.LoginResponse;
import com.vlad.wdino.background.DefaultBackgroundCallback;
import com.vlad.wdino.manager.RetrofitManager;
import com.vlad.wdino.model.Dino;
import com.vlad.wdino.utils.Constants;
import com.vlad.wdino.utils.InternetUtil;
import com.vlad.wdino.utils.SharedPreferenceHelper;
import com.vlad.wdino.view.dialog.LogoutConfirmDialog;

import java.util.List;

public class DinosViewActivity extends AppCompatActivity {
    private ImageButton mAddNewDino;
    private RecyclerView mRecyclerView;
    private RefreshLayout refreshLayout;
    private List<Dino> dinoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinos_view);

        initWidgets();
        initRefreshLayout();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                showLogoutDialog();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showLogoutDialog() {
        LogoutConfirmDialog dialog = new LogoutConfirmDialog(this, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                logout();
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.showDialog();

    }

    private void logout() {
        LoginResponse user = SharedPreferenceHelper.getObject(this, SharedPreferenceHelper.KEY_LOGIN_USER, LoginResponse.class);

        RetrofitManager.getInstance().logout(user, new DefaultBackgroundCallback<String>() {
            @Override
            public void doOnSuccess(String result) {
                if (result.contains("true")) {
                    Toast.makeText(DinosViewActivity.this, "Successfully logout", Toast.LENGTH_SHORT).show();
                    startLoginActivity();
                }
            }
        });
    }

    private void startLoginActivity() {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
    }

    private void initWidgets() {
        mAddNewDino = (ImageButton) findViewById(R.id.add_new_dino_button);
        mAddNewDino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAddActivity();
            }
        });
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);

        dinoList = (List<Dino>) getLastCustomNonConfigurationInstance();
        if (dinoList != null) {
            initRecyclerView(dinoList);
        } else {
            getDataSet();
        }
    }

    private void startAddActivity() {
        Intent intent = new Intent(this, AddDinoActivity.class);
        startActivity(intent);
    }

    private void initRefreshLayout() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000);
                if (InternetUtil.isInternetTurnOn(DinosViewActivity.this)) {
                    getDataSet();
                } else {
                    Toast.makeText(DinosViewActivity.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void getDataSet() {
        Log.i(Constants.LOG_TAG, "1");
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
            }
        });
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return dinoList;
    }

    private void initRecyclerView(List<Dino> dinoList) {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                new LinearLayoutManager(this).getOrientation());
        RecyclerAdapter rvAdapter = new RecyclerAdapter(dinoList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setAdapter(rvAdapter);
        mRecyclerView.setHasFixedSize(true);
    }

    private void refreshRecyclerView() {
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }
}
