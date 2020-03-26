package com.enigma.jeekay;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.NestedScrollView;

import com.enigma.jeekay.helper.InputValidation;
import com.enigma.jeekay.model.User;
import com.enigma.jeekay.sql.DatabaseHelper;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = RegisterActivity.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutName;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;
    private TextInputLayout textInputLayoutConfirmPassword;

    private TextInputEditText register_name;
    private TextInputEditText register_email;
    private TextInputEditText register_password;
    private TextInputEditText confirmPassword;

    private AppCompatButton register;
    private AppCompatTextView login;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private User user;

    @Override
    protected void onCreate(Bundle si){
        super.onCreate(si);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        initViews();
        initListener();
        initObjects();
    }

    private void initViews(){
        nestedScrollView = (NestedScrollView)findViewById(R.id.nestedScrollView);

        textInputLayoutName = (TextInputLayout)findViewById(R.id.textInputLayoutName);
        textInputLayoutEmail = (TextInputLayout)findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword);
        textInputLayoutConfirmPassword = (TextInputLayout)findViewById(R.id.textInputLayoutConfirmPassword);

        register_name = (TextInputEditText)findViewById(R.id.register_name);
        register_email = (TextInputEditText)findViewById(R.id.register_email);
        register_password = (TextInputEditText)findViewById(R.id.register_password);
        confirmPassword = (TextInputEditText)findViewById(R.id.confirmPassword);

        register = findViewById(R.id.register_btn);

        login = findViewById(R.id.login_link);
    }

    private void initListener(){
        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    private void initObjects(){
        inputValidation = new InputValidation(activity);
        databaseHelper = new DatabaseHelper(activity);
        user = new User();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_btn:
            postDataToSQLite();
            break;
            case R.id.login_link:
                finish();
                break;
        }
    }
    private void postDataToSQLite(){
        if (!inputValidation.isInputEditTextFilled(register_name, textInputLayoutName, getString(R.string.error_Name))){
            return;
        }
        if (!inputValidation.isInputEditTextFilled(register_email, textInputLayoutEmail, getString(R.string.error_Email))){
            return;
        }
        if (!inputValidation.isInputEditTextEmail(register_email, textInputLayoutEmail, getString(R.string.error_Email))){
            return;
        }
        if (!inputValidation.isInputEditTextFilled(register_password, textInputLayoutPassword, getString(R.string.error_Password))){
            return;
        }
        if (!inputValidation.isInputEditTextMatches(register_password, confirmPassword, textInputLayoutConfirmPassword, getString(R.string.error_password_match))){
            return;
        }

        if (!databaseHelper.checkUser(register_email.getText().toString().trim(), register_password.getText().toString().trim())){
            user.setName(register_name.getText().toString().trim());
            user.setEmail(register_email.getText().toString().trim());
            user.setPassword(register_password.getText().toString().trim());

            databaseHelper.addUser(user);

            Snackbar.make(nestedScrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
            emptyInputEditText();

        } else {
            Snackbar.make(nestedScrollView, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show();
        }
    }

    private void emptyInputEditText(){
        register_name.setText(null);
        register_email.setText(null);
        register_password.setText(null);
        confirmPassword.setText(null);
    }
}
