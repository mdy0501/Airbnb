package com.android.airbnb.signup;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.airbnb.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpNameFragment extends Fragment implements View.OnClickListener{

    private SignUpActivity signUpActivity;
    private Button btnPreviousName, btnNextName;
    private TextView txtFirstName, txtLastName;
    private EditText editFirstName, editLastName;

    public SignUpNameFragment() {
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
        View view = inflater.inflate(R.layout.fragment_sign_up_name, container, false);
        setViews(view);
        setListeners();
        return view;
    }

    private void setViews(View view){
        btnPreviousName = (Button) view.findViewById(R.id.btnPreviousName);
        btnNextName = (Button) view.findViewById(R.id.btnNextName);
        txtFirstName = (TextView) view.findViewById(R.id.txtFirstName);
        txtLastName = (TextView) view.findViewById(R.id.txtLastName);
        editFirstName = (EditText) view.findViewById(R.id.editFirstName);
        editLastName = (EditText) view.findViewById(R.id.editLastName);
    }

    private void setListeners(){
        btnPreviousName.setOnClickListener(this);
        btnNextName.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnPreviousName : // move Previous
                signUpActivity.onBackPressed();
                break;
            case R.id.btnNextName : // move NextStep
                signUpActivity.signUpData.setFirst_name(editFirstName.getText().toString());
                signUpActivity.signUpData.setLast_name(editLastName.getText().toString());
                goSignUpEmailFragment();
                break;
        }
    }

    // move SignUpEmailFragment
    private void goSignUpEmailFragment(){
        signUpActivity.getSupportFragmentManager().beginTransaction()
                .add(R.id.container, signUpActivity.signUpEmailFragment)
                .addToBackStack(null)
                .commit();
    }
}
