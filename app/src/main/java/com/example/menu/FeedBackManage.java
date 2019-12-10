package com.example.menu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.menu.Model.FeedBack;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class FeedBackManage extends AppCompatActivity {

    private TextView txt_feedbackContent;
    private Button btn_feedbackManage_delete;
    private int position;
    private int id;
    private List<FeedBack> feedbackList=new ArrayList<>();
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back_manage);
        Intent intent=getIntent();
        position=intent.getIntExtra("position",0);
        id=intent.getIntExtra("id",0);

        txt_feedbackContent=(TextView)findViewById(R.id.feedback_content);
        btn_feedbackManage_delete=(Button)findViewById(R.id.btn_feedbackManage_delete);
        scrollView=(ScrollView)findViewById(R.id.scroll_feebackManage);

        feedbackList=((DataApplication)getApplication()).GetFeedBackList();

        setDatasync();

        btn_feedbackManage_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Delete();
            }
        });
    }

    private void Back(){

    }

    private void Delete(){
        OkHttpUtils
                .post()
                .url("http://101.132.227.53:8080/api/v1/feedback/delete")
                .addParams("id",String.valueOf(id))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getApplicationContext(), "失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            Toast.makeText(getApplicationContext(), "删除成功", Toast.LENGTH_SHORT).show();
                            feedbackList.remove(position);
                            Toast.makeText(getApplicationContext(), feedbackList.get(position).getFeedback(), Toast.LENGTH_SHORT).show();
                            ((DataApplication)getApplication()).SetFeedBackList(feedbackList);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void setDatasync(){
        txt_feedbackContent.setText(feedbackList.get(position).getFeedback());
    }
}
