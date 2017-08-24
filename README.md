# Airbnb Application


## 어플리케이션 소개

- #### 안드로이드 개발공부를 목적으로 약 2억 명의 Guest, 3백만 개의 숙소를 확보하고 있는 숙박 플랫폼 'Airbnb App'을 따라 개발하기로 하였다.
- #### 최대한 Airbnb의 UI(VIew)를 따라 만들기 위해 노력했으며 숙박 정책과 같이 현재 불필요한 부분 간소화했다.
- #### Airbnb App을 분석하는 과정에서 개발로직과 구현 이유에 대해 생각해볼 수 있는 좋은 기회였다.
- #### 또한 백엔드 개발자와의 협업을 통해 실제 웹서버로부터 데이터를 받아 어플리케이션에 셋팅할 수 있는 좋은 경험이었다.

<br>

- ### **[전체소스코드](https://github.com/mdy0501/Airbnb/tree/master/Airbnb/app/src/main/java/com/android/airbnb)**

<br>
<br>
<br>


## 어플리케이션 화면

![screenshot01](https://github.com/mdy0501/Airbnb/blob/master/Airbnb/graphics/screenshot01.png)

![screenshot02](https://github.com/mdy0501/Airbnb/blob/master/Airbnb/graphics/screenshot02.png)

![screenshot03](https://github.com/mdy0501/Airbnb/blob/master/Airbnb/graphics/screenshot03.png)

![screenshot04](https://github.com/mdy0501/Airbnb/blob/master/Airbnb/graphics/screenshot04.png)

![screenshot05](https://github.com/mdy0501/Airbnb/blob/master/Airbnb/graphics/screenshot05.png)

<br>
<br>
<br>

## 어플리케이션 영상 링크
- #### https://www.youtube.com/watch?v=2XVoROcGZWc


<br>
<br>
<br>

## API 문서 링크
- #### https://gaius8.gitbooks.io/wps5-backend-airbnb/content/


<br>
<br>
<br>

## 사용된 개념들 (Jun Hee Lee & Dong Yeon Min)

## by Jun Hee Lee

### 1. Retrofit 2.0

##### 1.1. REST로 form-data POST하기 [[소스보기]](https://goo.gl/urA4mo)

- `form-data`를 서버로 `POST`할 경우, `@Multipart` annotation과 파라미터로 `@Part` 어노테이션과 함께 RequestBody에 `POST`할 내용을 담아 보내준다.
- `@Query`의 어노테이션의 경우, 서버측에서 REST 방식의 주소체계를 지켜주지 않을 경우 request가 거절당했다.
  - 서버측에 해당 내용 전달하여 URL을 변경하여 해결하였다.

> Retrofit 2.0 Interface (IServerApi.class)

```java
@Multipart
@POST("apis/reservations/")
Call<Reservation> postReservation(@Header("Authorization") String token,
                                      @Query("house") String pk,
                                      @Part("checkin_date") RequestBody checkin,
                                      @Part("checkout_date") RequestBody checkout);
```



### 2. 네이버 Papago API

- [번역하기 구동영상](https://www.youtube.com/edit?o=U&video_id=HoFVZxLsXPk) **[[소스코드]](https://goo.gl/G9FNyB)**


- 영문으로 된 설명을 번역하기 위해 Papago API를 활용했다.
- 위의 Retrofit 2.0을 활용하여 REST 통신을 하여 영문 String 데이터를 번역된 String 데이터를 reponse 받았다.
- response 데이터를 담는 최상위 클래스인 `Message class`의 변수명이 response 받는 JSON String 데이터의 변수명과 달라, `@SerializedNanme("변수명")` 어노테이션을 활용하였다.

```java
public class Message {

    // field 값 다른 이름으로 파싱할 때 @SerializedName 어노테이션을 사용한다.
    @SerializedName("@service")
    private String service;

    @SerializedName("@type")
    private String type;

    @SerializedName("@version")
    private String version;

    // 이하 코드 생략
}
```



### 2. Google Map, View Pager, Pager Adapter, BottomSheet Adapter

- [Map 구현 영상보기](https://youtu.be/jQRT8LHs3vM)
- [Google Map, View Pager 소스보기](https://goo.gl/foDoh1)
- [PagerAdapter 소스보기](https://goo.gl/e9ikyJ)
- [BottomSheetAdapter 소스보기](https://goo.gl/rSuDUp)

##### 1.1. GoogleMap 초기 셋팅 [[소스코드]]()

- Google Map API docs를 참고하여 Google Map 구현하였다.
- Google Map은 최초 초기화할 때 , 비동기로 처리된다.

> GoogleMap 초기 셋팅 코드 (GoogleMapViewPagerActivity.class)

```java
// 1. Fragment - supportMapFragment를 xml 상에 작성하고, OnMapReadyCallback를 implements한다.
public class GoogleMapViewPagerActivity extends FragmentActivity implements OnMapReadyCallback {

@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map_view_pager);

      	// 1. SupportMapFragment를 클래스의 멤버변수와 연결한다.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.detail_house_mapFragment);

      	// 2. map을 셋팅하기 위해 getMapAsync를 호출해 비동기처리를 한다.
        mapFragment.getMapAsync(GoogleMapViewPagerActivity.this);

      // 세부 코드 생략 ...
    }

	// 3. onMapReady가 콜백된다.
	@Override
    public void onMapReady(GoogleMap googleMap) {
      	// 3.1. widget 및 map 관련된 작업을 한다.
        MapsInitializer.initialize(getApplicationContext());
        mMap = googleMap;
        setMarkers(mMap);
        setMarkerOnClick();
        initMap();
    }

  // ....  

}
```



##### 1.2. MapPagerAdapter & ViewPager [[소스코드]](https://goo.gl/foDoh1)

- `PagerAdapter`를 상속하여 `MapPagerAdapter.class` 설계하였다.
- ViewPager처럼 좌/우 swipe를 통해 map 상의 데이터 조회가 가능하다.

```java
 private void setOnClick() {

   		// widget setOnClick 셋팅 코드 생략...

        btnWish.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                btnWishIsClicked(isChecked);
            }
        });
    }

	// button wish의 onClick 발생 시, 수행해야하는 작업들을 모아 메소드화하였다.
    private void btnWishIsClicked(boolean isChecked) {

        // 1. click 이벤트가 발생한 house의 wish 값 변경
        houseList.get(currentPostition).setWished(isChecked);

      	// 2. wishlist 업데이트를 위해 서버와 통신
        Loader.postWishList("Token " + PreferenceUtil.getToken(context), 						houseList.get(currentPostition).getPk(), MapPagerAdapter.this);

        // 3. parent actvity의 ui 변경을 위해 인터페이스로 callback
        onMapPagerListener.btnWishClicked(isChecked);
    }
```



##### 1.3. MapFragment 상의 Marker 셋팅 [[소스코드]](https://goo.gl/foDoh1)

- REST 통신을 통해 받아온 House LatLng 위치를 표시하는 Marker를 map 상에 셋팅한다.

> 다수의 Marker를 셋팅하는 `setMarkers();` (GoogleMapViewPagerActivity.class)

```java
	// 현재 선택된 marker의 id를 저장한다.
	public String selectedMarkerID = "";
    public Map<String, Marker> markerMap = new HashMap<>();

    // 다수의 Map Marker를 생성 및 map 위에 생성을 담당하는 메소드
    private void setMarkers(final GoogleMap googleMap) {

        // 1. 커스텀 title box 설정을 위해 iconGenerator를 설정해준다.
        IconGenerator iconGenerator = new IconGenerator(GoogleMapViewPagerActivity.this);
        for (int i = 0; i < houseList.size(); i++) {

            // 2. MarkerOptions 객체에 marker 설정 셋팅
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(houseList.get(i).getLatLng())
                    .icon(BitmapDescriptorFactory.fromBitmap(iconGenerator.makeIcon("₩" + 					   houseList.get(i).getPrice_per_day())))
                    .anchor(iconGenerator.getAnchorU(), iconGenerator.getAnchorV());

            // 3. 위에서 설정한 markerOptions을 map에 뿌려준다.
            Marker marker = googleMap.addMarker(markerOptions);

            // 3.1. 추후에 marker를 통해 객체를 꺼낼 수 있도록 setTag() 메소드를 활용해 tag로 House 객체				       를 저장한다.
            marker.setTag(houseList.get(i));

            // 3.2. title의 경우, Icon을 사용했으므로 default title Window가 뜨지 않도록 설정한다.
            marker.hideInfoWindow();
            houseList.get(i).setMarker(marker);

            // 4. marker 관리를 위해 Collection Framework에 저장한다.
            markerList.add(marker);
            markerMap.put(marker.getId(), marker);
            progress.stop();
        }

        // 5. 최초 map 로드 시, 선택되는 0번째 마커를 셋팅한다.
        setMarkerChanged(markerList.get(0));
    }
```



- 위의 메소드 안에 개별 marker를 셋팅하는 `setMarkerIcon()`,  `setMarkerIcon()` 그리고 `setMarkerChanged()` 메소드는 아래와 같이 작성하였다.

```java
	// IconGenerator 객체 사용하여 Marker title window 설정
    private void setMarkerIcon(Marker marker, int styleId, int color) {
        House house = (House) marker.getTag();
        IconGenerator iconGenerator = new IconGenerator(getApplicationContext());
        iconGenerator.setTextAppearance(styleId);
        iconGenerator.setColor(color);
        marker.setIcon(BitmapDescriptorFactory.fromBitmap(iconGenerator.makeIcon("₩" + 			house.getPrice_per_day())));
    }

    // Map 상의 marker에 setOnMarkerClickListener 설정
    public void setMarkerOnClick() {
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                setMarkerChanged(marker);
                return false;
            }
        });
    }

    private void setMarkerChanged(Marker marker) {
        // 1. Map Marker 선택 시, marker 셋팅하는 코드
        if (selectedMarkerID != "") {
            Marker exMarker = markerMap.get(selectedMarkerID);
            setMarkerIcon(exMarker, R.style.iconGenTxt_default, 									Color.parseColor("#ffffff"));
        }

        // 2. 최초 Map activity 로드 시, marker selected 셋팅하는 코드
        setMarkerIcon(marker, R.style.iconGenTxt_clicked, Color.parseColor("#00A599"));
        selectedMarkerID = marker.getId();
        mapListPager.setCurrentItem(markerList.indexOf(marker));
    }
```



##### 1.4. BottomSheet [[소스코드]](https://goo.gl/foDoh1)

- wish botton 클릭 이벤트 발생 시, 바로 wishlist 목록이 화면 아래에서 지정된 높이의 `RecyclerView`가 나타난다.

- `CoordinatorLayout`과 `layout_behavior`의 `Bottom_sheet_behavior` 속성을 활용하여 구현하였다.

```java
// BottomSheetBehavior 객체를 활용하여 wishlist를 RecyclerView 형태로 보여준다.
private BottomSheetBehavior behavior;

    // bottomSheetAdapter의 wish button 클릭 시, 바로 UI에 반영되도록 하는 메소드
    @Override
    public void btnWishClicked(boolean btnIsChecked) {

        // 1. wish button 클릭 시, 통신을 통해 wishlist로 로드한다.
        Loader.getWishList("Token " + PreferenceUtil.getToken(this), this);

        // 2. 받아온 wishlist 데이터 업데이트 후, adapter 또한 notifyDataSetChanged()한다.
        bottomSheetAdapter.notifyDataSetChanged();

        // 3. recyclerView position을 0으로 설정하여 초기화 시, 0번째 index로 셋팅한다.
        wishBottomRecycler.smoothScrollToPosition(0);

        // 4. wish button의 체크상태에 따라 서버와의 통신을 시도한다.
        // 4.1. 스낵바 '위시리스트' 액션버튼을 통해 wishlist를 바로 확인할 수 있다.
        if (btnIsChecked)
            setSnackbar("[wishlist]에 저장됨");
        else
            setSnackbar("[wishlist]에 삭제됨");
    }

	// 이벤트 발생 시, snackbar와 bottomsheet 셋팅하는 메소드
    private void setSnackbar(String title) {
        Snackbar.make(snackbarPlace, title, Snackbar.LENGTH_SHORT).setAction("위시리스트", 			new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Loader.getWishList(userToken, GoogleMapViewPagerActivity.this);

                // 4.2. wishlist 보기 onClick 시, BottomSheet가 화면 상에 올라오는 위치를 설정해준다.
                setBottomSheet(300.f);
            }
        }).show();
    }

private void setBottomSheet(float height) {
        behavior.setPeekHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, height, getResources().getDisplayMetrics()));
        behavior.setHideable(true);
        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

  // 이하 셋팅 코드 생략

}
```



### 3. Calendar (CustomView)  

- [Calendar 구동영상](https://youtu.be/2VeYNV4wyrU) / [Calendar를 활용한 예약하기 영상](https://www.youtube.com/edit?o=U&video_id=HoFVZxLsXPk)


- Calendar 관련 다수의 라이브러리가 존재하나 공부를 위해 직접 CustomView로 설계하였다.
- CustomView를 자바 코드로 작성할 경우, XML 작성 시 구조를 머리에 떠올리면 조금 더 쉽게 자바코드로 접근할 수 있었다.

##### 1.1. CalendarItem.class [[소스코드]](https://goo.gl/UmXT6N)

- 1달의 Calendar를 만들기 위한 class를 설계했다.
- days -> week -> month 순으로 `addView();`를 하도록 설계하였다.

>CustomView 틀을 만들기 위한 기초 작업 (CalendarItem.class)

```java
public class CalendarItem extends LinearLayout {

  	private LinearLayout.LayoutParams calendarLayoutParams;
    private LinearLayout.LayoutParams rowParams;
    private LinearLayout.LayoutParams textParams;

  	// 다른 멤버변수 생략 ...

	private void initView() {
        setOrientation(VERTICAL);
        setCalendarItemParams(this);
        setTitle(year, month);
        setDays();
    }

    // CalendarItem의 1달 달력의 가장 외곽 Layout의 설정값을 셋팅하는 메소드
    private void setCalendarItemParams(CalendarItem layout) {
        calendarLayoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 		LayoutParams.WRAP_CONTENT);
        calendarLayoutParams.setMargins(0, 0, 0, 30);
        layout.setLayoutParams(calendarLayoutParams);
    }

    // CalendarItem의 1주씩의 Layout의 설정값을 셋팅하는 메소드
    private void setRowParams(LinearLayout row) {
        rowParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 			LayoutParams.WRAP_CONTENT);
        rowParams.height = 150;
        rowParams.gravity = Gravity.CENTER;
        row.setGravity(Gravity.CENTER);
        row.setLayoutParams(rowParams);
    }

    // CalendarItem의 하루하루의 Layout의 설정값을 셋팅하는 메소드
    private void setTextViewParams(TextView tv) {
        textParams = new LinearLayout.LayoutParams(0, 											ViewGroup.LayoutParams.MATCH_PARENT);
        textParams.weight = 1;
        textParams.gravity = Gravity.CENTER;
        tv.setGravity(Gravity.CENTER);
        tv.setLayoutParams(textParams);
    }
}
```

- 해당 월의 날짜를 셋팅하기 위해 아래로 같은 로직을  작성하였다.

> `setDays();`메소드 구현

```java
 	// CalendarItem의 Rows 하루하루를 셋팅하는 메소드
    private void setDays(){

        LinearLayout row = null;

        // 1. 1주차 날짜를 적용하기 위해 LinearLayout을 new한다.
        for (int i = 0; i < days.size(); i++) {

            // 1.1. 아래 조건문의 경우 한주 단위로 row를 생성하기 위해 작성했다.
            if (((i != 0) && i % 7 == 0 || i == 0)) {
                row = new LinearLayout(context);
                row.setOrientation(HORIZONTAL);
                setRowParams(row);

                // 2. 완성된 row를 parent view에 addView(); 한다.
                addView(row);
            }

            final TextView col = new TextView(context);

          	// 2. TextView를 셋팅한다.
            setTextViewParams(col);
            col.setText(days.get(i) + "");
            col.setBackgroundResource(R.color.white);
            col.setTextColor(Color.BLACK);
            col.setTextSize(20f);

            // 3. 추후 날짜 검색을 쉽게 하기 위해 tag를 달아준다.
            // 3.1. 날짜가 없는 것("")은 tag를 달지 않기 위해 아래와 같은 조건문을 작성했다.
            if (days.get(i) != "" || !days.get(i).equals("")) {
                col.setTag(DATE_TAG, year + "-" + 									   					Utils.CalendarUtil.getFormattedForCal(month) +
                        "-" + Utils.CalendarUtil.getFormattedForCal(days.get(i) + ""));
                col.setTag(CHECK_BOOKING_TAG, false);
            }

          	// 3.2. 달력을 그리기 위해 공란("")으로 둔 textView의 경우, 온클릭 이벤트가 발생되지 않도록 설정					했다.
            col.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    String key = (String) v.getTag(DATE_TAG);
                    if (key != null) {
                        Toast.makeText(v.getContext(), key, Toast.LENGTH_SHORT).show();
                    }
                    setCompleted(key, CalendarActivity.STATUS, col);
                }
            });
            if (!days.get(i).equals("") && days.get(i) != "") {
                cols.add(col);
            }
            row.addView(col);
        }
    }
```



- reservation 이 완료된 날짜와 `check in`, `check out` 선택된 날짜의 `resource`를 변경하는 코드 작성

```java
	// reservation 완료된 날짜 속성값 변경하는 메소드
    public void setBooked(TextView tv) {
        if (tv.getText() != "" || !"".equals(tv.getText())) {
            tv.setTag(CHECK_BOOKING_TAG, true);
            tv.setClickable(false);
            tv.setBackgroundColor(getResources().getColor(R.color.white));
            tv.setTextColor(Color.GRAY);
            tv.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            tv.setTypeface(null, Typeface.BOLD_ITALIC);
        }
    }

    // check in & check out 선택 완료 시, 해당 기간 내의 날짜 resource 바꾸는 메소드
    public void setRanged(int start, int end) {

        // status에 따라 다른 작업을 하도록 분기한다
        if (CalendarActivity.STATUS == CalendarActivity.NOTHING) {
            for (int i = start; i <= end; i++) {
                setBooked(cols.get(i));
            }
        } else {
            for (int i = start; i <= end; i++) {
                setSelected(cols.get(i));
            }
        }
    }

    // checkIn checkOut 선택 시, 속성값 변경하는 메소드
    public void setSelected(TextView tv) {
        if (tv.getText() != "" || !tv.getText().equals(""){
          tv.setBackgroundColor(getResources().getColor(R.color.selected_day_background));
          tv.setTextColor(Color.WHITE);
          tv.setTypeface(null, Typeface.BOLD);
        }
    }
```



##### 1.2. CaledarActivity.class [[소스보기]](https://goo.gl/iRjA8X)

- 생성된 CalendarItem(CustomView)의 전체를 Control 하는 일종의 presenter 역할을 하는 Activity이다.

> 초기 Calendar를 생성하는 setCalendar() 메소드

```java
// CaledarActivity.class의 멤버변수
private List<CalendarItem> items;

private void setCalendar() {
        if (items.size() > 0)
            items.clear();

        for (int i = 0; i < calendarDatas.size(); i++) {
            CalendarData calendarData = calendarDatas.get(i);
            CalendarItem calendarItem = new CalendarItem(calendarData, this, this);

            // 서버에서 받아오는 날짜 데이터와 형식을 맞춰 객체에 .setTag()를 한다.
            String tag = calendarData.getYear() + "-" +            		                                                     			 Utils.CalendarUtil.getFormattedForCal(calendarData.getMonth());
            calendarItem.setTag(tag);
            Log.e("CustomCalendar", "tag : " + tag);
            items.add(calendarItem);
            calendarContainer.addView(calendarItem);
        }
    }
```



- check in & check out 날짜와 예약완료된 날짜를 Caledar 상에 체크해주는 메소드를 작성했다.

```java
// 예약된 날짜를 Calendar에 셋팅하는 처리하는 메소드
private void setBookedDates(String checkin, String checkout) {

        String subCheckin = checkin.substring(0, 7);
        String subCheckout = checkout.substring(0, 7);

        int subCheckinDd = Integer.parseInt(checkin.substring(8));
        int subCheckoutDd = Integer.parseInt(checkout.substring(8));

        for (CalendarItem item : items) {

            String itemKey = (String) item.getTag();

            if ((subCheckin).equals(itemKey) && (subCheckout).equals(itemKey)) {
                for (int i = subCheckinDd - 1; i < subCheckoutDd; i++) {
                    item.setBooked(item.getCols().get(i));
                }

            } else if ((subCheckin).equals(itemKey) || (subCheckout).equals(itemKey)) {
                int checkInDate = 0;
                int checkOutDate = 0;

                if ((subCheckin).equals(itemKey))
                    checkInDate = items.indexOf(item);
                else if ((subCheckout).equals(itemKey))
                    checkOutDate = items.indexOf(item);

                setBookedRange(checkInDate, checkOutDate);
            }
        }
    }

	// CalendarItem마다 index를 활용하여 범위를 처리하는 메소드
    private void setBookedRange(int startIndex, int endIndex) {
        for (int i = startIndex; i <= endIndex; i++) {

            CalendarItem item = items.get(i);
            List<TextView> cols = item.getCols();

            if (i == startIndex) {
                item.setRanged(cols.indexOf(cols.get(startIndex)), cols.size() - 1);
            } else if (i == endIndex) {
                item.setRanged(0, cols.indexOf(cols.get(endIndex)));
            } else {
                item.setRanged(0, cols.size() - 1);
            }
        }
    }

    // check in 날짜와 check out 날짜 선택 시, Calendar에 체크하는 메소드 (예약 완료된 날짜 아님)
    private void setRange(String startDate, String endDate) {

      	// 'yyyy-MM-dd' 형태로 넘어오는 String data를 잘라서 분기에 사용한다.
        String subCheckin = startDate.substring(0, 7);
        String subCheckout = endDate.substring(0, 7);

        int startIndex = 0;
        int endIndex = 0;

        for (CalendarItem item : items) {

            String itemKey = (String) item.getTag();

            if ((subCheckin).equals(itemKey) && (subCheckout).equals(itemKey)) {
                int startTextview = item.getCols().indexOf(item.getSelectedTvs().get(0));
                int endTextView = item.getCols().indexOf(item.getSelectedTvs().get(1));

                for (int i = startTextview; i <= endTextView; i++) {
                    item.setSelected(item.getCols().get(i));
                }
            } else if ((subCheckin).equals(itemKey) || (subCheckout).equals(itemKey)) {
                if ((subCheckin).equals(itemKey))
                    startIndex = items.indexOf(item);
                else if ((subCheckout).equals(itemKey))
                    endIndex = items.indexOf(item);

                setCalendarRange(startIndex, endIndex);
            }
        }
    }

	// 위 메소드에서 받은 index를 활용하여 CalendarItem에 날짜를 체크하는 메소드 (예약 완료된 날짜 아님)
    private void setCalendarRange(int startIndex, int endIndex) {

        for (int i = startIndex; i <= endIndex; i++) {

            CalendarItem item = items.get(i);
            List<TextView> cols = item.getCols();
            List<TextView> selectedTvs = item.getSelectedTvs();

            if (i == startIndex) {
                item.setRanged(cols.indexOf(item.getSelectedTvs().get(0)), cols.size() - 1);
            } else if (i == endIndex) {
                item.setRanged(0, cols.indexOf(selectedTvs.get(0)));

            } else {
                item.setRanged(0, cols.size() - 1);
            }
        }
    }
```



- 다른 객체에서 변경된 데이터를 Activity에 바로 반영할 수 있도록 `CalendarItem.OnCalendarChangedListener` interface 작성했다.

```java
public class CalendarActivity extends AppCompatActivity implements CalendarItem.OnCalendarChangedListener {

    private TextView checkinDateTxt;
    private TextView checkoutDateTxt;

  // ... 코드 생략

  // CalendarItem.OnCalendarChangedListener interface를 구현하여 CalendarItem.class에서 넘겨 받      은 check in & check out date를 Activity의 TextView widget에 반영한다.

   @Override
    public void checkInChanged(String date) {
        checkinDateTxt.setText("Check in" + "\n" + date);
    }

    @Override
    public void checkOutChanged(String date) {
        checkoutDateTxt.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        checkoutDateTxt.setText("Check out" + "\n" + date);
        calendarResultTxt.setText(Utils.CalendarUtil.calculatePeriod(checkinDate, 				checkoutDate) + "박을 선택했습니다.");
        setRange(checkinDate, checkoutDate);
    }
}
```


<br>
<br>

## by Dong Yeon Min

### 1. SharedPreference
##### 1.1. SharedPreference의 용도
- 로그인시 서버로부터 response받게 되는 token값을 저장
- 사용자 primary key값을 저장
- 사용자 email값을 저장

<br>

##### 1.2. Util성으로 구현한 코드
```java
public class PreferenceUtil {
    private static SharedPreferences sharedPreferences = null;

    private static void setSharedPreferences(Context context){
        if(sharedPreferences == null)
            sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE); // MODE_PRIVATE - 다른 앱이 접근하지 못하게
    }

    private static void setString(Context context, String key, String value){
        setSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    private static String getString(Context context, String key){
        setSharedPreferences(context);
        return sharedPreferences.getString(key, "해당 데이터 없음");
    }

    // token
    public static void setToken(Context context, String token){
        setString(context,"userToken", token);
    }
    public static String getToken(Context context) {
        return getString(context, "userToken");
    }

    // pk
    public static void setPrimaryKey(Context context, String primaryKey){
        setString(context,"userPrimaryKey", primaryKey);
    }
    public static String getPrimaryKey(Context context) {
        return getString(context, "userPrimaryKey");
    }

    // email
    public static void setEmail(Context context, String email){
        setString(context,"userEmail", email);
    }
    public static String getEmail(Context context) {
        return getString(context, "userEmail");
    }
}
```

<br>
<br>

### 2. Facebook 로그인 및 로그아웃 기능 구현
##### 2.1. Facebook SDK 가져오기
- Maven Central Repository를 dependencies 앞의 `build.gradle`에 다음과 같이 추가
```
repositories {
    mavenCentral()
}
```

- compile 'com.facebook.android:facebook-android-sdk:[4,5)'를 build.gradle 종속성에 추가
```
dependencies {
  compile 'com.facebook.android:facebook-android-sdk:[4,5)'
}
```

<br>

##### 2.2. Manifest 수정
- strings.xml 파일에 Facebook 앱 ID값을 추가
```
<string name="facebook_app_id">119275832057276</string>
<string name="fb_login_protocol_scheme">fb119275832057276</string>
```
- Manifest에 user-permission 요소를 추가
```
<uses-permission android:name="android.permission.INTERNET"/>
```

<br>

##### 2.3. 개발 해시키 생성
- `windows`는 아래 명령어를 명령프롬프트 창에서 실행
```
keytool -exportcert -alias androiddebugkey -keystore %HOMEPATH%\.android\debug.keystore | openssl sha1 -binary | openssl base64
```
- 이 명령으로 개발 환경에 고유한 28자 해시키가 생성된다.

<br>

##### 2.4. 앱에 대한 SSO 활성화

<br>

##### 2.5. Facebook 로그인 버튼 추가 및 콜백 등록
- Facebook 로그인 버튼을 추가하려면 전체 클래스 이름인 com.facebook.widget.LoginButton으로 레이아웃 XML 파일에 버튼을 추가
- CallbackManager.Factory.create를 호출하여 로그인 응답을 처리할 콜백 관리자를 만든다.
- callbackManager를 통해 LoginManager에 로그인 결과를 전달하기 위해 callbackManager.onActivityResult를 호출한다.
- 로그인 결과에 응답하기 위해 로그인버튼(btnLoginFacebook)에 콜백을 등록한다.
```java
btnLoginFacebook = (LoginButton) view.findViewById(R.id.login_button);

callbackManager = CallbackManager.Factory.create();
btnLoginFacebook.setReadPermissions(Arrays.asList("public_profile", "email"));
btnLoginFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                loginDialog.show();
                AccessToken accessToken = loginResult.getAccessToken();
                Profile profile = Profile.getCurrentProfile();

                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                        String email = "";

                        Log.e("페이스북 로그인 결과 == ", response.toString());
                        Log.e("페이스북 토큰 : ", loginResult.getAccessToken().getToken());
                        Log.e("페이스북 User ID : ", loginResult.getAccessToken().getUserId());
                        Log.e("Facebook result : ",object.toString());

                        try {
                            email = object.getString("email");
                            Log.e("페이스북 User Email : ", email);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        PreferenceUtil.setToken(WelcomeActivity.this, loginResult.getAccessToken().getToken());
                        postFacebookLoginData(loginResult.getAccessToken().getToken());
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Log.e("LoginErr", error.toString());
            }
        });
```

- 로그인에 성공하면 LoginResult 매개변수에 새로운 AccessToken과 최근에 부여되거나 거부된 권한이 포함된다.
  - LoginResult 매개변수에 들어있는 AccessToken을 SharedPreference에 저장하고, 이후 서버와 통신을 진행한다.
  ```java
  AccessToken accessToken = loginResult.getAccessToken();
  ```
- onActivityResult()에서 callbackManager에 생성된 onCreate()로 로그인 결과를 전달한다.

```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    callbackManager.onActivityResult(requestCode, resultCode, data);
}
```

<br>

##### 2.6. 로그아웃
```java
LoginManager.getInstance().logOut();
```


<br>
<br>



### 3. RadioButton 커스텀
##### 3.1. res - drawable - button_radio.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <!-- UNSELECTED -->
    <item android:state_checked="false">
        <layer-list>
            <!-- 이미지 영역 -->
            <item android:drawable="@drawable/unchecked" android:top="4dp" android:right="4dp" android:bottom="4dp" android:left="4dp" />
        </layer-list>
    </item>
    <!-- SELECTED -->
    <item android:state_checked="true">
        <layer-list>
            <!-- 이미지 영역 -->
            <item android:drawable="@drawable/checked" android:top="4dp" android:right="4dp" android:bottom="4dp" android:left="4dp" />
        </layer-list>
    </item>
</selector>
```

<br>

##### 3.2. RadioButton xml에 transparent 추가
```xml
<RadioButton
    android:id="@+id/radioButtonAll"
    android:layout_width="45dp"
    android:layout_height="45dp"
    android:background="@drawable/button_radio"
    android:button="@android:color/transparent" />
```

<br>

##### 3.3. RadioGroup - onCheckedChanged
```java
// 숙소유형 RadioButton 선택
@Override
public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
    switch (checkedId){
        case R.id.radioButtonAll:
            hostingHouse.setRoom_type("House");
            Log.e("R_B_All :: ", HostingHouse.getInstance().getRoom_type());
            break;
        case R.id.radioButtonPrivateRoom:
            hostingHouse.setRoom_type("Individual");
            Log.e("R_B_PrivateRoom :: ", hostingHouse.getRoom_type());
            break;
        case R.id.radioButtonMultiRoom:
            hostingHouse.setRoom_type("Shared_Room");
            Log.e("R_B_MultiRoom :: ", hostingHouse.getRoom_type());
            break;
    }

}
```

<br>

### 4. Spinner
##### 4.1. 숙소 유형의 경우, res - values - strings.xml 에 다음과 같이 추가한다.
```xml
<!-- 숙소 유형 선택 Spinner 항목 -->
    <string-array name="typeOfBuilding">
        <item>아파트</item>
        <item>주택</item>
        <item>베드 앤 브랙퍼스트</item>
        <item>로프트</item>
        <item>오두막</item>
        <item>별장</item>
        <item>성</item>
        <item>기숙사</item>
        <item>트리하우스</item>
        <item>보트</item>
        <item>비행기</item>
        <item>캠핑카</item>
        <item>이글루</item>
        <item>등대</item>
        <item>유르트 (몽골의 전통 텐트)</item>
        <item>티피 (원뿔형 천막)</item>
        <item>동굴</item>
        <item>섬</item>
        <item>스위스식 오두막</item>
        <item>기차</item>
        <item>텐트</item>
        <item>기타</item>
    </string-array>
```

<br>

##### 4.2. Spinner 연결하기
```java
private Spinner spinnerType;

...

spinnerType = (Spinner) view.findViewById(R.id.spinnerType);

...


ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.typeOfBuilding, android.R.layout.simple_spinner_item);
adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
spinnerType.setAdapter(adapter);
spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.e("position ", position + "");
        Log.e("id ", id + "");
        Log.e("SelectedItem", parent.getSelectedItem()+ "");

        ((TextView)parent.getChildAt(0)).setTextColor(Color.BLACK); // 스피너 안의 글씨색 설정
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
});
```

<br>
<br>



### 5. 자료구조 Map을 이용한 편의시설 선택 기능 구현
##### 5.1. 숙소등록시, 편의시설 현황을 POST로 보낼때, 다음과 같은 문자열 형식으로 보낸다.
  - ex) TV, Internet, Breakfast

<br>

##### 5.2. 이에 따라 해당 항목의 CheckBox가 클릭되면 문자열이 추가되고, 다시 한번더 클릭되면 문자열이 제거되는 코드를 작성했다.

- 처음에는 String 연산으로 코드를 작성했고, 같은 항목을 여러번 클릭하면 같은 문자열이 중복되어 추가되는 문제를 해결하기 위해 자료구조 Map을 사용하였다.
  - 자료구조 Map은 <Key, Valuse>형태로 구성
```java
public class HostingHouse {

    public static HostingHouse hostingHouse = null;


    public Map<String, String> facilitiesMap = new HashMap<>();

    public void addFacilities(String key, String value){
        facilitiesMap.put(key, value);
    }
    public void removeFacilities(String key){
        facilitiesMap.remove(key);
    }
}
```

- CheckBox를 체크하는 이벤트가 발생할때, key값을 알 수 있기 때문에 key값에 해당되는 value를 편의시설 현황에 추가 혹은 제거했다.
- 무선인터넷(WireLess_Internet)항목을 편의시설 현황에 추가 및 제거하는 코드이다.

```java
// 무선 인터넷
checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            hostingHouse.addFacilities("Wireless_Internet", "Wireless_Internet");
        } else if(isChecked == false){
            hostingHouse.removeFacilities("Wireless_Internet");
        }
    }
});
```

<br>

##### 5.3. 체크가 완료되면, 체크된 항목들을 String 변수에 담는다.
```java
String totalAmenities = "";

for(String key : hostingHouse.facilitiesMap.keySet()){
    totalAmenities = totalAmenities + ", " + hostingHouse.facilitiesMap.get(key);
}

// 예외처리 (하나라도 체크한 경우만)
if(totalAmenities != null && !"".equals(totalAmenities)){
    totalAmenities = totalAmenities.substring(2, totalAmenities.length());
}

hostingHouse.setAmenities(totalAmenities);
```


<br>
<br>



### 6. TabLayout을 이용해 Fragment 안에 Fragment로 구성 (Guest Main화면)
##### 6.1. GuestMainActivity에서 '탐색하기', '저장하기', '여행', '메시지', '프로필'에 해당하는 항목(Tab)을 클릭하면 해당 항목을 나타내는 화면으로 바꿔준다.
- 처음 화면을 실행하면 '탐색하기' 화면에 해당하는 Fragment를 guest_main_container에 add한다.
- TabLayout을 이용해 해당 항목을 클릭하면 guest_main_container에 있는 Fragment가 replace되도록 한다.
- 상수를 사용하여 TabLayout switch문에서 해당 항목 내용이 무엇인지 바로 알 수 있게 했다.
  - ex) private static final int SEARCH = 0;

```java
private TabLayout guestMainTabLayout;
private static final int SEARCH = 0;
private static final int WISH = 1;
private static final int TRAVEL = 2;
private static final int MESSAGE = 3;
private static final int PROFILE = 4;

...

// 처음 화면을 실행하면 '탐색하기' 화면을 보여준다.
private void setViews(){
        guestMainTabLayout = (TabLayout) findViewById(hostMainTabLayout);
        getSupportFragmentManager().beginTransaction()
                .add(guest_main_container, guestSearchFragment)
                .commit();
    }

// TabLayout을 이용해 해당 항목을 클릭하면 guest_main_container에 있는 Fragment가 replace되도록 한다.
private void setListeners(){
    // TabLayout Listener
    guestMainTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            switch (tab.getPosition()){
                case SEARCH :
                    getSupportFragmentManager().beginTransaction()
                            .replace(guest_main_container, guestSearchFragment)
                            .commit();
                    break;
                case WISH :
                    getSupportFragmentManager().beginTransaction()
                            .replace(guest_main_container, wishFragment)
                            .addToBackStack(null)
                            .commit();
                    break;
                case TRAVEL :
                    getSupportFragmentManager().beginTransaction()
                            .replace(guest_main_container, guestTravelFragment)
                            .addToBackStack(null)
                            .commit();
                    break;
                case MESSAGE :
                    getSupportFragmentManager().beginTransaction()
                            .replace(guest_main_container, guestMessageFragment)
                            .addToBackStack(null)
                            .commit();
                    break;

                case PROFILE :
                    getSupportFragmentManager().beginTransaction()
                            .replace(guest_main_container, guestProfileFragment)
                            .addToBackStack(null)
                            .commit();
                    break;
            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    });
}

```

<br>

##### 6.2. GuestSearchFragment 에서 '추천', '숙소', '트립', '장소' 에 해당하는 항목(Tab)을 클릭하면 해당 항목을 나타내는 화면으로 바꿔준다.

```java
private GuestMainActivity guestMainActivity;
private TabLayout searchTabLayout;

...

@Override
public void onAttach(Context context) {
    super.onAttach(context);
    guestMainActivity = (GuestMainActivity) context;
}

...

private void setViews(View view){
    searchTabLayout = (TabLayout) view.findViewById(R.id.searchTabLayout);
    guestMainActivity.getSupportFragmentManager().beginTransaction()
            .add(R.id.search_container, guestSearchRecommendFragment)
            .commit();
}

...

private void setListeners(){
    searchTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            switch (tab.getPosition()){
                case RECOMMEND :
                    guestMainActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.search_container, guestSearchRecommendFragment)
                            .commit();
                    break;
                case ROOMS :
                    guestMainActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.search_container, guestSearchRoomsFragment)
                            .commit();
                    break;
                case TRIP :
                    guestMainActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.search_container, guestSearchTripFragment)
                            .commit();
                    break;
                case PLACE :
                    guestMainActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.search_container, guestSearchPlaceFragment)
                            .commit();
                    break;
            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    });
}
```

<br>
<br>



<br>
<br>

### 7. Retrofit을 이용한 Restful 통신
##### 7.1. Retrofit 인터페이스 생성
- 기본 base URL은 static 변수로 선언한다.
```java
public interface IServerApi {

