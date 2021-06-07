package com.gcitcomplaint.voice;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;


import java.util.HashMap;
import java.util.Map;
public class UserHelperAdapter extends FirebaseRecyclerAdapter<Complaints, UserHelperAdapter.ViewHolderClass> {

    public UserHelperAdapter(@NonNull FirebaseRecyclerOptions<Complaints> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolderClass holder,
                                    final int position, @NonNull Complaints complaints) {
        holder.name.setText(complaints.getPerson_name());
        holder.id.setText(complaints.getPerson_id());
        holder.title.setText(complaints.getComplaint_title());
        holder.type.setText(complaints.getComplaint_type());
        holder.description.setText(complaints.getDescription());
        holder.status.setText(complaints.getComplaint_status());


        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus=DialogPlus.newDialog(holder.edit.getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialogcontent))
                        .setExpanded(true,1200)
                        .create();
                View myview=dialogPlus.getHolderView();
                final  TextView pname = myview.findViewById(R.id.update_name);
                final  TextView pid = myview.findViewById(R.id.update_id);
                final EditText purl=myview.findViewById(R.id.updateTitle);
                final EditText name=myview.findViewById(R.id.updateType);
                final EditText course=myview.findViewById(R.id.complaintDetails);
                final  TextView stat = myview.findViewById(R.id.complaintStatus);
                Button submit=myview.findViewById(R.id.usubmit);
                Button cancel = myview.findViewById(R.id.cancel);
                pname.setText(complaints.getPerson_name());
                pid.setText(complaints.getPerson_id());
                purl.setText(complaints.getComplaint_title());
                name.setText(complaints.getComplaint_type());
                course.setText(complaints.getDescription());
                stat.setText(complaints.getComplaint_status());

                dialogPlus.show();

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map=new HashMap<>();
                        map.put("person_name",pname.getText().toString());
                        map.put("person_id",pid.getText().toString());
                        map.put("complaint_title",purl.getText().toString());
                        map.put("complaint_type",name.getText().toString());
                        map.put("description",course.getText().toString());
                        map.put("complaint_status", stat.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("Complaints")
                                .child(getRef(position).getKey()).updateChildren(map);
//                                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void aVoid) {
//                                        dialogPlus.dismiss();
//                                    }
//                                })
//                                .addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        dialogPlus.dismiss();
//                                    }
//                                });
                        String id = complaints.getPerson_id();
                        FirebaseDatabase.getInstance().getReference().child(id)
                                .child(getRef(position).getKey()).updateChildren(map);
                        dialogPlus.dismiss();
//                                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void aVoid) {
//                                        dialogPlus.dismiss();
//                                    }
//                                })
//                                .addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        dialogPlus.dismiss();
//                                    }
//                                });
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogPlus.dismiss();
                    }
                });
            }
        });
    }

    @NonNull
    @Override
    public ViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent,false);
        UserHelperAdapter.ViewHolderClass viewHolderClass = new UserHelperAdapter.ViewHolderClass(view);
        return viewHolderClass;
    }
    public class ViewHolderClass extends RecyclerView.ViewHolder {
        TextView name, id, title, type, description, status;
        ImageButton edit;
        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_dispalay);
            id = itemView.findViewById(R.id.email_dispalay);
            title = itemView.findViewById(R.id.title_dispalay);
            type = itemView.findViewById(R.id.type_display);
            description = itemView.findViewById(R.id.des_display);
            status = itemView.findViewById(R.id.status_display);
            edit = itemView.findViewById(R.id.delete_btn);
        }
    }

}
