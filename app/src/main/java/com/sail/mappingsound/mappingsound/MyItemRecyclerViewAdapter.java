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

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link RecordItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private List<RecordItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyItemRecyclerViewAdapter(List<RecordItem> items, OnListFragmentInteractionListener listener) {
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
        holder.mPlaceTitle.setText(holder.mItem.getPlace());

        holder.mExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    if(holder.mRecordDetail == null) {
                        mListener.onCollapse((ViewGroup) holder.mView, holder.mItem);
                        holder.mRecordDetail = (LinearLayout) holder.mView.
                                findViewById(R.id.record_detail);
                    }
                    else {
                        ((ViewGroup) holder.mView).removeView(holder.mRecordDetail);
                        holder.mRecordDetail = null;
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
        public final TextView mPlaceTitle;
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
            mPlaceTitle = (TextView) view.findViewById(R.id.place_record_title);
            mPlay = (Button) view.findViewById(R.id.play);
            mExpand = (Button) view.findViewById(R.id.collapse);

            mRecordDetail = (LinearLayout) view.findViewById(R.id.record_detail);

            mType = (EditText) view.findViewById(R.id.type);
            mPlace = (EditText) view.findViewById(R.id.place);
            mName = (EditText) view.findViewById(R.id.name);
            mAge = (EditText) view.findViewById(R.id.age);
        }

    }
}
