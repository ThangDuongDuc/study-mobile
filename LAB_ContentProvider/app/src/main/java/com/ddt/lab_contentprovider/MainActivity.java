package com.ddt.lab_contentprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.ddt.lab_contentprovider.Activity.AddOrDeleteActivity;
import com.ddt.lab_contentprovider.Custom.Adapter.CustomListTaskAdapter;
import com.ddt.lab_contentprovider.Model.Task;
import com.ddt.lab_contentprovider.SQL.MyDatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton btn_Add;
    ListView listViewTask;
    CustomListTaskAdapter customListTaskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.binding();
        MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(this);
        myDatabaseHelper.createDefaultTaskIfNeed();
        ArrayList<Task> list =  myDatabaseHelper.getAll();

        btn_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddOrDeleteActivity.class);
                intent.putExtra("ACTION", "add");
                startActivity(intent);
            }
        });

        customListTaskAdapter = new CustomListTaskAdapter(this, R.layout.custom_list_view_task_layout, list);
        listViewTask.setAdapter(this.customListTaskAdapter);
        customListTaskAdapter.notifyDataSetChanged();
    }

    private void binding() {
        this.btn_Add = findViewById(R.id.floatingActionButton);
        this.listViewTask = findViewById(R.id.listView);
    }
}