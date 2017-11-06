package hospital.hospitalqueue;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class UserVisitDetail extends AppCompatActivity {

    private Spinner sp_dx;
    private Button b_reset,b_reservations_queue;
    private EditText ed_patient_name,ed_patient_lastname,ed_patient_dx_other,ed_patient_pid;
    private RadioGroup ra_group_patient_sex;
    private RadioButton ra_patient_sex_male,ra_patient_sex_female;
    private final String clear = null;
    private boolean flagQueueNumber;
    //private boolean checkNet = false;
    private int queueNumber,queueNext,queuePresent,queueCancel;
    private final  String KEY = "patient";
    private final String pathcheckNet = ".info/connected";

    DatabaseReference databasePatient;
    DatabaseReference databasePatientQueue;


    private ValueEventListener databasePatientQueue_temp;
    SavePatientData savePatientData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_visit_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        databasePatient = FirebaseDatabase.getInstance().getReference(getString(R.string.firebase_date)+"/"+ DateThai.getDate(true));
        databasePatientQueue = FirebaseDatabase.getInstance().getReference(getString(R.string.firebase_date_Number)+"/"+ DateThai.getDate(true));

        sp_dx = (Spinner) findViewById(R.id.sp_dx);

        b_reset = (Button) findViewById(R.id.b_reset);
        b_reservations_queue = (Button) findViewById(R.id.b_reservations_queue);

        ed_patient_name = (EditText) findViewById(R.id.ed_patient_name);
        ed_patient_lastname = (EditText) findViewById(R.id.ed_patient_lastname);
        ed_patient_dx_other = (EditText) findViewById(R.id.ed_patient_dx_other);
        ed_patient_pid = (EditText) findViewById(R.id.ed_patient_pid);

        ra_group_patient_sex = (RadioGroup) findViewById(R.id.ra_group_patient_sex);
        ra_patient_sex_female = (RadioButton) findViewById(R.id.ra_patient_sex_female);
        ra_patient_sex_male = (RadioButton) findViewById(R.id.ra_patient_sex_male);

        queueNumber = 0;
        queueNext = 0;
        queuePresent = 0;

        sp_dx.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position == 8) ed_patient_dx_other.setVisibility(View.VISIBLE);
                else  ed_patient_dx_other.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        b_reservations_queue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckConnection.checkNet)
                addQueue();
                else Toast.makeText(UserVisitDetail.this,getString(R.string.t_checkconnect),Toast.LENGTH_SHORT).show();
            }
        });

        b_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearForm();
            }
        });

        savePatientData = new SavePatientData(UserVisitDetail.this);

        //CheckConnectFirebase();

        //Log.d("ShowQueue",getString(R.string.firebase_date_child_present));


    }

    /*private void CheckConnectFirebase(){

        final DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(pathcheckNet);
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    checkNet = true;
                }else {
                    checkNet = false;
                    Toast.makeText(UserVisitDetail.this,getString(R.string.t_checkconnect),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Listener was cancelled at .info/connected");
                 checkNet = false;
                Toast.makeText(UserVisitDetail.this,getString(R.string.t_checkconnect),Toast.LENGTH_SHORT).show();
            }
        });
    }*/


    @Override
    protected void onStart() {
        super.onStart();

        databasePatientQueue_temp = databasePatientQueue.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(getString(R.string.firebase_date_child)).exists()) {
                    flagQueueNumber = true;
                    queueNumber = Integer.parseInt(dataSnapshot.child(getString(R.string.firebase_date_child)).getValue().toString());
                    Log.d("firebase","true");
                }
                else {
                    flagQueueNumber = false;
                    databasePatientQueue.child(getString(R.string.firebase_date_child)).setValue(String.valueOf(queueNumber));
                    databasePatientQueue.child(getString(R.string.firebase_date_child_next)).setValue(String.valueOf(queueNext));
                    databasePatientQueue.child(getString(R.string.firebase_date_child_present)).setValue(String.valueOf(queuePresent));
                    databasePatientQueue.child(getString(R.string.firebase_date_child_cancel)).setValue(String.valueOf(queueCancel));
                    Log.d("firebase","false");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }



    private void addQueue(){

        String patientName = ed_patient_name.getText().toString().trim();
        String patientLastNmae = ed_patient_lastname.getText().toString().trim();

        String patientDx = sp_dx.getSelectedItemPosition() == 8 ?ed_patient_dx_other.getText().toString().trim():sp_dx.getSelectedItem().toString();
        String patientSex = ra_group_patient_sex.getCheckedRadioButtonId() == R.id.ra_patient_sex_male?getString(R.string.ra_patient_sex_male):getString(R.string.ra_patient_sex_female);
        String patient_queue_date = DateThai.getDate(false);
        String queueNumber = String.valueOf(this.queueNumber+1);
        String patient_status = getString(R.string.st_user_visit_detail);
        String patien_call = getString(R.string.patien_call);

        String patientPid = ed_patient_pid.getText().toString();

        if(!TextUtils.isEmpty(patientName) && !TextUtils.isEmpty(patientLastNmae) && !TextUtils.isEmpty(patientDx) && ra_group_patient_sex.getCheckedRadioButtonId() != -1 && !TextUtils.isEmpty(patientDx) && (!TextUtils.isEmpty(patientPid) && patientPid.length() == 13)){

            String patientId = databasePatient.push().getKey();
            Patient patient = new Patient(patientName,patientLastNmae,patientId,patientDx,patientSex,queueNumber,patient_queue_date,patient_status,patien_call,patientPid);

            databasePatient.child(patientId).setValue(patient);
            databasePatientQueue.child(getString(R.string.firebase_date_child)).setValue(queueNumber);

            savePatientData.createSetting_Sound(true);
            savePatientData.createSetting_Vibrator(true);
            savePatientData.createSetting_Notify(true);


            Intent intent = new Intent(UserVisitDetail.this,ShowQueue.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable(KEY, patient);
            intent.putExtras(bundle);
            startActivity(intent);
            clearFirebaseListenner();
            finish();

        }else {
            Toast.makeText(this,R.string.t_user_visit_detail,Toast.LENGTH_SHORT).show();
        }


    }


    private void clearForm(){

        ed_patient_name.setText(clear);
        ed_patient_lastname.setText(clear);
        ed_patient_dx_other.setText(clear);
        ra_group_patient_sex.clearCheck();
    }


    @Override
    public boolean onSupportNavigateUp() {
        //onBackPressed();
        // return super.onSupportNavigateUp();
        clearFirebaseListenner();
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        clearFirebaseListenner();
        moveTaskToBack(true);

    }


    private void clearFirebaseListenner(){
        databasePatientQueue.removeEventListener(databasePatientQueue_temp);
    }
}
