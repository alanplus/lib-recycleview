package com.alan.lib.recycle.api;

import android.view.View;

/**
 * @author Alan
 * 时 间：2020-05-14
 * 简 述：<功能简述>
 */
public interface IRecycleView<T, H> extends IBaseRecycleView<T> {

    void bindTitleView(H h, View view, int position);

    void bindHeadView(View headView);

    void bindFootView(View view);
}
