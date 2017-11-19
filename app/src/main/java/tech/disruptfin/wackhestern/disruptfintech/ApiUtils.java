package tech.disruptfin.wackhestern.disruptfintech;

public class ApiUtils {

    private ApiUtils() {}

    public static final String BASE_URL = "https://investeon.herokuapp.com/";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }

    public static APIData getAPIData() {

        return RetrofitClient.getClient(BASE_URL).create(APIData.class);
    }
}