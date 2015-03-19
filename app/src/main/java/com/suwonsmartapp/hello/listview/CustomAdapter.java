
package com.suwonsmartapp.hello.listview;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.suwonsmartapp.hello.R;

import java.util.List;

/**
 * Created by junsuk on 15. 3. 19..
 */
public class CustomAdapter<String> extends ArrayAdapter<String> {

    // ViewHolder 패턴
    static class ViewHolder {
        TextView labelText;
    }

    // Layout을 가져오기 위한 객체
    private LayoutInflater inflater;

    public CustomAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("CustomAdapter", "position : " + position);

        ViewHolder holder;
        View view = convertView;

        if (view == null) {
            // View 를 처음 로딩할 때, Data 를 처음 셋팅할 때
            inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_item, null);
            TextView label = (TextView)view.findViewById(R.id.list_item_tv);
            holder = new ViewHolder();
            holder.labelText = label;
            view.setTag(holder);
        } else {
            // View, Data 재사용
            holder = (ViewHolder) view.getTag();
        }
        
        // position 위치의 데이터를 취득
        String name = getItem(position);
        if (!TextUtils.isEmpty(name.toString())) {
            holder.labelText.setText(name.toString());
        }

        // 홀수, 짝수 줄 배경색 변경
        if (position % 2 == 0) {
            holder.labelText.setBackgroundColor(Color.parseColor("#aa0000"));
        } else {
            holder.labelText.setBackgroundColor(Color.parseColor("#880000"));
        }

        // 애니메이션 적용
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.translation);
        view.startAnimation(animation);

        // 완성된 View return
        return view;
    }

}
