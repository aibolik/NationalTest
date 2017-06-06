package kz.akmarzhan.nationaltest.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.squareup.otto.Bus;

import io.realm.Realm;
import kz.akmarzhan.nationaltest.bus.BusProvider;
import kz.akmarzhan.nationaltest.models.User;
import kz.akmarzhan.nationaltest.utils.Logger;
import kz.akmarzhan.nationaltest.views.authentication.LoginActivity;

/**
 * Created by Akmarzhan Raushanova on 5/14/2017.
 * Working on NationalTest. MobiLabs
 * You can contact me at: akmarzhan.raushnanova@is.sdu.edu.kz
 */

public class BaseActivity extends AppCompatActivity {

    private static final String TAG = BaseActivity.class.getSimpleName();

    protected Bus mBus;
    protected Realm realm;

    protected User mUser;

    private ViewGroup rootView;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        realm = Realm.getDefaultInstance();
    }

    @Override protected void onResume() {
        super.onResume();

        getBus().register(this);
    }

    @Override protected void onDestroy() {
        super.onDestroy();

        if(realm != null) {
            realm.close();
        }
    }

    @Override protected void onPause() {
        super.onPause();

        getBus().unregister(this);
    }

    protected void saveUser(final BackendlessUser loggedInUser,
                          Realm.Transaction.OnSuccess onSuccess,
                          Realm.Transaction.OnError onError) {
        Logger.d(TAG, "saveUser: ");

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                User user = new User();
                user.setObjectId(loggedInUser.getObjectId());
                user.setName(loggedInUser.getProperty("name").toString());
                user.setEmail(loggedInUser.getEmail());
                user.setExp((Integer) loggedInUser.getProperty("exp"));
                bgRealm.copyToRealmOrUpdate(user);
            }
        }, onSuccess, onError);

    }

    protected void getUser() {
        Logger.d(TAG, "getUser: ");
        mUser = realm.where(User.class).findFirst();
        if (mUser == null) {
            logout();
            finish();
        }
    }

    protected void logout() {
        Backendless.UserService.logout(new AsyncCallback<Void>() {
            public void handleResponse(Void response) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override public void execute(Realm realm) {
                        realm.delete(User.class);
                    }
                });
                Logger.d(TAG, "handleResponse: user deleted. Logging out");
                Intent intent = new Intent(getContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
                finish();
            }

            public void handleFault(BackendlessFault fault) {
                Logger.d(TAG, "handleFault: " + fault.getMessage());
            }
        });
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
