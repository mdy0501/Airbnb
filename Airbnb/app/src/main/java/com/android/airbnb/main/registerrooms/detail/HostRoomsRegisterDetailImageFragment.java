package com.android.airbnb.main.registerrooms.detail;


import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.airbnb.R;

import static android.app.Activity.RESULT_OK;
import static com.android.airbnb.main.registerrooms.HostRoomsRegisterActivity.hostingHouse;

/**
 * 숙소등록 2-1단계 (숙소사진 등록)
 */
public class HostRoomsRegisterDetailImageFragment extends Fragment implements View.OnClickListener{

    private HostRoomsRegisterDetailActivity hostRoomsRegisterDetailActivity;
    private TextView txtTitle, txtLimit, txtImage;
    private ImageButton ImgBtnRegisterPicture, ImgBtnNext, ImgBtnBack;
    private View view = null;

    public HostRoomsRegisterDetailImageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        hostRoomsRegisterDetailActivity = (HostRoomsRegisterDetailActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(view == null){
            view = inflater.inflate(R.layout.fragment_host_rooms_register_detail_image, container, false);
        }
        setViews(view);
        setListeners();
        return view;
    }

    private void setViews(View view) {
        ImgBtnRegisterPicture = (ImageButton) view.findViewById(R.id.ImgBtnRegisterPicture);
        txtLimit = (TextView) view.findViewById(R.id.txtLimit);
        ImgBtnNext = (ImageButton) view.findViewById(R.id.ImgBtnNext);
        ImgBtnBack = (ImageButton) view.findViewById(R.id.ImgBtnBack);
        txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        txtImage = (TextView) view.findViewById(R.id.txtImage);
    }

    private void setListeners(){
        ImgBtnBack.setOnClickListener(this);
        ImgBtnNext.setOnClickListener(this);
        ImgBtnRegisterPicture.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ImgBtnBack:
                hostRoomsRegisterDetailActivity.onBackPressed();
                break;
            case R.id.ImgBtnNext:
                goHostRoomsRegisterDetailIntroduceFragment();
                break;
            case R.id.ImgBtnRegisterPicture:
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);   // EXTERNAL_CONTENT_URI 에 여러가지가 있는데 그 중에서 이미지들을 가져올 수 있게 해준다.
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                intent.setType("image");
                startActivityForResult( Intent.createChooser(intent, "앱을 선택하세요."), 100);    // 사진앱이 여러개일 경우 선택하게끔 해준다.
                break;
        }
    }

    // startActivityForResult()가 끝나면 호출되는 메소드
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case 100:
                    ClipData clipData = data.getClipData();
                    int count = clipData.getItemCount();
                    for(int i=0 ; i<count ; i++){
                        ClipData.Item item = clipData.getItemAt(i);
                        Uri imageUri = item.getUri(); // image Uri를 저장한다.
                        hostingHouse.uris.add(imageUri);
                        String filePath = getPathFromUri(getActivity(), imageUri);  // Uri에서 실제 경로를 꺼낸다.
                        hostingHouse.filePaths.add(filePath);
                    }
            }

        }
    }

    // Uri에서 실제 경로 꺼내는 함수
    private String getPathFromUri(Context context, Uri uri){
        String realPath = "";
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        if( cursor.moveToNext() ){
            realPath = cursor.getString(cursor.getColumnIndex("_data"));
        }
        cursor.close();

        return realPath;
    }

    private void goHostRoomsRegisterDetailIntroduceFragment() {
        hostRoomsRegisterDetailActivity.getSupportFragmentManager().beginTransaction()
                .add(R.id.detailRoomsRegisterContainer, hostRoomsRegisterDetailActivity.hostRoomsRegisterDetailIntroduceFragment)
                .addToBackStack(null)
                .commit();
    }
}
