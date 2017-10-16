package thedorkknightrises.techraceapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.YuvImage;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import thedorkknightrises.techraceapp.ui.ParticipantApplyActivity;
import thedorkknightrises.techraceapp.ui.VolunteerApplyActivity;

/**
 * Created by Tejas on 28-08-2017.
 */

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.LeaderboardViewHolder> {

    ArrayList<LeaderboardItem> leaderboardItems;
    Context context;

    public LeaderboardAdapter(ArrayList<LeaderboardItem> leaderboardItems,Context context)
    {
        this.leaderboardItems=leaderboardItems;
        this.context=context;
    }

    @Override
    public LeaderboardAdapter.LeaderboardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.leaderboard_item, parent, false);
        return new LeaderboardAdapter.LeaderboardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LeaderboardAdapter.LeaderboardViewHolder holder, int position) {

        final String state=leaderboardItems.get(position).state;
        final String name=leaderboardItems.get(position).name;
        final String applied=leaderboardItems.get(position).applied;

        if (TextUtils.equals(state,"1"))
        {
            int theme= Color.parseColor("#FFA500");
            holder.cardView.setCardBackgroundColor(theme);
        }else if (TextUtils.equals(state,"2"))
        {
            int theme= Color.parseColor("#FF1919");
            holder.cardView.setCardBackgroundColor(theme);
        }

        if (TextUtils.equals(applied,"1"))
        {
            holder.appliedText.setText("+4");
        }else if (TextUtils.equals(applied,"2")){
            holder.appliedText.setText("+2");
        } else if (TextUtils.equals(applied,"3")){
            holder.appliedText.setText("+T");
        }

        holder.positionText.setText(String.valueOf(position+1));
        holder.name.setText(leaderboardItems.get(position).name);
        holder.location.setText(leaderboardItems.get(position).location);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.equals("0",state))
                {
                    Intent intent=new Intent(context, ParticipantApplyActivity.class);
                    intent.putExtra("name",name);
                    context.startActivity(intent);
                }

                else if (TextUtils.equals("1",state))
                {
                    Intent intent=new Intent(context, VolunteerApplyActivity.class);
                    intent.putExtra("name",name);
                    context.startActivity(intent);
                }

                else if (TextUtils.equals("2",state))
                {
                    Toast.makeText(context,"Already applied :)",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return leaderboardItems.size();
    }

    public static class LeaderboardViewHolder extends RecyclerView.ViewHolder {

        TextView name,location,positionText,appliedText;
        CardView cardView;
        public LeaderboardViewHolder(View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.leaderboard_item_name);
            location=(TextView)itemView.findViewById(R.id.leaderboard_item_clues_solved);
            positionText=(TextView)itemView.findViewById(R.id.leaderboard_item_position);
            appliedText=(TextView)itemView.findViewById(R.id.leaderboard_item_applied);

            cardView=(CardView)itemView.findViewById(R.id.leaderboard_item_card);
        }
    }
}
