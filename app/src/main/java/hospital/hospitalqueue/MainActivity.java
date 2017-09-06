package hospital.hospitalqueue;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button b_employee,b_user;
    private SavePatientData savePatientData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        b_employee = (Button) findViewById(R.id.b_employee);
        b_user = (Button) findViewById(R.id.b_user);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        savePatientData = new SavePatientData(MainActivity.this);

        if(savePatientData.getDateVisitPatient().equals(DateThai.getDate(true))) {
            Intent intent = new Intent(MainActivity.this,ShowQueue.class);
            startActivity(intent);
        }

        b_employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EmployeeLogin.class);
                startActivity(intent);
            }
        });

        b_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UserVisitDetail.class);
                startActivity(intent);
            }
        });


    }

}
