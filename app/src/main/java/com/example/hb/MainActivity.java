package com.example.hb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @Bind(R.id.toolBar)
    Toolbar toolBar;
    @Bind(R.id.list)
    android.support.v7.widget.RecyclerView list;
    @Bind(R.id.refresh)
    SwipeRefreshLayout refresh;

    private boolean isCase = false;
    private List<String> caseList = new ArrayList();
    private List<String> noteList = new ArrayList();
    private MyHandler mHandler;
    private Class[] clazz = {HappyBirthdayActivity.class,CurtCounterActivity.class,OrderCoffeeActivity.class};
    private String[] urls = {
            "http://www.studyjamscn.com/thread-3800-1-1.html",
            "http://www.studyjamscn.com/thread-5217-1-1.html",
            "http://www.studyjamscn.com/thread-7544-1-1.html",
            "http://www.studyjamscn.com/thread-9008-1-1.html",
            "http://www.studyjamscn.com/thread-9381-1-1.html",
            "http://www.studyjamscn.com/thread-9419-1-1.html",
            "http://www.studyjamscn.com/thread-9697-1-1.html",
            "http://www.studyjamscn.com/thread-10483-1-1.html",
            "http://www.studyjamscn.com/thread-4872-1-1.html",
            "https://github.com/FreeVic/StudyJams"
    };
    private MainAdapter mainAdapter;

    static class MyHandler extends Handler{
        WeakReference<Activity> reference;
        MyHandler(Activity act){
            reference = new WeakReference(act);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity activity = (MainActivity) reference.get();
            if(activity==null)
                return;
            switch (msg.what) {
                case 1:
                    activity.changeAdapter();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mHandler = new MyHandler(this);
        init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    private void init(){
        toolBar.inflateMenu(R.menu.menu_main);
        caseList = Arrays.asList(getResources().getStringArray(R.array.caseList));
        noteList = Arrays.asList(getResources().getStringArray(R.array.noteList));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(layoutManager);
        mainAdapter = new MainAdapter(isCase?caseList:noteList);
        mainAdapter.setItemClickListener(new MainAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, String data, int position) {
//                Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
                Intent intent;
                if(isCase){
                    intent = new Intent(MainActivity.this,clazz[position]);
                }else{
                    intent = new Intent(MainActivity.this,WebActivity.class);
                    intent.putExtra(AppConstant.INTENT_URL,urls[position]);
                }
                startActivity(intent);


            }
        });

        list.setAdapter(mainAdapter);

        //下拉刷新
        refresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
        refresh.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh.setRefreshing(true);
                mHandler.sendEmptyMessageDelayed(1, 800);
            }
        });
    }
    private void changeAdapter(){
        refresh.setRefreshing(false);
        isCase = !isCase;
        mainAdapter.setmDataset(isCase?caseList:noteList);
    }


}

