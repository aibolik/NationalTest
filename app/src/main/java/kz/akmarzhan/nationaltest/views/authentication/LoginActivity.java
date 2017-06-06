package kz.akmarzhan.nationaltest.views.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import kz.akmarzhan.nationaltest.R;
import kz.akmarzhan.nationaltest.utils.Logger;
import kz.akmarzhan.nationaltest.views.BaseActivity;
import kz.akmarzhan.nationaltest.views.menu.MenuActivity;

public class LoginActivity extends BaseActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    @BindView(R.id.et_login) EditText etEmail;
    @BindView(R.id.et_password) EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.bt_login) void login() {
        showDialog("", "Signing in. Please wait...");
        Backendless.UserService.login(etEmail.getText().toString(),
                etPassword.getText().toString(), new AsyncCallback<BackendlessUser>() {
                    public void handleResponse(final BackendlessUser loggedInUser) {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.hide();
                        }
                        saveUser(loggedInUser, new Realm.Transaction.OnSuccess() {
                            @Override
                            public void onSuccess() {
                                Logger.d(TAG, "onSuccess: user added: " + loggedInUser.toString());
                                Intent intent = new Intent(getContext(), MenuActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();

                            }
                        }, new Realm.Transaction.OnError() {
                            @Override
                            public void onError(Throwable error) {
                                Logger.d(TAG, "onError: user not added - " + error.getMessage());
                            }
                        });
                    }

                    public void handleFault(BackendlessFault fault) {
                        hideDialog();
                        Toast.makeText(LoginActivity.this, fault.getMessage(), Toast.LENGTH_LONG).show();
                        Logger.d(TAG, "handleFault: " + fault.getMessage());
                        // login failed, to get the error code call fault.getCode()
                    }
                }, true);
    }

    @OnClick(R.id.bt_register) void openRegistration() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }



}
