package com.junction.healthtech.utils;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.junction.healthtech.Application;
import com.junction.healthtech.R;

public class FirebaseActivity extends AppCompatActivity {
//    public static final String TASK_ID = "taskId";

    //    protected String taskId;
    protected String groupId;
    protected String courseId;
    protected FirebaseDbHelper fdbHelper;
    protected FirebaseAuth auth;
    protected FirebaseAuth authInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Application.changeSelectedDB(this);
        fdbHelper = Application.getFdbHelper();
        auth = Application.getAuth();
        authInstance = Application.getAuthInstance();
    }

    protected void refreshFBHelper() {
        fdbHelper = Application.getFdbHelper();
        auth = Application.getAuth();
        authInstance = Application.getAuthInstance();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
