package com.example.schooldiary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_student_base.*

class StudentBaseActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_base)
        auth = Firebase.auth
    }

    override fun onStart() {
        super.onStart()

        val docRef = db.collection("users").document(auth.currentUser!!.uid)
        docRef.get()
            .addOnSuccessListener { document ->
                if(document != null) {
                    Log.d("DOCREF STUDENT MEN", "DocumentSnapshot data: ${document.data}")
                    tvStudent.text = "StudentID = ${auth.currentUser!!.uid}\n" +
                            "\t${auth.currentUser!!.email}\n" +
                            "\tForm ${document.data!!["Form"] as Long}\n" +
                            "\tClass Teacher ${document.data!!["Added by"] as String}"
                }
                else {
                    Log.d("DOCREF STUDENT MENU", "No such document")
                    tvStudent.text = "Failed to load data"
                }
            }
            .addOnFailureListener { e ->
                Log.d("DOCUMENT REFERENCE", "get failed with ", e)
                tvStudent.text = "Failed to load data$e"
            }
    }
}