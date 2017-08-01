package com.android.airbnb.signup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.android.airbnb.R;

public class SignUpActivity extends AppCompatActivity {

    SignUpNameFragment signUpNameFragment;
    SignUpEmailFragment signUpEmailFragment;
    SignUpPasswordFragment signUpPasswordFragment;
    SignUpBirthdayFragment signUpBirthdayFragment;
    SignUpBeforeFragment signUpBeforeFragment;
    SignUpBeforeDetailFragment signUpBeforeDetailFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setFragments();
        addFirstFragment();
    }

    private void addFirstFragment(){
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, signUpNameFragment)
                .commit();
    }

    private void setFragments(){
        signUpNameFragment = new SignUpNameFragment();
        signUpEmailFragment = new SignUpEmailFragment();
        signUpPasswordFragment = new SignUpPasswordFragment();
        signUpBirthdayFragment = new SignUpBirthdayFragment();
        signUpBeforeFragment = new SignUpBeforeFragment();
        signUpBeforeDetailFragment = new SignUpBeforeDetailFragment();
    }


}
