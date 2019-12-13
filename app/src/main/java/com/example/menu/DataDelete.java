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

public class DataDelete extends AppCompatActivity {

    private EditText edit_delete;
    private Button btn_delete;
    private List<Admit> admitList=new ArrayList<>();
    private int deleteId;
    private int deleteDBId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_delete);

        edit_delete=(EditText)findViewById(R.id.edit_name_delete);
        btn_delete=(Button)findViewById(R.id.btn_certain_delete);
        admitList=((DataApplication)getApplication()).GetAdmitList();

        /*btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Admit admit=new Admit();
                admitList.add(admit);
                ((DataApplication)getApplication()).SetAdmitList(admitList);
            }
        });*/

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for(int i=0;i<admitList.size();i++){
                    if(admitList.get(i).getName().equals(edit_delete.getText().toString())){
                        //Toast.makeText(getApplicationContext(), "有", Toast.LENGTH_SHORT).show();
                        deleteId=Integer.valueOf(admitList.get(i).getId());
                        deleteDBId=i;
                        break;
                    }
                }

                admitList.remove(deleteDBId);
                ((DataApplication)getApplication()).SetAdmitList(admitList);
                UpLoadDeleteData();
            }
        });
    }

    private void UpLoadDeleteData(){
        OkHttpUtils
                .post()
                .url("http://101.132.227.53:8080/api/v1/food/content/delete")
                .addParams("id",String.valueOf(deleteId))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            Toast.makeText(getApplicationContext(), "删除成功", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
