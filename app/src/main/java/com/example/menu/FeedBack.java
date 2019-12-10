package com.example.menu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class FeedBack extends AppCompatActivity {

    private List<FeedBack> feedbackList=new ArrayList<>();
    private EditText edit_feedback;
    private Button btn_feedback;
    private String feedbackContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);

        edit_feedback=(EditText)findViewById(R.id.edit_feedback);
        btn_feedback=(Button)findViewById(R.id.btn_certain_feedback) ;

        btn_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                feedbackContent=edit_feedback.getText().toString();
                UpLoadFeedBack();
            }
        });
    }

    private void UpLoadFeedBack(){
        OkHttpUtils
                .post()
                .url("http://101.132.227.53:8080/api/v1/feedback/insert")
                .addParams("content",feedbackContent)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            Toast.makeText(getApplicationContext(), "上传成功", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
