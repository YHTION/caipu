package com.example.menu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.menu.Model.Admit;
import com.example.menu.Model.CaiPu;
import com.example.menu.Model.Step;
import com.example.menu.Model.FeedBack;
import com.google.gson.JsonObject;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class LoginActivity extends AppCompatActivity {

    private Button btn_login;
    private Button btn_client;
    private List<CaiPu> caiPuList=new ArrayList<>();
    private List<Step>stepList=new ArrayList<>();
    private List<Admit>admitList=new ArrayList<>();
    private List<FeedBack>feedbackList=new ArrayList<>();
    private String cid="11";
    private String[] cids={"11","10","117","104","12"};
    private RadioGroup rad_group;
    private RadioButton rad_manager;
    private RadioButton rad_client;
    private RelativeLayout view_manager;
    private RelativeLayout view_client;
    private boolean isClient=false;

    private EditText edit_account;
    private EditText edit_password;
    private String account;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_login=(Button)findViewById(R.id.login);
        rad_group=(RadioGroup)findViewById(R.id.rad_group);
        rad_manager=(RadioButton)findViewById(R.id.rad_manager);
        rad_client=(RadioButton)findViewById(R.id.rad_clent);
        view_manager=(RelativeLayout)findViewById(R.id.view_manager);
        view_client=(RelativeLayout)findViewById(R.id.view_clent);
        btn_client=(Button)findViewById(R.id.btn_client);
        edit_account=(EditText)findViewById(R.id.username);
        edit_password=(EditText)findViewById(R.id.password);

        ManagerOrClient();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                account=edit_account.getText().toString();
                password=edit_password.getText().toString();

                if(account.equals("wzq")&&password.equals("123456")){
                    //Intent intent=new Intent(LoginActivity.this,Manager.class);
                    Intent intent=new Intent(LoginActivity.this,Manager.class);
                    GetFoodContent();
                    GetFeedBack();
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(),"账号密码错误",Toast.LENGTH_LONG).show();
                }


            }
        });

    }

    private void ManagerOrClient(){

        rad_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(rad_manager.getId()==i){
                    isClient=false;
                    view_manager.setVisibility(View.VISIBLE);
                    view_client.setVisibility(View.INVISIBLE);
                }else {
                    isClient=true;
                    view_manager.setVisibility(View.INVISIBLE);
                    view_client.setVisibility(View.VISIBLE);

                    if(isClient){
                        btn_client.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent=new Intent(LoginActivity.this,Soft1714080902104Activity.class);
                                //getDataForPlay();
                                getDatasync();
                                GetFoodContent();
                                startActivity(intent);
                            }
                        });
                    }
                }
            }
        });
    }

    private void getDataForPlay(){

        for(int i=0;i<cids.length;i++){
            cid=cids[i];
            getDatasync();
        }
    }

    private void GetFeedBack(){
        feedbackList.clear();
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
                            //Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray jsonArray=jsonObject.getJSONArray("data");
                            for(int i=0;i<jsonArray.length();i++){
                                FeedBack feedBack=new FeedBack();
                                JSONObject jsonObject_for=jsonArray.getJSONObject(i);
                                feedBack.setId(Integer.parseInt(jsonObject_for.getString("id")));
                                feedBack.setFeedback(jsonObject_for.getString("content"));
                                feedbackList.add(feedBack);
                            }
                            //Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_SHORT).show();
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
                            ((DataApplication)getApplication()).SetAdmitList(admitList);

                            //Toast.makeText(getApplicationContext(),admitList.get(admitList.size()-1).getId(),Toast.LENGTH_SHORT).show();
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void getDatasync(){

        OkHttpUtils
                .post()
                .url("http://apis.juhe.cn/cook/index")
                .addParams("key","ed58fe16ed5281337c00b25f420c248a")
                .addParams("cid",cid)
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
                            //Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_SHORT).show();
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

                                stepList.clear();

                                for(int k=0;k<jsonArray2.length();k++){
                                    JSONObject jsonObject3=jsonArray2.getJSONObject(k);
                                    Step step=new Step();
                                    step.setImg(jsonObject3.getString("img"));
                                    step.setStep(jsonObject3.getString("step"));
                                    stepList.add(step);
                                }

                                caiPu.setStepList(stepList);
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
