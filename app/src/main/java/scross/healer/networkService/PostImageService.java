package scross.healer.networkService;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by hoyeon on 2017-07-17.
 */

public interface PostImageService {
    @Multipart
    @POST("upload")
    Call<ResponseBody> PostImage(
            @Part MultipartBody.Part image,
            @Part("height") RequestBody height,
            @Part("weight") RequestBody weight,
            @Part("gender") RequestBody gender,
            @Part("plots") RequestBody plots
    );
    @FormUrlEncoded
    @POST("rate")
    Call<ResponseBody> PostRate(
            @Field("id") String id,
            @Field("rate") String rate
    );
}
