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
import android.widget.ToggleButton;

import com.android.airbnb.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpEmailFragment extends Fragment implements View.OnClickListener{

    private SignUpActivity signUpActivity;
    private Button btnPreviousEmail, btnNextEmail;
    private TextView txtTitle, txtEmail, txtDescription;
    private EditText editEmail;
    private ToggleButton btnToggle;


    public SignUpEmailFragment() {
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
        View view = inflater.inflate(R.layout.fragment_sign_up_email, container, false);
        setViews(view);
        setListeners();
        return view;
    }

    private void setViews(View view){
        btnPreviousEmail = (Button) view.findViewById(R.id.btnPreviousEmail);
        btnNextEmail = (Button) view.findViewById(R.id.btnNextEmail);
        btnToggle = (ToggleButton) view.findViewById(R.id.btnToggle);
        txtTitle = (TextView) view.findViewById(R.id.txtTitle1);
        txtEmail = (TextView) view.findViewById(R.id.txtEmail);
        txtDescription = (TextView) view.findViewById(R.id.txtIntroduce);
        editEmail = (EditText) view.findViewById(R.id.editEmail);


    }

    private void setListeners(){
        btnPreviousEmail.setOnClickListener(this);
        btnNextEmail.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnPreviousEmail :
                signUpActivity.onBackPressed();
                break;
            case R.id.btnNextEmail :
                goSignUpPasswordFragment();
                break;
        }
    }

    // move SignUpPasswordFragment
    private void goSignUpPasswordFragment() {
        signUpActivity.getSupportFragmentManager().beginTransaction()
                .add(R.id.container, signUpActivity.signUpPasswordFragment)
                .addToBackStack(null)
                .commit();
    }

}
