package com.example.anwarhanif.qrattendancesys;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class CheckInOut {

    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Students");
    private static int i=2;
    private static String checkEpNo= "EP";
    public CheckInOut(String epNo){
        DatabaseReference ref = reference.child("pswd"+epNo);
        if (i == 2){
            ref.child("checkIn").setValue(new Date().toString());
            checkEpNo = ""+epNo;
            i++;
            return;
        }
        if(!checkEpNo.equals(epNo)){//if both EP No is equal then nothing should happened
           i--;
        }

        if(i%2 == 0) {
            checkIn(ref);
        }else{
            checkOut(ref);
        }
        i++;
        checkEpNo=""+epNo;
    }

    public void checkIn(DatabaseReference reference1){
        reference1.child("checkIn").setValue(new Date().toString());
        reference1.child("checkOut").setValue("");
    }

    public void checkOut(DatabaseReference reference2){
        reference2.child("checkOut").setValue(new Date().toString());
    }
}

