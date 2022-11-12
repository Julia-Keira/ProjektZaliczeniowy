package com.example.projektzaliczeniowy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;


public class OrderAdapter extends ArrayAdapter<Order> {

    public OrderAdapter(Context context, List<Order> orders) {
        super(context, 0, orders);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Order order = getItem(position);
        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.order_cell, parent, false);
        TextView date = convertView.findViewById(R.id.date_list);
        TextView name = convertView.findViewById(R.id.name_list);
        TextView items = convertView.findViewById(R.id.items_list);

        date.setText(order.data);
        name.setText(order.personalData);
        items.setText(order.itemsString);
        return convertView;
    }

}
