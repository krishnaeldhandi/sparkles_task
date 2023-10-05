package com.sparkles.sparkles_landing.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sparkles.sparkles_landing.R;
import com.sparkles.sparkles_landing.models.HomeModel;
import com.squareup.picasso.Picasso;

import java.util.List;


public class HomeViewAdapter extends RecyclerView.Adapter<HomeViewAdapter.MyViewHolder> {

            private final String TAG_NAME = "HomeViewAdapter";
            private Context context;
            private List<HomeModel> list;
    public OnItemClickListener itemClickListener;

            public HomeViewAdapter(Context context, List<HomeModel> list) {
                    this.context = context;
                    this.list = list;
                    }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View rootView = LayoutInflater.from(context).inflate(R.layout.home_views_list, parent, false);
                    return new MyViewHolder(rootView);
                    }

            @Override
            public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
                HomeModel homeModel = list.get(position);

                // Set data to the views
                holder.titleTextView.setText(homeModel.getService());
                Log.d(TAG_NAME,"title Text: "+homeModel.getService());

                Glide.with(context)
                        .load(homeModel.getImage())
                        .placeholder(R.drawable.laundry)
                        .into(holder.imageView);

                Log.d(TAG_NAME,"image Uri: "+homeModel.getImage());

                // Attach a click listener if needed
                holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Check if the itemClickListener is set
                    if (itemClickListener != null) {
                        // Notify the click event with the clicked HomeModel
                        itemClickListener.onItemClick(homeModel);
                        }
                    }
                });
            }

            @Override
            public int getItemCount() {
                    return list.size();
                    }

            @SuppressLint("NotifyDataSetChanged")
            public void setHomeViewList(List<HomeModel> homeModelList) {
                this.list = homeModelList;
                notifyDataSetChanged();
            }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }
            public static class MyViewHolder extends RecyclerView.ViewHolder {
                TextView titleTextView;
                ImageView imageView;
                CardView cardView;

                public MyViewHolder(@NonNull View itemView) {
                    super(itemView);
                    titleTextView = itemView.findViewById(R.id.homeViewText);
                    imageView = itemView.findViewById(R.id.homeViewImage);
                    cardView = itemView.findViewById(R.id.list_cardView);
                }
            }

    public interface OnItemClickListener {
        void onItemClick(HomeModel homeModel);
    }
}
