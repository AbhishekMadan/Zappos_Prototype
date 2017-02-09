package com.abhishek.abc.ilovezappos.network;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class ZapRestAdapter {

    private static ZapRestAdapter apiAdapter = null;
    private final String TAG = "ZAP_Adapter";
    protected Retrofit retrofit = null;
    protected ZapEndpointInterface zapEndpoint;
    static final String BASE_URL = "https://api.zappos.com";

    ZapRestAdapter() {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .build();
            zapEndpoint = retrofit.create(ZapEndpointInterface.class);
    }

    public static ZapRestAdapter getRestAdapter() {
        if(apiAdapter==null) {
            apiAdapter = new ZapRestAdapter();
        }
        return apiAdapter;
    }

    public Call<ResponseBody> getProducts(String p, String key) {
        return zapEndpoint.getSearchProduct(p,key);
    }
}
