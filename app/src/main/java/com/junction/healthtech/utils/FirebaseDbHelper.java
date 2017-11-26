package com.junction.healthtech.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.text.format.DateFormat;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.junction.healthtech.models.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import rx.Observable;
import rxfirebase.RxFirebaseDatabase;

public class FirebaseDbHelper {
    public final static String DEFAULT_INSTITUTION_ID = "DEFAULT_INSTITUTION";

    private static final String TAG = FirebaseDbHelper.class.getSimpleName();

    private static final String USERS = "users";

    private DatabaseReference database;
    private FirebaseStorage storage;
    private FirebaseAuth auth;
    private FirebaseApp secondApp;

    public FirebaseDbHelper(Context context) {
            // CoopClear
            secondApp = FirebaseApp.getInstance();
            database = FirebaseDatabase.getInstance().getReference();
            storage = FirebaseStorage.getInstance();
            auth = getFirebaseAuth();
    }

    public FirebaseAuth getFirebaseAuth(){
            return auth = FirebaseAuth.getInstance();
    }

    public FirebaseInstanceId getInstanceID(){
            return FirebaseInstanceId.getInstance();
    }

    public FirebaseAuth getAuthInstance(){
            return auth.getInstance();
    }



    private DatabaseReference getUserRef(String userId) {
        return database.child(USERS).child(userId);
    }


    public Observable<User> fetchUserById(String userId) {
        return RxFirebaseDatabase
                .observeSingleValueEvent(getUserRef(userId), User.class);
    }


    public void createNewUser(Context context, String userId, User newUser) {
        getUserRef(userId).runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Log.d(TAG, "createNewUser.doTransaction");
                User existingUser = mutableData.getValue(User.class);
                Log.d(TAG, "Existing User: " + existingUser);
                if (existingUser == null) {
                    mutableData.setValue(newUser);
                    String notificationToken = Utils.getNotificationToken(context);
                    if (notificationToken!=null)
                        mutableData.child("notificationTokens").child(notificationToken).setValue(true);
                }
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                Log.d(TAG, "createNewUser.onComplete");
                User user = dataSnapshot.getValue(User.class);
                Log.d(TAG, "Final User: " + user);
            }
        });
    }


    public void saveNotificationToken(String role, String userId, String token) {
        if (token==null) {
            Log.e(TAG, "Notification token is null in saveNotificationToken");
            return;
        }
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/" + USERS + "/" + userId + "/notificationTokens/" + token, true);
        database.updateChildren(childUpdates);
    }


    public void removeNotificationToken(String userId, String token) {
        if (token==null) {
            Log.e(TAG, "Notification token is null in removeNotificationToken");
            return;
        }
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/" + USERS + "/" + userId + "/notificationTokens/" + token, false);
        database.updateChildren(childUpdates);
    }
}
