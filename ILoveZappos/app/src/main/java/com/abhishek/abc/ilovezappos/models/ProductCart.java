package com.abhishek.abc.ilovezappos.models;

import com.abhishek.abc.ilovezappos.util.NumberConversionUtil;

import java.util.ArrayList;

/**
 * Created by abc on 06-Feb-17.
 */

public class ProductCart {
    private static ProductCart cart = null;
    private static ArrayList<Product> itemList = null;
    private double total;

    private ProductCart() {
        itemList = new ArrayList<>();
        total=0;
    }

    public static ProductCart getCartInstance() {
        if (cart==null) {
            cart  = new ProductCart();
        }
        return  cart;
    }

    public boolean isCartEmpty(){
        return itemList.size()>0?true:false;
    }

    public int getItemCount() {
        return itemList.size();
    }

    public void addItemToCart(Product prod) {
        total+=prod.getPrice();
        itemList.add(new Product(prod));
    }

    public boolean isItemInCart(Product p) {
        for(Product prod:itemList) {
            if(prod.getProductId()==p.getProductId()) {
                return true;
            }
        }
        return false;
    }

    public void removeItemFromCart(Product p) {

        for(Product prod: itemList) {
            if(prod.getProductId() == p.getProductId()) {
                itemList.remove(prod);
                total-=p.getPrice();
                break;
            }
        }
    }

    public ArrayList<Product> getItemsInCart() {
        return itemList;
    }

    public String getCartTotalAsString() {
        return "$"+ NumberConversionUtil.setPrecision(total);
    }
}
