
package com.suwonsmartapp.hello.challenge.challenge07_08;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by junsuk on 15. 3. 24.. 달력 표시용 View
 */
public class CalendarView extends GridView {

    public CalendarView(Context context) {
        this(context, null);
    }

    public CalendarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        setBackgroundColor(Color.DKGRAY);
        setHorizontalSpacing(1);
        setVerticalSpacing(1);
        setNumColumns(7);
        setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
    }

}
