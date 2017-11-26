package com.junction.healthtech.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.junction.healthtech.models.TimelineItem;
import com.junction.healthtech.utils.ItemViewHolderSimpleClick;

import java.util.List;

public class TimelineAdapter extends RecyclerView.Adapter<TimeLineViewHolder> {
    private static final String TAG = TimelineAdapter.class.getSimpleName();
    private final LayoutInflater inflater;
    private final ItemViewHolderSimpleClick listener;
    private List<TimelineItem> items;
    private int selectedPosition = -1;

    public TimelineAdapter(Context context, List<TimelineItem> items, ItemViewHolderSimpleClick listener) {
        inflater = LayoutInflater.from(context);
        this.items = items;
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onBindViewHolder(TimeLineViewHolder viewHolder, int position) {
        TimelineItem item = items.get(position);
        viewHolder.bindTo(item);
}

    @Override
    public TimeLineViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return TimeLineViewHolder.create(inflater, viewGroup, listener);
    }
}