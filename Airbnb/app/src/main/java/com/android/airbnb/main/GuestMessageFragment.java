package com.android.airbnb.main;


import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.airbnb.R;
import com.android.airbnb.util.GlideApp;

import static com.android.airbnb.R.id.guest_main_container;

/**
 * A simple {@link Fragment} subclass.
 */
public class GuestMessageFragment extends Fragment implements View.OnClickListener{

    private GuestMessageNoticeFragment guestMessageNoticeFragment;
    private GuestMessageStorageFragment guestMessageStorageFragment;

    private GuestMainActivity guestMainActivity;
    private TextView txtTitle, txtMessageTitle, txtMessageContent, txtMessageQuestion;
    private ConstraintLayout layoutMessage;
    private ImageView imgProfile;
    private ImageButton imgBtnNotice, imgBtnStorage;


    public GuestMessageFragment() {
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
        View view = inflater.inflate(R.layout.fragment_guest_message, container, false);
        setFragments();
        setViews(view);
        setListeners();
        return view;
    }

    private void setViews(View view){
        txtTitle = (TextView) view.findViewById(R.id.txtTitle1);
        txtMessageTitle = (TextView) view.findViewById(R.id.txtMessageTitle);
        txtMessageContent = (TextView) view.findViewById(R.id.txtMessageContent);
        txtMessageQuestion = (TextView) view.findViewById(R.id.txtMessageQuestion);
        imgProfile = (ImageView) view.findViewById(R.id.imgProfile);
        GlideApp
                .with(guestMainActivity.getBaseContext())
                .load(R.drawable.douwe)
                .circleCrop()
                .into(imgProfile);
        imgBtnNotice = (ImageButton) view.findViewById(R.id.imgBtnNotice);
        imgBtnStorage = (ImageButton) view.findViewById(R.id.imgBtnStorage);
        layoutMessage = (ConstraintLayout) view.findViewById(R.id.layoutMessage);
    }

    private void setListeners(){
        imgBtnNotice.setOnClickListener(this);
        imgBtnStorage.setOnClickListener(this);
        layoutMessage.setOnClickListener(this);
    }

    private void setFragments(){
        guestMessageNoticeFragment = new GuestMessageNoticeFragment();
        guestMessageStorageFragment = new GuestMessageStorageFragment();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgBtnStorage:
                guestMainActivity.getSupportFragmentManager().beginTransaction()
                        .add(guest_main_container, guestMessageStorageFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.imgBtnNotice:
                guestMainActivity.getSupportFragmentManager().beginTransaction()
                        .add(guest_main_container, guestMessageNoticeFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.layoutMessage :
                Toast.makeText(guestMainActivity.getBaseContext(), "메시지 클릭", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