    public static final String BASE_URL = "http://crusia.xyz/";

    @GET("apis/house/")
    Call<List<House>> readAllHouses();

    @GET("apis/house/{pk}")
    Call<House> readOneHouse(@Path("pk") String pk);

    @GET("apis/user/")
    Call<List<Host>> readAllHosts();

    @GET("apis/house/{pk}")
    Call<Host> readOneHost(@Path("pk") String pk);

    @GET("apis/reservations/")
    Call<List<Reservation>> readReservation(@Query("house") String housePk);

    // 회원가입 POST
    @Multipart
    @POST("apis/user/")
    Call<SignUpData> postSignUpData(@Part("email")RequestBody email,
                                    @Part("password1") RequestBody password1,
                                    @Part("password2")RequestBody password2,
                                    @Part("first_name")RequestBody firstName,
                                    @Part("last_name") RequestBody lastName,
                                    @Part("birthday") RequestBody birthday,
                                    @Part("agreement")RequestBody agreement);

    // 로그인 POST
    @Multipart
    @POST("apis/user/login/")
    Call<LoginResult> postLoginData(@Part("email") RequestBody email,
                                    @Part("password") RequestBody password);


    // 페이스북 로그인 POST
    @Multipart
    @POST("apis/user/facebook-login/")
    Call<FacebookLoginResult> postFacebookLoginData(@Part("token") RequestBody token);


