package tech.disruptfin.wackhestern.disruptfintech;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIData {
    @POST("/stocks/data")
    @Headers(HerokuHeader.REPLACEMENT_HEADER)
    Call<List<SponsorsResult>> setData(@Body FooRequest body);
}
