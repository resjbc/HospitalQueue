package hospital.hospitalqueue;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.DataSnapshot;

/**
 * Created by IT252 on 24/8/2560.
 */

public class Patient  implements Parcelable {


    private String patientName;
    private String patientLastname;
    private String patientId;
    private String patientDx;
    private String patientSex;
    private String patientQueueNumber;
    private String patient_queue_date;
    private String patient_status;
    private String patien_call;
    private String patien_Pid;

    private OnDataListener onDataListener = null;
    private OnDataQueueListener onDataQueueListener = null;

    public Patient() {
    }


    public Patient(String patientName, String patientLastname, String patientId, String patientDx, String patientSex, String patientQueueNumber, String patient_queue_date, String patient_status, String patien_call,String patien_Pid) {
        this.patientName = patientName;
        this.patientLastname = patientLastname;
        this.patientId = patientId;
        this.patientDx = patientDx;
        this.patientSex = patientSex;
        this.patientQueueNumber = patientQueueNumber;
        this.patient_queue_date = patient_queue_date;
        this.patient_status = patient_status;
        this.patien_call = patien_call;
        this.patien_Pid = patien_Pid;
    }


    protected Patient(Parcel in) {
        patientName = in.readString();
        patientLastname = in.readString();
        patientId = in.readString();
        patientDx = in.readString();
        patientSex = in.readString();
        patientQueueNumber = in.readString();
        patient_queue_date = in.readString();
        patient_status = in.readString();
        patien_call = in.readString();
        patien_Pid =  in.readString();
    }

    public static final Creator<Patient> CREATOR = new Creator<Patient>() {
        @Override
        public Patient createFromParcel(Parcel in) {
            return new Patient(in);
        }

        @Override
        public Patient[] newArray(int size) {
            return new Patient[size];
        }
    };


    public String getPatien_Pid() {
        return patien_Pid;
    }

    public void setPatien_Pid(String patien_Pid) {
        this.patien_Pid = patien_Pid;
    }

    public String getPatient_queue_date() {
        return patient_queue_date;
    }

    public void setPatient_queue_date(String patient_queue_date) {
        this.patient_queue_date = patient_queue_date;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientLastname() {
        return patientLastname;
    }

    public void setPatientLastname(String patientLastname) {
        this.patientLastname = patientLastname;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientDx() {
        return patientDx;
    }

    public void setPatientDx(String patientDx) {
        this.patientDx = patientDx;
    }

    public String getPatientSex() {
        return patientSex;
    }

    public void setPatientSex(String patientSex) {
        this.patientSex = patientSex;
    }

    public String getPatientQueueNumber() {
        return patientQueueNumber;
    }

    public void setPatientQueueNumber(String patientQueueNumber) {
        this.patientQueueNumber = patientQueueNumber;
    }

    public String getPatient_status() {
        return patient_status;
    }

    public void setPatient_status(String patient_status) {
        this.patient_status = patient_status;
    }

    public String getPatien_call() {
        return patien_call;
    }

    public void setPatien_call(String patien_call) {
        this.patien_call = patien_call;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(patientName);
        parcel.writeString(patientLastname);
        parcel.writeString(patientId);
        parcel.writeString(patientDx);
        parcel.writeString(patientSex);
        parcel.writeString(patientQueueNumber);
        parcel.writeString(patient_queue_date);
        parcel.writeString(patient_status);
        parcel.writeString(patien_call);
        parcel.writeString(patien_Pid);
    }

    public interface OnDataListener {
        public void onPassData();
    }

    public interface OnDataQueueListener {
        public void onPassDataQueue(DataSnapshot dataSnapshot);
    }

    public void setOnPassDataListener(OnDataListener listener) {
        onDataListener = listener;
    }
    public void setOnPassDataQueueListener(OnDataQueueListener listener) {
        onDataQueueListener = listener;
    }

}





