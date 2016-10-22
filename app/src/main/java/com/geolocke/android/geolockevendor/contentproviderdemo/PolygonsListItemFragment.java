package com.geolocke.android.geolockevendor.contentproviderdemo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geolocke.android.geolockevendor.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PolygonsListItemFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_polygons_list_item, container, false);
    }

}
