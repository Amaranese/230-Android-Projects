package com.example.tuitionnotesofthestudent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tuitionnotesofthestudent.parent.ParentSignin;
import com.example.tuitionnotesofthestudent.student.StudentSignin;
import com.example.tuitionnotesofthestudent.tutor.TutorSignUp;

public class MainActivity extends AppCompatActivity {

    Button btn_tutor;
    Button btn_student;
    Button btn_parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_tutor = findViewById(R.id.btn_tutor);
        btn_student = findViewById(R.id.btn_student);
        btn_parent = findViewById(R.id.btn_parent);

        btn_tutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TutorSignUp.class));
            }
        });

        btn_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ParentSignin.class));
            }
        });

        btn_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, StudentSignin.class));
            }
        });

    }
}