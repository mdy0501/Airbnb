package com.android.airbnb.main;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.airbnb.R;

import static com.android.airbnb.R.id.guest_main_container;

/**
 * A simple {@link Fragment} subclass.
 */
public class GuestMessageNoticeFragment extends Fragment implements View.OnClickListener {

    private GuestMessageFragment guestMessageFragment;

    private GuestMainActivity guestMainActivity;
    private TextView txtTitle, txtContent1, txtContent2;
    private ImageButton imgBtnBack;

    public GuestMessageNoticeFragment() {
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
        View view = inflater.inflate(R.layout.fragment_guest_message_notice, container, false);
        setFragments();
        setViews(view);
        setListeners();
        return view;
    }

    private void setViews(View view){
        txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        txtContent1 = (TextView) view.findViewById(R.id.txtContent1);
        txtContent2 = (TextView) view.findViewById(R.id.txtContent2);
        imgBtnBack = (ImageButton) view.findViewById(R.id.ImgBtnCancel);
    }

    private void setListeners(){
        imgBtnBack.setOnClickListener(this);
    }

    private void setFragments(){
        guestMessageFragment = new GuestMessageFragment();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ImgBtnCancel:
                guestMainActivity.getSupportFragmentManager().beginTransaction()
                        .replace(guest_main_container, guestMessageFragment)
                        .commit();
                break;
        }
    }
}
