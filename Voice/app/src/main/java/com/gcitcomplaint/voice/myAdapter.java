package com.gcitcomplaint.voice;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class myAdapter extends FirebaseRecyclerAdapter<Complaints, myAdapter.ViewHolderClass>{


    public myAdapter(@NonNull FirebaseRecyclerOptions<Complaints> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolderClass holder, final int position, @NonNull final Complaints complaints) {
        holder.name.setText(complaints.getPerson_name());
        holder.id.setText(complaints.getPerson_id());
        holder.title.setText(complaints.getComplaint_title());
        holder.type.setText(complaints.getComplaint_type());
        holder.description.setText(complaints.getDescription());
        holder.status.setText(complaints.getComplaint_status());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.delete.getContext());
                builder.setTitle("");
                builder.setMessage("Are you sure you want to delete it?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("Complaints")
                                .child(getRef(position).getKey()).removeValue();
                        String id = complaints.getPerson_id();
                        FirebaseDatabase.getInstance().getReference().child(id)
                                .child(getRef(position).getKey()).removeValue();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        });
    }
    @NonNull
    @Override
    public ViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent,false);
        myAdapter.ViewHolderClass viewHolderClass = new myAdapter.ViewHolderClass(view);
        return viewHolderClass;
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder {
        TextView name, id, title, type, description,status;
        ImageButton delete;
        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_dispalay);
            id = itemView.findViewById(R.id.email_dispalay);
            title = itemView.findViewById(R.id.title_dispalay);
            type = itemView.findViewById(R.id.type_display);
            description = itemView.findViewById(R.id.des_display);
            status = itemView.findViewById(R.id.status_display);
            delete = itemView.findViewById(R.id.delete_btn);
        }
    }
}
