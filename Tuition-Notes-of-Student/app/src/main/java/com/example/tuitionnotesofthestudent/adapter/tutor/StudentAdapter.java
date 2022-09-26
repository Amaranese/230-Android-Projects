package com.example.tuitionnotesofthestudent.adapter.tutor;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tuitionnotesofthestudent.Model.tutor.StudentModel;
import com.example.tuitionnotesofthestudent.R;
import com.example.tuitionnotesofthestudent.tutor.TutorSignUp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.viewHolder> {

    private Context mContext;
    private List<StudentModel> mList = new ArrayList<>();
    private String teacherId = "";
    private String studentRemarks="0/10";
    private String studentAttendance="absent";

    private ProgressDialog progressDialog;

    public StudentAdapter(Context mContext, List<StudentModel> mList, String teacherId) {
        this.mContext = mContext;
        this.mList = mList;
        this.teacherId = teacherId;
        progressDialog = new ProgressDialog(mContext);
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_student, parent, false);
        return new viewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        StudentModel model = mList.get(position);
        holder.tv_studentName.setText(model.getStudentName());
        holder.tv_studentSubject.setText(model.getStudentSubject());
        LocalDate currentDate = LocalDate.now();
        String date = currentDate.toString();
        holder.tv_todayDate.setText(date);

        holder.btn_present.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Marking Attendance");
                progressDialog.setTitle("Attendance....");
                progressDialog.show();
                studentAttendance = "Present";
                markAsPresent(model, date);
            }
        });
        holder.btn_absent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Marking Attendance");
                progressDialog.setTitle("Attendance....");
                progressDialog.show();
                studentAttendance = "Absent";
                markAsPresent(model, date);
            }
        });
        holder.btn_remarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Marking Attendance");
                progressDialog.setTitle("Attendance....");
                progressDialog.show();
                createDialog(model, date);

            }
        });
    }

    private void createDialog(StudentModel model, String date) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.custom_remarks, null, false);
        builder.setView(view);
        EditText et_remarks = view.findViewById(R.id.et_studentRemarks);
        Button btn_giveRemarks = view.findViewById(R.id.btn_giveRemarks);
        AlertDialog alertDialog = builder.create();

        alertDialog.show();
        btn_giveRemarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String remarks = et_remarks.getText().toString();
                if (remarks.isEmpty()) {
                    et_remarks.setError("Empty Remarks");
                } else {
                    progressDialog.setMessage("Adding Your Remarks");
                    progressDialog.setTitle("Adding...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    studentRemarks = remarks+"/10";
                    markAsPresent(model, date);
                    alertDialog.dismiss();
                }
            }
        });
    }

//    private void giveRemarks(StudentModel model, String date) {
//        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        String userId = firebaseUser.getUid();
//
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(TutorSignUp.ATTENDANCE).child(model.getStudentName()).child(date);
//        HashMap<String, String> hashMap = new HashMap<>();
//        hashMap.put("id", userId);
//        hashMap.put("studentName", model.getStudentName());
//        hashMap.put("email", model.getStudentEmail());
//        hashMap.put("studentSubject", model.getStudentSubject());
//        hashMap.put("remarks", remarks+"/10");
//        hashMap.put("date", date);
//        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                progressDialog.dismiss();
//                if (task.isSuccessful()) {
//                    Toast.makeText(mContext, "Remarks Added Successfully", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(mContext, "Remarks Added Failed", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }

    private void markAsPresent(StudentModel model, String date) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = firebaseUser.getUid();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(TutorSignUp.ATTENDANCE).child(model.getStudentName()).child(date);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", userId);
        hashMap.put("studentName", model.getStudentName());
        hashMap.put("email", model.getStudentEmail());
        hashMap.put("studentSubject", model.getStudentSubject());
        hashMap.put("present", studentAttendance);
        hashMap.put("remarks", studentRemarks);
        hashMap.put("date", date);
        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    Toast.makeText(mContext, "Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class viewHolder extends RecyclerView.ViewHolder {

        private TextView tv_studentName;
        private TextView tv_studentSubject;
        private TextView tv_todayDate;
        private Button btn_present;
        private Button btn_absent;
        private Button btn_remarks;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            tv_studentName = itemView.findViewById(R.id.tv_studentName);
            tv_studentSubject = itemView.findViewById(R.id.tv_studentSubject);
            tv_todayDate = itemView.findViewById(R.id.tv_todayDate);
            btn_present = itemView.findViewById(R.id.btn_present);
            btn_absent = itemView.findViewById(R.id.btn_absent);
            btn_remarks = itemView.findViewById(R.id.btn_remarks);
        }
    }

}
