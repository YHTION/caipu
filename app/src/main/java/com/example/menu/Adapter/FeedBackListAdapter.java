package com.example.menu.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.menu.CPSelect;
import com.example.menu.CXSelect;
import com.example.menu.FeedBackManage;
import com.example.menu.Model.Admit;
import com.example.menu.Model.CaiPu;
import com.example.menu.Model.FeedBack;
import com.example.menu.R;
import com.example.menu.Soft1714080902104Activity;

import java.util.ArrayList;
import java.util.List;

public class FeedBackListAdapter extends RecyclerView.Adapter<FeedBackListAdapter.MyViewHolder> {

    private Context context;


    private List<FeedBack>feedBackList=new ArrayList<>();

    public FeedBackListAdapter(Context context, List<FeedBack> data) {
        this.context = context;
        this.feedBackList = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_fb, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.textView.setText(feedBackList.get(position).getFeedback());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, FeedBackManage.class);
                intent.putExtra("id",feedBackList.get(position).getId());
                intent.putExtra("position",position);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.feedBackList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        private CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textView=(TextView)itemView.findViewById((R.id.fb_name));
            this.cardView=(CardView)itemView.findViewById(R.id.fb_cardview);
        }
    }

}
