
package com.suwonsmartapp.hello.activity.event;

import android.view.View;

import com.suwonsmartapp.hello.challenge.challenge17.MyEvent;

/**
 * Created by junsuk on 15. 6. 11..
 */
public class ButtonClickEvent implements MyEvent {
    private int mClickCount = -1;

    public View getView() {
        return mView;
    }

    private View mView = null;

    public ButtonClickEvent(final int clickCount) {
        mClickCount = clickCount;
    }

    public ButtonClickEvent(final View view) {
        mView = view;
    }

    public int getClickCount() {
        return mClickCount;
    }

    @Override
    public String toString() {
        return "click : " + mClickCount + ", view : " + (mView == null ? "null" : mView);
    }
}
