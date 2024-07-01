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

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder> {

    private Context context;
    private List<Score> scoreList;

    public ScoreAdapter(Context context, List<Score> scoreList) {
        this.context = context;
        this.scoreList = scoreList;
    }

    @NonNull
    @Override
    public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_score, parent, false);
        return new ScoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreViewHolder holder, int position) {
        Score score = scoreList.get(position);
        holder.pontuationTextView.setText(score.getPontuation());
        holder.fotoTextView.setText(score.getFoto());
        holder.locationTextView.setText(score.getLocation());
        // Add more fields if necessary
    }

    @Override
    public int getItemCount() {
        return scoreList.size();
    }

    public static class ScoreViewHolder extends RecyclerView.ViewHolder {
        TextView pontuationTextView, fotoTextView, locationTextView;

        public ScoreViewHolder(@NonNull View itemView) {
            super(itemView);
            pontuationTextView = itemView.findViewById(R.id.pontuationTextView);
            fotoTextView = itemView.findViewById(R.id.fotoTextView);
            locationTextView = itemView.findViewById(R.id.locationTextView);
        }
    }
}
