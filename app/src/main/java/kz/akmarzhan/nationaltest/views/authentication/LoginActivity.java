package kz.akmarzhan.nationaltest.views.authentication;

import android.content.Intent;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.akmarzhan.nationaltest.R;
import kz.akmarzhan.nationaltest.views.BaseActivity;

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.bt_register) void openRegistration() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

}
