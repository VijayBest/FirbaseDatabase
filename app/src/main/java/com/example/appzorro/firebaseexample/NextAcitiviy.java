package com.example.appzorro.firebaseexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

public class NextAcitiviy extends AppCompatActivity {

    ArrayList<User>list1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_acitiviy);

        for (int i=0; i<MainActivity.list.size();i++) {
            User user = MainActivity.list.get(i);
            Log.e("image", user.getImage());
        }



    }
}
