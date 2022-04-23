package com.ddt.myfirstapplication;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.ddt.myfirstapplication.Model.Product;
import com.ddt.myfirstapplication.Model.SanPham;
import com.ddt.myfirstapplication.SQL.ProductOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class SanPhamSQLite extends AppCompatActivity {
    private ListView listView;
    private Button btn_Add, btn_Update, btn_Delete, btn_All;
    private EditText txt_id, txt_name, txt_inventory;

    private final List<SanPham> noteList = new ArrayList<SanPham>();
    private ArrayAdapter<SanPham> listViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham_sqlite);

        this.listView = findViewById(R.id.listSp);
        this.btn_Add = findViewById(R.id.addsp);
        this.btn_All = findViewById(R.id.allsp);
        this.btn_Delete = findViewById(R.id.deletesp);
        this.btn_Update = findViewById(R.id.updatesp);
        this.txt_id = findViewById(R.id.idsp);
        this.txt_name = findViewById(R.id.namesp);
        this.txt_inventory = findViewById(R.id.slsp);

        ProductOpenHelper productOpenHelper = new ProductOpenHelper(this);
        productOpenHelper.createDefaultSanPhamsIfNeed();
        List<SanPham> list =  productOpenHelper.getAllSanPham();

        this.listViewAdapter = new ArrayAdapter<SanPham>(this,
                android.R.layout.simple_list_item_1, list);

        // Assign adapter to ListView
        this.listView.setAdapter(this.listViewAdapter);

        // Register the ListView for Context menu
        registerForContextMenu(this.listView);

        this.btn_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Id = txt_id.getText().toString().trim();
                String name = txt_name.getText().toString().trim();
                Integer inventory =Integer.parseInt(txt_inventory.getText().toString().trim());
                SanPham sanPham = new SanPham(Id, name, inventory);
                productOpenHelper.addSanPham(sanPham);
                btn_All.callOnClick();
            }
        });

        this.btn_All.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<SanPham> list =  productOpenHelper.getAllSanPham();

                listViewAdapter = new ArrayAdapter<SanPham>(SanPhamSQLite.this,
                        android.R.layout.simple_list_item_1, list);

                // Assign adapter to ListView
                listView.setAdapter(listViewAdapter);
            }
        });

        this.btn_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Id = txt_id.getText().toString().trim();
                String name = txt_name.getText().toString().trim();
                Integer inventory =Integer.parseInt(txt_inventory.getText().toString().trim());
                SanPham sanPham = new SanPham(Id, name, inventory);
                productOpenHelper.updateSanPham(sanPham);
                btn_All.callOnClick();
            }
        });

        this.btn_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Id = txt_id.getText().toString().trim();
                productOpenHelper.deleteSanPham(Id);
                btn_All.callOnClick();
            }
        });

        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SanPham sanPham = (SanPham) listView.getItemAtPosition(i);
                txt_id.setText(sanPham.getId());
                txt_name.setText(sanPham.getName());
                txt_inventory.setText(String.valueOf(sanPham.getIsInventory()));
            }
        });
    }
}