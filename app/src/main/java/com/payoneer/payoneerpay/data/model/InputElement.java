package com.payoneer.payoneerpay.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class InputElement implements Parcelable {

    private String name;
    private String type;

    protected InputElement(Parcel in) {
        name = in.readString();
        type = in.readString();
    }

    public static final Creator<InputElement> CREATOR = new Creator<InputElement>() {
        @Override
        public InputElement createFromParcel(Parcel in) {
            return new InputElement(in);
        }

        @Override
        public InputElement[] newArray(int size) {
            return new InputElement[size];
        }
    };

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        dest.writeString(name);
        dest.writeString(type);
    }
}
