package hospital.hospitalqueue;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EmployeeShowQueue extends AppCompatActivity {

    private RecyclerView re_ShowQueue;
    private List<Patient> patients;
    private PatientAdapter patientAdapter;
    private final Context context = EmployeeShowQueue.this;
    private String presentQueueNumber = "0",nextQueueNumber = "0",allQueueNumber = "0",CancelQueueNumber="0";
    private FloatingActionButton f_call,f_next,f_delete;
    private View rootView;
    //public boolean flagRowCard = false;

    DatabaseReference databasePatient;
    ChildEventListener databasePatient_temp;
    ValueEventListener databasePatientQueue_temp;
    DatabaseReference databasePatientQueue;
    FrameLayout frameLayout;
    TextView t_loading,t_no_queqe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_show_queue);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        databasePatient = FirebaseDatabase.getInstance().getReference(getString(R.string.firebase_date)+"/"+ DateThai.getDate(true));
        databasePatientQueue = FirebaseDatabase.getInstance().getReference(getString(R.string.firebase_date_Number)+"/"+ DateThai.getDate(true));

        patients = new ArrayList<>();
        patientAdapter = new PatientAdapter(patients,context);

        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        rootView = findViewById(R.id.f_call);
        f_call = (FloatingActionButton) findViewById(R.id.f_call);
        f_delete = (FloatingActionButton) findViewById(R.id.f_delete);
        f_next = (FloatingActionButton) findViewById(R.id.f_next);
        frameLayout = (FrameLayout) findViewById(R.id.fr_onload);

        t_loading = (TextView) findViewById(R.id.t_loading);
        t_no_queqe = (TextView) findViewById(R.id.t_no_queqe);

        re_ShowQueue = (RecyclerView) findViewById(R.id.re_showqueue);
        re_ShowQueue.setHasFixedSize(true);
        re_ShowQueue.setLayoutManager(manager);

        //TestRecycleView();
        frameLayout.setVisibility(View.VISIBLE);
        re_ShowQueue.setAdapter(patientAdapter);


        f_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(patients.size() >= 1) {
                    ShowDialog();

                }
            }
        });

        f_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(patients.size() >= 1) {
                   databasePatientQueue.child(getString(R.string.firebase_date_child_present)).setValue(patients.get(0).getPatientQueueNumber());
                   if(patients.size() > 1)databasePatientQueue.child(getString(R.string.firebase_date_child_next)).setValue(patients.get(1).getPatientQueueNumber());
                 Toast.makeText(context,getString(R.string.t_show_f_call),Toast.LENGTH_SHORT).show();
                   databasePatient.child(patients.get(0).getPatientId()).child(getString(R.string.patien_call3)).setValue(getString(R.string.patien_call2));
               }
            }
        });

        f_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(patients.size() >= 1){
                    databasePatient.child(patients.get(0).getPatientId()).child(getString(R.string.firebase_key)).setValue(getString(R.string.t_show_queue_patient_status_success));
                   if(patients.size() > 1) databasePatientQueue.child(getString(R.string.firebase_date_child_present)).setValue(patients.get(1).getPatientQueueNumber());
                   //else databasePatientQueue.child(getString(R.string.firebase_date_child_present)).setValue(patients.get(1).getPatientQueueNumber());
                }else if(patients.size() <= 0) Snackbar.make(rootView,getString(R.string.t_end_queue), Snackbar.LENGTH_LONG).show();

            }
        });

        StartFirebase();
    }

    private void ButtonkQueue() {

        f_next.setVisibility(View.VISIBLE);
        f_delete.setVisibility(View.VISIBLE);
        f_call.setVisibility(View.VISIBLE);

        //if(patients.size() > 1) f_next.setVisibility(View.VISIBLE);
         if(patients.size() <= 0){
            f_next.setVisibility(View.GONE);
            f_delete.setVisibility(View.GONE);
            f_call.setVisibility(View.GONE);
        }
    }



    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Dd","onStart");
        //if(patients.size() != 0) patients.removeAll(patients);
    }

    private void StartFirebase(){
        databasePatient_temp = databasePatient.orderByChild(getString(R.string.firebase_key)).equalTo(getString(R.string.t_show_queue_patient_status_wait)).limitToFirst(10)
                .addChildEventListener(new ChildEventListener() {

                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Log.d("Dd","onChildAdded");

                        patients.add(dataSnapshot.getValue(Patient.class));
                        patientAdapter.notifyDataSetChanged();

                        if(patients.size() > 2  )
                            databasePatientQueue.child(getString(R.string.firebase_date_child_next)).setValue(patients.get(1).getPatientQueueNumber());
                        else if(patients.size() == 1 )
                            databasePatientQueue.child(getString(R.string.firebase_date_child_next)).setValue(patients.get(0).getPatientQueueNumber());
                        else if(patients.size() == 2 && presentQueueNumber.equals(nextQueueNumber))
                            databasePatientQueue.child(getString(R.string.firebase_date_child_next)).setValue(patients.get(1).getPatientQueueNumber());


                        CheckLastItem();
                        ButtonkQueue();

                        //patients.removeAll(patients);

                       /* if (dataSnapshot.exists()) {

                            Log.d("Dd",dataSnapshot.getValue().toString());

                            for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                Patient patient = childSnapshot.getValue(Patient.class);
                                patients.add(patient);
                                //Log.d("Dd",patient.getPatientDx());

                            }


                            patientAdapter.notifyDataSetChanged();
                        }*/
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        Log.d("Dd","onChildChanged");
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        Log.d("Dd","onChildRemoved");

                        Patient patient = dataSnapshot.getValue(Patient.class);

                        int index = getIndexItem(patient);

                        patients.remove(index);
                        patientAdapter.notifyItemRemoved(index);


                        patientAdapter.notifyDataSetChanged();
                        if(patients.size() > 1 ) databasePatientQueue.child(getString(R.string.firebase_date_child_next)).setValue(patients.get(1).getPatientQueueNumber());

                        CheckLastItem();

                        ButtonkQueue();


                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                        Log.d("Dd","onChildMoved");
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("Dd","onCancelled");
                    }
                });

        databasePatientQueue_temp =  databasePatientQueue.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                presentQueueNumber =  dataSnapshot.child(getString(R.string.firebase_date_child_present)).getValue().toString();
                nextQueueNumber =  dataSnapshot.child(getString(R.string.firebase_date_child_next)).getValue().toString();
                allQueueNumber = dataSnapshot.child(getString(R.string.firebase_date_child_allQueueNumber)).getValue().toString();
                CancelQueueNumber = dataSnapshot.child(getString(R.string.firebase_date_child_cancel)).getValue().toString();

                // if(allQueueNumber.equals(context.getResources().getString(R.string.t_zero))) fab.setVisibility(View.GONE);
                //  else fab.setVisibility(View.VISIBLE);



                CheckLastItem();
                ButtonkQueue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void ShowDialog(){
        AlertDialog.Builder builder =
                new AlertDialog.Builder(context);
        builder.setMessage(getString(R.string.d_quation_delete));
        builder.setPositiveButton(getString(R.string.d_quation_delete_button_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                databasePatient.child(patients.get(0).getPatientId()).child(getString(R.string.firebase_key)).setValue(getString(R.string.t_show_queue_patient_status_cancel));
                databasePatientQueue.child(getString(R.string.firebase_date_child_cancel)).setValue((Integer.parseInt(CancelQueueNumber) + 1) + "");
            }

        });
        builder.setNegativeButton(getString(R.string.d_quation_delete_button_no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();

    }

    private void CheckLastItem(){
        if(patients.size() > 0) {
            if(presentQueueNumber.equals(allQueueNumber)) {

                Snackbar.make(rootView,getString(R.string.t_end_queue), Snackbar.LENGTH_LONG)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        }).setActionTextColor(Color.YELLOW).show();
                //fab.setImageResource(android.R.drawable.ic_media_pause);
            }
            frameLayout.setVisibility(View.GONE);
            t_loading.setVisibility(View.GONE);
            t_no_queqe.setVisibility(View.GONE);
        }
        else {
            frameLayout.setVisibility(View.VISIBLE);
            t_loading.setVisibility(View.GONE);
            t_no_queqe.setVisibility(View.VISIBLE);
            //databasePatientQueue.child(getString(R.string.firebase_date_child_next)).setValue("-1");
        }

    }

    private int getIndexItem(Patient patient){

        int index = -1;

        for (int i =0;i<patients.size();i++){
            if(patient.getPatientId().equals(patients.get(i).getPatientId())){
                index = i;
                break;
            }
        }

        return index;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case 0:
                //if(item.getGroupId() > 0) break;
                if(patients.size() >= 1) {
                    databasePatientQueue.child(getString(R.string.firebase_date_child_present)).setValue(patients.get(0).getPatientQueueNumber());
                    if(patients.size() > 1)databasePatientQueue.child(getString(R.string.firebase_date_child_next)).setValue(patients.get(1).getPatientQueueNumber());
                    Toast.makeText(context,getString(R.string.t_show_f_call),Toast.LENGTH_SHORT).show();
                    databasePatient.child(patients.get(0).getPatientId()).child(getString(R.string.patien_call3)).setValue(getString(R.string.patien_call2));
                }
                break;
            case 1:
                //Toast.makeText(context,patients.get(item.getGroupId()).getPatientQueueNumber(),Toast.LENGTH_SHORT).show();
                databasePatient.child(patients.get(item.getGroupId()).getPatientId()).child(getString(R.string.firebase_key)).setValue(getString(R.string.t_show_queue_patient_status_cancel));
                databasePatientQueue.child(getString(R.string.firebase_date_child_cancel)).setValue((Integer.parseInt(CancelQueueNumber)+1)+"");
                //Log.d("Dd",patients.get(item.getGroupId()).getPatientQueueNumber());
                break;
        }


        return super.onContextItemSelected(item);
    }

    private void TestRecycleView(){
        for(int i=0;i<10;i++)
            patients.add(new Patient("Test","Test","wdwadadsdsdad","ปวดหัว","ชาย",i+"","","wait","done"));
    }

    @Override
    public boolean onSupportNavigateUp() {
        //onBackPressed();
       // return super.onSupportNavigateUp();

        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        moveTaskToBack(true);

    }

    @Override
    protected void onStop() {

        if(patients.size() > 0) patients.removeAll(patients);

        super.onStop();
        Log.d("Dd","onStop");

    }

    @Override
    protected void onPause() {
        if(patients.size() > 0) patients.removeAll(patients);
       if(databasePatient_temp!=null) databasePatient.removeEventListener(databasePatient_temp);
        if(databasePatientQueue_temp!=null)  databasePatientQueue.removeEventListener(databasePatientQueue_temp);
        super.onPause();
        Log.d("Dd","onPause");

    }

    @Override
    protected void onResume() {
        if(patients.size() > 0) patients.removeAll(patients);
        super.onResume();
        Log.d("Dd","onResume");
    }
}
