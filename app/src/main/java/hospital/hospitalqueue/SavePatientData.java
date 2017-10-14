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

    public void createQueueComing(boolean flagQueueComing) {
        editor.putBoolean("QueueComing", flagQueueComing);
        editor.commit();
    }

    public boolean getQueueComing() {
        return sharedPerfs.getBoolean("QueueComing",false);
    }

    public void createSetting_Sound(boolean sound) {
        editor.putBoolean("Sound", sound);
        editor.commit();
    }

    public boolean getSetting_Sound() {
        return sharedPerfs.getBoolean("Sound",true);
    }

    public void createSetting_Vibrator(boolean vibrator) {
        editor.putBoolean("Vibrator", vibrator);
        editor.commit();
    }

    public boolean getSetting_Vibrator() {
        return sharedPerfs.getBoolean("Vibrator",true);
    }

    public void createSetting_Notify(boolean notify) {
        editor.putBoolean("Notify", notify);
        editor.commit();
    }

    public boolean getSetting_Notifyr() {
        return sharedPerfs.getBoolean("Notify",true);
    }



    public void ClearPatientData(){
        editor.clear().commit();
    }
}
