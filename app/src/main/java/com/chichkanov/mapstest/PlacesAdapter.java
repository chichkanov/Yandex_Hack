package com.chichkanov.mapstest;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chichkanov.mapstest.model.Place;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lysva on 08.07.2017.
 */

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.ViewHolder> {
    private List<Place> mData = new ArrayList<>();
    private OnItemClickListener mListener;

    public PlacesAdapter(List<Place> data, OnItemClickListener listener) {
        mData = data;
        mListener = listener;
    }

    @Override
    public PlacesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_place, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlacesAdapter.ViewHolder holder, int position) {
        Place place = mData.get(position);
        holder.bind(place, mListener);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void updateData(List<Place> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTxtName;
        private TextView mTxtDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            mTxtName = (TextView) itemView.findViewById(R.id.txt_name);
            mTxtDescription = (TextView) itemView.findViewById(R.id.txt_description);
        }

        public void bind(final Place place, final OnItemClickListener listener) {
            mTxtName.setText(place.getName().trim());
            mTxtDescription.setVisibility(place.getDescription().length() == 0 ? View.GONE : View.VISIBLE);
            mTxtDescription.setText(place.getDescription().trim());
            if(listener != null) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onItemClick(place);
                    }
                });
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Place place);
    }
}
