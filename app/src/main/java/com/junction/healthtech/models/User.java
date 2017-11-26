package com.junction.healthtech.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


@IgnoreExtraProperties
public class User extends ParentItem implements Parcelable {
    public static final String USER_ID = "user_id";
    public static final String ROLE = "role";
    public static final String USER_NAME = "user_name";
    public static final String USER_AVATAR = "user_avatar";
    public static final String PHONE = "phone";
    public static String ROLE_PATIENT = "patient";
    public static String ROLE_DOCTOR = "doctor";

    private String email;
    private String phone;
    private String role;
    private Map<String, Boolean> groups;
    private String avatarUrl;
    private Map<String, Boolean> notificationTokens;
    private long timestamp;     // timestamp when user deselect chat

    public String getEmail() {
        return email;
    }

    public String getPhone(){return phone;}

    public String getRole() {
        return role;
    }

    /**
     * null safety getter for {@link #groups} field
     */
    public Map<String, Boolean> getGroups() {
        return groups == null ? Collections.emptyMap() : groups;
    }

    public boolean isDoctor() {
        return ROLE_DOCTOR.equals(role);
    }

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
        this.role = ROLE_PATIENT;
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "Username: %s, Email: %s, Role %s", name, email, role);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.email);
        dest.writeString(this.phone);
        dest.writeString(this.role);
        dest.writeString(this.avatarUrl);
        if (notificationTokens!=null) {
            dest.writeInt(this.notificationTokens.size());
            for (Map.Entry<String, Boolean> entry : this.notificationTokens.entrySet()) {
                dest.writeString(entry.getKey());
                dest.writeValue(entry.getValue());
            }
        } else
            dest.writeInt(0);

        if (getGroups()!=null) {
            dest.writeInt(getGroups().size());
            for (Map.Entry<String, Boolean> entry : getGroups().entrySet()) {
                dest.writeString(entry.getKey());
                dest.writeValue(entry.getValue());
            }
        }
    }

    protected User(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.email = in.readString();
        this.phone = in.readString();
        this.role = in.readString();
        this.avatarUrl = in.readString();

        int notificationTokensSize = in.readInt();
        this.notificationTokens = new HashMap<>(notificationTokensSize);
        if (notificationTokensSize>0) {
            for (int i = 0; i < notificationTokensSize; i++) {
                String key = in.readString();
                Boolean value = (Boolean) in.readValue(Boolean.class.getClassLoader());
                this.notificationTokens.put(key, value);
            }
        }
            int groupsSize = in.readInt();
            this.groups = new HashMap<>(groupsSize);
            for (int i = 0; i < groupsSize; i++) {
                String key = in.readString();
                Boolean value = (Boolean) in.readValue(Boolean.class.getClassLoader());
                this.groups.put(key, value);
            }
//        leader = in.readByte() != 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Map<String, Boolean> getNotificationTokens() {
        return notificationTokens;
    }

    public void setNotificationTokens(Map<String, Boolean> notificationTokens) {
        this.notificationTokens = notificationTokens;
    }

    public void setTimestamp(long timestamp){ this.timestamp = timestamp; }

    public long getTimestamp(){ return this.timestamp; }
}