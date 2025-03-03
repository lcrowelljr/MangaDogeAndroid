package com.seohajgod.mangadoge.fragmentstuff;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.seohajgod.mangadoge.R;

import java.util.List;

public class ChapterListAdapter extends ArrayAdapter<String> {

    public ChapterListAdapter(@NonNull Context context, List<String> chapters) {
        super(context, 0, chapters);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        String chapter = getItem(position);
        TextView chapterTextView = convertView.findViewById(android.R.id.text1);
        chapterTextView.setText(chapter);

        return convertView;
    }
}
