package scross.healer;

import android.content.Context;
import android.content.SharedPreferences;
import static android.content.Context.MODE_PRIVATE;


public class SharedPreferenceUtil {

    private static SharedPreferenceUtil instance;

    public static SharedPreferenceUtil getInstance() {
        if (instance == null) {
            instance = new SharedPreferenceUtil(HealerContext.getContext());
        }
        return instance;
    }


    public SharedPreferenceUtil(Context context) {
        mContext = context;
        mPrefs = mContext.getSharedPreferences("userInfo", MODE_PRIVATE);
        mEditor = mPrefs.edit();
    }


    private static Context mContext;
    private static SharedPreferences mPrefs;
    private static SharedPreferences.Editor mEditor;

    final static  String USER_TOKEN = "Token";


    /*
     서버로 넘길 ID 또는 토큰 값
     */
    private static final String FIELD_FACEBOOK_ID = "facebookId";
    private static final String FIELD_FACEBOOK_TOKEN_KEY = "facebookToken";
    private static final String LOCALE_KEY = "localeAreaKey";
    private static final String SAVE_TIME = "saveTime";
    private static final String SAVE_DAY = "saveDay";
    private static final String PROCESS= "process";
    private static final String PHONE= "phoneNum";
    private static final String SAVE_NETWORK_TYPE= "saveNetwork";
    private static final String SURVEY_LINK= "link";
    private static final String SURVEY_STATE= "surveyState";



    public void setSurveyState(int surveyState){
        mEditor.putInt(SURVEY_STATE, surveyState);
        mEditor.commit();
    }
    public int getSurveyState(){
        return mPrefs.getInt(SURVEY_STATE, 0);
    }





    public void setSurveyLink(String link){
        mEditor.putString(SURVEY_LINK, link);
        mEditor.commit();
    }
    public String getSurveyLink(){
        return mPrefs.getString(SURVEY_LINK, "www.naver.com");
    }




    public void setSaveNetworkType(int saveNetworkType){
        mEditor.putInt(SAVE_NETWORK_TYPE, saveNetworkType);
        mEditor.commit();
    }
    public int getSaveNetworkType(){
        return mPrefs.getInt(SAVE_NETWORK_TYPE, 0);
    }



    public void setPhoneNum(int phoneNum){
        mEditor.putInt(PHONE, phoneNum);
        mEditor.commit();
    }
    public int getPhoneNum(){
        return mPrefs.getInt(PHONE, 0);
    }




    public void setSaveTime(int saveTime){
        mEditor.putInt(SAVE_TIME, saveTime);
        mEditor.commit();
    }
    public int getSaveTime(){
        return mPrefs.getInt(SAVE_TIME, 0);
    }




    public void setLastDay(int lastDay){
        mEditor.putInt(SAVE_DAY, lastDay);
        mEditor.commit();
    }
    public int getLastDay(){
        return mPrefs.getInt(SAVE_DAY, 1);
    }



    public void setProcess(int process){
        mEditor.putInt(PROCESS, process);
        mEditor.commit();
    }
    public int getProcess(){
        return mPrefs.getInt(PROCESS, 1);
    }




    public void setLocaleArea(String locationArea){
        mEditor.putString(LOCALE_KEY, locationArea);
        mEditor.commit();
    }
    public String getLocaleKey(){
        return mPrefs.getString(LOCALE_KEY,"");
    }
    public void setFacebookId(String id) {
        mEditor.putString(FIELD_FACEBOOK_ID, id);
        mEditor.commit();
    }

    public String getFaceBookId() {
        return mPrefs.getString(FIELD_FACEBOOK_ID, "");
    }

    public void setFacebookToken(String token) {
        mEditor.putString(FIELD_FACEBOOK_TOKEN_KEY, token);
        mEditor.commit();
    }

    public String getFieldFacebookTokenKey() {
        return mPrefs.getString(FIELD_FACEBOOK_TOKEN_KEY, "");
    }



    //토큰 저장하기
    public static void setAccessToken(int token) {

        mEditor.putInt(USER_TOKEN, token);
        mEditor.commit();
    }

    //토큰 가져오기
    public static int getAccessToken() {


        return mPrefs.getInt(USER_TOKEN, 0);

    }

    //토큰 삭제
    public static void removeAccessToken() {

        mEditor.remove(USER_TOKEN);
        mEditor.commit();


    }


    private static final String FCM_TOKEN_KEY = "fcmTokenKey";
    private static final String UUID_KEY = "uuidKey";


    public void setFcmTokenKey(String tokenKey) {
        mEditor.putString(FCM_TOKEN_KEY, tokenKey);
        mEditor.commit();

    }

    public String getFcmTokenKey() {
        return mPrefs.getString(FCM_TOKEN_KEY, "");
    }

    public void setUuidKey(String uuid) {
        mEditor.putString(UUID_KEY, uuid);
        mEditor.commit();
    }

    public String getUuidKey() {
        return mPrefs.getString(UUID_KEY, "");
    }

}
