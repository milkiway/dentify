package com.junction.healthtech;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.junction.healthtech.adapters.TimelineAdapter;
import com.junction.healthtech.databinding.FragmentHomeBinding;
import com.junction.healthtech.models.TimelineItem;
import com.junction.healthtech.utils.ItemViewHolderSimpleClick;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    private FragmentHomeBinding binding;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<TimelineItem> items = new ArrayList<>();
//        items.add(new TimelineItem("23.11.2017", "Check-up", "2 cavities filled. Next appointment booked"));
//        items.add(new TimelineItem("24.11.2017", "Filling", "Cavities filled"));
//        items.add(new TimelineItem("24.11.2017", "Emergency call", "Root canal. Next appointment booked."));

        items.add(new TimelineItem("24.11.2017", "Filling", "Root canal finished."));
        items.add(new TimelineItem("24.11.2017", "Appointment booked", "To take 3D image"));
        items.add(new TimelineItem("24.11.2017", "3D image taken", "Image is being analysed"));

        items.add(new TimelineItem("25.11.2017", "3D image is ready", "Doctor setting an appointment"));
        items.add(new TimelineItem("25.11.2017", "Appointment options", "Confirmation required"));
        items.add(new TimelineItem("25.11.2017", "Appointment booked", "Confirmed"));

        binding.recyclerView.setAdapter(new TimelineAdapter(getContext(), items, new ItemViewHolderSimpleClick() {
            @Override
            public void onItemClicked(View v, int position) {
                if (position==4) {

                }
            }
        }));

    }
}
