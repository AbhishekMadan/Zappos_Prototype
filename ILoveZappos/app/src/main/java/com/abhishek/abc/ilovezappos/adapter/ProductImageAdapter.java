package com.abhishek.abc.ilovezappos.adapter;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.abhishek.abc.ilovezappos.R;
import com.squareup.picasso.Picasso;

/**
 * Created by abc on 07-Feb-17.
 */

public class ProductImageAdapter {
    @BindingAdapter({"android:src"})
    public static void loadThumbnail(ImageView view, String url) {
        if(!url.equals("") || url.length()>0) {
            Picasso.with(view.getContext())
                    .load(url)
                    .resize(300,300)
                    .placeholder(R.drawable.no_image)
                    .into(view);
        }
    }
}
