package hospital.hospitalqueue;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
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
    public void onBindViewHolder(final PatientViewHolder holder, int position) {

        Patient patient = patients.get(position);

        holder.t_patient_name_item.setText(context.getString(R.string.t_patient_name)+" "+patient.getPatientName()+" "+patient.getPatientLastname());
        holder.t_patient_dx.setText(context.getString(R.string.t_patient_dx)+" "+patient.getPatientDx());
        holder.t_patient_queues_status.setText(context.getString(R.string.t_status)+" "+patient.getPatient_status());
        holder.t_patient_number_item.setText(context.getString(R.string.t_queue)+" "+patient.getPatientQueueNumber());
        holder.t_patient_sex_item.setText(context.getString(R.string.t_show_queue_sex)+" "+patient.getPatientSex());
        Log.d("FF","dddsssss"+position);
        if(position == 0 /*&& ((EmployeeShowQueue) context).flagRowCard*/) {
            holder.ca_queue.setCardBackgroundColor(ContextCompat.getColor(context, R.color.card));
            holder.t_patient_name_item.setTextColor(ContextCompat.getColor(context, R.color.colorAccent4));
            holder.t_patient_dx.setTextColor(ContextCompat.getColor(context, R.color.colorAccent4));
            holder.t_patient_queues_status.setTextColor(ContextCompat.getColor(context, R.color.colorAccent4));
            holder.t_patient_number_item.setTextColor(ContextCompat.getColor(context, R.color.colorAccent4));
            holder.t_patient_sex_item.setTextColor(ContextCompat.getColor(context, R.color.colorAccent4));
        }else {
            holder.ca_queue.setCardBackgroundColor(ContextCompat.getColor(context, R.color.main));
            holder.t_patient_name_item.setTextColor(ContextCompat.getColor(context, R.color.white));
            holder.t_patient_dx.setTextColor(ContextCompat.getColor(context, R.color.white));
            holder.t_patient_queues_status.setTextColor(ContextCompat.getColor(context, R.color.white));
            holder.t_patient_number_item.setTextColor(ContextCompat.getColor(context, R.color.white));
            holder.t_patient_sex_item.setTextColor(ContextCompat.getColor(context, R.color.white));
        }

        holder.itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                contextMenu.setHeaderTitle(context.getString(R.string.t_option_menu));
                if(holder.getAdapterPosition() == 0)
                contextMenu.add(holder.getAdapterPosition(), 0, 0, context.getString(R.string.t_call_patient));
                contextMenu.add(holder.getAdapterPosition(), 1, 0, context.getString(R.string.b_show_queue_cancel));
            }
        });

    }

    @Override
    public int getItemCount() {
        return patients.size();
    }

     class PatientViewHolder extends RecyclerView.ViewHolder{

         TextView t_patient_name_item,t_patient_dx,t_patient_number_item,t_patient_queues_status,t_patient_sex_item;
         CardView ca_queue;

         public PatientViewHolder(View itemView) {
             super(itemView);

             t_patient_name_item = (TextView) itemView.findViewById(R.id.t_patient_name_item);
             t_patient_dx = (TextView) itemView.findViewById(R.id.t_patient_dx_item);
             t_patient_number_item = (TextView) itemView.findViewById(R.id.t_patient_number_item);
             t_patient_queues_status = (TextView) itemView.findViewById(R.id.t_patient_queues_status_item);
             t_patient_sex_item = (TextView) itemView.findViewById(R.id.t_patient_sex_item);
             ca_queue = (CardView) itemView.findViewById(R.id.ca_queue);

         }
     }
}
