package com.example.appzorro.firebaseexample;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;

public class MainActivity extends AppCompatActivity {
    EditText name, email;
    private DatabaseReference mDatabase;
    ImageView imageView;
    static ArrayList<User>list;
    ArrayList<String>Phonenumber;
    ListView listView;
    public  static final int RequestPermissionCode  = 1 ;
    Uri filepath;
 android.app.AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        imageView = (ImageView) findViewById(R.id.image);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        listView =(ListView)findViewById(R.id.listvew);


        EnableRuntimePermission();



    }



    public void savedata(View V){

       /* User user = new User();
        user.setEmail(email.getText().toString());
        user.setName(name.getText().toString());
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        String userId = mDatabase.push().getKey();
        mDatabase.child(userId).setValue(user);
        Log.e("working fine","hheherkersdf");*/
        GetContactsIntoArrayList();


    }


 //this fuction getting all data from the firbase
    public void showdata(View V) {

        dialog = new SpotsDialog(MainActivity.this);
        dialog.show();

       list= new ArrayList<>();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    User product = postSnapshot.getValue(User.class);
                    Log.e("image url",product.getImage());
                    Log.e("product",product.getEmail().toString());
                    list.add(product);
                }

                Intent intent = new Intent(MainActivity.this,NextAcitiviy.class);
                startActivity(intent);
                dialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //  this code for retrive all conatcts from your device and show on Listview


    public void EnableRuntimePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(
                MainActivity.this,
                android.Manifest.permission.READ_CONTACTS))

        {

            Toast.makeText(MainActivity.this,"CONTACTS permission allows us to Access CONTACTS app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                    android.Manifest.permission.READ_CONTACTS}, RequestPermissionCode);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        switch (requestCode){


            case RequestPermissionCode:
                if (grantResults.length>0&& grantResults[0]== PackageManager.PERMISSION_GRANTED){

                    Toast.makeText(MainActivity.this,"permisson granted",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this,"permisson  not granted",Toast.LENGTH_SHORT).show();

                }

        }
    }



    // this fuction getting all the contacts


    public void GetContactsIntoArrayList(){

        Phonenumber = new ArrayList<>();

      Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null, null, null);

        while (cursor.moveToNext()) {

           String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

          String  phonenumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

           Log.e("phone number",phonenumber);
            Phonenumber.add(phonenumber+"    "+name);
            Log.e("contact name",name);
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,Phonenumber);

        listView.setAdapter(arrayAdapter);


        cursor.close();

    }
}