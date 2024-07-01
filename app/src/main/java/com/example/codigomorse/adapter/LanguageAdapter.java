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

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.LanguageViewHolder> {

    private Context context;
    private List<Language> languageList;

    public LanguageAdapter(Context context, List<Language> languageList) {
        this.context = context;
        this.languageList = languageList;
    }

    @NonNull
    @Override
    public LanguageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_language, parent, false);
        return new LanguageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LanguageViewHolder holder, int position) {
        Language language = languageList.get(position);
        holder.nameTextView.setText(language.getName());
        holder.lettersTextView.setText(language.getLetters());
        holder.translationsTextView.setText(language.getTranslations());
        // Add more fields if necessary
    }

    @Override
    public int getItemCount() {
        return languageList.size();
    }

    public static class LanguageViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, lettersTextView, translationsTextView;

        public LanguageViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            lettersTextView = itemView.findViewById(R.id.lettersTextView);
            translationsTextView = itemView.findViewById(R.id.translationsTextView);
        }
    }
}
