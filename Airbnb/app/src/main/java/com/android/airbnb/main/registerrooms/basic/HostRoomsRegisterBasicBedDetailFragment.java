package com.android.airbnb.main.registerrooms.basic;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.airbnb.R;
import com.android.airbnb.domain.register.Bed;

import static com.android.airbnb.main.registerrooms.HostRoomsRegisterActivity.hostingHouse;

/**
 * A simple {@link Fragment} subclass.
 */
public class HostRoomsRegisterBasicBedDetailFragment extends Fragment implements View.OnClickListener{

    private HostRoomsRegisterBasicActivity hostRoomsRegisterBasicActivity;
    private TextView txtTitle, txtKingTitle, txtKingResult, txtQueenTitle, txtQueenResult, txtDoubleTitle, txtDoubleResult, txtSingleTitle, txtSingleResult;
    private ImageButton ImgBtnKingPlus, ImgBtnKingMinus, ImgBtnQueenPlus, ImgBtnQueenMinus, ImgBtnDoublePlus, ImgBtnDoubleMinus, ImgBtnSinglePlus, ImgBtnSingleMinus, ImgBtnCancel, ImgBtnSave;
    private Bed bed;
    private View view = null;

    public HostRoomsRegisterBasicBedDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        hostRoomsRegisterBasicActivity = (HostRoomsRegisterBasicActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(view == null){
            view = inflater.inflate(R.layout.fragment_host_rooms_register_basic_bed_detail, container, false);
        }
        setViews(view);
        setListeners();
        init();
        return view;
    }

    private void init(){
        bed = new Bed();
        txtKingResult.setText(bed.getKingBed()+"");
        txtQueenResult.setText(bed.getQueenBed()+"");
        txtDoubleResult.setText(bed.getDoubleBed()+"");
        txtSingleResult.setText(bed.getSingleBed()+"");
    }

    private void setViews(View view) {
        ImgBtnSingleMinus = (ImageButton) view.findViewById(R.id.ImgBtnSingleMinus);
        txtSingleResult = (TextView) view.findViewById(R.id.txtSingleResult);
        ImgBtnSinglePlus = (ImageButton) view.findViewById(R.id.ImgBtnSinglePlus);
        txtSingleTitle = (TextView) view.findViewById(R.id.txtSingleTitle);
        ImgBtnDoubleMinus = (ImageButton) view.findViewById(R.id.ImgBtnDoubleMinus);
        txtDoubleResult = (TextView) view.findViewById(R.id.txtDoubleResult);
        ImgBtnDoublePlus = (ImageButton) view.findViewById(R.id.ImgBtnDoublePlus);
        txtDoubleTitle = (TextView) view.findViewById(R.id.txtDoubleTitle);
        txtQueenResult = (TextView) view.findViewById(R.id.txtQueenResult);
        ImgBtnQueenMinus = (ImageButton) view.findViewById(R.id.ImgBtnQueenMinus);
        ImgBtnQueenPlus = (ImageButton) view.findViewById(R.id.ImgBtnQueenPlus);
        txtQueenTitle = (TextView) view.findViewById(R.id.txtQueenTitle);
        txtKingResult = (TextView) view.findViewById(R.id.txtKingResult);
        ImgBtnKingMinus = (ImageButton) view.findViewById(R.id.ImgBtnKingMinus);
        ImgBtnKingPlus = (ImageButton) view.findViewById(R.id.ImgBtnKingPlus);
        ImgBtnSave = (ImageButton) view.findViewById(R.id.ImgBtnSave);
        txtKingTitle = (TextView) view.findViewById(R.id.txtKingTitle);
        ImgBtnCancel = (ImageButton) view.findViewById(R.id.ImgBtnCancel);
        txtTitle = (TextView) view.findViewById(R.id.txtTitle);
    }

    private void setListeners(){
        ImgBtnCancel.setOnClickListener(this);
        ImgBtnSave.setOnClickListener(this);
        ImgBtnKingPlus.setOnClickListener(this);
        ImgBtnKingMinus.setOnClickListener(this);
        ImgBtnQueenPlus.setOnClickListener(this);
        ImgBtnQueenMinus.setOnClickListener(this);
        ImgBtnDoublePlus.setOnClickListener(this);
        ImgBtnDoubleMinus.setOnClickListener(this);
        ImgBtnSinglePlus.setOnClickListener(this);
        ImgBtnSingleMinus.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ImgBtnCancel:
                hostRoomsRegisterBasicActivity.onBackPressed();
                break;
            case R.id.ImgBtnSave:
                hostingHouse.setKingBeds(txtKingResult.getText().toString());
                hostingHouse.setQueenBeds(txtQueenResult.getText().toString());
                hostingHouse.setDoubleBeds(txtDoubleResult.getText().toString());
                hostingHouse.setSingleBeds(txtSingleResult.getText().toString());
                Log.e("킹사이즈 침대 :: " , txtKingResult.getText().toString());
                Log.e("퀸사이즈 침대 :: " , txtQueenResult.getText().toString());
                Log.e("더블사이즈 침대 :: " , txtDoubleResult.getText().toString());
                Log.e("싱글사이즈 침대 :: " , txtSingleResult.getText().toString());
//                hostRoomsRegisterBasicActivity.onBackPressed();
                hostRoomsRegisterBasicActivity.getSupportFragmentManager().popBackStack();
                hostRoomsRegisterBasicActivity.getSupportFragmentManager().beginTransaction()
                        .add(R.id.basicRoomsRegisterContainer, hostRoomsRegisterBasicActivity.hostRoomsRegisterBasicBedFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.ImgBtnKingPlus:
                bed.setKingBed(bed.getKingBed()+1);
                txtKingResult.setText(bed.getKingBed()+"");
                break;
            case R.id.ImgBtnKingMinus:
                bed.setKingBed(bed.getKingBed()-1);
                txtKingResult.setText(bed.getKingBed()+"");
                break;
            case R.id.ImgBtnQueenPlus:
                bed.setQueenBed(bed.getQueenBed()+1);
                txtQueenResult.setText(bed.getQueenBed()+"");
                break;
            case R.id.ImgBtnQueenMinus:
                bed.setQueenBed(bed.getQueenBed()-1);
                txtQueenResult.setText(bed.getQueenBed()+"");
                break;
            case R.id.ImgBtnDoublePlus:
                bed.setDoubleBed(bed.getDoubleBed()+1);
                txtDoubleResult.setText(bed.getDoubleBed()+"");
                break;
            case R.id.ImgBtnDoubleMinus:
                bed.setDoubleBed(bed.getDoubleBed()-1);
                txtDoubleResult.setText(bed.getDoubleBed()+"");
                break;
            case R.id.ImgBtnSinglePlus:
                bed.setSingleBed(bed.getSingleBed()+1);
                txtSingleResult.setText(bed.getSingleBed()+"");
                break;
            case R.id.ImgBtnSingleMinus:
                bed.setSingleBed(bed.getSingleBed()-1);
                txtSingleResult.setText(bed.getSingleBed()+"");
                break;
        }
    }
}
