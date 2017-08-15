package scross.healer.networkService;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by hoyeon on 2017-07-17.
 */

public interface NetworkService {

    @GET("main")
    Call<ResponseBody> main();

    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody> login(
            @Field("phone") int phone,
            @Field("pw") String pw
    );

    @FormUrlEncoded
    @POST("signup")
    Call<ResponseBody> signup(
            @Field("phone") int phone,
            @Field("name") String name,
            @Field("pw") String pw,
            @Field("birth") int birth,
            @Field("gender") String sex
    );

    @FormUrlEncoded
    @POST("edit_profile")
    Call<ResponseBody> editProfile(
            @Field("birth") int birth,
            @Field("gender") String name
    );

    @GET("timeline")
    Call<ResponseBody> timeline();


    @FormUrlEncoded
    @POST("survey_before")
    Call<ResponseBody> surveyBefore(
            @Field("phone") int phone
    );

    @FormUrlEncoded
    @POST("survey_after")
    Call<ResponseBody> surveyAfter(
            @Field("phone") int phone
    );

    @GET("survey")
    Call<ResponseBody> surveyState();

    @GET("nowstate")
    Call<ResponseBody> nowState();

    @Multipart
    @POST("day/{day}/process1")
    Call<ResponseBody> process1(
            @Path("day") String day,
            @Part MultipartBody.Part picture
    );
    @FormUrlEncoded
    @POST("day/{day}/process2")
    Call<ResponseBody> process2(
            @Path("day") String day,
            @Field("emotion") int emotion
    );
    @FormUrlEncoded
    @POST("day/{day}/process3")
    Call<ResponseBody> process3(
            @Path("day") String day,
            @Field("time") int time
    );
    @Multipart
    @POST("day/{day}/process4")
    Call<ResponseBody> process4(
            @Path("day") String day,
            @Part MultipartBody.Part picture
    );
    @FormUrlEncoded
    @POST("day/{day}/process5")
    Call<ResponseBody> process5(
            @Path("day") String day,
            @Field("emotion") int emotion
    );

    @FormUrlEncoded
    @POST("rate")
    Call<ResponseBody> PostRate(
            @Field("id") String id,
            @Field("rate") String rate
    );

    @FormUrlEncoded
    @POST("sendEmail")
    Call<ResponseBody> SendEmail(
            @Field("phone") int phone
    );

    @FormUrlEncoded
    @POST("check_phone")
    Call<ResponseBody> checkPhone(
            @Field("phone") int phone
    );


}
