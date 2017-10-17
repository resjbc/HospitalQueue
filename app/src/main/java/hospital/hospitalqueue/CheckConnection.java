package hospital.hospitalqueue;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Idres on 17/10/2560.
 */

public class CheckConnection {

    static public final String pathcheckNet = ".info/connected";
    static public boolean checkNet = false;

    static public void CheckConnectFirebase(final Context context){

        final DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(pathcheckNet);
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);


                    //Toast.makeText(context,context.getString(R.string.t_checkconnect),Toast.LENGTH_SHORT).show();
                checkNet =  connected == true?true:false;
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Listener was cancelled at .info/connected");
                checkNet = false;
                //Toast.makeText(context,context.getString(R.string.t_checkconnect),Toast.LENGTH_SHORT).show();
            }
        });
    }

}
