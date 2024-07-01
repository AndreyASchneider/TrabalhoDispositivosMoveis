package com.example.codigomorse.entity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codigomorse.R;

import java.util.List;

public class ELI_RecyclerViewAdapter extends RecyclerView.Adapter<ELI_RecyclerViewAdapter.ELI_ViewHolder> {
    Context context;
    List<EnableLetterItem> enableLetterItemList;

    public ELI_RecyclerViewAdapter(Context context, List<EnableLetterItem> enableLetterItemList){
        this.context = context;
        this.enableLetterItemList = enableLetterItemList;
    }

    @NonNull
    @Override
    public ELI_RecyclerViewAdapter.ELI_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create the look of the layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.enable_letter_item_view, parent, false);
        return new ELI_RecyclerViewAdapter.ELI_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ELI_RecyclerViewAdapter.ELI_ViewHolder holder, int position) {
        holder.swEnabled.setOnCheckedChangeListener(null);

        // bind values as they get recycled
        holder.swEnabled.setChecked(enableLetterItemList.get(position).isEnabled());
        holder.tvLetter.setText(enableLetterItemList.get(position).getLetter().toString());
        holder.tvTranslation.setText(enableLetterItemList.get(position).getTranslation());

        holder.swEnabled.setOnCheckedChangeListener((buttonView, isChecked) -> {
            enableLetterItemList.get(position).setEnabled(isChecked);
        });
    }

    @Override
    public int getItemCount() {
        return enableLetterItemList.size();
    }

    public static class ELI_ViewHolder extends RecyclerView.ViewHolder {

        Switch swEnabled;
        TextView tvLetter, tvTranslation;

        public ELI_ViewHolder(@NonNull View itemView) {
            super(itemView);

            swEnabled = itemView.findViewById(R.id.switchEnableLetter);
            tvLetter = itemView.findViewById(R.id.lblLetter);
            tvTranslation = itemView.findViewById(R.id.lblTranslation);
        }
    }
}
