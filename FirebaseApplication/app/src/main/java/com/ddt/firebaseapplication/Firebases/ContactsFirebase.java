package com.ddt.firebaseapplication.Firebases;

import androidx.annotation.NonNull;

import com.ddt.firebaseapplication.Entities.Person;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

public class ContactsFirebase {
    private List<Person> personList2 = new ArrayList<>();
    public void Add(DatabaseReference databaseReference, Person person) {
        databaseReference.child(person.getId().toString()).setValue(person.toString());
        databaseReference.child(person.getId().toString()).child("name").setValue(person.getName());
        databaseReference.child(person.getId().toString()).child("phone").setValue(person.getPhone());
    }

    public void Delete(DatabaseReference databaseReference, ArrayList<Person> personArrayList, String phone) {
        String key = "";
        for (Person person:
             personArrayList) {
            if (person.getPhone().equals(phone)) {
                key = person.getId().toString();
                break;
            }
        }
        if (!key.equals("")) {
            databaseReference.child(key).removeValue();
        }
    }

    public List<Person> getAll(DatabaseReference databaseReference) {
        CountDownLatch done = new CountDownLatch(1);
        List<Person> personList = new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Person person = dataSnapshot.getValue(Person.class);
                    personList.add(person);
                    done.countDown();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " + error.getCode());
            }
        });

       try {
           done.await();
       } catch(InterruptedException e) {
           e.printStackTrace();
       }
       return personList;
    }
//    final FirebaseDatabase database = FirebaseDatabase.getInstance();
//    DatabaseReference ref = database.getReference("server/saving-data/fireblog/posts");
//
//// Attach a listener to read the data at our posts reference
//ref.addValueEventListener(new ValueEventListener() {
//        @Override
//        public void onDataChange(DataSnapshot dataSnapshot) {
//            Post post = dataSnapshot.getValue(Post.class);
//            System.out.println(post);
//        }
//
//        @Override
//        public void onCancelled(DatabaseError databaseError) {
//            System.out.println("The read failed: " + databaseError.getCode());
//        }
//    });
}
