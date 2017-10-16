package hospital.hospitalqueue;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EmployeeLogin extends AppCompatActivity {

    Button b_Login;
    EditText ed_Username,ed_Password;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        context = EmployeeLogin.this;

        b_Login = (Button) findViewById(R.id.b_login);
        ed_Username = (EditText) findViewById(R.id.ed_username);
        ed_Password = (EditText) findViewById(R.id.ed_password);

        b_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*if(ed_Username.getText().toString().trim().equals(context.getResources().getString(R.string.ed_user_pass)) && ed_Password.getText().toString().trim().equals(context.getResources().getString(R.string.ed_user_pass))){
                    //Toast.makeText(EmployeeLogin.this,"OK",Toast.LENGTH_SHORT).show();
                    //Log.d("Dd",ed_Username.getText()+" "+ed_Password.getText());
                    Intent intent = new Intent(context,EmployeeShowQueue.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(EmployeeLogin.this,R.string.warning_login,Toast.LENGTH_SHORT).show();
                    //Log.d("Dd",ed_Username.getText()+" "+ed_Password.getText());
                    //Log.d("Dd",getString(R.string.ed_user_pass).toString()+" "+getString(R.string.ed_user_pass).toString());
                }*/

                Intent intent = new Intent(context,EmployeeShowQueue.class);
                startActivity(intent);
            }
        });

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
}
