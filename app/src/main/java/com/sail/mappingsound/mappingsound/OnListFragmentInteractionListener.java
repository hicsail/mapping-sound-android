package com.sail.mappingsound.mappingsound;

import android.view.ViewGroup;

import com.sail.mappingsound.mappingsound.model.RecordItem;

import java.util.HashSet;

public interface OnListFragmentInteractionListener {
    void onSaveEdit(RecordItem oldItem, RecordItem newItem);
    void onCollapse(ViewGroup viewGroup, RecordItem recordItem, HashSet<Integer> expandableItems);
    void playPressed(String filename);
}
