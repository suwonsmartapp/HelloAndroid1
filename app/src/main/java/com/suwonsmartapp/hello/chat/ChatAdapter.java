package com.suwonsmartapp.hello.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.suwonsmartapp.hello.R;

import java.util.ArrayList;

/**
 * Created by junsuk on 15. 4. 20..
 */
public class ChatAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<ChatHistory> mHistory;

    public ChatAdapter(Context context, ArrayList<ChatHistory> history) {
        mContext = context;
        mHistory = history;
    }

    @Override
    public int getCount() {
        return mHistory.size();
    }

    @Override
    public Object getItem(int position) {
        return mHistory.get(position);
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
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item_you_me, null);

            LinearLayout layoutYou = (LinearLayout)convertView.findViewById(R.id.layout_chat_item_you);
            TextView nicknameYou = (TextView)convertView.findViewById(R.id.tv_yourname);
            TextView messageYouFirst = (TextView)convertView.findViewById(R.id.tv_message_you_first);
            TextView messageYouSecond = (TextView)convertView.findViewById(R.id.tv_message_you_second);
            TextView timeYou = (TextView)convertView.findViewById(R.id.tv_time_you);

            LinearLayout layoutMe = (LinearLayout)convertView.findViewById(R.id.layout_chat_item_me);
            TextView timeMe = (TextView)convertView.findViewById(R.id.tv_time_me);
            TextView messageMe = (TextView)convertView.findViewById(R.id.tv_message_me);

            holder = new ViewHolder();
            holder.layout_you = layoutYou;
            holder.nickname_you = nicknameYou;
            holder.message_you1 = messageYouFirst;
            holder.message_you2 = messageYouSecond;
            holder.time_you = timeYou;
            holder.layout_me = layoutMe;
            holder.time_me = timeMe;
            holder.message_me = messageMe;

            convertView.setTag(holder);
        } else {
            // View, Data 재사용
            holder = (ViewHolder) convertView.getTag();
        }

        Object item = getItem(position);
        if (item instanceof Notice) {
            holder.layout_me.setVisibility(View.GONE);
            holder.layout_you.setVisibility(View.GONE);
        } else if (item instanceof ChatInfo) {
            ChatInfo chatInfo = (ChatInfo)item;
            if (chatInfo.isMe()) {
                holder.layout_me.setVisibility(View.VISIBLE);
                holder.layout_you.setVisibility(View.GONE);
                holder.time_me.setText(chatInfo.getTime());
                holder.message_me.setText(chatInfo.getMessage());
            } else {
                holder.layout_me.setVisibility(View.GONE);
                holder.layout_you.setVisibility(View.VISIBLE);
                holder.time_you.setText(chatInfo.getTime());
                holder.message_you1.setText(chatInfo.getMessage());
                holder.nickname_you.setText(chatInfo.getNickName());
            }
        }

        // 완성된 View return
        return convertView;
    }

    static class ViewHolder {
        LinearLayout layout_you;
        TextView nickname_you;
        TextView message_you1;
        TextView message_you2;
        TextView time_you;

        LinearLayout layout_me;
        TextView time_me;
        TextView message_me;
    }
}
