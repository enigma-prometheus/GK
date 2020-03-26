package com.enigma.jeekay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.NestedScrollView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.enigma.jeekay.helper.InputValidation;
import com.enigma.jeekay.sql.DatabaseHelper;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = LoginActivity.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;

    private TextInputEditText email;
    private TextInputEditText password;

    private AppCompatButton login;

    private AppCompatTextView register;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        initViews();
        initListener();
        initObjects();
    }

    private void initViews(){
        nestedScrollView = (NestedScrollView)findViewById(R.id.nestedScrollView);

        textInputLayoutEmail = (TextInputLayout)findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword);

        email = (TextInputEditText)findViewById(R.id.email);
        password = (TextInputEditText)findViewById(R.id.password);

        login = (AppCompatButton)findViewById(R.id.login);

        register = (AppCompatTextView)findViewById(R.id.register);
    }

    private void initListener(){
        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    private void initObjects(){
        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                verifyFromSQLite();
                break;
            case R.id.register:
                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentRegister);
                break;
        }
    }

    private void verifyFromSQLite(){
        if (!inputValidation.isInputEditTextFilled(email,textInputLayoutEmail, getString(R.string.error_Email))){
            return;
        }
        if (!inputValidation.isInputEditTextEmail(email, textInputLayoutEmail, getString(R.string.error_Email))){
            return;
        }
        if (!inputValidation.isInputEditTextFilled(password, textInputLayoutPassword,getString(R.string.error_Email))){
            return;
        }
        if (databaseHelper.checkUser(email.getText().toString().trim(), password.getText().toString().trim())){
            Intent accountIntent = new Intent(activity, UserActivity.class);
            accountIntent.putExtra("Email", email.getText().toString().trim());
            emptyInputEditText();
            startActivity(accountIntent);
        } else {
            Snackbar.make(nestedScrollView, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
        }
    }

    private void emptyInputEditText(){
        email.setText(null);
        password.setText(null);
    }
}
