package com.example.menu;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.example.menu.Adapter.FeedBackListAdapter;
import com.example.menu.Adapter.RecyclerViewAdapter;
import com.example.menu.Model.Admit;
import com.example.menu.Model.CaiPu;
import com.example.menu.Model.FeedBack;

import java.util.ArrayList;
import java.util.List;

public class FeedBackList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FeedBackListAdapter feedBackListAdapter;
    private List<FeedBack>feedbackList=new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;

    private List<FeedBack>example=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back_list);

        feedbackList=((DataApplication)getApplication()).GetFeedBackList();
        //Init();

        recyclerView = (RecyclerView) findViewById(R.id.recycle_view_feedbackList);

        feedBackListAdapter = new FeedBackListAdapter(this, feedbackList);
        //feedBackListAdapter = new FeedBackListAdapter(this, example);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(feedBackListAdapter);

        refreshData();

        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipe_refresh_feedbackList);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
    }

    private void refreshData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        feedBackListAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    private void Init(){
        for(int i=0;i<10;i++){
            FeedBack feedBack=new FeedBack();
            feedBack.setId(i);
            feedBack.setFeedback("aaa");
            example.add(feedBack);
        }

    }
}
