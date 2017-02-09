package com.abhishek.abc.ilovezappos.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.abhishek.abc.ilovezappos.R;
import com.abhishek.abc.ilovezappos.adapter.ShoppingCartAdapter;
import com.abhishek.abc.ilovezappos.models.VController;

/**
 * Created by abc on 09-Feb-17.
 */

public class CartDialogFragment extends DialogFragment implements View.OnClickListener{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View dialog =  inflater.inflate(R.layout.dialog_fragment_cart,container,false);
        ((ImageView)dialog.findViewById(R.id.close_dialog)).setOnClickListener(this);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        ShoppingCartAdapter adapter = new ShoppingCartAdapter(getActivity().getApplicationContext());
        ((ListView)dialog.findViewById(R.id.shopping_list_view)).setAdapter(adapter);
        ((TextView) dialog.findViewById(R.id.total_amount)).setText(VController.getInstance().getCart().getCartTotalAsString());
        return dialog;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        getDialog().cancel();
    }
}
