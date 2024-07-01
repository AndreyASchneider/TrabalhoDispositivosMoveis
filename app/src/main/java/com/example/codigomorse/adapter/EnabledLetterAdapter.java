package com.example.codigomorse.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import com.example.codigomorse.R;
import com.example.codigomorse.model.*;

public class EnabledLetterAdapter extends RecyclerView.Adapter<EnabledLetterAdapter.EnabledLetterViewHolder> {

    private Context context;
    private List<EnabledLetter> enabledLetterList;

    public EnabledLetterAdapter(Context context, List<EnabledLetter> enabledLetterList) {
        this.context = context;
        this.enabledLetterList = enabledLetterList;
    }

    @NonNull
    @Override
    public EnabledLetterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_enabled_letter, parent, false);
        return new EnabledLetterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EnabledLetterViewHolder holder, int position) {
        EnabledLetter enabledLetter = enabledLetterList.get(position);
        holder.userIdTextView.setText(String.valueOf(enabledLetter.getUserId()));
        holder.languageIdTextView.setText(String.valueOf(enabledLetter.getLanguageId()));
        holder.lettersTextView.setText(enabledLetter.getLetters());
        // Add more fields if necessary
    }

    @Override
    public int getItemCount() {
        return enabledLetterList.size();
    }

    public static class EnabledLetterViewHolder extends RecyclerView.ViewHolder {
        TextView userIdTextView, languageIdTextView, lettersTextView;

        public EnabledLetterViewHolder(@NonNull View itemView) {
            super(itemView);
            userIdTextView = itemView.findViewById(R.id.userIdTextView);
            languageIdTextView = itemView.findViewById(R.id.languageIdTextView);
            lettersTextView = itemView.findViewById(R.id.lettersTextView);
        }
    }
}
