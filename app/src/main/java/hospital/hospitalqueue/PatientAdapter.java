package hospital.hospitalqueue;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Idres on 12/10/2560.
 */

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.PatientViewHolder>{


    private List<Patient> patients;
    private Context context;

    public PatientAdapter(List<Patient> patients, Context context) {
        this.patients = patients;
        this.context = context;
    }


    @Override
    public PatientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PatientViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_queue_item, parent, false));
    }

    @Override
    public void onBindViewHolder(PatientViewHolder holder, int position) {

        Patient patient = patients.get(position);

        holder.t_patient_name_item.setText(context.getString(R.string.t_patient_name)+" "+patient.getPatientName()+" "+patient.getPatientLastname());
        holder.t_patient_dx.setText(context.getString(R.string.t_patient_dx)+" "+patient.getPatientDx());

    }

    @Override
    public int getItemCount() {
        return patients.size();
    }

     class PatientViewHolder extends RecyclerView.ViewHolder{

         TextView t_patient_name_item,t_patient_dx,t_patient_number_item,t_patient_queues_status;

         public PatientViewHolder(View itemView) {
             super(itemView);

             t_patient_name_item = (TextView) itemView.findViewById(R.id.t_patient_name_item);
             t_patient_dx = (TextView) itemView.findViewById(R.id.t_patient_dx_item);
             t_patient_number_item = (TextView) itemView.findViewById(R.id.t_patient_number_item);
             t_patient_queues_status = (TextView) itemView.findViewById(R.id.t_patient_queues_status_item);

         }
     }
}
