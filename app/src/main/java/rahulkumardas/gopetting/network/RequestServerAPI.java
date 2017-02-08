package rahulkumardas.gopetting.network;


import com.google.gson.JsonObject;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by Rahul Kumar Das on 08-09-2016.
 */
public class RequestServerAPI {


    private static final String BASE_URL = "https://guidebook.com/service/v2/";

    private static Retrofit retrofit;

    public static Retrofit getClient() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
