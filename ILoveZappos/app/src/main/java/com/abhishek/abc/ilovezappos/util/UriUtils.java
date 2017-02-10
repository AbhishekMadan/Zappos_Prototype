package com.abhishek.abc.ilovezappos.util;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.abhishek.abc.ilovezappos.R;
import com.abhishek.abc.ilovezappos.models.Product;
import com.abhishek.abc.ilovezappos.models.VController;
import com.google.common.base.Strings;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Created by abc on 09-Feb-17.
 */

public class UriUtils {

    private static String TAG = "URI_UTILS";

    public static Map<String, String> getUriData(Uri url) {
        Map<String, String> query_pairs = new LinkedHashMap<String, String>();
        String query = url.getQuery();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            try {
                query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                Log.d(TAG,"Exeption decoding URI");
            }
        }
        return query_pairs;
    }

 /*   public static boolean parseResponseAndSaveProduct(Context c, Response<ResponseBody> response)  {
        JSONObject res = null;
        boolean isSuccess = false;
        try {

            res = new JSONObject(new String(response.body().bytes()));
            if(res.getInt(c.getResources().getString(R.string.result_count))>0) {
                JSONObject jsonProd = res.getJSONArray("results").getJSONObject(0);
                VController.getInstance()
                        .updateProduct(new Product(
                                jsonProd.getString(c.getResources().getString(R.string.brandName)),
                                jsonProd.getString(c.getResources().getString(R.string.thumbnailImageUrl)),
                                jsonProd.getLong(c.getResources().getString(R.string.productId)),
                                NumberConversionUtil.getCurrencyFromString(jsonProd.getString(c.getResources().getString(R.string.originalPrice))),
                                jsonProd.getLong(c.getResources().getString(R.string.styleId)),
                                jsonProd.getLong(c.getResources().getString(R.string.colorId)),
                                NumberConversionUtil.getCurrencyFromString(jsonProd.getString(c.getResources().getString(R.string.price))),
                                NumberConversionUtil.getPercentFractionFromString(jsonProd.getString(c.getResources().getString(R.string.percentOff))),
                                jsonProd.getString(c.getResources().getString(R.string.productUrl)),
                                jsonProd.getString(c.getResources().getString(R.string.productName))));
            }
            isSuccess = true;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG,"JSON Parse exception");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG,"IO exception");
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e(TAG,"Conversion exception");
        }
        return isSuccess;
    }*/

}
