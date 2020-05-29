package com.alan.lib.recycle.api;

import android.support.annotation.IntDef;
import android.view.View;

/**
 * @author Alan
 * 时 间：2020-05-19
 * 简 述：<功能简述>
 */
public interface IHeadView {

    int STATE_NORMAL = 0;
    int STATE_READY = 1;
    int STATE_REFRESHING = 2;
    int STATE_SUCCESS = 3;

    @IntDef({STATE_NORMAL, STATE_READY, STATE_REFRESHING, STATE_SUCCESS})
    @interface State {

    }

    void refreshUpdatedAtValue();

    int getVisibleHeight();

    void setVisibleHeight(int height);

    void setState(@State int state);

    View getView();
}
