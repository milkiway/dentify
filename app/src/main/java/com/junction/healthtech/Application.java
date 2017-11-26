package com.junction.healthtech;

import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.junction.healthtech.utils.FirebaseDbHelper;

public class Application extends android.app.Application {

    private static FirebaseDbHelper fdbHelper;

    public static FirebaseDbHelper getFdbHelper() {
        return fdbHelper;
    }

    public static FirebaseInstanceId getInstanceId() { return fdbHelper.getInstanceID(); }

    public static FirebaseAuth getAuth() {
        return fdbHelper.getFirebaseAuth();
    }

    public static FirebaseAuth getAuthInstance() { return fdbHelper.getAuthInstance(); }


    @Override
    public void onCreate() {
        super.onCreate();
        fdbHelper = new FirebaseDbHelper(this);
        Fresco.initialize(getApplicationContext());
    }

    public static void changeSelectedDB(Context context){
        fdbHelper = new FirebaseDbHelper(context);
    }

}