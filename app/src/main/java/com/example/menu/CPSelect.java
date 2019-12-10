package com.example.menu;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.nfc.Tag;
import android.os.Debug;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.menu.Model.Admit;
import com.example.menu.Model.CaiPu;
import com.example.menu.Model.Step;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

import static android.provider.Telephony.Mms.Part.FILENAME;


public class CPSelect extends AppCompatActivity {

    private List<CaiPu>caiPuList=new ArrayList<>();
    private List<Admit> admitList=new ArrayList<>();

    private TextView cp_caiming_text;
    private ImageView cp_image;
    private TextView cp_jianjieneirong_text;
    private TextView cp_cailiao_text;
    private TextView cp_buzhou_text;
    private StringBuilder stringBuilder;
    private int position;
    private int way;
    private String name;
    private String curName="";

    //private Button btn_change;
    //private Button btn_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpselect);
        Intent intent=getIntent();
        way=intent.getIntExtra("way",0);
        name=intent.getStringExtra("name");
        position=intent.getIntExtra("position",0);

        stringBuilder=new StringBuilder("");

        cp_image=(ImageView)findViewById(R.id.cp_tupian_image);
        cp_caiming_text=(TextView)findViewById(R.id.cp_caiming_text);
        cp_cailiao_text=(TextView)findViewById(R.id.cp_cailiao_text);
        cp_jianjieneirong_text=(TextView)findViewById(R.id.cp_jianjieneirong_text);
        cp_buzhou_text=(TextView)findViewById(R.id.cp_buzhou_text);
        //btn_change=(Button)findViewById(R.id.btn_change);
        //btn_delete=(Button)findViewById(R.id.btn_delete);
        caiPuList=((DataApplication)getApplication()).GetDataList();
        admitList=((DataApplication)getApplication()).GetAdmitList();

        /*
        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CPSelect.this,DataChange.class);
                startActivity(intent);
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/

        if(way==1){
            setDataByName();
            //SetAdmitDataByName();
        }else {
            setDatasync();
        }
    }

    private void saveTextIntoInternalStorage(String text) {
        // 获取内部存储目录
        File dir = this.getFilesDir();
        //File dir = getCacheDir();

        if (dir.isDirectory()) {
//            dir.mkdir();
//            dir.mkdirs();
        }

        if (dir.isFile()) {
            // D:/Abc.txt , -> D:/Abc1.txt ->D:/abc.txt
        }

        File file = new File(dir, FILENAME);

//        try {
//            File = File.createTempFile(FILENAME, null, dir);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        if (file.exists()) { // 判断文件是否存在
            file.canRead();
            file.canWrite();
            file.canExecute();

            file.getFreeSpace();
            file.getTotalSpace();
        }

        FileOutputStream fos = null;  // 字节流  | char | cn : gbk 2 bytes, utf8 3 bytes

        try { // 使用API打开输出流
            fos = openFileOutput(FILENAME, MODE_PRIVATE);
            //FileOutputStream fos = new FileOutputStream(file);
            fos.write(text.getBytes()); // 写入内容
            fos.close(); // 关闭流
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileReader reader = null; // char

        try {
            reader = new FileReader(file.getAbsoluteFile());
            BufferedReader bReader = new BufferedReader(reader);
            String line = bReader.readLine();
            bReader.close();
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Toast.makeText(getApplicationContext(),"存储数据成功",Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(),"存储数据为：",Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(),text.toString(),Toast.LENGTH_SHORT).show();
    }

    public void setDatasync(){

        cp_caiming_text.setText(caiPuList.get(position).getTitle());
        cp_cailiao_text.setText(caiPuList.get(position).getBurden());
        cp_jianjieneirong_text.setText(caiPuList.get(position).getImtro());
        Glide.with(getApplicationContext()).load(caiPuList.get(position).getAlbum()).into(cp_image);

        List<Step>stepList=caiPuList.get(position).getStepList();
        for(int i=0;i<stepList.size();i++){

            stringBuilder.append(stepList.get(i).getStep());
            stringBuilder.append("\n");
        }
        cp_buzhou_text.setText(stringBuilder);
    }

    public void setDataByName(){

        for(int i=0;i<caiPuList.size();i++){

            if(name.equals(caiPuList.get(i).getTitle())){

                cp_caiming_text.setText(caiPuList.get(i).getTitle());
                cp_cailiao_text.setText(caiPuList.get(i).getBurden());
                cp_jianjieneirong_text.setText(caiPuList.get(i).getImtro());
                Glide.with(getApplicationContext()).load(caiPuList.get(i).getAlbum()).into(cp_image);

                List<Step>stepList=caiPuList.get(i).getStepList();
                for(int j=0;j<stepList.size();j++){

                    stringBuilder.append(stepList.get(j).getStep());
                    stringBuilder.append("\n");
                }
                cp_buzhou_text.setText(stringBuilder);
                break;

            }else {
                cp_caiming_text.setText("此菜品不存在");
            }
        }

    }

    public void SetAdmitDataByName(){

        for(int i=0;i<admitList.size();i++){

            if(name.equals(admitList.get(i).getName())){
                cp_caiming_text.setText(admitList.get(i).getName());
                Glide.with(getApplicationContext()).load(admitList.get(i).getPicture()).into(cp_image);
                cp_jianjieneirong_text.setText(admitList.get(i).getPicture());

                String temp=admitList.get(i).getStep();
                String[] tempStep=temp.split("#");
                StringBuilder step=new StringBuilder("");
                for(int k=1;k<tempStep.length;k++){
                    step.append(k+"."+tempStep[k]+"\n");
                }

                cp_buzhou_text.setText(step);
                break;
            }else {
                cp_caiming_text.setText("此菜品不存在");
            }
        }

    }
}
