package com.ddt.readcontacts_contentprovider.adapters;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.ddt.readcontacts_contentprovider.R;
import com.ddt.readcontacts_contentprovider.models.Contact;

import java.util.ArrayList;

public class ContactsAdapterCustom extends ArrayAdapter<Contact> {
    Context context;
    ArrayList<Contact> arrayList;
    int layoutResource;

    public ContactsAdapterCustom(@NonNull Context context, int resource, @NonNull ArrayList<Contact> objects) {
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
        TextView textView1 = (TextView) convertView.findViewById(R.id.text_1);
        textView1.setText(arrayList.get(position).getName());

        TextView textView2 = (TextView) convertView.findViewById(R.id.text_2);
        textView2.setText(arrayList.get(position).getPhone());

        Button btnCall = (Button) convertView.findViewById(R.id.btn_Call);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE) !=
                        PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) context, new
                            String[]{android.Manifest.permission.CALL_PHONE}, 0);
                } else {
                    context.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + textView2.getText().toString().trim())));
                }
            }
        });
        return convertView;
    }
}
