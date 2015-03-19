
package com.suwonsmartapp.hello.listview.adapter;

import android.content.Context;
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
import com.suwonsmartapp.hello.listview.data.Person;

import java.util.List;

/**
 * CustomAdapter
 */
public class CustomAdapter extends ArrayAdapter<Person> {

    // ViewHolder 패턴
    static class ViewHolder {
        TextView name;
        TextView skill;
        TextView birthday;
    }

    // Layout을 가져오기 위한 객체
    private LayoutInflater inflater;

    public CustomAdapter(Context context, int resource, List<Person> objects) {
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
            TextView name = (TextView)view.findViewById(R.id.list_item_tv);
            TextView birthday = (TextView)view.findViewById(R.id.list_item_tv2);
            TextView skill = (TextView)view.findViewById(R.id.list_item_tv3);
            holder = new ViewHolder();
            holder.name = name;
            holder.birthday = birthday;
            holder.skill = skill;
            view.setTag(holder);
        } else {
            // View, Data 재사용
            holder = (ViewHolder) view.getTag();
        }
        
        // position 위치의 데이터를 취득
        Person person = getItem(position);
        String name = person.getName();
        String birthday = person.getBirthday();
        String skill = person.getSkill();

        if (!TextUtils.isEmpty(name)) {
            holder.name.setText(name);
        }
        if (!TextUtils.isEmpty(birthday)) {
            holder.birthday.setText(birthday);
        }
        if (!TextUtils.isEmpty(skill)) {
            holder.skill.setText(skill);
        }

        // 홀수, 짝수 줄 배경색 변경
        // if (position % 2 == 0) {
        // holder.name.setBackgroundColor(Color.parseColor("#aa0000"));
        // } else {
        // holder.labelText.setBackgroundColor(Color.parseColor("#880000"));
        // }

        // 애니메이션 적용
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.translation);
        view.startAnimation(animation);

        // 완성된 View return
        return view;
    }

}
