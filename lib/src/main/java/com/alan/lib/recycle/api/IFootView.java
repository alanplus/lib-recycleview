package com.alan.lib.recycle.api;

import android.support.annotation.IntDef;
import android.view.View;

/**
 * @author Alan
 * 时 间：2020-05-19
 * 简 述：<功能简述>
 */
public interface IFootView {

    int STATE_NORMAL = 0;
    int STATE_READY = 1;
    int STATE_LOADING = 2;

    @IntDef({STATE_NORMAL, STATE_READY, STATE_LOADING})
    @interface State {

    }

    int getBottomMargin();

    void setState(@IFootView.State int state);

    View getView();
}
