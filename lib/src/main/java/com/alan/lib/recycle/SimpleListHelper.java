package com.alan.lib.recycle;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alan.lib.recycle.api.IBaseRecycleView;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.util.SmartUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alan
 * 时 间：2020-05-21
 * 简 述：<功能简述>
 */
public class SimpleListHelper<T> {

    private XmRecycleView mXmRecycleView;
    private RecyclerView mRecyclerView;

    private List<T> mListData;

    private IBaseRecycleView<T> mIBaseRecycleView;

    private boolean isRefresh;
    private boolean isLoadMore;

    private RecyclerView.LayoutManager layoutManager;

    private RefreshFooter mRefreshFooter;
    private RefreshHeader mRefreshHeader;

    private OnRefreshListener onRefreshListener;
    private OnLoadMoreListener onLoadMoreListener;

    private BaseRecycleAdapter<T> adapter;

    private View headView;
    private View footView;

    private boolean isReUserd = true;

    public SimpleListHelper(XmRecycleView xmRecycleView) {
        this.mXmRecycleView = xmRecycleView;
        isRefresh = false;
        isLoadMore = false;
        layoutManager = new LinearLayoutManager(xmRecycleView.getContext());
    }

    public RecyclerView getmRecyclerView() {
        return mRecyclerView;
    }

    public SimpleListHelper<T> setReUserd(boolean reUserd) {
        isReUserd = reUserd;
        return this;
    }

    public SimpleListHelper<T> setIBaseRecycleView(IBaseRecycleView<T> iBaseRcycleView) {
        this.mIBaseRecycleView = iBaseRcycleView;
        return this;
    }

    public SimpleListHelper<T> setData(List<T> list) {
        this.mListData = list;
        return this;
    }

    public SimpleListHelper<T> setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
        return this;
    }

    public SimpleListHelper<T> setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
        mXmRecycleView.setOnRefreshListener(onRefreshListener);
        setRefresh(null != onRefreshListener);
        return this;
    }

    public SimpleListHelper<T> setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
        mXmRecycleView.setOnLoadMoreListener(onLoadMoreListener);
        setLoadMore(onLoadMoreListener != null);
        return this;
    }

    public SimpleListHelper<T> setRefresh(boolean refresh) {
        isRefresh = refresh;
        mXmRecycleView.setEnableRefresh(refresh);
        return this;
    }

    public SimpleListHelper<T> setLoadMore(boolean loadMore) {
        isLoadMore = loadMore;
        mXmRecycleView.setEnableLoadMore(loadMore);
        return this;
    }

    public void build() {
        Context context = mXmRecycleView.getContext();
        if (isRefresh) {
            mXmRecycleView.setRefreshHeader(null == mRefreshHeader ? getRefreshHeader(context) : mRefreshHeader);
            mXmRecycleView.setOnRefreshListener(onRefreshListener);
        }

        if (isLoadMore) {
            mXmRecycleView.setRefreshFooter(null == mRefreshFooter ? getRefreshFooter(context) : mRefreshFooter);
            mXmRecycleView.setEnableLoadMoreWhenContentNotFull(true);//内容不满一页时候启用加载更多
            mXmRecycleView.setOnLoadMoreListener(onLoadMoreListener);
            mXmRecycleView.setEnableAutoLoadMore(false);
            mXmRecycleView.setEnableOverScrollBounce(true);
            mXmRecycleView.setEnableFooterFollowWhenNoMoreData(false);
        }
        mXmRecycleView.setEnableRefresh(isRefresh);
        mXmRecycleView.setEnableLoadMore(isLoadMore);

        mRecyclerView = new RecyclerView(context);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1, -2);
        mRecyclerView.setLayoutParams(layoutParams);
        mXmRecycleView.addView(mRecyclerView);
        mRecyclerView.setLayoutManager(layoutManager);
        adapter = new BaseRecycleAdapter<>(context, mListData, mIBaseRecycleView);
        mRecyclerView.setAdapter(adapter);
        if (!isReUserd) {
            mRecyclerView.getRecycledViewPool().setMaxRecycledViews(BaseRecycleAdapter.VIEW_TYPE_COMMON, 0);
        }
    }

    private RefreshHeader getRefreshHeader(Context context) {
        ClassicsHeader header = new ClassicsHeader(context).setSpinnerStyle(SpinnerStyle.FixedBehind);
        header.setSpinnerStyle(SpinnerStyle.Scale);
//        mXmRecycleView.setPrimaryColors(0xff444444, 0xffffffff);
        setTheme(header);
        return header;
    }

    public void refresh(List<T> list, boolean isAppend) {
        if (adapter == null) {
            return;
        }

        if (null == mListData) {
            mListData = new ArrayList<>();
        }

        if (isAppend && list != null && list.size() > 0) {
            mListData.addAll(list);
        }

        if (!isAppend) {
            mListData.clear();
            if (null != list && list.size() > 0) {
                mListData.addAll(list);
            }
        }
        adapter.notifyDataSetChanged();
    }

    public boolean autoRefresh() {
        return mXmRecycleView.autoRefresh();
    }

    public boolean autoRefresh(int delay) {
        return mXmRecycleView.autoRefresh(delay);
    }

    private RefreshFooter getRefreshFooter(Context context) {
        ClassicsFooter footer = new ClassicsFooter(context);
        footer.setBackgroundResource(android.R.color.white);
        footer.setSpinnerStyle(SpinnerStyle.Scale);//设置为拉伸模式
        return footer;//指定为经典Footer，默认是 BallPulseFooter
    }

    public XmRecycleView getXmRecycleView() {
        return mXmRecycleView;
    }

    public void setTheme(ClassicsHeader classicsHeader) {
        Drawable mDrawableProgress = ((ImageView) classicsHeader.findViewById(ClassicsHeader.ID_IMAGE_PROGRESS)).getDrawable();
        if (mDrawableProgress instanceof LayerDrawable) {
            mDrawableProgress = ((LayerDrawable) mDrawableProgress).getDrawable(0);
        }
        mXmRecycleView.getLayout().setBackgroundResource(android.R.color.transparent);
        mXmRecycleView.setPrimaryColors(0, 0xff666666);
        if (Build.VERSION.SDK_INT >= 21) {
            mDrawableProgress.setTint(0xff666666);
        } else if (mDrawableProgress instanceof VectorDrawableCompat) {
            ((VectorDrawableCompat) mDrawableProgress).setTint(0xff666666);
        }
    }

    public void complete(boolean isShowFootView) {
        setLoadMore(false);
        if (isShowFootView) {
            adapter.setFootView(getFootView());
            adapter.notifyDataSetChanged();
        }
    }

    public View getFootView() {
        if (null == footView) {
            footView = new TextView(mXmRecycleView.getContext());
            TextView tv = (TextView) footView;
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
            tv.setTextColor(Color.parseColor("#7c7c7c"));
            tv.setLayoutParams(new RecyclerView.LayoutParams(-1, SmartUtil.dp2px(50)));
            tv.setText("已全部加载完成");
        }
        return footView;
    }

}
