package com.example.schooldiary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_teacher_base.*

class TeacherBaseActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_base)
        auth = Firebase.auth
    }

    override fun onStart() {
        super.onStart()
        tvTeacher.text = "TeacherID = ${auth.currentUser!!.uid} " +
                "\n ${auth.currentUser!!.email}"
    }
}