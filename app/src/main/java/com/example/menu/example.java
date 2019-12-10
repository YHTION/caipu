package com.example.menu;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.menu.Model.Admit;
import com.example.menu.Model.CaiPu;
import com.example.menu.Model.FeedBack;
import com.example.menu.Model.Step;
import com.google.gson.JsonObject;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
//图片显示问题
public class example extends AppCompatActivity {

    private Button btn_example;
    private TextView txt_example;
    private List<Admit> admitList=new ArrayList<>();
    private List<CaiPu> caiPuList=new ArrayList<>();
    private List<FeedBack>feedbackList=new ArrayList<>();
    private ImageView img_example;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);

        btn_example=(Button)findViewById(R.id.btn_example);
        txt_example=(TextView)findViewById(R.id.txt_example);
        img_example=(ImageView)findViewById(R.id.img_example);
        caiPuList=((DataApplication)getApplication()).GetDataList();

        Toast.makeText(getApplicationContext(),caiPuList.get(0).getTitle(),Toast.LENGTH_LONG).show();

        btn_example.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //GetFeedBack();
                //txt_example.setText(caiPuList.get(0).getTitle());
            }
        });

    }

    private void CeShi(){
        OkHttpUtils
                .post()
                .url("http://101.132.227.53:8080/api/v1/feedback/delete")
                .addParams("id","6")
                //.addParams("url","ceshi")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                        txt_example.setText(e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                            txt_example.setText(response.toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void GetFeedBack(){
        OkHttpUtils
                .get()
                .url("http://101.132.227.53:8080/api/v1/feedback/all")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getApplicationContext(),"失败",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try{

                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray jsonArray=jsonObject.getJSONArray("data");

                            JSONObject jsonObject1=jsonArray.getJSONObject(0);


                            Toast.makeText(getApplicationContext(),jsonObject1.getString("id"),Toast.LENGTH_SHORT).show();
                            ((DataApplication)getApplication()).SetFeedBackList(feedbackList);
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void GetFoodContent(){

        OkHttpUtils
                .get()
                .url("http://101.132.227.53:8080/api/v1/food/content/all")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getApplicationContext(),"失败",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try{

                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray jsonArray=jsonObject.getJSONArray("data");
                            for(int i=0;i<jsonArray.length();i++){
                                Admit admit=new Admit();
                                JSONObject jsonObject_for=jsonArray.getJSONObject(i);
                                admit.setId(jsonObject_for.getString("id"));
                                admit.setName(jsonObject_for.getString("keyword"));
                                admit.setStep(jsonObject_for.getString("step"));
                                admit.setPicture(jsonObject_for.getString("url"));
                                admitList.add(admit);
                            }
                            //Glide.with(getApplicationContext()).load(admitList.get(0).getPicture()).into(img_example);
                            Glide.with(getApplicationContext()).load(caiPuList.get(0).getAlbum()).into(img_example);
                            txt_example.setText(admitList.get(0).getPicture());
                            ((DataApplication)getApplication()).SetAdmitList(admitList);
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                });
    }


    public void tishikuang(){
        AlertDialog.Builder dialog=new AlertDialog.Builder(example.this);
        dialog.setTitle("删除菜品请求");
        dialog.setMessage("是否删除次菜品");
        dialog.setCancelable(false);
        dialog.setPositiveButton("删除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.show();
    }

    public void getDatasync(){

        OkHttpUtils
                .post()
                .url("http://apis.juhe.cn/cook/index")
                .addParams("key","ec094bb8c7cd8ad35114c5fa0c81d678")
                .addParams("cid","10")
                .addParams("rn","30")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getApplicationContext(),"失败",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        try{
                            JSONObject jsonObject=new JSONObject(response);
                            JSONObject jsonObject1=jsonObject.getJSONObject("result");
                            JSONArray jsonArray=jsonObject1.getJSONArray("data");

                            for(int i=0;i<30;i++){

                                CaiPu caiPu=new CaiPu();
                                JSONObject jsonObject2=jsonArray.getJSONObject(i);
                                caiPu.setTitle(jsonObject2.getString("title"));
                                caiPu.setImtro(jsonObject2.getString("imtro"));
                                caiPu.setIngredients(jsonObject2.getString("ingredients"));
                                caiPu.setBurden(jsonObject2.getString("burden"));
                                JSONArray jsonArray1=jsonObject2.getJSONArray("albums");
                                caiPu.setAlbum(jsonArray1.getString(0));
                                JSONArray jsonArray2=jsonObject2.getJSONArray("steps");

                                caiPuList.add(caiPu);
                            }
                            //cp_caiming_text.setText(caiPuList.get(0).getTitle());
                            //cp_cailiao_text.setText(caiPuList.get(0).getBurden());
                            //cp_jianjieneirong_text.setText(caiPuList.get(0).getImtro());
                            //Glide.with(getApplicationContext()).load(caiPuList.get(0).getAlbum()).into(cp_image);
                            //saveTextIntoInternalStorage(jsonArray.toString());
                            ((DataApplication)getApplication()).SetDataList(caiPuList);
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
