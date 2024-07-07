package com.vst.myapplication.Utils;

import androidx.fragment.app.Fragment;

import java.io.Serializable;

public class DataModel implements Serializable {
    private Fragment fragment;

    public DataModel(Fragment fragment) {
        this.fragment = fragment;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
