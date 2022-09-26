package com.example.tuitionnotesofthestudent.tutor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tuitionnotesofthestudent.Model.tutor.StudentModel;
import com.example.tuitionnotesofthestudent.R;
import com.example.tuitionnotesofthestudent.adapter.tutor.StudentAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TutorDashboard extends AppCompatActivity {

    ImageView iv_addUser;
    FirebaseUser firebaseUser;

    Intent intent;

    String email = "";
    String password = "";

    List<StudentModel> mList = new ArrayList<>();
    RecyclerView recyclerView;
    StudentAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_dashboard);

        iv_addUser = findViewById(R.id.iv_addUser);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = firebaseUser.getUid();

        recyclerView = findViewById(R.id.rv_showAllSubject);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(TutorDashboard.this));

        intent = getIntent();
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");

        getAllStudent();

        iv_addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TutorDashboard.this, AddUser.class);
                intent.putExtra("teacherId", userId);
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                startActivity(intent);
            }
        });
    }

    private void getAllStudent() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser.getUid() != null) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(TutorSignUp.STUDENTS_USER).child(firebaseUser.getUid());
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    mList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        StudentModel model = dataSnapshot.getValue(StudentModel.class);
                        Log.d("TAG", "onDataChange: " + model.getStudentName());
                        mList.add(model);
                    }
                    mAdapter = new StudentAdapter(TutorDashboard.this, mList, firebaseUser.getUid());
                    recyclerView.setAdapter(mAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}