package com.alan.lib.test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alan.lib.recycle.SimpleListHelper;
import com.alan.lib.recycle.XmRecycleView;
import com.alan.lib.recycle.api.IBaseRecycleView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.util.SmartUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public SimpleListHelper<String> helper;
    private int position;

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            List<String> list = (List<String>) msg.obj;
            Log.d("test_recycle", "what:" + msg.what);
            helper.refresh(list, msg.what == 0);
            if (msg.what != 0) {
                helper.getXmRecycleView().finishRefresh(true);
            } else {
                helper.getXmRecycleView().finishLoadMore(true);
            }
            if (list.size() < 20) {
                helper.complete(true);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<String> list = new ArrayList<>();
        XmRecycleView recycleView = findViewById(R.id.view);
        helper = new SimpleListHelper<>(recycleView);
        helper.setData(list);
        helper.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                position = 0;
                loadData();
            }
        });

        helper.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadData();
            }
        });

        helper.setIBaseRecycleView(new IBaseRecycleView<String>() {
            @Override
            public View getView(Context context, int type) {
                TextView textView = new TextView(context);
                int i = SmartUtil.dp2px(10);
                textView.setPadding(i, i, i, i);
                textView.setBackgroundColor(Color.WHITE);
                textView.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
                return textView;
            }

            @Override
            public void bindView(String s, View view, int position) {
                if (view instanceof TextView) {
                    ((TextView) view).setText(s);
                }
            }
        });

        helper.build();
        helper.autoRefresh();
    }

    public void loadData() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (Exception e) {

                }
                int m = position;
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 20; i++) {
                    if (position >= 50) {
                        break;
                    }
                    list.add("test ----" + position++);
                }
                Message msg = new Message();
                msg.what = m == 0 ? 1 : 0;
                msg.obj = list;
                handler.sendMessage(msg);
            }
        }).start();
    }
}
