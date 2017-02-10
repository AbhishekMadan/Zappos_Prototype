package com.abhishek.abc.ilovezappos.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.abhishek.abc.ilovezappos.R;
import com.abhishek.abc.ilovezappos.fragment.CartDialogFragment;
import com.abhishek.abc.ilovezappos.fragment.ProductFragment;
import com.abhishek.abc.ilovezappos.fragment.SearchFragment;
import com.abhishek.abc.ilovezappos.models.VController;
import com.abhishek.abc.ilovezappos.util.UriUtils;


public class MainActivity extends AppCompatActivity implements DataFetchSuccessCommunication {

    private FragmentManager fragmentManager;
    private Fragment searchFrag, productFrag;
    private final String TAG = "ILOVEZAP";
    private Toolbar actionBar;
    private Menu menu;
    private View badgeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initAndGetView(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main2,menu);
        this.menu = menu;
        updateCartView();
        return true;
    }


    public void showCart(View view) {
        CartDialogFragment dialog = new CartDialogFragment();
        dialog.show(fragmentManager,"cart fragment");
    }

    private void initAndGetView(Bundle saveBundle) {
        //action bar
        actionBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(actionBar);

        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //fragment transaction for normal work-flow
        if(saveBundle==null) {
            searchFrag = new SearchFragment();
            fragmentTransaction.add(R.id.frag_container,searchFrag,"search_fragment");
            fragmentTransaction.commit();
        } else {
            searchFrag = fragmentManager.findFragmentByTag("search_fragment");
        }
    }

    private void changeView() {
        if (fragmentManager == null) {
            fragmentManager = getFragmentManager();
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if(VController.ROTATION%2!=0){
            productFrag = new ProductFragment();
            transaction.setCustomAnimations(R.animator.card_flip_right_in,
                    R.animator.card_flip_right_out,
                    R.animator.card_flip_left_in,
                    R.animator.card_flip_left_out);
            transaction.replace(R.id.frag_container, productFrag);
            transaction.addToBackStack(null);
        } else {
            fragmentManager.popBackStack();
        }
        VController.ROTATION = (VController.ROTATION+1)%2;
        transaction.commit();
        updateCartView();
    }

    @Override
    public void onBackPressed() {
        VController.ROTATION = (VController.ROTATION+1)%2;
        updateCartView();
        //automatically pops the backstack for the fragment
        super.onBackPressed();
    }

    @Override
    public void dataFetchSuccessful() {
        changeView();
    }

    @Override
    public void updateCartView() {
        badgeLayout = menu.findItem(R.id.cart_settings).getActionView().findViewById(R.id.badge_layout1);
        TextView badge = (TextView) badgeLayout.findViewById(R.id.badge_notification_1);
        badge.setText(VController.getInstance().getCart().getItemCount()+"");
        if(VController.ROTATION%2!=0 && VController.getInstance().getCart().getItemCount()==0) {
            badgeLayout.setVisibility(View.INVISIBLE);
        } else {
            badgeLayout.setVisibility(View.VISIBLE);
        }
    }
}
