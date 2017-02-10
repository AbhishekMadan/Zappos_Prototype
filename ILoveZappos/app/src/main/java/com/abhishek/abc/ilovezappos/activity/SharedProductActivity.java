package com.abhishek.abc.ilovezappos.activity;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.abhishek.abc.ilovezappos.BR;
import com.abhishek.abc.ilovezappos.R;
import com.abhishek.abc.ilovezappos.fragment.CartDialogFragment;
import com.abhishek.abc.ilovezappos.models.Product;
import com.abhishek.abc.ilovezappos.models.VController;
import com.abhishek.abc.ilovezappos.network.ZapRestAdapter;
import com.abhishek.abc.ilovezappos.util.NumberConversionUtil;
import com.abhishek.abc.ilovezappos.util.UriUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SharedProductActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "SHARED_PRODUCT";
    private Toolbar actionBar;
    private View badgeLayout;
    private View progress_container;
    private Product sharedProduct;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_shared_product);
        ViewDataBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_shared_product);
        sharedProduct = new Product("","http:\\\\abc.com\\cc.jpg",0,0,0,0,0,0,"","");
        binding.setVariable(BR.product,sharedProduct);
        initView();
    }

    public void initView() {

        actionBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(actionBar);

        progress_container = findViewById(R.id.progress_container1);
        showProgressDialog();
        Intent in = getIntent();
        Uri data = in.getData();
        if(data!=null) {
            getProduct(UriUtils.getUriData(data).get("term"));
        }
    }

    public void getProduct(String prod) {
        String apiKey = getApplicationContext().getResources().getString(R.string.api_key);
        Call<ResponseBody> call = ZapRestAdapter.getRestAdapter().getProducts(prod, apiKey);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(parseResponseAndSaveProduct(response)) {
                    Log.d(TAG,VController.getInstance().getProduct().toString());
                }
                setActionButtonView();
                hideProgressDialog();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "Error Response from Server.");
                Toast.makeText(getApplicationContext(),"Unable to Fetch Data",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean parseResponseAndSaveProduct(Response<ResponseBody> response)  {
        JSONObject res = null;
        boolean isSuccess = false;
        try {
            res = new JSONObject(new String(response.body().bytes()));
            if(res.getInt(getResources().getString(R.string.result_count))>0) {
                JSONObject jsonProd = res.getJSONArray("results").getJSONObject(0);
                sharedProduct.setBrandName(getResources().getString(R.string.brandName));
                sharedProduct.setThumbnailImageUrl(jsonProd.getString(getResources().getString(R.string.thumbnailImageUrl)));
                sharedProduct.setProductId(jsonProd.getLong(getResources().getString(R.string.productId)));
                sharedProduct.setOriginalPrice(NumberConversionUtil.getCurrencyFromString(jsonProd.getString(getResources().getString(R.string.originalPrice))));
                sharedProduct.setStyleId(jsonProd.getLong(getResources().getString(R.string.styleId)));
                sharedProduct.setColorId(jsonProd.getLong(getResources().getString(R.string.colorId)));
                sharedProduct.setPrice(NumberConversionUtil.getCurrencyFromString(jsonProd.getString(getResources().getString(R.string.price))));
                sharedProduct.setPercentOff(NumberConversionUtil.getPercentFractionFromString(jsonProd.getString(getResources().getString(R.string.percentOff))));
                sharedProduct.setProductUrl(jsonProd.getString(getResources().getString(R.string.productUrl)));
                sharedProduct.setProductName(jsonProd.getString(getResources().getString(R.string.productName)));
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
    }

    @Override
    public void onStart() {
        super.onStart();
        fab = (FloatingActionButton) findViewById(R.id.fab);
        setActionButtonView();
        fab.setOnClickListener(this);
    }


    public void setAddToCartView() {
        fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.add_shopping_cart_white));
        fab.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(),R.color.add_item));
    }

    public void setActionButtonView() {
        if (!VController.getInstance().getCart().isItemInCart(sharedProduct)) {
            setAddToCartView();
        } else {
            setRemoveFromCartView();
        }
    }

    public void setRemoveFromCartView() {
        fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.remove_shopping_cart_white));
        fab.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(),R.color.remove_item));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main2,menu);
        badgeLayout = menu.findItem(R.id.cart_settings).getActionView().findViewById(R.id.badge_layout1);
        TextView badge = (TextView) badgeLayout.findViewById(R.id.badge_notification_1);
        badge.setText(VController.getInstance().getCart().getItemCount()+"");
        badgeLayout.setVisibility(View.VISIBLE);
        return true;
    }

    private void showProgressDialog() {
        progress_container.setVisibility(View.VISIBLE);
    }

    private void hideProgressDialog() {
        progress_container.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        if(!VController.getInstance().getCart().isItemInCart(sharedProduct)){
            VController.getInstance().getCart().addItemToCart(sharedProduct);
        } else {
            VController.getInstance().getCart().removeItemFromCart(sharedProduct);
        }
        fab.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.animator.fab_expand));
        fab.postDelayed(new Runnable() {
            @Override
            public void run() {
                setActionButtonView();
                fab.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.animator.fab_contract));
            }
        },500);
        updateCartView();
    }

    public void updateCartView() {
        TextView badge = (TextView) badgeLayout.findViewById(R.id.badge_notification_1);
        badge.setText(VController.getInstance().getCart().getItemCount()+"");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //Intent intent = new Intent(this,MainActivity.class);
        //startActivity(intent);
        //finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    public void showCart(View view) {
        //Toast.makeText(getApplicationContext(),"Hello",Toast.LENGTH_SHORT).show();
        FragmentManager fragmentManager = getFragmentManager();
        CartDialogFragment dialog = new CartDialogFragment();
        dialog.show(fragmentManager,"cart fragment");
    }
}
