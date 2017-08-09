package com.android.airbnb.signup;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
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
        txtDescription = (TextView) view.findViewById(R.id.txtTitle);
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
                confirmEmail();
                break;
        }
    }

    // Email 형식 유효성 검사
    private void confirmEmail(){
        String email = editEmail.getText().toString();
        boolean emailValidate = Patterns.EMAIL_ADDRESS.matcher(email).matches();
        if(emailValidate == true){
            signUpActivity.signUpData.setEmail(editEmail.getText().toString());
            goSignUpPasswordFragment();
        } else {
            Toast.makeText(signUpActivity.getBaseContext(), "이메일 형식에 맞지 않습니다.", Toast.LENGTH_SHORT).show();
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
