package com.android.airbnb.util;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;

/**
 * Created by MDY on 2017-08-19.
 */

public class PermissionControl {
    public static final int REQ_FLAG = 1001231456;

    // permissions은 다른 클래스에서 사용할 일이 없기 때문에 private
    // 아래에 있는 static 함수들이 사용하려면 static 변수여야 한다.
    private static String permissions[] = {Manifest.permission.WRITE_EXTERNAL_STORAGE};




    public static void checkVersion(Activity activity){
        // 마시멜로 이상 버전에서만 런타임 권한 체크
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // level 23은 마시멜로이다.  Build.VERSION_CODES.M : 마시멜로를 가리킨다.
            // 마시멜로부터는 앞에 대문자 한자만 써도 된다.
            checkPermission(activity);
        } else {
            callInit(activity);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)       // @RequireApi 와 @TargetApi 는 동일하다고 생각해도 된다.
    public static void checkPermission(Activity activity) {
        // 1. 권한체크 - 특정권한이 있는지 시스템에 물어본다.
        // checkSelfPermission 반환값이 true, false가 아니라 미리 정의된 상수로 반환한다.

        boolean denied = false;
        for (String perm : permissions) {
            if (activity.checkSelfPermission(perm) != PackageManager.PERMISSION_GRANTED) {
                denied = true;
                break;
            }
        }

        // 권한이 거절이 된게 하나라도 있으면.. 아래를 실행
        if (denied) {
            // 2. 권한이 없으면 사용자에게 권한을 달라고 요청
            // 동시에 여러개 호출할 수 있으니까 복수로
            activity.requestPermissions(permissions, REQ_FLAG);  // -> 권한을 요구하는 팝업이 사용자 화면에 노출된다.
        } else {
            callInit(activity); // 아래에도 중복되어 사용해서 밖으로 뺐다.
            // ((CallBack) activity).init();    <= 넘어온 activity를 CallBack으로 캐스팅한 다음에 init 함수를 호출한다.
        }
    }

    public static void onResult(Activity activity, int requestCode, @NonNull int[] grantResults){
        if(requestCode == REQ_FLAG) {

            boolean granted = true;
            for (int grant : grantResults) {
                if (grant !=  PackageManager.PERMISSION_GRANTED) {
                    granted = false;
                    break;
                }
            }

            // 3.1 사용자가 승인을 했음.
            if(granted){
                callInit(activity);
                // 3.2 사용자가 거절 했음.
            } else {
                ((CallBack) activity).cancel();
                /*Toast.makeText(activity, "권한을 요청을 승인하셔야 앱을 사용할 수 있습니다.", Toast.LENGTH_SHORT).show();
                activity.finish();*/
            }
        }
    }

    private static void callInit(Activity activity){

        // activity가 CallBack을 구현했는지 확인하는 작업
        // 아니면 런타임 Exception을 날려주면 된다.
        if(activity instanceof  CallBack){
            ((CallBack) activity).init();
            // 넘어온 activity를 CallBack으로 캐스팅한 다음에 init 함수를 호출한다.
            // 캐스팅을 해줘야 init함수가 보인다.
        } else {
            throw new RuntimeException("must implement this.CallBack" );
        }
    }

    public interface CallBack {
        public void init();
        public void cancel();
    }
}