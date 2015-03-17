package com.example.vbrekelov.calc;

import android.app.Application;
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.test.AndroidTestCase;
import android.test.ApplicationTestCase;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import dalvik.annotation.TestTargetClass;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class CalcTest extends ActivityInstrumentationTestCase2<MainActivity> {

    MainActivity activity;

    public CalcTest(Class activityClass) {
        super(activityClass);
    }

    @Override
    protected void setUp() throws Exception{
        super.setUp();
        setActivityInitialTouchMode(true);

        this.activity = (MainActivity) getActivity();
    }

    public void test(){
    }

}