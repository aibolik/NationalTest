package kz.akmarzhan.nationaltest.views.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import kz.akmarzhan.nationaltest.models.User;
import kz.akmarzhan.nationaltest.utils.Logger;
import kz.akmarzhan.nationaltest.views.BaseActivity;
import kz.akmarzhan.nationaltest.views.menu.MenuActivity;

/**
 * Created by aibol on 5/21/17.
 */

public class StartupActivity extends BaseActivity {

    private static final String TAG = StartupActivity.class.getSimpleName();

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AsyncCallback<Boolean> isValidLoginCallback = new AsyncCallback<Boolean>() {
            @Override
            public void handleResponse(Boolean response) {
                Logger.d(TAG, "isValidLogin: " + response);
                if (!response) {
                    Intent intent = new Intent(StartupActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    User user = realm.where(User.class).findFirst();
                    if (user == null) {
                        Intent intent = new Intent(StartupActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(StartupActivity.this, MenuActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }

                }
                finish();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Logger.d(TAG, "handleFault: " + fault.getMessage());
                Intent intent = new Intent(StartupActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }

        };

        Backendless.UserService.isValidLogin(isValidLoginCallback);
    }
}
