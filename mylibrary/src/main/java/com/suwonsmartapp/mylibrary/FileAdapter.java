package com.suwonsmartapp.mylibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by junsuk on 15. 5. 6..
 *
 * 라이브러리 프로젝트 작성 방법
 *
 * 1. File -> New -> New Module -> Android Library Module
 * 2. 라이브러리로 빼고 싶은 클래스를 복사한다
 * 3. 라이브러리 프로젝트에서 우클릭 -> Make Module
 * 4. 에러가 없으면 성공
 */
public class FileAdapter extends BaseAdapter {

    private List<File> mData;
    private Context mContext;

    SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy.MM.dd a KK:mm");
    DecimalFormat mDecimalFormat = new DecimalFormat("#,###");

    public FileAdapter(Context context, List<File> data) {
        mContext = context;
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
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
            convertView = inflater.inflate(R.layout.item_file_list, null);
            TextView filename = (TextView) convertView.findViewById(R.id.tv_filename);
            TextView filesize = (TextView) convertView.findViewById(R.id.tv_filesize);
            TextView modified = (TextView) convertView.findViewById(R.id.tv_modified);

            holder = new ViewHolder();
            holder.fileName = filename;
            holder.fileSize = filesize;
            holder.modified = modified;

            convertView.setTag(holder);
        } else {
            // View, Data 재사용
            holder = (ViewHolder) convertView.getTag();
        }

        // position 위치의 데이터를 취득
        File file = (File) getItem(position);

        holder.fileName.setText(file.getName());

        // 디렉토리인지 아닌지
        if (file.isDirectory()) {
            holder.fileSize.setText("<dir>");
        } else {
            long size = file.length() / 1024;
            holder.fileSize.setText(mDecimalFormat.format(size) + "kb");
        }

        holder.modified.setText(mDateFormat.format(new Date(file.lastModified())));

        // 완성된 View return
        return convertView;
    }

    // ViewHolder 패턴
    static class ViewHolder {
        TextView fileName;
        TextView fileSize;
        TextView modified;
    }
}
