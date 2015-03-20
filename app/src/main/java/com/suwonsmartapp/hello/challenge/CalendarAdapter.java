
package com.suwonsmartapp.hello.challenge;

import com.suwonsmartapp.hello.R;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * 달력표시용 Adapter
 */
public class CalendarAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mList;

    private int mSelectedPosition = -1;
    // Layout을 가져오기 위한 객체
    private LayoutInflater inflater;

    public CalendarAdapter(Context context, List<String> list) {
        mContext = context;
        mList = list;
    }

    public int getSelectedPosition() {
        return mSelectedPosition;
    }

    public void setSelectedPosition(int selectedPosition) {
        mSelectedPosition = selectedPosition;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            // View 를 처음 로딩할 때, Data 를 처음 셋팅할 때
            inflater = (LayoutInflater) mContext.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.calendar_item, null);
            TextView date = (TextView) convertView.findViewById(R.id.calendar_item_date);

            holder = new ViewHolder();
            holder.date = date;
            convertView.setTag(holder);
        } else {
            // View, Data 재사용
            holder = (ViewHolder) convertView.getTag();
        }

        // position 위치의 데이터를 취득
        String date = (String) getItem(position);

        if (!TextUtils.isEmpty(date)) {
            holder.date.setText(date);
        }

        // 글자색
        if (position % 7 == 0) {
            holder.date.setTextColor(Color.RED);
        } else {
            holder.date.setTextColor(Color.BLACK);
        }

        // 배경색
        if (getSelectedPosition() == position) {
            holder.date.setBackgroundColor(Color.YELLOW);
        } else {
            holder.date.setBackgroundColor(Color.WHITE);
        }

        // 완성된 View return
        return convertView;
    }

    // ViewHolder 패턴
    static class ViewHolder {
        TextView date;
    }

}
