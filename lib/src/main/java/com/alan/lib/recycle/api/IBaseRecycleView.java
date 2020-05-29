package com.alan.lib.recycle.api;

import android.content.Context;
import android.view.View;

/**
 * @author Alan
 * 时 间：2020-05-14
 * 简 述：<功能简述>
 */
public interface IBaseRecycleView<T> {

    View getView(Context context, int type);

    void bindView(T t, View view, int position);
}
