package com.android.airbnb.papago;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by JunHee on 2017. 8. 4..
 */

public class Translator {

    static String clientId = "ORVua_Xj8g56JOfCpjhr"; //애플리케이션 클라이언트 id
    static String clientSecret = "MehwXU_4h6"; //애플리케이션 클라이언트 시크릿 key
    static String resultString = "";

    public static void doTranslate(String content, IPapago iPapago) {
        try {
            String text = URLEncoder.encode(content, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            // post request
            String postParams = "source=en&target=ko&text=" + text;
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            StringBuffer response = new StringBuffer();
            while ((resultString = br.readLine()) != null) {
                response.append(resultString);
            }
            br.close();
            Log.e("Translator", response.toString());
            iPapago.getResult(response.toString());

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public interface IPapago {

        public void getResult(String result);

    }
}

