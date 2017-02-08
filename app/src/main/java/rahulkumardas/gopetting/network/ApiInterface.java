package rahulkumardas.gopetting.network;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;

/**
 * Created by Rahul Kumar Das on 08-02-2017.
 */

public interface ApiInterface {

    @GET("upcomingGuides/")
    Call<JsonObject> getData();
}
