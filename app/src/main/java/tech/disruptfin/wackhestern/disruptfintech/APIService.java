package tech.disruptfin.wackhestern.disruptfintech;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @POST("/stocks/sell")
    @Headers(HerokuHeader.REPLACEMENT_HEADER)
    Call<Post> savePost(@Body FooRequest body);
}