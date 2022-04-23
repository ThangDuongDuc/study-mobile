package com.ddt.firebaseapplication.Customs;

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

import com.ddt.firebaseapplication.Entities.Person;
import com.ddt.firebaseapplication.R;

import java.util.ArrayList;

public class ContactsAdapterCustom extends ArrayAdapter<Person> {
    Context context;
    ArrayList<Person> arrayList;
    int layoutResource;

    public ContactsAdapterCustom(@NonNull Context context, int resource, @NonNull ArrayList<Person> objects) {
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
//                Intent callIntent = new Intent(Intent.ACTION_DIAL);
//                callIntent.setData(Uri.parse("tel:" + textView2.getText().toString().trim()));
//                context.startActivity(callIntent);
                ///////////
                if(ContextCompat.checkSelfPermission(context ,android.Manifest.permission.CALL_PHONE) !=
                        PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) context, new
                            String[]{android.Manifest.permission.CALL_PHONE}, 0);
                } else {
                    context.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + textView2.getText().toString().trim())));
                }
            }
        });

//        Person person = arrayList.get(position);
//        if (person != null) {
//            EditText txt_Name = (EditText) convertView.findViewById(R.id.txtName);
//            EditText txt_Phone = (EditText) convertView.findViewById(R.id.txtPhone);
//            textView1.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    System.out.println(">>> Custom adapter: " + person.getName());
//                    txt_Name.setText(person.getName());
////                    txt_Phone.setText(person.getPhone());
//                }
//            });
//
//            textView2.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
////                    txt_Name.setText(person.getName());
////                    txt_Phone.setText(person.getPhone());
//                }
//            });
//        }

        return convertView;
    }
}
