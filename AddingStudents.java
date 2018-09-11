package com.example.anwarhanif.qrattendancesys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddingStudents extends AppCompatActivity {

    private Spinner mSection, mSemester, mYear;
    private EditText mName, mRollNo;
    private String Name, RollNo, Section, Semester, Year;
    private Button submit;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_students);

        ref = FirebaseDatabase.getInstance().getReference().child("Students");

        mName = (EditText) findViewById(R.id.nameTxtView);
        mRollNo = (EditText) findViewById(R.id.rollNo);
        mSection = (Spinner) findViewById(R.id.spinner);
        mSemester = (Spinner) findViewById(R.id.spinner2);
        mYear = (Spinner) findViewById(R.id.spinner3);

        submit = (Button) findViewById(R.id.submit);


        ArrayAdapter<String> spAdapter = new ArrayAdapter<>(AddingStudents.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.section));
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSection.setAdapter(spAdapter);

        mSection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        Section = "A";
                        break;
                    case 1:
                        Section = "B";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String> sp2Adapter = new ArrayAdapter<>(AddingStudents.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.semester));
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSemester.setAdapter(sp2Adapter);

        mSemester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        Semester = "1st";
                        break;
                    case 1:
                        Semester = "2nd";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        ArrayAdapter<String> sp3Adapter = new ArrayAdapter<>(AddingStudents.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.year));
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mYear.setAdapter(sp3Adapter);

        mYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        Year = "1st";
                        break;
                    case 1:
                        Year = "2nd";
                        break;
                    case 2:
                        Year = "3rd";
                        break;
                    case 3:
                        Year = "4th";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Name = mName.getText().toString();
                RollNo = mRollNo.getText().toString();
                DatabaseReference dbRef = ref.child("pswd"+RollNo);
                //ref.child("pswd"+RollNo);
                dbRef.child("Name").setValue(Name);
                dbRef.child("RollNo").setValue(RollNo);
                dbRef.child("Semester").setValue(Semester+" Semester");
                dbRef.child("Section").setValue("Section "+Section);
                dbRef.child("Year").setValue(Year+" Year");
                //DatabaseReference ref = dbRef.child("CheckInOut");
                Intent intent = new Intent(AddingStudents.this, QRAttendanceSysMain.class);
                startActivity(intent);
            }
        });

    }

}
