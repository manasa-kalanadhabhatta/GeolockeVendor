 package com.geolocke.android.geolockevendor.polygonalgeofence;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geolocke.android.geolockevendor.R;


 public class ChoosePolygonNextPointFragment extends Fragment {

     public ChoosePolygonNextPointFragment() {
         // Required empty public constructor
     }

     @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
         // Inflate the layout for this fragment
         return inflater.inflate(R.layout.fragment_choose_polygon_next_point, container, false);
     }

 }
