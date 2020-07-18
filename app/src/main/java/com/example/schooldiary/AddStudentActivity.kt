package com.example.schooldiary

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
import kotlinx.android.synthetic.main.activity_add_student.*
import java.util.*

class AddStudentActivity : AppCompatActivity(), View.OnClickListener {

    val ACTIVITY_RESULT_SUCCESS = 11
    val ACTIVITY_RESULT_FAILURE_ON_AUTHENTICATION = 12
    val ACTIVITY_RESULT_FAILURE_ON_WRITE_DOCUMENT = 13


    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore

    //private var addedByEmail: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)
        auth = Firebase.auth
        btnTeacherGenerateStudentPassword.setOnClickListener(this)
        btnTeacherAddStudent.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        //addedByEmail = auth.currentUser!!.email as String
    }


    override fun onClick(p0: View?) {
        when (p0) {
            btnTeacherGenerateStudentPassword -> {
                val generatedStudentPassword = (100000..999999).random()
                tvStudentGeneratedPassword.text = generatedStudentPassword.toString()
            }
            btnTeacherAddStudent -> {
                val studentEmail = etTeacherAddStudentEmail.text.toString()
                val studentPassword = tvStudentGeneratedPassword.text.toString()
                val docRef = db.collection("users").document(auth.currentUser!!.uid)
                docRef.get()
                    .addOnSuccessListener { document ->
                        if (document != null) {
                            Log.d("DOCUMENT REFERENCE", "DocumentSnapshot data: ${document.data}")
                            val form = document.data!!["Form"] as Long
                            val addedByEmail = auth.currentUser!!.email as String
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
                                                setResult(ACTIVITY_RESULT_SUCCESS)
                                                finish()
                                            }
                                            .addOnFailureListener { e ->
                                                Log.e("DATA WRITING", "Error writing document", e)
                                                setResult(ACTIVITY_RESULT_FAILURE_ON_WRITE_DOCUMENT)
                                                finish()
                                            }
                                    }
                                    else {
                                        Log.e("SIGNUP", "failed", task.exception)
                                        Toast.makeText(baseContext, "Authentication failed", Toast.LENGTH_SHORT).show()
                                        setResult(ACTIVITY_RESULT_FAILURE_ON_AUTHENTICATION)
                                        finish()
                                    }
                                }
                        }
                    }
            }
        }
    }


}