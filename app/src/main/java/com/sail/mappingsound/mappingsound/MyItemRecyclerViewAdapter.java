package com.sail.mappingsound.mappingsound;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.sail.mappingsound.mappingsound.model.RecordsData.RecordItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link RecordItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<RecordItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyItemRecyclerViewAdapter(List<RecordItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
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


        holder.mPlace_title.setText(holder.mItem.place);
        holder.mPlace.setText(holder.mItem.place);
        holder.mType.setText(holder.mItem.type);
        holder.mName.setText(holder.mItem.name);
        holder.mAge.setText(""+holder.mItem.age);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.

                    mListener.onListFragmentInteraction(holder.mItem);
                    holder.mCollapsable.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mPlace_title;
        public final View mCollapsable;

        public final EditText mType;
        public final EditText mPlace;
        public final EditText mName;
        public final EditText mAge;
        public RecordItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mPlace_title = (TextView) view.findViewById(R.id.place_record_title);
            mCollapsable = (LinearLayout) view.findViewById(R.id.record_detail);

            mType = (EditText) view.findViewById(R.id.type);
            mPlace = (EditText) view.findViewById(R.id.place);
            mName = (EditText) view.findViewById(R.id.name);
            mAge = (EditText) view.findViewById(R.id.age);
        }

    }
}
