package com.junction.healthtech;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseUser;
import com.junction.healthtech.databinding.ActivitySignInBinding;
import com.junction.healthtech.models.User;
import com.junction.healthtech.utils.FirebaseActivity;
import com.junction.healthtech.utils.Utils;

public class SignInActivity extends FirebaseActivity {
    private static final String TAG = "SignInActivity";

    private ActivitySignInBinding binding;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int count = FirebaseApp.getApps(this).size();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in);

        // Click listeners
        binding.signInBtnView.setOnClickListener(v -> signIn());
        binding.signUpBtnView.setOnClickListener(v -> signUp());
        // TODO
//        binding.switchDB.setOnCheckedChangeListener((buttonView, isChecked) -> switchChange(isChecked));

//        Application.changeSelectedDB(this);
        // TODO
//        Application.changeSelectedDB(this, Utils.getSwitchPosition());
        refreshFBHelper();
        // TODO
//        binding.switchDB.setChecked(Utils.getSwitchPosition());
    }


    @Override
    public void onStart() {
        super.onStart();

        // Check auth on Activity start
        if (auth.getCurrentUser() != null) {
            //
            Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(this, R.anim.fade_in, R.anim.fade_out);
            startActivity(intent,options.toBundle());
            finish();
        }
    }

    private void signIn() {
        Log.d(TAG, "signIn");
        if (!validateForm()) {
            return;
        }

        showProgressDialog();

        String email = binding.emailFieldView.getText().toString().trim();
        String password = binding.passwordFieldView.getText().toString();

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            Log.d(TAG, "signIn:onComplete:" + task.isSuccessful());
            hideProgressDialog();

            if (task.isSuccessful()) {
                onAuthSuccess(task.getResult().getUser());
            } else {
                Toast.makeText(SignInActivity.this, R.string.error_sign_in_failed,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void signUp() {
        Log.d(TAG, "signUp");
        if (!validateForm()) {
            return;
        }

        showProgressDialog();

        String email = binding.emailFieldView.getText().toString();
        String password = binding.passwordFieldView.getText().toString();

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            Log.d(TAG, "createUser:onComplete:" + task.isSuccessful());
            hideProgressDialog();

            if (task.isSuccessful()) {
                onAuthSuccess(task.getResult().getUser());
            } else {
                Toast.makeText(SignInActivity.this, R.string.error_sign_up_failed,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onAuthSuccess(FirebaseUser firebaseUser) {
        fdbHelper.fetchUserById(firebaseUser.getUid()).first().subscribe(user-> {

            boolean isNew = user == null;

            if (isNew) {
                String username = usernameFromEmail(firebaseUser.getEmail());
                user = new User(username, firebaseUser.getEmail());
            }

            fdbHelper.saveNotificationToken(user.getRole(), firebaseUser.getUid(), Utils.getNotificationToken(this));
            SharedPreferences.Editor sharedPrefEditor = Utils.getSharedPrefEditor(this);
            sharedPrefEditor.putString(User.ROLE, user.getRole());
            sharedPrefEditor.putString(User.USER_ID, firebaseUser.getUid());
            sharedPrefEditor.putString(User.USER_NAME, user.getName());
            sharedPrefEditor.putString(User.USER_AVATAR, user.getAvatarUrl());
            sharedPrefEditor.putString(User.PHONE, user.getPhone());
            sharedPrefEditor.apply();

            if(isNew)
                fdbHelper.createNewUser(this, firebaseUser.getUid(), user);

            // Go to HomeActivity
            Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(this, R.anim.fade_in, R.anim.fade_out);
            startActivity(intent, options.toBundle());

            finish();
        }, Throwable::printStackTrace);
    }

    public static String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(binding.emailFieldView.getText().toString())) {
            binding.emailFieldView.setError(getString(R.string.warning_field_required));
            result = false;
        } else {
            binding.emailFieldView.setError(null);
        }

        if (TextUtils.isEmpty(binding.passwordFieldView.getText().toString())) {
            binding.passwordFieldView.setError(getString(R.string.warning_password_field_required));
            result = false;
        } else {
            binding.passwordFieldView.setError(null);
        }

        return result;
    }

    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage(getString(R.string.sign_in_loading_dialog));
        }

        progressDialog.show();
    }

    private void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
