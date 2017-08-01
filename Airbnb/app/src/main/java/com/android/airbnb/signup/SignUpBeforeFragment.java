package com.android.airbnb.signup;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.airbnb.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpBeforeFragment extends Fragment implements View.OnClickListener{

    private SignUpActivity signUpActivity;
    private Button btnAccept, btnRefuse;
    private TextView txtTitle, txtDetail, txtDescription1, txtDescription2;


    public SignUpBeforeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        signUpActivity = (SignUpActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up_before, container, false);
        setViews(view);
        setListeners();
        return view;
    }

    private void setViews(View view){
        btnAccept = (Button) view.findViewById(R.id.btnAccept);
        btnRefuse = (Button) view.findViewById(R.id.btnRefuse);
        txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        txtDetail = (TextView) view.findViewById(R.id.txtDetail);
        txtDescription1 = (TextView) view.findViewById(R.id.txtDescription1);
        txtDescription2 = (TextView) view.findViewById(R.id.txtDescription2);
    }

    private void setListeners(){
        btnAccept.setOnClickListener(this);
        btnRefuse.setOnClickListener(this);
        txtDetail.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAccept :

                break;
            case R.id.btnRefuse :

                break;
            case R.id.txtDetail :
                goSignUpBeforeDetailFragment();
                break;
        }
    }

    private void goSignUpBeforeDetailFragment() {
        signUpActivity.getSupportFragmentManager().beginTransaction()
                .add(R.id.container, signUpActivity.signUpBeforeDetailFragment)
                .addToBackStack(null)
                .commit();
    }
}
