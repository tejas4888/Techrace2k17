package thedorkknightrises.techraceapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by Tejas on 27-08-2017.
 */

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedItemViewHolder> {

    ArrayList<FeedItem> feedItems;
    Context context;

    public FeedAdapter(ArrayList<FeedItem> feedItems,Context context)
    {
        this.feedItems=feedItems;
        this.context=context;
    }

    @Override
    public FeedAdapter.FeedItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.feed_item, parent, false);
        return new FeedAdapter.FeedItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FeedAdapter.FeedItemViewHolder holder, int position) {

        holder.title.setText(feedItems.get(position).title);
        holder.description.setText(feedItems.get(position).description);
        holder.time.setText(feedItems.get(position).time);

        if (!TextUtils.isEmpty(feedItems.get(position).url))
        {
            holder.imageView.setVisibility(View.VISIBLE);
            Glide.with(context).load(feedItems.get(position).url).into(holder.imageView);
        }

    }

    @Override
    public int getItemCount() {
        return feedItems.size();
    }

    static class FeedItemViewHolder extends RecyclerView.ViewHolder {

        TextView title,description,time;
        ImageView imageView;

        public FeedItemViewHolder(View itemView) {
            super(itemView);

            title=(TextView)itemView.findViewById(R.id.feed_title);
            description=(TextView)itemView.findViewById(R.id.feed_description);
            time=(TextView)itemView.findViewById(R.id.feed_time);
            imageView=(ImageView)itemView.findViewById(R.id.feed_image);
        }
    }
}
