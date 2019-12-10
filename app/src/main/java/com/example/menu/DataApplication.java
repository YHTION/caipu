package com.example.menu;

import android.app.Application;

import com.example.menu.Model.Admit;
import com.example.menu.Model.CaiPu;
import com.example.menu.Model.FeedBack;

import java.util.ArrayList;
import java.util.List;

public class DataApplication extends Application {

    private static List<CaiPu>caiPuList=new ArrayList<>();
    private static List<Admit>admitCaipuList=new ArrayList<>();
    private static List<FeedBack>feedbackList=new ArrayList<>();

    public List<CaiPu> GetDataList(){
        return caiPuList;
    }

    public List<Admit> GetAdmitList(){
        return admitCaipuList;
    }

    public List<FeedBack>GetFeedBackList(){
        return feedbackList;
    }

    public void SetDataList(List<CaiPu>m_caiPuList){
        caiPuList.clear();
        caiPuList.addAll(m_caiPuList);
    }

    public void SetAdmitList(List<Admit>m_caiPuList){
        admitCaipuList.clear();
        admitCaipuList.addAll(m_caiPuList);
    }

    public void SetFeedBackList(List<FeedBack>m_feedbackList){
        feedbackList.clear();
        feedbackList.addAll(m_feedbackList);
    }
}
