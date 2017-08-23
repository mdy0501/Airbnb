package com.android.airbnb.main.registerrooms;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.airbnb.R;
import com.android.airbnb.data.ApiService;
import com.android.airbnb.domain.airbnb.HostingHouse;
import com.android.airbnb.main.registerrooms.basic.HostRoomsRegisterBasicActivity;
import com.android.airbnb.main.registerrooms.detail.HostRoomsRegisterDetailActivity;
import com.android.airbnb.main.registerrooms.prepare.HostRoomsRegisterPrepareActivity;
import com.android.airbnb.util.PreferenceUtil;
import com.android.airbnb.util.Remote.IServerApi;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HostRoomsRegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private ConstraintLayout layoutTitle, layoutBasic, layoutDetail, layoutPrepare;
    private ImageButton imgBtnBack, imgBtnBasic, imgBtnDetail, imgBtnPrepare;
    private TextView txtTitle, txtBasicTitle, txtBasicContent, txtDetailTitle, txtDetailContent, txtPrepareTitle, txtPrepareContent;
    private Button btnRegisterRooms;

    Retrofit retrofit;
    IServerApi iServerApi;
    private Bitmap bitmap, rotateBitmap;

    // HostingHouse Singleton으로 인스턴스 생성
    public static HostingHouse hostingHouse = HostingHouse.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_rooms_register);
        setViews();
        setListeners();


    }

    private void setViews() {
        layoutPrepare = (ConstraintLayout) findViewById(R.id.layoutPrepare);
        imgBtnPrepare = (ImageButton) findViewById(R.id.imgBtnPrepare);
        txtPrepareContent = (TextView) findViewById(R.id.txtTypeDescription);
        txtPrepareTitle = (TextView) findViewById(R.id.txtTypeTitle);
        layoutDetail = (ConstraintLayout) findViewById(R.id.layoutDescription);
        imgBtnDetail = (ImageButton) findViewById(R.id.imgBtnDetail);
        txtDetailContent = (TextView) findViewById(R.id.txtDetailContent);
        txtDetailTitle = (TextView) findViewById(R.id.txtKingTitle);
        layoutBasic = (ConstraintLayout) findViewById(R.id.layoutBasic);
        imgBtnBasic = (ImageButton) findViewById(R.id.imgBtnBasic);
        txtBasicContent = (TextView) findViewById(R.id.txtBasicContent);
        txtBasicTitle = (TextView) findViewById(R.id.txtBasicTitle);
        layoutTitle = (ConstraintLayout) findViewById(R.id.layoutTitle);
        imgBtnBack = (ImageButton) findViewById(R.id.ImgBtnCancel);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        btnRegisterRooms = (Button) findViewById(R.id.btnRegisterRooms);
    }

    private void setListeners() {
        imgBtnBack.setOnClickListener(this);
        imgBtnBasic.setOnClickListener(this);
        imgBtnDetail.setOnClickListener(this);
        imgBtnPrepare.setOnClickListener(this);
        btnRegisterRooms.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.ImgBtnCancel:
                finish();
                break;
            case R.id.imgBtnBasic:
                intent = new Intent(this, HostRoomsRegisterBasicActivity.class);
                startActivity(intent);
                break;
            case R.id.imgBtnDetail:
                intent = new Intent(this, HostRoomsRegisterDetailActivity.class);
                startActivity(intent);
                break;
            case R.id.imgBtnPrepare:
                intent = new Intent(this, HostRoomsRegisterPrepareActivity.class);
                startActivity(intent);
                break;
            case R.id.btnRegisterRooms:
                Toast.makeText(this, "숙소등록 버튼 클릭!", Toast.LENGTH_SHORT).show();

                String totalAmenities = "";
                for(String key : hostingHouse.facilitiesMap.keySet()){
                    totalAmenities = totalAmenities + ", " + hostingHouse.facilitiesMap.get(key);
                }

                if(totalAmenities != null && !"".equals(totalAmenities)){
                    totalAmenities = totalAmenities.substring(2, totalAmenities.length());
                }

                hostingHouse.setAmenities(totalAmenities);
                Log.e("편의시설 변수", totalAmenities + " 편의시설 ");
                postRegiserRooms();
                break;
        }
    }

    private void postRegiserRooms(){
        retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        iServerApi = retrofit.create(IServerApi.class);

        Log.e("title ::", hostingHouse.getTitle());
        Log.e("address ::", hostingHouse.getAddress());
        Log.e("introduce ::", hostingHouse.getIntroduce());
        Log.e("space_info ::", hostingHouse.getSpace_info());
        Log.e("guest_access ::", hostingHouse.getGuest_access());
        Log.e("price_per_day ::", hostingHouse.getPrice_per_day());
        Log.e("extra_people_fee ::", hostingHouse.getExtra_people_fee());
        Log.e("cleaning_fee ::", hostingHouse.getCleaning_fee());
        Log.e("weekly_discount ::", hostingHouse.getWeekly_discount());
        Log.e("accommodates ::", hostingHouse.getAccommodates());
        Log.e("bathrooms ::", hostingHouse.getBathrooms());
        Log.e("bedrooms ::", hostingHouse.getBedrooms());
        Log.e("beds ::", hostingHouse.getBeds());
        Log.e("room_type ::", hostingHouse.getRoom_type());
//        hostingHouse.setAmenities("TV, Internet, Dryer");
        if(hostingHouse.getAmenities() == null){
            hostingHouse.setAmenities(" ");
        }

        Log.e("amenities ::", hostingHouse.getAmenities());
        Log.e("latitude ::", hostingHouse.getLatitude());
        Log.e("longitude ::", hostingHouse.getLongitude());

//        RequestBody token = RequestBody.create(MediaType.parse("text/plain"), PreferenceUtil.getToken(this));
        String token = "Token " + PreferenceUtil.getToken(this);



        List<MultipartBody.Part> photos = new ArrayList<>();

        if(hostingHouse.filePaths != null){
            for(int i=0 ; i<hostingHouse.filePaths.size() ; i++){
                String imagePath = hostingHouse.filePaths.get(i);
                File file = new File(imagePath);
                // 이미지를 비트맵으로 변환하는 옵션을 만들어준다
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                options.inSampleSize = 2; // 이미지의 사이즈를 1/2로 축소
                bitmap = BitmapFactory.decodeFile(imagePath, options); // 비트맵으로 만들어준다
                rotateBitmap = imgRotate(bitmap); // 사진을 변환하게되면 EXIF 값중 회전값이 날아가는데 이걸 완충하려고 미리 오른쪽으로 90도를 돌린다.

                // 비트맵을 바이트 어레이로 변경 --> 이미지를 축소하려면 변경해야되고 , 전송까지 하려면 변경해야된다
                ByteArrayOutputStream stream = new ByteArrayOutputStream();


                // Compress the bitmap to jpeg format and 50% image quality --> 크기줄인것을 압축을 하는 작업이다. (용량을줄인다)
                rotateBitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);

                // Create a byte array from ByteArrayOutputStream  --> JPEG 포맷을 서버와의 통신을 위해 바이트어레이로 변경
                byte[] byteArray = stream.toByteArray();

                RequestBody imageFile = RequestBody.create(MediaType.parse("image/*"), byteArray);

                photos.add(  MultipartBody.Part.createFormData("photo"+i, file.getName(), imageFile)  );
            }
        }


        RequestBody title = RequestBody.create(MediaType.parse("text/plain"), hostingHouse.getTitle());
        RequestBody address = RequestBody.create(MediaType.parse("text/plain"), hostingHouse.getAddress());
        RequestBody introduce = RequestBody.create(MediaType.parse("text/plain"), hostingHouse.getIntroduce());
        RequestBody space_info = RequestBody.create(MediaType.parse("text/plain"), hostingHouse.getSpace_info());
        RequestBody guest_access = RequestBody.create(MediaType.parse("text/plain"), hostingHouse.getGuest_access());
        RequestBody price_per_day = RequestBody.create(MediaType.parse("text/plain"), hostingHouse.getPrice_per_day());
        RequestBody extra_people_day = RequestBody.create(MediaType.parse("text/plain"), hostingHouse.getExtra_people_fee());
        RequestBody cleaning_fee = RequestBody.create(MediaType.parse("text/plain"), hostingHouse.getCleaning_fee());
        RequestBody weekly_discount = RequestBody.create(MediaType.parse("text/plain"), hostingHouse.getWeekly_discount());
        RequestBody accommodates = RequestBody.create(MediaType.parse("text/plain"), hostingHouse.getAccommodates());
        RequestBody bathrooms = RequestBody.create(MediaType.parse("text/plain"), hostingHouse.getBathrooms());
        RequestBody bedrooms = RequestBody.create(MediaType.parse("text/plain"), hostingHouse.getBedrooms());
        RequestBody beds = RequestBody.create(MediaType.parse("text/plain"), hostingHouse.getBeds());
        RequestBody room_type = RequestBody.create(MediaType.parse("text/plain"), hostingHouse.getRoom_type());
        RequestBody amenities = RequestBody.create(MediaType.parse("text/plain"), hostingHouse.getAmenities());
        RequestBody latitude = RequestBody.create(MediaType.parse("text/plain"), hostingHouse.getLatitude());
        RequestBody longitude = RequestBody.create(MediaType.parse("text/plain"), hostingHouse.getLongitude());

        Call<ResponseBody> postRegisterRooms = iServerApi.postRegisterRooms(token, title, address, introduce, space_info, guest_access, price_per_day, extra_people_day, cleaning_fee, weekly_discount, accommodates, bathrooms, bedrooms, beds, room_type, amenities, latitude, longitude, photos);
        postRegisterRooms.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("숙소 등록 성공", "숙소등록 성공");
                Log.e("숙소 등록 isSuccessful ", response.isSuccessful() + "");
                Log.e("response.code ::" , response.code()+"");

                if(response.isSuccessful()){

                    // 통신이 다끝났을때 bitmap 누수 현상을 막기 위해서 recycle을 해주었고
                    // 일부 기기에서는 recycle이 되지 않아서 null값을 따로 넣어주었다.
                    bitmap.recycle();
                    bitmap = null;

                    rotateBitmap.recycle();
                    rotateBitmap = null;

                    hostingHouse = null;    // HostingHouse 인스턴스 초기화

                    setResult(RESULT_OK);
                    finish();
                } else {
                    int statusCode = response.code();
                    Log.e("WriteActivity", "image 응답코드 ============= " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    private Bitmap imgRotate(Bitmap bmp){
        int width = bmp.getWidth();
        int height = bmp.getHeight();

        Matrix matrix = new Matrix();
        matrix.postRotate(0);

        bitmap = Bitmap.createBitmap(bmp, 0, 0, width, height, matrix, true);

        return bitmap;
    }
}
