package com.example.schooldiary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.material.navigation.NavigationView
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_drawer_teacher_main.*
import kotlinx.android.synthetic.main.activity_teacher_base.*
import java.util.*
import kotlin.random.Random

class TeacherBaseActivity : AppCompatActivity(), View.OnClickListener, NavigationView.OnNavigationItemSelectedListener  {

    private lateinit var auth: FirebaseAuth
    private lateinit var drawer: DrawerLayout
    private val db = Firebase.firestore
    private var addedByEmail: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawer_teacher_main)
        setSupportActionBar(toolbar)
        auth = Firebase.auth
        drawer = teacher_drawer_layout
        var navigationView: NavigationView = nav_teacher_view
        navigationView.setNavigationItemSelectedListener(this)
        var toggle = ActionBarDrawerToggle(this, drawer, toolbar,
                                R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(
                R.id.fragment_teacher_container,
                TeacherCalendarFragment()
            ).commit()
            navigationView.setCheckedItem(R.id.nav_teacher_calendar)
        }
    //    btnAddStudent.setOnClickListener(this)
    //    btnCalendarTeacher.setOnClickListener(this)
    }

    override fun onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onStart() {
        super.onStart()
/*        tvTeacher.text = "TeacherID = ${auth.currentUser!!.uid} " +
                "\n ${auth.currentUser!!.email}"*/
        addedByEmail = auth.currentUser!!.email as String
    }


    override fun onClick(p0: View?) {/*
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
            btnCalendarTeacher -> {
                val calendarIntent = Intent(this, Calendar::class.java)
                startActivityForResult(calendarIntent,0)
            }
        }*/
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.nav_teacher_calendar -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_teacher_container,
                                                                    TeacherCalendarFragment()).commit()
            }
            R.id.nav_teacher_students -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_teacher_container,
                    TeacherStudentsFragment()).commit()
            }
            R.id.nav_teacher_settings -> {
                Toast.makeText(this, "Settings",Toast.LENGTH_LONG).show()
            }
            R.id.nav_teacher_exit -> {
                Toast.makeText(this, "Log Out",Toast.LENGTH_LONG).show()
            }
        }

        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}