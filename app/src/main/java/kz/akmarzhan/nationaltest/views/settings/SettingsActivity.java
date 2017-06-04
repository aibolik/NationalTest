package kz.akmarzhan.nationaltest.views.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;

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
import kz.akmarzhan.nationaltest.utils.Utils;
import kz.akmarzhan.nationaltest.views.BaseActivity;

/**
 * Created by aibol on 6/4/17.
 */

public class SettingsActivity extends BaseActivity {

    private static final String TAG = SettingsActivity.class.getSimpleName();

    @BindView(R.id.et_name) EditText etName;
    @BindView(R.id.et_email) EditText etEmail;
    @BindView(R.id.et_password_old) EditText etPasswordOld;
    @BindView(R.id.et_password) EditText etPassword;
    @BindView(R.id.et_password2) EditText etPassword2;


    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ButterKnife.bind(this);

        getUser();

        etName.setText(mUser.getName());
        etEmail.setText(mUser.getEmail());
    }

    @OnClick(R.id.bt_save) void save() {
        final String name = etName.getText().toString();
        final String email = etEmail.getText().toString();
        String oldPassword = etPasswordOld.getText().toString();
        final String newPassword = etPassword.getText().toString();
        String newPassword2 = etPassword2.getText().toString();

        boolean isChangingPassword = false;
        if (!oldPassword.isEmpty() || !newPassword.isEmpty() || !newPassword2.isEmpty()) {
            isChangingPassword = true;
        }

        if (validateFields(isChangingPassword)) {
            BackendlessUser user = new BackendlessUser();
            user.setProperty("objectId", mUser.getObjectId());
            user.setEmail(email);
            user.setProperty("name", name);

            if (isChangingPassword) {
                Logger.d("SettingsActivity", "save: changing password" );
                Backendless.UserService.login(mUser.getEmail(), oldPassword, new AsyncCallback<BackendlessUser>() {
                    @Override public void handleResponse(final BackendlessUser newUser) {
                        Logger.d("SettingsActivity", "handleResponse: success password");
                        newUser.setEmail(email);
                        newUser.setProperty("name", name);
                        newUser.setPassword(newPassword);

                        Backendless.UserService.update(newUser, new AsyncCallback<BackendlessUser>() {
                            @Override public void handleResponse(final BackendlessUser newUser) {
                                showMessage("Sussesfully updated");
                                saveUser(newUser, new Realm.Transaction.OnSuccess() {
                                    @Override
                                    public void onSuccess() {
                                        Logger.d(TAG, "onSuccess: user updated: " + newUser.toString());

                                    }
                                }, new Realm.Transaction.OnError() {
                                    @Override
                                    public void onError(Throwable error) {
                                        Logger.d(TAG, "onError: user not updated - " + error.getMessage());
                                    }
                                });
                            }

                            @Override public void handleFault(BackendlessFault fault) {
                                Logger.d("SettingsActivity", "handleFault: " + fault.getMessage());
                            }
                        });
                    }

                    @Override public void handleFault(BackendlessFault fault) {
                        showMessage("Your old password is incorrect");
                    }
                });
            } else {
                Backendless.UserService.update(user, new AsyncCallback<BackendlessUser>() {
                    @Override public void handleResponse(final BackendlessUser newUser) {
                        showMessage("Sussesfully updated");
                        saveUser(newUser, new Realm.Transaction.OnSuccess() {
                            @Override
                            public void onSuccess() {
                                Logger.d(TAG, "onSuccess: user updated: " + newUser.toString());

                            }
                        }, new Realm.Transaction.OnError() {
                            @Override
                            public void onError(Throwable error) {
                                Logger.d(TAG, "onError: user not updated - " + error.getMessage());
                            }
                        });
                    }

                    @Override public void handleFault(BackendlessFault fault) {
                        Logger.d("SettingsActivity", "handleFault: " + fault.getMessage());
                    }
                });
            }
        }

    }

    private boolean validateFields(boolean isChangingPassword) {
        if (Utils.isEmpty(etName.getText().toString()) || Utils.isEmpty(etEmail.getText().toString())) {
            showMessage("Please, fill all fields");
            return false;
        }
        if (!Utils.isValidEmail(etEmail.getText().toString())) {
            showMessage("Please, write valid email");
            return false;
        }
        if (isChangingPassword) {
            if (Utils.isEmpty(etPasswordOld.getText().toString())) {
                showMessage("Please, fill old password");
                return false;
            }
            if (Utils.isEmpty(etPassword2.getText().toString())) {
                showMessage("Please, fill new password");
                return false;
            }
            if (!Utils.passwordsMatch(etPassword.getText().toString(), etPassword2.getText().toString())) {
                showMessage("Passwords do not match");
                return false;
            }
            return true;
        } else {
            return true;
        }
    }

}
