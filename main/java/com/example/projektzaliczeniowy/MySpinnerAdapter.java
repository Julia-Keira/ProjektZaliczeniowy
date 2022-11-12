package com.example.projektzaliczeniowy;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class MySpinnerAdapter extends ArrayAdapter<String> {
    Context context;
    List<String> itemsName;
    List<Integer> itemsImage;

    public MySpinnerAdapter(Context context, List<String> itemsName, List<Integer> itemsImage) {
        super(context, R.layout.item, itemsName);
        this.context = context;
        this.itemsName = itemsName;
        this.itemsImage = itemsImage;
    }


    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.dropdown_item, parent, false);

        // Image and TextViews
        TextView text = row.findViewById(R.id.text);
        ImageView img = row.findViewById(R.id.myimage);


        //Set state abbreviation and state flag
        text.setText(itemsName.get(position));
        img.setImageResource(itemsImage.get(position));

        return row;
    }


}
