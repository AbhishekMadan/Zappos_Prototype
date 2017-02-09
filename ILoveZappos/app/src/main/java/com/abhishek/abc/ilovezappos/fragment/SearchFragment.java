package com.abhishek.abc.ilovezappos.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.abhishek.abc.ilovezappos.R;
import com.abhishek.abc.ilovezappos.activity.MainActivity;
import com.abhishek.abc.ilovezappos.models.Product;
import com.abhishek.abc.ilovezappos.models.VController;
import com.abhishek.abc.ilovezappos.network.ZapRestAdapter;
import com.abhishek.abc.ilovezappos.util.NumberConversionUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchFragment extends Fragment implements View.OnClickListener {

    private final String TAG = "SEARCH_FRAGMENT";
    private TextView searchButton;
    private EditText searchItem;
    private View progress_container;
    private Context activityContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        if (savedInstanceState != null) {
            searchItem.setText(savedInstanceState.getString("search_item"));
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activityContext = context;
    }

    public void init() {
        searchItem = (EditText) getActivity().findViewById(R.id.et_searchInput);
        searchButton = (TextView) getActivity().findViewById(R.id.tv_searchButton);
        searchButton.setOnClickListener(this);
        progress_container = getActivity().findViewById(R.id.progress_container);
    }

    private void showProgressDialog() {
        progress_container.setVisibility(View.VISIBLE);
    }

    private void hideProgressDialog() {
        progress_container.setVisibility(View.INVISIBLE);
    }


    @Override
    public void onClick(View v) {
        if (searchItem.getText().toString().trim().length() > 0) {
            showProgressDialog();
            getProducts(searchItem.getText().toString().trim());
        }
    }

    public void getProducts(String prod) {
        String apiKey = getResources().getString(R.string.api_key);
        Call<ResponseBody> call = ZapRestAdapter.getRestAdapter().getProducts(prod, apiKey);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(parseResponseAndSaveProduct(response)) {
                        Log.d(TAG,VController.getInstance().getProduct().toString());
                        ((MainActivity) getActivity()).dataFetchSuccessful();
                    }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "Error Response from Server.");
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
                VController.getInstance()
                        .updateProduct(new Product(
                                jsonProd.getString(getResources().getString(R.string.brandName)),
                                jsonProd.getString(getResources().getString(R.string.thumbnailImageUrl)),
                                jsonProd.getLong(getResources().getString(R.string.productId)),
                                NumberConversionUtil.getCurrencyFromString(jsonProd.getString(getResources().getString(R.string.originalPrice))),
                                jsonProd.getLong(getResources().getString(R.string.styleId)),
                                jsonProd.getLong(getResources().getString(R.string.colorId)),
                                NumberConversionUtil.getCurrencyFromString(jsonProd.getString(getResources().getString(R.string.price))),
                                NumberConversionUtil.getPercentFractionFromString(jsonProd.getString(getResources().getString(R.string.percentOff))),
                                jsonProd.getString(getResources().getString(R.string.productUrl)),
                                jsonProd.getString(getResources().getString(R.string.productName))));
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
        } finally {
            hideProgressDialog();
        }
        return isSuccess;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (searchItem != null && searchItem.getText().toString().trim().length() > 0)
            outState.putString("search_item", searchItem.getText().toString());
    }
}