    // 로그아웃 GET
    // Key : Authorization  ,  Value : Token xxxxxxxxxxxxxxx
    @GET("apis/user/logout/")
    Call<ResponseBody> getLogout(@Header("Authorization") String token);


    // 숙소 등록 POST
    @Multipart
    @POST("apis/house/")
    Call<ResponseBody> postRegisterRooms(@Header("Authorization") String token,
                                         @Part("title") RequestBody title,
                                         @Part("address") RequestBody address,
                                         @Part("introduce") RequestBody introduce,
                                         @Part("space_info") RequestBody space_info,
                                         @Part("guest_access") RequestBody guest_access,
                                         @Part("price_per_day") RequestBody price_per_day,
                                         @Part("extra_people_fee") RequestBody extra_people_fee,
                                         @Part("cleaning_fee") RequestBody cleaning_fee,
                                         @Part("weekly_discount") RequestBody weekly_discount,
                                         @Part("accommodates") RequestBody accomodates,
                                         @Part("bathrooms") RequestBody bathrooms,
                                         @Part("bedrooms") RequestBody bedrooms,
                                         @Part("beds") RequestBody beds,
                                         @Part("room_type") RequestBody room_type,
                                         @Part("amenities") RequestBody amenities,
                                         @Part("latitude") RequestBody latitude,
                                         @Part("longitude") RequestBody longitude,
                                         @Part List<MultipartBody.Part> photos);
}
```

<br>

##### 7.2. 데이터 통신을 위한 ITask 인터페이스 설계
- 인터페이스 안에 인터페이스를 설계함으로써 ITask를 구현한 Activity 혹은 Fragment에서 필요한 메소드만 Implements 할 수 있게 하였다.
```java
public interface ITask {

