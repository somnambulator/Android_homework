package com.example.chapter3.homework;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chapter3.homework.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends ArrayAdapter<item> {
    private item It;

    public MyAdapter(Context context, ArrayList<item> members)
    {
        super(context,0, members);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // Get the data item for this position
        item members = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_in_list, parent, false);
        }

        // Lookup view for data population
        TextView tvName = convertView.findViewById(R.id.textView2);
        TextView tvMessage = convertView.findViewById(R.id.textView3);
        TextView tvTime = convertView.findViewById(R.id.textView4);

        // Populate the data into the template view suing the data object
        tvName.setText(members.getTitle());
        tvMessage.setText(members.getBody());
        tvTime.setText(members.getTime());

        // return the complete view to render on screen
        return  convertView;
    }
}
