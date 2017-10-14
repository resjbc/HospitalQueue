package hospital.hospitalqueue;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ShowQueue extends AppCompatActivity implements Patient.OnDataListener {

    private final  String KEY = "patient";
    private final  String patient_status = "cancel";
    Patient patient;
    private String patient_id = null;

    private TextView t_patient_name_lastname,t_patient_dx,t_patient_sex;
    private TextView t_patient_queue_present,t_patient_queue_next,t_patient_queue_description_head;
    private TextView t_patient_queue_all_queue,t_patient_present_date;
    private TextView t_patient_queue_number,t_patient_compute_queue;
    private FloatingActionButton f_setting;
    private Button b_cancel_queue;

    private LinearLayout l_next_queue;

    DatabaseReference databasePatient;
    DatabaseReference databasePatientQueue;
    ValueEventListener databasePatientQueue_temp;
    ValueEventListener databasePatient_temp;

    SavePatientData savePatientData;


    int nextQueue,presentQueue,countQueue,myQueue,cancelQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_queue);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        t_patient_name_lastname = (TextView) findViewById(R.id.t_patient_name);
        t_patient_dx = (TextView) findViewById(R.id.t_patient_dx);
        t_patient_queue_present = (TextView) findViewById(R.id.t_patient_queue_present);
        t_patient_queue_next = (TextView) findViewById(R.id.t_patient_queue_next);
        t_patient_queue_description_head = (TextView) findViewById(R.id.t_patient_queue_description_head);
        t_patient_queue_all_queue = (TextView) findViewById(R.id.t_patient_queue_all_queue);
        t_patient_present_date = (TextView) findViewById(R.id.t_patient_present_date);
        t_patient_queue_number = (TextView) findViewById(R.id.t_patient_number_item);
        t_patient_compute_queue = (TextView) findViewById(R.id.t_patient_compute_queue);
        t_patient_sex = (TextView) findViewById(R.id.t_patient_sex);

        f_setting = (FloatingActionButton) findViewById(R.id.f_setting);

        b_cancel_queue = (Button) findViewById(R.id.b_cancel_queue);

        l_next_queue = (LinearLayout) findViewById(R.id.l_next_queue);

        savePatientData = new SavePatientData(ShowQueue.this);
        databasePatient = FirebaseDatabase.getInstance().getReference(getString(R.string.firebase_date)+"/"+ DateThai.getDate(true));
        databasePatientQueue = FirebaseDatabase.getInstance().getReference(getString(R.string.firebase_date_Number)+"/"+ DateThai.getDate(true));

        //savePatientData.createQueueComing(false);

        if(getIntent().getParcelableExtra(KEY) != null) {
            patient = getIntent().getParcelableExtra(KEY);
            patient_id = patient.getPatientId();
            savePatientData.createPatientId(patient_id);
            savePatientData.createDateVisitPatient(DateThai.getDate(true));
            ShowQueueText();
        }else {
            patient_id = savePatientData.getPatientId();
            //Log.d("ShowQueue",patient_id);
            databasePatient.child(patient_id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    patient = dataSnapshot.getValue(Patient.class);
                    onPassData();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

        f_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Dd","Setting");
            }
        });



        b_cancel_queue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(b_cancel_queue.getText().equals(getString(R.string.b_show_queue_cancel))) ClearCancel();
               else if(b_cancel_queue.getText().equals(getString(R.string.b_show_queue_finish))) ClearSuccessAndCancel();
            }
        });
           // Log.d("ShowQueue",patient.getPatient_queue_date()+","+patient.getPatientName());

    }



    @Override
    protected void onStart() {
        super.onStart();
        //OnLoading();
        Log.d("Dd","onStart");

        databasePatient_temp =  databasePatient.child(patient_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                patient = dataSnapshot.getValue(Patient.class);
                onPassData();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        t_patient_present_date.setText(getString(R.string.t_show_queue_date)+" "+DateThai.getDate(true));

        databasePatientQueue_temp = databasePatientQueue.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ShowQueues(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void ShowQueues(DataSnapshot dataSnapshot){


        String queuePresent = "";
        String queueNext = "";
        String queueNumber =  "";
        String queueCancel =  "";


            queuePresent = dataSnapshot.child(getString(R.string.firebase_date_child_present)).getValue().toString();
            presentQueue = Integer.parseInt(queuePresent);
            if(savePatientData.getQueueComing())
                t_patient_queue_present.setText(patient.getPatientQueueNumber());
            else t_patient_queue_present.setText(queuePresent);


            queueNext = dataSnapshot.child(getString(R.string.firebase_date_child_next)).getValue().toString();
            nextQueue = Integer.parseInt(queueNext);

         ShowQueueTextDetail();

        queueNumber = dataSnapshot.child(getString(R.string.firebase_date_child)).getValue().toString();
        countQueue = Integer.parseInt(queueNumber);
        queueCancel = dataSnapshot.child(getString(R.string.firebase_date_child_cancel)).getValue().toString();
        cancelQueue = Integer.parseInt(queueCancel);
        t_patient_queue_all_queue.setText(getString(R.string.t_show_queue_count)+" "+(countQueue-cancelQueue));

    }

    private void ShowQueueTextDetail(){

       if(presentQueue == myQueue)  savePatientData.createQueueComing(true);

        if(savePatientData.getQueueComing()){
            Log.d("Dd","ss");
            l_next_queue.setVisibility(View.GONE);
            t_patient_compute_queue.setText(getString(R.string.t_show_queue_compute_myqueue4));
            t_patient_compute_queue.setTextColor(ContextCompat.getColor(ShowQueue.this, R.color.colorAccent4));
            b_cancel_queue.setText(getString(R.string.b_show_queue_finish));

        }else if(nextQueue == 0 ) {
            Log.d("Dd","aa");
            l_next_queue.setVisibility(View.GONE);
            t_patient_compute_queue.setText(getString(R.string.t_show_queue_compute_myqueue3));
            b_cancel_queue.setText(getString(R.string.b_show_queue_cancel));
        }
        else if (nextQueue == myQueue ) {
                    t_patient_compute_queue.setText(getString(R.string.t_show_queue_compute_myqueue5));
                    l_next_queue.setVisibility(View.VISIBLE);
                    t_patient_queue_next.setText(String.valueOf(nextQueue));
                    b_cancel_queue.setText(getString(R.string.b_show_queue_cancel));
                    Log.d("Dd","ww");
        } else {
                        t_patient_compute_queue.setText(getString(R.string.t_show_queue_compute_myqueue) + " " + (myQueue - nextQueue) + " " + getString(R.string.t_show_queue_compute_myqueue2));
                        t_patient_compute_queue.setTextColor(ContextCompat.getColor(ShowQueue.this, R.color.white));
                        Log.d("Dd", "ff");
                    l_next_queue.setVisibility(View.VISIBLE);
                    t_patient_queue_next.setText(String.valueOf(nextQueue));
                    b_cancel_queue.setText(getString(R.string.b_show_queue_cancel));
        }



    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        moveTaskToBack(true);

    }

    private void ClearCancel(){

        databasePatient.child(patient_id).child(getString(R.string.firebase_date_child_patient_status)).setValue(patient_status);
        databasePatientQueue.child(getString(R.string.firebase_date_child_cancel)).setValue(String.valueOf(cancelQueue + 1));
        if (databasePatientQueue_temp != null) databasePatientQueue.removeEventListener(databasePatientQueue_temp);
        if (databasePatient_temp != null) databasePatient.removeEventListener(databasePatient_temp);
        savePatientData.ClearPatientData();
        finish();
    }

    private void ClearSuccessAndCancel(){

        if (databasePatientQueue_temp != null) databasePatientQueue.removeEventListener(databasePatientQueue_temp);
        if (databasePatient_temp != null) databasePatient.removeEventListener(databasePatient_temp);
        savePatientData.ClearPatientData();
        finish();
    }


    @Override
    public void onPassData() {
        ShowQueueText();
    }

    private void ShowQueueText(){
        t_patient_queue_number.setText(getString(R.string.t_show_queue_myqueue)+" "+patient.getPatientQueueNumber());
        t_patient_name_lastname.setText(" "+getString(R.string.t_show_queue_prefix_name)+"   "+patient.getPatientName()+"   "+patient.getPatientLastname());
        t_patient_sex.setText(" "+getString(R.string.t_show_queue_sex)+"   "+patient.getPatientSex());
        t_patient_dx.setText(" "+getString(R.string.t_show_queue_t_show_queue_dx)+"   "+patient.getPatientDx());

        myQueue = Integer.parseInt(patient.getPatientQueueNumber());
        ShowQueueTextDetail();

        if(patient.getPatient_status().equals(getString(R.string.t_show_queue_patient_status_cancel)) || patient.getPatient_status().equals(getString(R.string.t_show_queue_patient_status_success)))
            ClearSuccessAndCancel();
    }

    private void OnLoading(){
        t_patient_queue_number.setText(getString(R.string.t_showAll));
        t_patient_queue_present.setText(getString(R.string.t_showAll_zero));
        t_patient_queue_next.setText(getString(R.string.t_showAll_zero));
        t_patient_compute_queue.setText(getString(R.string.t_showAll));
        t_patient_name_lastname.setText(getString(R.string.t_showAll));
        t_patient_sex.setText(getString(R.string.t_showAll));
        t_patient_dx.setText(getString(R.string.t_showAll));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ClearSuccessAndCancel();
    }

    @Override
    protected void onResume() {
        Log.d("Dd","onResume");
       try {
           if(patient.getPatient_status().equals(getString(R.string.t_show_queue_patient_status_cancel)) || patient.getPatient_status().equals(getString(R.string.t_show_queue_patient_status_success)))
               ClearSuccessAndCancel();
       }catch (Exception e){}
        super.onResume();
    }
}


