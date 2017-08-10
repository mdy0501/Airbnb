package com.android.airbnb.main;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.airbnb.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HostProfileFragment extends Fragment implements View.OnClickListener{

    private HostMainActivity hostMainActivity;
    private TextView txtName, txtModifyProfile, txtTravelCredit, txtChangeMode, txtSetting, txtHelp, txtFeedback;
    private ImageView imgProfile, imgTravelCredit, imgChangeMode, imgSetting, imgHelp, imgFeedback;
    private ConstraintLayout layoutProfile, layoutTravelCredit, layoutChangeMode, layoutSetting, layoutHelp, layoutFeedback;


    public HostProfileFragment() {
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
        View view = inflater.inflate(R.layout.fragment_host_profile, container, false);
        setViews(view);
        setListeners();
        return view;
    }

    private void setViews(View view){
        txtName = (TextView) view.findViewById(R.id.txtName);
        txtModifyProfile = (TextView) view.findViewById(R.id.txtModifyProfile);
        txtTravelCredit = (TextView) view.findViewById(R.id.txtTravelCredit);
        txtChangeMode = (TextView) view.findViewById(R.id.txtChangeMode);
        txtSetting = (TextView) view.findViewById(R.id.txtSetting);
        txtHelp = (TextView) view.findViewById(R.id.txtHelp);
        txtFeedback = (TextView) view.findViewById(R.id.txtFeedback);
        imgProfile = (ImageView) view.findViewById(R.id.imgProfile);
        imgTravelCredit = (ImageView) view.findViewById(R.id.imgTravelCredit);
        imgChangeMode = (ImageView) view.findViewById(R.id.imgChangeMode);
        imgSetting = (ImageView) view.findViewById(R.id.imgSetting);
        imgHelp = (ImageView) view.findViewById(R.id.imgHelp);
        imgFeedback = (ImageView) view.findViewById(R.id.imgFeedback);
        layoutProfile = (ConstraintLayout) view.findViewById(R.id.layoutProfile);
        layoutTravelCredit = (ConstraintLayout) view.findViewById(R.id.layoutTravelCredit);
        layoutChangeMode = (ConstraintLayout) view.findViewById(R.id.layoutChangeMode);
        layoutSetting = (ConstraintLayout) view.findViewById(R.id.layoutSetting);
        layoutHelp = (ConstraintLayout) view.findViewById(R.id.layoutHelp);
        layoutFeedback = (ConstraintLayout) view.findViewById(R.id.layoutFeedback);
    }

    private void setListeners(){
        layoutProfile.setOnClickListener(this);
        layoutTravelCredit.setOnClickListener(this);
        layoutChangeMode.setOnClickListener(this);
        layoutSetting.setOnClickListener(this);
        layoutHelp.setOnClickListener(this);
        layoutFeedback.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.layoutProfile :
                Toast.makeText(hostMainActivity.getBaseContext(), "Profile 클릭", Toast.LENGTH_SHORT).show();
                break;
            case R.id.layoutTravelCredit :
                Toast.makeText(hostMainActivity.getBaseContext(), "TravelCredit 클릭", Toast.LENGTH_SHORT).show();
                break;
            case R.id.layoutChangeMode :
                Toast.makeText(hostMainActivity.getBaseContext(), "Guest 모드로 전환됩니다.", Toast.LENGTH_SHORT).show();
                intent = new Intent(getActivity(), GuestMainActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
            case R.id.layoutSetting :
                intent = new Intent(hostMainActivity.getBaseContext(), SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.layoutHelp :
                Toast.makeText(hostMainActivity.getBaseContext(), "Help 클릭", Toast.LENGTH_SHORT).show();
                break;
            case R.id.layoutFeedback :
                Toast.makeText(hostMainActivity.getBaseContext(), "Feedback 클릭", Toast.LENGTH_SHORT).show();
                break;

        }
    }

}
