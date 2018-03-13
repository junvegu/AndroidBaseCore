package com.raccoon.peruvende;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.raccoon.peruvende.ecomerce.startup.MainActivity;
import com.raccoon.peruvende.ecomerce.startup.WelcomeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_forget_password)
    TextView etForgetPassword;
    @BindView(R.id.et_register)
    TextView etRegister;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_login_facebook)
    Button btnLoginFacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_login, R.id.btn_login_facebook})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                launchHomeScreen();

                break;
            case R.id.btn_login_facebook:

                launchHomeScreen();
                break;
        }
    }

    private void launchHomeScreen() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }
}
