package tech.disruptfin.wackhestern.disruptfintech;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @POST("/stocks/sell")
    @Headers(HerokuHeader.REPLACEMENT_HEADER)
    Call<Post> savePost(@Body FooRequest body);

    @POST("/user/setfunds")
    @Headers(HerokuHeader.REPLACEMENT_HEADER)
    Call<Post> setPost(@Body FooRequestSet body);

    @POST("/user/availablefunds")
    @Headers(HerokuHeader.REPLACEMENT_HEADER)
    Call<Post> avFund(@Body FooRequest body);

    @POST("/stocks/buy")
    @Headers(HerokuHeader.REPLACEMENT_HEADER)
    Call<Post> buyPost(@Body FooRequest body);

    @POST("/stocks/canbuy")
    @Headers(HerokuHeader.REPLACEMENT_HEADER)
    Call<Post> canBuyPost(@Body FooRequest body);

    @POST("/stocks/numOwned")
    @Headers(HerokuHeader.REPLACEMENT_HEADER)
    Call<Post> curNum(@Body FooRequestNet body);

    @POST("/stocks/price")
    @Headers(HerokuHeader.REPLACEMENT_HEADER)
    Call<Post> curPrice(@Body FooRequestNet body);


    @POST("/stocks/portfoliovalue")
    @Headers(HerokuHeader.REPLACEMENT_HEADER)
    Call<Post> val(@Body FooRequestNet body);

    @POST("/stocks/lastadded")
    @Headers(HerokuHeader.REPLACEMENT_HEADER)
    Call<Post> last(@Body FooRequestNet body);
}