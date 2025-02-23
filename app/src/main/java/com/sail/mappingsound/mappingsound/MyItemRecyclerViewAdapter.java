package com.sail.mappingsound.mappingsound;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sail.mappingsound.mappingsound.model.RecordItem;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

/**
 * {@link RecyclerView.Adapter} that can display a {@link RecordItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private List<RecordItem> mValues;
    private HashSet<Integer> expandedItems;

    private final OnListFragmentInteractionListener mListener;

    public MyItemRecyclerViewAdapter(List<RecordItem> items, OnListFragmentInteractionListener listener) {
        expandedItems = new HashSet<>();
        mValues = items;
        mListener = listener;
    }


    public void setRecords(List<RecordItem> items){
        mValues = items;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mPlace_time.setText(formatTitleView(holder.mItem.getPlace(),
                holder.mItem.getTimestamp()));

        holder.setIsRecyclable(false);
        holder.mExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    View view = (LinearLayout) holder.mView.findViewById(R.id.record_detail);

                    if(expandedItems.contains(holder.mItem.getId())){
                        ((ViewGroup) holder.mView).removeView(view);
                        expandedItems.remove(holder.mItem.getId());
                    } else {
                        expandedItems.add(holder.mItem.getId());
                        mListener.onCollapse((ViewGroup) holder.mView, holder.mItem, expandedItems);
                    }
                }
            }
        });

        holder.mPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.playPressed(holder.mItem.getPath_to_record());
            }
        });

    }

    @Override
    public int getItemCount() {
        if(mValues == null) return 0;
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mPlace_time;
        public Button mPlay;
        public Button mExpand;

        public View mRecordDetail;

        public final EditText mType;
        public final EditText mPlace;
        public final EditText mName;
        public final EditText mAge;
        public RecordItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mPlace_time = (TextView) view.findViewById(R.id.place_time);
            mPlay = (Button) view.findViewById(R.id.play);
            mExpand = (Button) view.findViewById(R.id.collapse);

            mRecordDetail = (LinearLayout) view.findViewById(R.id.record_detail);

            mType = (EditText) view.findViewById(R.id.type);
            mPlace = (EditText) view.findViewById(R.id.place);
            mName = (EditText) view.findViewById(R.id.name);
            mAge = (EditText) view.findViewById(R.id.age);
        }

    }

    private String formatTitleView(String place, long timestamp){
        if(place == null) place = "";
        if(place.length() > 10) place = place.substring(0,10) + "...";

        return place + " | " + convertTimeStampToDate(timestamp);
    }
    private String convertTimeStampToDate(long timestamp){
        return  new java.text.SimpleDateFormat(
                "dd/MM/yyyy HH:mm").format(new java.util.Date(timestamp));
    }
}
