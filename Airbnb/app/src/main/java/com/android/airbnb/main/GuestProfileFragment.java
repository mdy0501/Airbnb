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

import static com.android.airbnb.R.id.guest_main_container;

/**
 * A simple {@link Fragment} subclass.
 */
public class GuestProfileFragment extends Fragment implements View.OnClickListener{

    private GuestMainActivity guestMainActivity;
    private TextView txtName, txtModifyProfile, txtTravelCredit, txtChangeMode, txtReserved, txtSetting, txtHelp, txtFeedback;
    private ImageView imgProfile, imgTravelCredit, imgChangeMode, imgReserved,imgSetting, imgHelp, imgFeedback;
    private ConstraintLayout layoutProfile, layoutTravelCredit, layoutChangeMode, layoutReserved ,layoutSetting, layoutHelp, layoutFeedback;
    private GuestReservedListFragment reservedListFragment;

//    private ProgressDialog changeModeDialog;


    public GuestProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        guestMainActivity = (GuestMainActivity) context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guest_profile, container, false);
        setViews(view);
        setListeners();

//        changeModeDialog = new ProgressDialog(getActivity());
//        changeModeDialog.setTitle("Host 모드로 전환");
//        changeModeDialog.setMessage("Host 모드로 전환하고 있습니다.");
//        changeModeDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        return view;
    }

    private void setViews(View view){
        txtName = (TextView) view.findViewById(R.id.txtName);
        txtModifyProfile = (TextView) view.findViewById(R.id.txtModifyProfile);
        txtTravelCredit = (TextView) view.findViewById(R.id.txtTravelCredit);
        txtChangeMode = (TextView) view.findViewById(R.id.txtChangeMode);
        txtReserved = (TextView) view.findViewById(R.id.txtReserved);
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
        layoutReserved = (ConstraintLayout) view.findViewById(R.id.layoutReserved);
        layoutSetting = (ConstraintLayout) view.findViewById(R.id.layoutSetting);
        layoutHelp = (ConstraintLayout) view.findViewById(R.id.layoutHelp);
        layoutFeedback = (ConstraintLayout) view.findViewById(R.id.layoutFeedback);
    }

    private void setListeners(){
        layoutProfile.setOnClickListener(this);
        layoutTravelCredit.setOnClickListener(this);
        layoutChangeMode.setOnClickListener(this);
        layoutReserved.setOnClickListener(this);
        layoutSetting.setOnClickListener(this);
        layoutHelp.setOnClickListener(this);
        layoutFeedback.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.layoutProfile :
                Toast.makeText(guestMainActivity.getBaseContext(), "Profile 클릭", Toast.LENGTH_SHORT).show();
                break;
            case R.id.layoutTravelCredit :
                Toast.makeText(guestMainActivity.getBaseContext(), "TravelCredit 클릭", Toast.LENGTH_SHORT).show();
                break;
            case R.id.layoutChangeMode :
//                changeModeDialog.show();
                Toast.makeText(getActivity(), "Host 모드로 전환됩니다.", Toast.LENGTH_SHORT).show();
                intent = new Intent(getActivity(), HostMainActivity.class);
                startActivity(intent);
//                getActivity().overridePendingTransition(R.anim.change_mode_slide_in, R.anim.change_mode_step_back);
//                changeModeDialog.dismiss();
                getActivity().finish();
                break;
            case R.id.layoutReserved:
                Toast.makeText(guestMainActivity.getBaseContext(), "예약 확인하기 클릭", Toast.LENGTH_SHORT).show();
                reservedListFragment = new GuestReservedListFragment();
                guestMainActivity.getSupportFragmentManager().beginTransaction().add(guest_main_container, reservedListFragment).commit();
                break;
            case R.id.layoutSetting :
                intent = new Intent(guestMainActivity.getBaseContext(), SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.layoutHelp :
                Toast.makeText(guestMainActivity.getBaseContext(), "Help 클릭", Toast.LENGTH_SHORT).show();
                break;
            case R.id.layoutFeedback :
                Toast.makeText(guestMainActivity.getBaseContext(), "Feedback 클릭", Toast.LENGTH_SHORT).show();
                break;

        }
    }
}
