package com.example.menu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.menu.Model.Admit;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class DataChange extends AppCompatActivity {

    private EditText edit_name;
    private EditText edit_picture;
    private EditText edit_step;
    private Button btn_certain;
    private List<Admit> admitList=new ArrayList<>();

    private String strCurrentid;
    private int intCurrentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_change);

        edit_name=(EditText)findViewById(R.id.edit_name_change);
        edit_picture=(EditText)findViewById(R.id.edit_picture_change);
        edit_step=(EditText)findViewById(R.id.edit_step_change);
        admitList=((DataApplication)getApplication()).GetAdmitList();

        btn_certain=(Button)findViewById(R.id.btn_certain_change);

        btn_certain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Admit admit=new Admit();
                admit.setName(edit_name.toString());
                admit.setPicture(edit_picture.toString());
                admit.setStep(edit_step.toString());

                admitList.add(admit);
                ((DataApplication)getApplication()).SetAdmitList(admitList);*/

                for(int i=0;i<admitList.size();i++){
                    if(admitList.get(i).getName().equals(edit_name.getText().toString())){

                        strCurrentid=admitList.get(i).getId();
                        break;
                    }
                }

                UpLoadChangeData();
            }
        });
    }

    private void UpLoadChangeData(){
        OkHttpUtils
                .post()
                .url("http://101.132.227.53:8080/api/v1/food/content/update")
                .addParams("id",strCurrentid)
                .addParams("keyword",edit_name.getText().toString())
                .addParams("step",edit_step.getText().toString())
                .addParams("url",edit_picture.getText().toString())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
