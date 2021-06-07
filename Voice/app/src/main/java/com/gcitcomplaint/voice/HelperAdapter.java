package com.gcitcomplaint.voice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

//This class is for fetching the complaints data
public class HelperAdapter extends RecyclerView.Adapter {
    List<Complaints> complaintsDataList;

    public HelperAdapter(List<Complaints> complaintsDataList) {
        this.complaintsDataList = complaintsDataList;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sp, parent,false);
        ViewHolderClass viewHolderClass = new ViewHolderClass(view);
        return viewHolderClass;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderClass viewHolderClass = (ViewHolderClass)holder;
        Complaints complaints = complaintsDataList.get(position);
        viewHolderClass.title.setText(complaints.getComplaint_title());
        viewHolderClass.type.setText(complaints.getComplaint_type());
        viewHolderClass.description.setText(complaints.getDescription());
    }
    @Override
    public int getItemCount() {
        return complaintsDataList.size();
    }

    private class ViewHolderClass extends RecyclerView.ViewHolder {
        TextView title, type, description;
        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_dispalay);
            type = itemView.findViewById(R.id.type_display);
            description = itemView.findViewById(R.id.des_display);

        }
    }
}
