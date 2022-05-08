package com.ddt.testtaskcontentprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.ddt.testtaskcontentprovider.Activity.AddOrDeleteActivity;
import com.ddt.testtaskcontentprovider.Custom.Adapter.CustomListTaskAdapter;
import com.ddt.testtaskcontentprovider.Model.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    public static final String PROVIDER_NAME = "com.ddt.lab_contentprovider.Custom.Provider.TaskContentProvider";
    public static final String URL = "content://" + PROVIDER_NAME + "/tasks";
    public static final Uri CONTENT_URI = Uri.parse(URL);

    private ListView listView;
    private FloatingActionButton btn_Add;
    ArrayList<Task> tasks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.binding();

        btn_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddOrDeleteActivity.class);
                intent.putExtra("ACTION", "add");
                startActivity(intent);
            }
        });

        tasks = showData();
        CustomListTaskAdapter adapter = new CustomListTaskAdapter(this, R.layout.custom_list_view_task_layout, tasks);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
    }

    private void binding() {
        this.btn_Add = findViewById(R.id.floatingActionButton);
        this.listView = findViewById(R.id.listview);
    }

    private  ArrayList<Task> showData(){
        Cursor cursor =  getContentResolver().query(CONTENT_URI, null, null, null, null);

        if (cursor != null) {
            tasks.clear();
            while (cursor.moveToNext()) {
                String tenCotId = "Id";
                String tenCotName = "Name";
                String tenCotDes = "Description";

                int viTriCotId = cursor.getColumnIndex(tenCotId);
                int viTriCotName = cursor.getColumnIndex(tenCotName);
                int viTriCotDes = cursor.getColumnIndex(tenCotDes);

                String Id = cursor.getString(viTriCotId);
                String Name = cursor.getString(viTriCotName);
                String Description = cursor.getString(viTriCotDes);

                Task task = Task.builder()
                        .Id(UUID.fromString(Id))
                        .Name(Name)
                        .Description(Description)
                        .build();
                tasks.add(task);
            }
        }
        return tasks;
    }
}