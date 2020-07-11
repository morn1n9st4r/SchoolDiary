package com.example.schooldiary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_teacher_base.*
import java.util.*
import kotlin.random.Random

class TeacherBaseActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore
    private var addedByEmail: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_base)
        auth = Firebase.auth
        btnAddStudent.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        tvTeacher.text = "TeacherID = ${auth.currentUser!!.uid} " +
                "\n ${auth.currentUser!!.email}"
        addedByEmail = auth.currentUser!!.email as String
    }


    override fun onClick(p0: View?) {
        when(p0) {
            btnAddStudent -> {
                val studentEmail = etAddStudentEmail.text.toString()
                val studentPassword = Random.nextInt(1000000).toString()
                tvAddStudentPassword.text = studentPassword.toString()
                val docRef = db.collection("users").document(auth.currentUser!!.uid)
                docRef.get()
                    .addOnSuccessListener { document ->
                        if (document != null) {
                            Log.d("DOCUMENT REFERENCE", "DocumentSnapshot data: ${document.data}")
                            var form = document.data!!["Form"] as Long
                            auth.createUserWithEmailAndPassword(studentEmail, studentPassword)
                                .addOnCompleteListener(this) {task ->
                                    if(task.isSuccessful) {
                                        Log.d("SIGNUP", "success")
                                        val user = auth.currentUser
                                        val userData = hashMapOf(
                                            "Teacher" to false,
                                            "Date" to Timestamp(Date()),
                                            "Form" to form,
                                            "Added by" to addedByEmail
                                        )
                                        db.collection("users").document(user!!.uid).set(userData)
                                            .addOnSuccessListener {
                                                Log.d("DATA WRITING", "DocumentSnapshot successfully written!")
                                            }
                                            .addOnFailureListener { e ->
                                                Log.e("DATA WRITING", "Error writing document", e)
                                            }
                                    }
                                    else {
                                        Log.e("SIGNUP", "failed", task.exception)
                                        Toast.makeText(baseContext, "Authentication failed", Toast.LENGTH_SHORT).show()
                                        //updateUI
                                    }
                                }
                        }
                    }

            }
        }
    }
}