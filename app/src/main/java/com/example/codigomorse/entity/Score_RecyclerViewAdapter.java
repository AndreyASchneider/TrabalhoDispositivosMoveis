package com.example.codigomorse.entity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codigomorse.R;
import com.example.codigomorse.model.Score;

import java.util.List;

public class Score_RecyclerViewAdapter extends RecyclerView.Adapter<Score_RecyclerViewAdapter.Score_ViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;

    Context context;
    List<Score> scoreList;

    public Score_RecyclerViewAdapter(Context context, List<Score> scoreList, RecyclerViewInterface recyclerViewInterface){
        this.context = context;
        this.scoreList = scoreList;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public Score_RecyclerViewAdapter.Score_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.score_item_view, parent, false);

        return new Score_RecyclerViewAdapter.Score_ViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull Score_RecyclerViewAdapter.Score_ViewHolder holder, int position) {
        holder.imgHighscoreMore.setColorFilter(ContextCompat.getColor(context, R.color.tanAccent), android.graphics.PorterDuff.Mode.MULTIPLY);

        holder.lblHighscoreScore.setText("Score: " + scoreList.get(position).getPontuation());
        holder.lblHighscoreDatetime.setText(scoreList.get(position).getDateCreate());
    }

    @Override
    public int getItemCount() {
        return scoreList.size();
    }

    public static class Score_ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgHighscoreMore;
        TextView lblHighscoreScore, lblHighscoreDatetime;

        public Score_ViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            imgHighscoreMore = itemView.findViewById(R.id.imgHighscoreMore);
            lblHighscoreScore = itemView.findViewById(R.id.lblHighscoreScore);
            lblHighscoreDatetime = itemView.findViewById(R.id.lblHighscoreDatetime);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerViewInterface != null){
                        int pos = getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
