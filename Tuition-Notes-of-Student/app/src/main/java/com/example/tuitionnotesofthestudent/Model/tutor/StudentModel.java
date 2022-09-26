package com.example.tuitionnotesofthestudent.Model.tutor;

public class StudentModel {
    private String studentName;
    private String studentSubject;
    private String studentEmail;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentSubject() {
        return studentSubject;
    }

    public void setStudentSubject(String studentSubject) {
        this.studentSubject = studentSubject;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public StudentModel() {
    }

    public StudentModel(String studentName, String studentSubject,String studentEmail) {
        this.studentName = studentName;
        this.studentSubject = studentSubject;
        this.studentEmail = studentEmail;
    }
}
