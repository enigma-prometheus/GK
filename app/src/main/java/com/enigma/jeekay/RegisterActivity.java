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

    private TextInputEditText name;
    private TextInputEditText email;
    private TextInputEditText password;
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

        textInputLayoutEmail = (TextInputLayout)findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword);

        email = (TextInputEditText)findViewById(R.id.email);
        password = (TextInputEditText)findViewById(R.id.password);

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
        if (!inputValidation.isInputEditTextFilled(name, textInputLayoutName, getString(R.string.error_Name))){
            return;
        }
        if (!inputValidation.isInputEditTextFilled(email, textInputLayoutEmail, getString(R.string.error_Email))){
            return;
        }
        if (!inputValidation.isInputEditTextEmail(email, textInputLayoutEmail, getString(R.string.error_Email))){
            return;
        }
        if (!inputValidation.isInputEditTextFilled(password, textInputLayoutPassword, getString(R.string.error_Password))){
            return;
        }
        if (!inputValidation.isInputEditTextMatches(password, confirmPassword, textInputLayoutConfirmPassword, getString(R.string.error_password_match))){
            return;
        }

        if (!databaseHelper.checkUser(email.getText().toString().trim(), password.getText().toString().trim())){
            user.setName(name.getText().toString().trim());
            user.setEmail(email.getText().toString().trim());
            user.setPassword(password.getText().toString().trim());

            databaseHelper.addUser(user);

            Snackbar.make(nestedScrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
            emptyInputEditText();

        } else {
            Snackbar.make(nestedScrollView, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show();
        }
    }

    private void emptyInputEditText(){
        name.setText(null);
        email.setText(null);
        password.setText(null);
        confirmPassword.setText(null);
    }
}
