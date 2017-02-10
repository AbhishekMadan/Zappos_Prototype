package com.abhishek.abc.ilovezappos.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by abc on 06-Feb-17.
 */

public interface ZapEndpointInterface {

    //Search?term=wallet&key=b743e26728e16b81da139182bb2094357c31d33
    @GET("Search")
    public Call<ResponseBody> getSearchProduct(@Query("term") String searchWord, @Query("key") String apiKey);

}
