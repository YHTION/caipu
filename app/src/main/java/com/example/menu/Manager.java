package com.example.menu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Manager extends AppCompatActivity {

    private Button btn_add;
    private Button btn_change;
    private Button btn_delete;
    private Button btn_feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        btn_add=(Button)findViewById(R.id.btn_add);
        btn_change=(Button)findViewById(R.id.btn_change);
        btn_delete=(Button)findViewById(R.id.btn_delete);
        btn_feedback=(Button)findViewById(R.id.btn_feendbackList);


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataAdd();
            }
        });

        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataChange();
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataDelete();
            }
        });

        btn_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FeedBack();
            }
        });
    }

    private void DataAdd(){
        Intent intent=new Intent(Manager.this,DataAdd.class);
        startActivity(intent);
    }

    private void DataChange(){
        Intent intent=new Intent(Manager.this,DataChange.class);
        startActivity(intent);
    }

    private void DataDelete(){
        Intent intent=new Intent(Manager.this,DataDelete.class);
        startActivity(intent);
    }

    private void FeedBack(){
        Intent intent=new Intent(Manager.this,FeedBackList.class);
        startActivity(intent);
    }
}
