package com.abhishek.abc.ilovezappos.fragment;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.abhishek.abc.ilovezappos.R;
import com.abhishek.abc.ilovezappos.activity.MainActivity;
import com.abhishek.abc.ilovezappos.databinding.FragmentProductBinding;
import com.abhishek.abc.ilovezappos.models.Product;
import com.abhishek.abc.ilovezappos.models.VController;

/**
 * Created by abc on 06-Feb-17.
 */


public class ProductFragment extends Fragment implements View.OnClickListener{

    private FragmentProductBinding binding;
    private Product product;
    private FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_product,container,false);
        product = VController.getInstance().getProduct();
        binding.setProduct(VController.getInstance().getProduct());
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        setActionButtonView();
        fab.setOnClickListener(this);
    }

    public void setAddToCartView() {
        fab.setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(),R.drawable.add_shopping_cart_white));
        fab.setBackgroundTintList(ContextCompat.getColorStateList(getActivity().getApplicationContext(),R.color.add_item));
    }

    public void setActionButtonView() {
        if (!VController.getInstance().getCart().isItemInCart(product)) {
            setAddToCartView();
        } else {
            setRemoveFromCartView();
        }
    }

    public void setRemoveFromCartView() {
        fab.setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(),R.drawable.remove_shopping_cart_white));
        fab.setBackgroundTintList(ContextCompat.getColorStateList(getActivity().getApplicationContext(),R.color.remove_item));
    }

    @Override
    public void onClick(View v) {
        if(!VController.getInstance().getCart().isItemInCart(product)){
            VController.getInstance().getCart().addItemToCart(product);
        } else {
            VController.getInstance().getCart().removeItemFromCart(product);
        }
        fab.startAnimation(AnimationUtils.loadAnimation(getActivity().getApplicationContext(),R.animator.fab_expand));
        fab.postDelayed(new Runnable() {
            @Override
            public void run() {
                setActionButtonView();
                fab.startAnimation(AnimationUtils.loadAnimation(getActivity().getApplicationContext(),R.animator.fab_contract));
            }
        },500);
        ((MainActivity)getActivity()).updateCartView();
    }
}
