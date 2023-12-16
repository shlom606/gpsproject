package com.example.gpsproject;

import static android.provider.Settings.System.putLong;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    EditText tvfirstName, tvlastName, tvage, tvuserName;
    FirebaseDatabase db;
    DatabaseReference reference;
    String firstName, lastName, age, userName;
    Button register;
    CheckBox issith,isjedi;
    Boolean sith,jedi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvfirstName = findViewById(R.id.et_firstName);
        tvlastName = findViewById(R.id.et_lastName);
        tvage = findViewById(R.id.et_age);
        register=findViewById(R.id.btn_register);
        tvuserName = findViewById(R.id.et_userName);
        issith=findViewById(R.id.et_sith);
        isjedi=findViewById(R.id.et_jedi);
        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                firstName = tvfirstName.getText().toString();
                lastName = tvlastName.getText().toString();
                age = tvage.getText().toString();
                userName = tvuserName.getText().toString();
                sith=issith.isChecked();
                jedi=isjedi.isChecked();
                Toast.makeText(MainActivity.this, firstName, Toast.LENGTH_SHORT).show();

                if (!firstName.isEmpty() && !lastName.isEmpty() && !age.isEmpty() && !userName.isEmpty()&&(sith||jedi)) {
                    User users = new User(firstName, lastName, age, userName,sith,jedi);
                    db = FirebaseDatabase.getInstance();
                    reference = db.getReference("Users");
                    reference.child("username").setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            tvfirstName.setText("");
                            tvlastName.setText("");
                            tvage.setText("");
                            tvuserName.setText("");


                            Toast.makeText(MainActivity.this, "Successfuly Updated", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, DemoGame.class);
                            startActivity(intent);

                        }
                    });
                }
            }
        });


    }
}