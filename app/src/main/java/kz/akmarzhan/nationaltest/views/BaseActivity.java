package kz.akmarzhan.nationaltest.views;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.otto.Bus;

import kz.akmarzhan.nationaltest.bus.BusProvider;

/**
 * Created by Aibol Kussain on 5/14/2017.
 * Working on NationalTest. MobiLabs
 * You can contact me at: aibolikdev@gmail.com
 */

public class BaseActivity extends AppCompatActivity {

    private static final String TAG = BaseActivity.class.getSimpleName();

    protected Bus mBus;

    private ViewGroup rootView;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override protected void onResume() {
        super.onResume();

        getBus().register(this);
    }

    @Override protected void onPause() {
        super.onPause();

        getBus().unregister(this);
    }

    protected Bus getBus() {
        if (mBus == null) {
            mBus = BusProvider.getInstance();
        }
        return mBus;
    }

    protected Context getContext() {
        return this;
    }

    protected ViewGroup getView() {
        return rootView;
    }

    protected void showMessage(String message) {
        if(this.getView() != null) {
            Snackbar.make(this.getView(), "Replace with your own action", Snackbar.LENGTH_LONG)
                    .show();
        }
        else {
            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
        }
    }
}
