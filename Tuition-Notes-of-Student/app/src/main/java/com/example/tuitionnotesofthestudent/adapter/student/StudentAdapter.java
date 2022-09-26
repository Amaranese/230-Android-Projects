package com.example.tuitionnotesofthestudent.adapter.student;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tuitionnotesofthestudent.Model.parent.StudentModel;
import com.example.tuitionnotesofthestudent.R;

import java.util.ArrayList;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.viewHolder>{

    private Context mContext;
    private List<StudentModel> mList = new ArrayList<>();
    private ProgressDialog progressDialog;

    public StudentAdapter(Context mContext, List<StudentModel> mList) {
        this.mContext = mContext;
        this.mList = mList;
        progressDialog = new ProgressDialog(mContext);
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.student_layout_for_parent, parent, false);
        return new viewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        StudentModel model = mList.get(position);
        holder.tv_studentName.setText(model.getStudentName());
        holder.tv_studentSubject.setText(model.getStudentSubject());
        holder.tv_todayDate.setText(model.getDate());
        holder.tv_present.setText(model.getPresent());
        holder.tv_remarks.setText(model.getRemarks());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class viewHolder extends RecyclerView.ViewHolder {

        private TextView tv_studentName;
        private TextView tv_studentSubject;
        private TextView tv_todayDate;
        private TextView tv_present;
        private TextView tv_remarks;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            tv_studentName = itemView.findViewById(R.id.tv_studentName);
            tv_studentSubject = itemView.findViewById(R.id.tv_studentSubject);
            tv_todayDate = itemView.findViewById(R.id.tv_todayDate);
            tv_present = itemView.findViewById(R.id.tv_present);
            tv_remarks = itemView.findViewById(R.id.tv_remarks);
        }
    }

}
