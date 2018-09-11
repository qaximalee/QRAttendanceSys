package com.example.anwarhanif.qrattendancesys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
/**
 * Created by Anwar Hanif on 05-May-18.
 */

public class CheckEpIsAvailable extends AppCompatActivity{

    private DatabaseReference reference;
    private ArrayList<String> lists = new ArrayList<>();
    private ListView listView;
    private String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_keys);

        reference = FirebaseDatabase.getInstance().getReference();

        final ArrayAdapter<String> arrayAdapter = new
                ArrayAdapter<>(this, android.R.layout.simple_list_item_1,lists);

        listView = (ListView) findViewById(R.id.keys);

        listView.setAdapter(arrayAdapter);


        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                str = getIntent().getExtras().getString("EPNO");
                Toast.makeText(CheckEpIsAvailable.this,str,Toast.LENGTH_LONG).show();
                if(dataSnapshot.hasChild("pswd"+str)){
                    DatabaseReference ref = reference.child("Students").child("pswd"+str);
                    ref.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            String value = dataSnapshot.getValue(String.class);
                            lists.add(value);
                            arrayAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }else {
                    Toast.makeText(CheckEpIsAvailable.this, "Enter Right Value", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
/*
        String s = "KKKKK";
        Intent intent = new Intent();
        intent.putExtra("Message",s);
        setResult(1,intent);
        finish();*/
    }
}
