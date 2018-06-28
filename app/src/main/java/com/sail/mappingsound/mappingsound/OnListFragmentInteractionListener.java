package com.sail.mappingsound.mappingsound;

import android.view.ViewGroup;

import com.sail.mappingsound.mappingsound.model.RecordItem;

public interface OnListFragmentInteractionListener {
    void onSaveEdit(RecordItem oldItem, RecordItem newItem);
    void onCollapse(ViewGroup viewGroup, RecordItem recordItem);
}
