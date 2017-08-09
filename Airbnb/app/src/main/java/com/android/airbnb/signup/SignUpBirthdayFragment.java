package com.android.airbnb.signup;


import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.android.airbnb.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpBirthdayFragment extends Fragment implements View.OnClickListener{

    private SignUpActivity signUpActivity;
    private Button btnPreviousBirthday, btnNextBirthday;
    private TextView txtTitle, txtDescription, txtBirthday, txtBirthdaySetting;


    public SignUpBirthdayFragment() {
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
        View view = inflater.inflate(R.layout.fragment_sign_up_birthday, container, false);
        setViews(view);
        setListeners();
        return view;
    }

    private void setListeners() {
        btnPreviousBirthday.setOnClickListener(this);
        btnNextBirthday.setOnClickListener(this);
        txtBirthdaySetting.setOnClickListener(this);
    }

    private void setViews(View view) {
        btnPreviousBirthday = (Button) view.findViewById(R.id.btnPreviousBirthday);
        btnNextBirthday = (Button) view.findViewById(R.id.btnNextBirthday);
        txtTitle = (TextView) view.findViewById(R.id.txtTitle1);
        txtDescription = (TextView) view.findViewById(R.id.txtTitle);
        txtBirthday = (TextView) view.findViewById(R.id.txtBirthday);
        txtBirthdaySetting = (TextView) view.findViewById(R.id.txtBirthdaySetting);
        txtBirthdaySetting.setText("_ _  / _ _  / _ _ _ _");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnPreviousBirthday :
                signUpActivity.onBackPressed();
                break;
            case R.id.btnNextBirthday :
                goSignUpBeforeFragment();
                break;
            case R.id.txtBirthdaySetting :
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), dateSetListener, 1989, 4, 1);
                dialog.show();
                break;
        }
    }

    // 생년월일 설정 - DatePickerDialog
    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            int realMonth = month+1;
            String theDay = String.format("%d-%02d-%02d", year, realMonth, dayOfMonth);
            txtBirthdaySetting.setText(theDay);
            signUpActivity.signUpData.setBirthday(theDay);
        }
    };

    // move SignUpPasswordFragment
    private void goSignUpBeforeFragment() {
        signUpActivity.getSupportFragmentManager().beginTransaction()
                .add(R.id.container, signUpActivity.signUpBeforeFragment)
                .addToBackStack(null)
                .commit();
    }
}
