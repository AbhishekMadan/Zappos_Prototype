package com.abhishek.abc.ilovezappos.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.abhishek.abc.ilovezappos.BR;
import com.abhishek.abc.ilovezappos.R;
import com.abhishek.abc.ilovezappos.models.Product;
import com.abhishek.abc.ilovezappos.models.VController;

import java.util.ArrayList;

/**
 * Created by abc on 09-Feb-17.
 */

public class ShoppingCartAdapter extends BaseAdapter {

    private ArrayList<Product> list;
    private Context context;

    public ShoppingCartAdapter(Context c){
        list = VController.getInstance().getCart().getItemsInCart();
        context = c;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.cart_item_view_layout,parent,false);
        viewDataBinding.setVariable(BR.product,list.get(position));
        return viewDataBinding.getRoot();
    }
}
