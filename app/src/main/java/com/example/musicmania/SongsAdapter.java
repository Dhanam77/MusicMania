package com.example.musicmania;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.ViewHolder> implements Filterable {

    private View myView;
    private Context mContext;
    private ArrayList<String> applicationsArrayList;
    private ArrayList<String> applicationsArrayListFull;

    public SongsAdapter(Context mContext, ArrayList<String> applicationsArrayList) {
        this.mContext = mContext;
        this.applicationsArrayList = applicationsArrayList;
        applicationsArrayListFull = new ArrayList<>(applicationsArrayList);
    }

    @NonNull
    @Override
    public SongsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_item_layout,parent,false);
        return new SongsAdapter.ViewHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull SongsAdapter.ViewHolder holder, int position) {

        final String apps = applicationsArrayList.get(position);
        holder.appName.setText(apps);


        holder.appLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MusicPlayerActivity.class);

                mContext.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return applicationsArrayList.size();
    }

    @Override
    public Filter getFilter() {

        return categoryFilter;


    }


    private Filter categoryFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<String> filteredList = new ArrayList<>();

            if(constraint== null || constraint.length() ==0)
            {
                filteredList.addAll(applicationsArrayListFull);
            }
            else
            {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(String applications : applicationsArrayListFull)
                {
                    if(applications.toLowerCase().contains(filterPattern))
                    {
                        filteredList.add(applications);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            applicationsArrayList.clear();
            applicationsArrayList.addAll((ArrayList)results.values);
            notifyDataSetChanged();
        }
    };




    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView appName;
        private RelativeLayout appLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            appName = (TextView)myView.findViewById(R.id.music_name);
            appLayout = (RelativeLayout) myView.findViewById(R.id.app_layout);

        }
    }
}
