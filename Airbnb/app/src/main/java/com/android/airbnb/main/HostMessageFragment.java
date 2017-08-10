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

import static com.android.airbnb.R.id.host_main_container;

/**
 * A simple {@link Fragment} subclass.
 */
public class HostMessageFragment extends Fragment implements View.OnClickListener{

    private HostMessageNoticeFragment hostMessageNoticeFragment;
    private HostMessageStorageFragment hostMessageStorageFragment;

    private HostMainActivity hostMainActivity;
    private TextView txtTitle, txtDescription;
    private ImageButton imgBtnNotice, imgBtnStorage;

    public HostMessageFragment() {
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
        View view = inflater.inflate(R.layout.fragment_host_message, container, false);
        setFragments();
        setViews(view);
        setListeners();
        return view;
    }

    private void setViews(View view){
        txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        txtDescription = (TextView) view.findViewById(R.id.txtDescription);
        imgBtnNotice = (ImageButton) view.findViewById(R.id.imgBtnNotice);
        imgBtnStorage = (ImageButton) view.findViewById(R.id.imgBtnStorage);
    }

    private void setListeners(){
        imgBtnNotice.setOnClickListener(this);
        imgBtnStorage.setOnClickListener(this);
    }

    private void setFragments(){
        hostMessageNoticeFragment = new HostMessageNoticeFragment();
        hostMessageStorageFragment = new HostMessageStorageFragment();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgBtnStorage:
                hostMainActivity.getSupportFragmentManager().beginTransaction()
                        .add(host_main_container, hostMessageStorageFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.imgBtnNotice:
                hostMainActivity.getSupportFragmentManager().beginTransaction()
                        .add(host_main_container, hostMessageNoticeFragment)
                        .addToBackStack(null)
                        .commit();
                break;

        }

    }
}
