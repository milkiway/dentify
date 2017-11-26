package com.junction.healthtech.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.junction.healthtech.databinding.ItemTimelineBinding;
import com.junction.healthtech.models.TimelineItem;
import com.junction.healthtech.utils.ItemViewHolderSimpleClick;

public class TimeLineViewHolder extends RecyclerView.ViewHolder{
    private ItemTimelineBinding binding;
    private final ItemViewHolderSimpleClick listener;

    static TimeLineViewHolder create(LayoutInflater inflater, ViewGroup parent, ItemViewHolderSimpleClick listener) {
        ItemTimelineBinding binding = ItemTimelineBinding
                .inflate(inflater, parent, false);
        return new TimeLineViewHolder(binding, listener);
    }

    private TimeLineViewHolder(ItemTimelineBinding binding, ItemViewHolderSimpleClick listener) {
        super(binding.getRoot());
        this.binding = binding;
        this.listener = listener;
    }
    public void bindTo(TimelineItem item) {
        binding.getRoot().setOnClickListener(v->listener.onItemClicked(v, getLayoutPosition()));
        binding.title.setText(item.getTitle());
        binding.date.setText(item.getDate());
        binding.info.setText(item.getInfo());
        binding.executePendingBindings();
    }
}