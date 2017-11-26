package com.junction.healthtech.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class ParentItem implements Parcelable {
    public final static String ID = "id";
    public final static String NAME = "name";
    @Exclude
    protected String id;
    protected String name;

    public ParentItem() {
    }

    public ParentItem(String name) {
        this.name = name;
    }

    @Exclude
    public String getId() {
        return id;
    }

    @Exclude
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
    }

    protected ParentItem(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
    }

}
