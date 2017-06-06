package kz.akmarzhan.nationaltest.views.authentication;

import android.content.Intent;
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
import kz.akmarzhan.nationaltest.R;
import kz.akmarzhan.nationaltest.utils.Logger;
import kz.akmarzhan.nationaltest.utils.Utils;
import kz.akmarzhan.nationaltest.views.BaseActivity;

/**
 * Created by Akmarzhan Raushanova on 5/21/2017.
 * Working on NationalTest. MobiLabs
 * You can contact me at: akmarzhan.raushnanova@is.sdu.edu.kz
 */

public class RegisterActivity extends BaseActivity {

    private static final String TAG = RegisterActivity.class.getSimpleName();

    @BindView(R.id.et_name) EditText etFullName;
    @BindView(R.id.et_email) EditText etEmail;
    @BindView(R.id.et_password) EditText etPassword;
    @BindView(R.id.et_password2) EditText etPassword2;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.bt_register) void registerUser() {
        AsyncCallback<BackendlessUser> callback = new AsyncCallback<BackendlessUser>() {
            @Override public void handleResponse(BackendlessUser registeredUser) {
                hideDialog();
                Logger.d(TAG, "User has been registered. ObjectID: " + registeredUser.getObjectId());
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }

            @Override public void handleFault(BackendlessFault fault) {
                hideDialog();
                Logger.d(TAG, "handleFault: " + fault.getMessage());
                showMessage(fault.getMessage());
            }
        };

        String email = etEmail.getText().toString();
        String name = etFullName.getText().toString();
        String password = etPassword.getText().toString();
        String password2 = etPassword2.getText().toString();

        if(Utils.isEmpty(password) || Utils.isEmpty(password2)
                || Utils.isEmpty(name) || Utils.isEmpty(email)) {
            showMessage("Please, fill all fields");
            return;
        }

        if(!Utils.passwordsMatch(password, password2)) {
            showMessage("Passwords do not match");
            return;
        }

        BackendlessUser user = new BackendlessUser();
        user.setEmail(etEmail.getText().toString());
        user.setPassword(etPassword.getText().toString());
        user.setProperty("name", etFullName.getText().toString());

        Backendless.UserService.register(user, callback);
        showDialog("Registration", "Please wait...");
    }

}
