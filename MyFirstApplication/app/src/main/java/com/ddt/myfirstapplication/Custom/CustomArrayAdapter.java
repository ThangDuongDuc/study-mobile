package com.ddt.myfirstapplication.Custom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ddt.myfirstapplication.Model.Product;
import com.ddt.myfirstapplication.R;

import java.util.ArrayList;
import java.util.List;

public class CustomArrayAdapter extends ArrayAdapter<Product> {
    Context context;
    ArrayList<Product> arrayList;
    int layoutResource;

    public CustomArrayAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Product> objects) {
        super(context, resource, objects);
        this.context = context;
        this.arrayList = objects;
        this.layoutResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(layoutResource, null);
        TextView textView1 = (TextView) convertView.findViewById(R.id.name);
        textView1.setText(arrayList.get(position).getName());

        TextView textView2 = (TextView) convertView.findViewById(R.id.price);
        textView2.setText(String.valueOf(arrayList.get(position).getPrice()));

        ImageView imageView = (ImageView) convertView.findViewById(R.id.img);
        imageView.setImageResource(arrayList.get(position).getImage());

        return convertView;
    }
}
