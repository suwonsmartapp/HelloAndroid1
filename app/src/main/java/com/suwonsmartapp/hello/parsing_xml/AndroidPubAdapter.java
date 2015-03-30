
package com.suwonsmartapp.hello.parsing_xml;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by junsuk on 15. 3. 30..
 */
public class AndroidPubAdapter extends BaseAdapter {

    // Layout을 가져오기 위한 객체
    private LayoutInflater inflater;
    private Context mContext;
    private List<AndroidPubInfo> mList;

    public AndroidPubAdapter(Context context, List<AndroidPubInfo> list) {
        mContext = context;
        mList = list;
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
        View view = convertView;

        if (view == null) {
            // View 를 처음 로딩할 때, Data 를 처음 셋팅할 때
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(android.R.layout.simple_list_item_1, null);
            TextView title = (TextView) view.findViewById(android.R.id.text1);
            holder = new ViewHolder();
            holder.title = title;
            view.setTag(holder);
        } else {
            // View, Data 재사용
            holder = (ViewHolder) view.getTag();
        }

        // position 위치의 데이터를 취득
        AndroidPubInfo recipeInfo = (AndroidPubInfo) getItem(position);
        String title = recipeInfo.getTitle();

        if (!TextUtils.isEmpty(title)) {
            holder.title.setText(title);
        }

        // 완성된 View return
        return view;
    }

    // ViewHolder 패턴
    static class ViewHolder {
        TextView title;
        String url;
    }
}
