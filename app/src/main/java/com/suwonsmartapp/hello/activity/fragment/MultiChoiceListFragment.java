package com.suwonsmartapp.hello.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.suwonsmartapp.hello.R;

import java.util.ArrayList;

/**
 * Created by junsuk on 15. 6. 18..
 */
public class MultiChoiceListFragment extends Fragment {
    private ListView mListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_multichoice_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        mListView = (ListView) view.findViewById(R.id.listView);

        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            arrayList.add("item " + i);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_multiple_choice, arrayList);

        mListView.setAdapter(adapter);


        view.findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SparseBooleanArray checked = mListView.getCheckedItemPositions();
                StringBuilder builder = new StringBuilder();

                int len = mListView.getCount();
                for (int i = 0; i < len; i++) {
                    if (checked.get(i)) {
                        builder.append(mListView.getItemAtPosition(i) + "\n");
                    }
                }

                Toast.makeText(getActivity(), builder.toString(), Toast.LENGTH_SHORT).show();
            }
        });


        super.onViewCreated(view, savedInstanceState);
    }
}
