package com.example.anwarhanif.qrattendancesys;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class QRAttendanceSysMain extends Activity {

    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    private Button addStudents, searchInput;
    private EditText searchCheckIn;
    private String str;
    private String contents;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set the main content layout of the Activity
        setContentView(R.layout.activity_qrattendance_sys_main);

        addStudents = (Button) findViewById(R.id.addStudents);
        addStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QRAttendanceSysMain.this, AddingStudents.class);
                startActivity(intent);
            }
        });

        searchCheckIn = (EditText) findViewById(R.id.checkInInput);

        EpNoForCheckIn checkIn = new EpNoForCheckIn("pswd"+str);
        searchInput = (Button) findViewById(R.id.searchStudents);
        searchInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    str = searchCheckIn.getText().toString();
                    Intent intent = new Intent(QRAttendanceSysMain.this, CheckEpIsAvailable.class);
                    intent.putExtra("EPNO",str);
                    startActivity(intent);
            }
        });

    }

    //product qr code mode
    public void scanQR(View v) {
        try {
            //start the scanning activity from the com.google.zxing.client.android.SCAN intent
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, 0);

        } catch (ActivityNotFoundException anfe) {
            //on catch, show the download dialog
            showDialog(QRAttendanceSysMain.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }

    }

    //alert dialog for downloadDialog
    private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {

                }
            }
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return downloadDialog.show();
    }

    //on ActivityResult method
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                //get the extras that are returned from the intent
                contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                EPNoClass.epNo = contents;
                Toast toast = Toast.makeText(this, "Content:" + contents + " Format:" + format, Toast.LENGTH_LONG);
                toast.show();
                CheckInOut checkInOut = new CheckInOut(contents);
                stdDetails();
            }
        }
    }
    public void stdDetails(){
        startActivity(new Intent(QRAttendanceSysMain.this,ShowStudentData.class));
    }
}
