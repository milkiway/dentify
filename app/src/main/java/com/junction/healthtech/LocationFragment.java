package com.junction.healthtech;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.proximi.proximiiolibrary.ProximiioAPI;
import io.proximi.proximiiolibrary.ProximiioGeofence;
import io.proximi.proximiiolibrary.ProximiioGoogleMapHelper;
import io.proximi.proximiiolibrary.ProximiioListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class LocationFragment extends Fragment {
    @Nullable private ProximiioGoogleMapHelper mapHelper;

    private static final String TAG = "ProximiioDemo";

    public static final String AUTH = "fb8c587b-a2f7-44b1-a1cf-58d011869bc0"; // TODO: Replace with your own!

    private ProximiioAPI proximiioAPI;
    private TextView textView;

    public LocationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_location, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textView = (TextView) view.findViewById(R.id.centerText);

        // Create our Proximi.io listener
        proximiioAPI = new ProximiioAPI("LocationFragment", getContext());
        proximiioAPI.setListener(new ProximiioListener() {
            @Override
            public void geofenceEnter(ProximiioGeofence geofence) {
                //Log.d(TAG, "Geofence enter: " + geofence.getName());
                if(geofence.getName().equals("id1")) {
                    textView.setText("Please go forward 4 meters.");
                    return;
                }
                if(geofence.getName().equals("id11")) {
                    textView.setText("Turn left and continue ahead.");
                    return;

                }
                if(geofence.getName().equals("id2")){
                    textView.setText("Keep going!");
                    return;
                }
                if(geofence.getName().equals("id3")){
                    textView.setText("Turn right and go ahead.");
                    return;
                }
                if(geofence.getName().equals("id4")) {
                    textView.setText("Nice, wait for your appoitment!");
                    return;

                }



            }

            @Override
            public void geofenceExit(ProximiioGeofence geofence, @Nullable Long dwellTime) {
                Log.d("LocationFragment", "Geofence exit: " + geofence.getName() + ", dwell time: " + String.valueOf(dwellTime));



            }

            @Override
            public void loginFailed(LoginError loginError) {
                Log.e("LocationFragment", "LoginError! (" + loginError.toString() + ")");
            }
        });
        //proximiioAPI.setAuth(AUTH);
        proximiioAPI.setLogin("a.levinskis2@gmail.com","pass1234");
        proximiioAPI.setActivity(getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mapHelper != null) {
            mapHelper.destroy();
        }
        proximiioAPI.destroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        proximiioAPI.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        proximiioAPI.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
