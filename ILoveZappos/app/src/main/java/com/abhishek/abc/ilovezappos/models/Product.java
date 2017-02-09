package com.abhishek.abc.ilovezappos.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.abhishek.abc.ilovezappos.util.NumberConversionUtil;
import com.android.databinding.library.baseAdapters.BR;


/**
 * Created by abc on 06-Feb-17.
 */

public class Product extends BaseObservable {
    private String brandName;
    private String thumbnailImageUrl;
    private long productId;
    private double originalPrice;
    private long styleId;
    private long colorId;
    private double price;
    private double percentOff;
    private String productUrl;
    private String productName;

    public Product() {
    }

    public Product(Product p) {
        this.brandName = p.brandName;
        this.thumbnailImageUrl = p.thumbnailImageUrl;
        this.productId = p.productId;
        this.originalPrice = p.originalPrice;
        this.styleId = p.styleId;
        this.colorId = p.colorId;
        this.price = p.price;
        this.percentOff = p.percentOff;
        this.productUrl = p.productUrl;
        this.productName = p.productName;
    }

    public Product(String brandName,
                   String thumbnailImageUrl,
                   long productId,
                   double originalPrice,
                   long styleId,
                   long colorId,
                   double price,
                   double percentOff,
                   String productUrl,
                   String productName) {
        this.brandName = brandName;
        this.thumbnailImageUrl = thumbnailImageUrl;
        this.productId = productId;
        this.originalPrice = originalPrice;
        this.styleId = styleId;
        this.colorId = colorId;
        this.price = price;
        this.percentOff = percentOff;
        this.productUrl = productUrl;
        this.productName = productName;
    }

    @Bindable
    public String getBrandName() {
        return brandName;
    }


    public void setBrandName(String brandName) {
        this.brandName = brandName;
        notifyPropertyChanged(BR.brandName);
    }

    public String getThumbnailImageUrl() {
        return thumbnailImageUrl;
    }

    public void setThumbnailImageUrl(String thumbnailImageUrl) {
        this.thumbnailImageUrl = thumbnailImageUrl;
    }

    public long getProductId() {
        return productId;
    }

    @Bindable
    public String getProductIdAsString() {
        return productId+"";
    }

    public void setProductId(long productId) {
        this.productId = productId;
        notifyPropertyChanged(BR.productIdAsString);
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    @Bindable
    public String getOriginalPriceAsString() {
        return "$" + NumberConversionUtil.setPrecision(getOriginalPrice());
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
        notifyPropertyChanged(BR.originalPriceAsString);
    }

    public long getStyleId() {
        return styleId;
    }

    @Bindable
    public String getStyleIdAsString() {
        return getStyleId()+"";
    }

    public void setStyleId(long styleId) {
        this.styleId = styleId;
        notifyPropertyChanged(BR.styleIdAsString);
    }

    public long getColorId() {
        return colorId;
    }

    @Bindable
    public String getColorIdAsString() {
        return getColorId()+"";
    }

    public void setColorId(long colorId) {
        this.colorId = colorId;
        notifyPropertyChanged(BR.colorIdAsString);
    }

    public double getPrice() {
        return price;
    }

    @Bindable
    public String getPriceAsString() {
        return "$"+NumberConversionUtil.setPrecision(getPrice());
    }

    public void setPrice(double price) {
        this.price = price;
        notifyPropertyChanged(BR.priceAsString);
    }

    @Bindable
    public String getPercentOffAsString() {
        return getPercentOff()+"%";
    }

    public double getPercentOff() {
        return percentOff;
    }

    public void setPercentOff(double percentOff) {
        this.percentOff = percentOff;
    }

    @Bindable
    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    @Bindable
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
        notifyPropertyChanged(BR.productName);
    }

    @Override
    public String toString() {
        return "Product{" +
                "brandName='" + brandName + '\'' +
                ", thumbnailImageUrl='" + thumbnailImageUrl + '\'' +
                ", productId=" + productId +
                ", originalPrice=" + originalPrice +
                ", styleId=" + styleId +
                ", colorId=" + colorId +
                ", price=" + price +
                ", percentOff=" + percentOff +
                ", productUrl='" + productUrl + '\'' +
                ", productName='" + productName + '\'' +
                '}';
    }
}
