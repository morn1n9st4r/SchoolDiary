package com.example.schooldiary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_student_base.*

class StudentBaseActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_base)
        auth = Firebase.auth
    }

    override fun onStart() {
        super.onStart()
        tvStudent.text = "StudentID = ${auth.currentUser!!.uid}"
    }
}