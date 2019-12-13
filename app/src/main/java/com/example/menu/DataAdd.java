package com.example.menu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.menu.Model.Admit;
import com.example.menu.Model.CaiPu;
import com.example.menu.Model.Step;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class DataAdd extends AppCompatActivity {

    private EditText edit_name;
    private EditText edit_picture;
    private EditText edit_step;
    private Button btn_add;

    private String strCurrentid;
    private int intCurrentId;

    private List<Admit>admitList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_add);

        edit_name=(EditText)findViewById(R.id.edit_name_add);
        edit_picture=(EditText)findViewById(R.id.edit_picture_add);
        edit_step=(EditText)findViewById(R.id.edit_step_add);
        btn_add=(Button)findViewById(R.id.btn_certain_add);
        admitList=((DataApplication)getApplication()).GetAdmitList();

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Admit admit=new Admit();
                admit.setName(edit_name.toString());
                admit.setPicture(edit_picture.toString());
                admit.setStep(edit_step.toString());

                admitList.add(admit);
                ((DataApplication)getApplication()).SetAdmitList(admitList);*/
                strCurrentid=admitList.get(admitList.size()-1).getId();
                intCurrentId=Integer.valueOf(strCurrentid)+1;
                strCurrentid=String.valueOf(intCurrentId);

                //Toast.makeText(getApplicationContext(), id, Toast.LENGTH_SHORT).show();
                UpLoadAddData();
            }
        });


    }

    private void UpLoadAddData(){

        OkHttpUtils
                .post()
                .url("http://101.132.227.53:8080/api/v1/food/content/insert")
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
                            Toast.makeText(getApplicationContext(), "添加成功", Toast.LENGTH_SHORT).show();
                            Admit admit=new Admit();
                            admit.setName(edit_name.getText().toString());
                            admit.setPicture(edit_step.getText().toString());
                            admit.setStep(edit_step.getText().toString());
                            admit.setId(strCurrentid);
                            admitList.add(admit);

                            ((DataApplication)getApplication()).SetAdmitList(admitList);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