    public interface totalHouseList{
        public void doTotalHouseList(List<House> houseList);
    }

    public interface oneHouseList{
        public void doOneHouseList(House house);
    }

}
```
- 예를 들어, ITask 인터페이스가 다음과 같이 설계되어 있으면 ITask를 구현한 경우, 무조건 2개의 메소드를 Implements 해야 한다.
```java
public interface ITask {

    public void doTotalHouseList(List<House> houseList);
    public void doOneHouseList(House house);

}
```

<br>

##### 7.3. Loader 클래스 설계
```java
public class Loader {
    public static List<House> houseList;
    public static House house;

    // House pk값을 통해 House 1개만 불러오는 경우
    public static void getOneHouse(String pk, final ITask.oneHouseList oneHouseList) {
        Retrofit client = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IServerApi serverApi = client.create(IServerApi.class);
        Call<House> call = serverApi.readOneHouse(pk);
        call.enqueue(new Callback<House>() {
            @Override
            public void onResponse(Call<House> call, Response<House> response) {
                house = response.body();
                oneHouseList.doOneHouseList(house);
            }

            @Override
            public void onFailure(Call<House> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    // House 전체를 불러오는 경우
    public static void getTotalHouse(final ITask.totalHouseList totalHouseList) {
        Retrofit client = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IServerApi serverApi = client.create(IServerApi.class);
        Call<List<House>> call = serverApi.readAllHouses();
        call.enqueue(new Callback<List<House>>() {
            @Override
            public void onResponse(Call<List<House>> call, Response<List<House>> response) {
                try {
                    houseList = response.body();
                    totalHouseList.doTotalHouseList(houseList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<House>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
```

<br>

##### 7.4. 데이터 통신
- 데이터 통신을 하는 Activity 혹은 Fragment에서 ITask를 구현하고, 해당되는 메소드를 Implements한다.
- 전체 House를 받아오는 것을 예로 들면, 다음과 같은 코드를 통해 해당 Activity 혹은 Fragment를 인자로 넘겨준다.
```java
private List<House> dataList;

...

Loader.getTotalHouse(this);
```

- 그리고 Retrofit 통신을 통해 넘겨받은 House 전체를 doTotalHouseList()의 인자로 해당 Activity 혹은 Fragment로 다시 넘겨준다.
```java
@Override
public void doTotalHouseList(List<House> houseList) {
    dataList = houseList;
}
```

<br>
<br>





### 8. 숙소 등록 데이터 통신

##### 8.1. 갤러리에서 사진 여러개 선택해서 가져오기

```java
public class HostingHouse {

    private static HostingHouse hostingHouse = null;

    public List<Uri> uris = new ArrayList<>();
    public List<String> filePaths = new ArrayList<>();

}

...

Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);   // EXTERNAL_CONTENT_URI 에 여러가지가 있는데 그 중에서 이미지들을 가져올 수 있게 해준다.
intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
startActivityForResult( Intent.createChooser(intent, "앱을 선택하세요."), 100);    // 사진앱이 여러개일 경우 선택하게끔 해준다.

...

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

```

<br>

##### 8.2. 인터페이스 설계

```java
public interface IServerApi {
    public static final String BASE_URL = "http://crusia.xyz/";

    // 숙소 등록 POST
    @Multipart
    @POST("apis/house/")
    Call<ResponseBody> postRegisterRooms(@Header("Authorization") String token,
                                         @Part("title") RequestBody title,
                                         @Part("address") RequestBody address,
                                         @Part("introduce") RequestBody introduce,
                                         @Part("space_info") RequestBody space_info,
                                         @Part("guest_access") RequestBody guest_access,
                                         @Part("price_per_day") RequestBody price_per_day,
                                         @Part("extra_people_fee") RequestBody extra_people_fee,
                                         @Part("cleaning_fee") RequestBody cleaning_fee,
                                         @Part("weekly_discount") RequestBody weekly_discount,
                                         @Part("accommodates") RequestBody accomodates,
                                         @Part("bathrooms") RequestBody bathrooms,
                                         @Part("bedrooms") RequestBody bedrooms,
                                         @Part("beds") RequestBody beds,
                                         @Part("room_type") RequestBody room_type,
                                         @Part("amenities") RequestBody amenities,
                                         @Part("latitude") RequestBody latitude,
                                         @Part("longitude") RequestBody longitude,
                                         @Part List<MultipartBody.Part> photos);
}
```

<br>

##### 8.3.  데이터 통신하는 코드

```java
private void postRegiserRooms(){
    retrofit = new Retrofit.Builder()
            .baseUrl(ApiService.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    iServerApi = retrofit.create(IServerApi.class);

    if(hostingHouse.getAmenities() == null){
        hostingHouse.setAmenities(" ");
    }

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
                Log.e("statusCode", statusCode + "");
            }
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {

        }
    });
}
```
