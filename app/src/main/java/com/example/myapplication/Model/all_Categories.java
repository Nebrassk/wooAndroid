package com.example.myapplication.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList ;

public class all_Categories implements Parcelable {
ArrayList<categories> arrayList;


    public all_Categories(ArrayList<categories> arrayList) {
        this.arrayList = arrayList;
    }

    protected all_Categories(Parcel in) {
    }

    public static final Creator<all_Categories> CREATOR = new Creator<all_Categories>() {
        @Override
        public all_Categories createFromParcel(Parcel in) {
            return new all_Categories(in);
        }

        @Override
        public all_Categories[] newArray(int size) {
            return new all_Categories[size];
        }
    };

    public ArrayList<categories> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<categories> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
