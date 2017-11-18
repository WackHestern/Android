package tech.disruptfin.wackhestern.disruptfintech;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIService {

    @POST("stocks/buy")
    @FormUrlEncoded
    Call<Post> savePost(@Field("stockName") String stockName,
                        @Field("amount") String amount);
}