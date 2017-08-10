package com.android.airbnb.main;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.airbnb.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HostCalendarFragment extends Fragment {

    private HostMainActivity hostMainActivity;
    private TextView txtTitle, txtDescription;

    public HostCalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        hostMainActivity = (HostMainActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_host_calendar, container, false);
        setViews(view);
        return view;
    }

    private void setViews(View view){
        txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        txtDescription = (TextView) view.findViewById(R.id.txtDescription);
    }

}
