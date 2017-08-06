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
public class SignUpBeforeDetailFragment extends Fragment implements View.OnClickListener{

    private SignUpActivity signUpActivity;
    private Button btnPrevious;
    private TextView txtTitle, txtQuestion1, txtAnswer1;


    public SignUpBeforeDetailFragment() {
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
        View view = inflater.inflate(R.layout.fragment_sign_up_before_detail, container, false);
        setViews(view);
        setListeners();
        return view;
    }

    private void setViews(View view){
        btnPrevious = (Button) view.findViewById(R.id.btnPreviousLogin);
        txtTitle = (TextView) view.findViewById(R.id.txtTitle1);
        txtQuestion1 = (TextView) view.findViewById(R.id.txtQuestion1);
        txtAnswer1 = (TextView) view.findViewById(R.id.txtAnswer1);
    }

    private void setListeners(){
        btnPrevious.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        signUpActivity.onBackPressed();
    }
}
