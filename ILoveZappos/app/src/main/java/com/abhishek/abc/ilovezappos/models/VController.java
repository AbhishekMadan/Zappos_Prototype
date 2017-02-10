package com.abhishek.abc.ilovezappos.models;

import android.app.Application;
import android.nfc.Tag;
import android.util.Log;

import com.abhishek.abc.ilovezappos.R;
import com.abhishek.abc.ilovezappos.util.NumberConversionUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;

import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Created by abc on 06-Feb-17.
 */

public class VController extends Application{
    public static int ROTATION = 1;
    private static final String TAG = "VCONTROLLER";
    private ProductCart cart = ProductCart.getCartInstance();
    private Product product = new Product();
    private Product sharedProduct = new Product();
    private static VController instance = null;

    public static VController getInstance() {
        if(instance==null) {
            instance = new VController();
        }
        return instance;
    }

    public void updateProduct(Product p) {
        product.setBrandName(p.getBrandName());
        product.setThumbnailImageUrl(p.getThumbnailImageUrl());
        product.setProductId(p.getProductId());
        product.setOriginalPrice(p.getOriginalPrice());
        product.setStyleId(p.getStyleId());
        product.setColorId(p.getProductId());
        product.setPrice(p.getPrice());
        product.setPercentOff(p.getPercentOff());
        product.setProductUrl(p.getProductUrl());
        product.setProductName(p.getProductName());
    }

    public Product getProduct() {
        return product;
    }

    public ProductCart getCart() {
        return cart;
    }

}
