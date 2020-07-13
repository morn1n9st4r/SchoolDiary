package com.example.schooldiary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*
import kotlin.random.Random

class User(val isTeacher: Boolean, val timestamp: Timestamp)

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = Firebase.auth
        btnLogin.setOnClickListener(this)
        btnSignup.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onClick(p0: View?) {
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()

        when(p0) {
            btnLogin -> {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) {task ->
                        if(task.isSuccessful) {
                            Log.d("LOGIN", "success")
                            val user = auth.currentUser
                            Toast.makeText(baseContext, user?.uid, Toast.LENGTH_SHORT).show()
                            updateUi(user!!)
                        }
                        else {
                            Log.e("LOGIN", "failed", task.exception)
                            Toast.makeText(baseContext, "Authentication failed", Toast.LENGTH_SHORT).show()
                            //updateUI
                        }
                    }
            }
            btnSignup -> {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) {task ->
                        if(task.isSuccessful) {
                            val form = etFormTeacher.text.toString().toLong()
                            Log.d("SIGNUP", "success")
                            val user = auth.currentUser
                            val userData = hashMapOf(
                                "Teacher" to true,
                                "Date" to Timestamp(Date()),
                                "Form" to form,
                                "Added by" to "System"
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

    private fun updateUi(user: FirebaseUser) {
        val docRef = db.collection("users").document(user.uid)
        docRef.get()
            .addOnSuccessListener { document ->
                if(document != null) {
                    Log.d("DOCUMENT REFERENCE", "DocumentSnapshot data: ${document.data}")
                    val isTeacher = document.data!!["Teacher"] as Boolean
                    if(isTeacher) {
                        val teacherIntent = Intent(this, TeacherBaseActivity::class.java)
                        startActivity(teacherIntent)
                    } else {
                        val studentIntent = Intent(this, StudentBaseActivity::class.java)
                        startActivity(studentIntent)
                    }
                }
                else {
                    Log.d("DOCUMENT REFERENCE", "No such document")
                }
            }
            .addOnFailureListener { e ->
                Log.d("DOCUMENT REFERENCE", "get failed with ", e)
            }
    }

}