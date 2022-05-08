package com.ddt.testtaskcontentprovider.Custom.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.ddt.testtaskcontentprovider.Activity.AddOrDeleteActivity;
import com.ddt.testtaskcontentprovider.Model.Task;
import com.ddt.testtaskcontentprovider.R;
import com.google.gson.Gson;

import java.util.ArrayList;

import lombok.NonNull;

public class CustomListTaskAdapter extends ArrayAdapter<Task> {
    Context context;
    ArrayList<Task> arrayList;
    int layoutResource;

    public CustomListTaskAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Task> objects) {
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
        Task task = arrayList.get(position);
        TextView textView1 = (TextView) convertView.findViewById(R.id.nameTask);
        textView1.setText(task.getName());

        TextView textView2 = (TextView) convertView.findViewById(R.id.descriptionTask);
        textView2.setText(task.getDescription());

//        Button btn_Remove = (Button) convertView.findViewById(R.id.removeTask);
//        btn_Remove.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(context);
//                myDatabaseHelper.delete(task.getId().toString());
//                arrayList.remove(position);
////                System.out.println("abc");
////                Intent intent = new Intent(context, AddOrDeleteActivity.class);
////                context.startActivity(intent);
//                notifyDataSetChanged();
//            }
//        });
//
        Button btn_Update = (Button) convertView.findViewById(R.id.editTask);
        btn_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddOrDeleteActivity.class);
                Gson gson = new Gson();
                String taskJson = gson.toJson(task);
                intent.putExtra("ACTION", "update");
                intent.putExtra("TaskJson", taskJson);
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}