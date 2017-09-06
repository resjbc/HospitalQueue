package hospital.hospitalqueue;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Idres on 27/8/2560.
 */

public class SavePatientData {

    private Context context;
    private SharedPreferences sharedPerfs;
    private SharedPreferences.Editor editor;

    static String perfsName = "Patientdata";
    static int perfsMode = 0;

    public SavePatientData(Context context) {
        this.context = context;
        this.sharedPerfs = this.context.getSharedPreferences(perfsName, perfsMode);
        this.editor = sharedPerfs.edit();
    }

    public void createPatientId(String patientId) {
        editor.putString("patientId", patientId);
        editor.commit();
    }

    public String getPatientId() {
        return sharedPerfs.getString("patientId", "0");
    }

    public void createDateVisitPatient(String DateVisitPatient) {
        editor.putString("DateVisitPatient", DateVisitPatient);
        editor.commit();
    }

    public String getDateVisitPatient() {
        return sharedPerfs.getString("DateVisitPatient", "0");
    }

    public void ClearPatientData(){
        editor.clear().commit();
    }
}
