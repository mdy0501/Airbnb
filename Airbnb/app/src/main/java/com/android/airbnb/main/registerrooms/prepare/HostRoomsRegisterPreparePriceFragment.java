package com.android.airbnb.main.registerrooms.prepare;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.airbnb.R;

import static com.android.airbnb.main.registerrooms.HostRoomsRegisterActivity.hostingHouse;

/**
 * A simple {@link Fragment} subclass.
 */
public class HostRoomsRegisterPreparePriceFragment extends Fragment implements View.OnClickListener{

    private HostRoomsRegisterPrepareActivity hostRoomsRegisterPrepareActivity;
    private EditText editPricePerDay, editExtraPeopleFee, editCleaningFee, editWeeklyDiscount;
    private ImageButton ImgBtnBack, ImgBtnNext;
    private TextView txtTitle, textView1, textView2, textView3, textView4, textView5, textView6, textView7, textView8;
    private View view = null;

    public HostRoomsRegisterPreparePriceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        hostRoomsRegisterPrepareActivity = (HostRoomsRegisterPrepareActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(view == null){
            view = inflater.inflate(R.layout.fragment_host_rooms_register_prepare_price, container, false);
        }
        setViews(view);
        setListeners();
        return view;
    }

    private void setViews(View view) {
        editWeeklyDiscount = (EditText) view.findViewById(R.id.editWeeklyDiscount);
        editCleaningFee = (EditText) view.findViewById(R.id.editCleaningFee);
        editExtraPeopleFee = (EditText) view.findViewById(R.id.editExtraPeopleFee);
        editPricePerDay = (EditText) view.findViewById(R.id.editPricePerDay);
        textView1 = (TextView) view.findViewById(R.id.textView1);
        textView2 = (TextView) view.findViewById(R.id.textView2);
        textView3 = (TextView) view.findViewById(R.id.textView3);
        textView4 = (TextView) view.findViewById(R.id.textView4);
        textView5 = (TextView) view.findViewById(R.id.textView5);
        textView6 = (TextView) view.findViewById(R.id.textView6);
        textView7 = (TextView) view.findViewById(R.id.textView7);
        textView8 = (TextView) view.findViewById(R.id.textView8);
        ImgBtnNext = (ImageButton) view.findViewById(R.id.ImgBtnNext);
        ImgBtnBack = (ImageButton) view.findViewById(R.id.ImgBtnBack);
        txtTitle = (TextView) view.findViewById(R.id.txtTitle);
    }

    private void setListeners(){
        ImgBtnBack.setOnClickListener(this);
        ImgBtnNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ImgBtnBack:
                hostRoomsRegisterPrepareActivity.onBackPressed();
                break;
            case R.id.ImgBtnNext:
                hostingHouse.setPrice_per_day(editPricePerDay.getText().toString());
                hostingHouse.setExtra_people_fee(editExtraPeopleFee.getText().toString());
                hostingHouse.setCleaning_fee(editCleaningFee.getText().toString());
                hostingHouse.setWeekly_discount(editWeeklyDiscount.getText().toString());
                getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.prepareRoomsRegisterContainer)).commit();
                getActivity().finish();
//                Intent intent = new Intent(getActivity(), HostRoomsRegisterActivity.class);
//                startActivity(intent);
                break;
        }
    }

}
