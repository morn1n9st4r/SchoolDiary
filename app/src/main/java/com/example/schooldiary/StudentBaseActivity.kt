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
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_drawer_student_main.*
import kotlinx.android.synthetic.main.activity_drawer_teacher_main.*
import kotlinx.android.synthetic.main.activity_drawer_teacher_main.toolbar
import kotlinx.android.synthetic.main.activity_student_base.*

class StudentBaseActivity : AppCompatActivity(), View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore
    private lateinit var drawer: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawer_student_main)
        auth = Firebase.auth
        setSupportActionBar(toolbar)
        drawer = student_drawer_layout
        var navigationView: NavigationView = nav_student_view
        navigationView.setNavigationItemSelectedListener(this)
        var toggle = ActionBarDrawerToggle(this, drawer, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(
                R.id.fragment_student_container,
                TeacherCalendarFragment()
            ).commit()
            navigationView.setCheckedItem(R.id.nav_student_calendar)
        }
        //btnCalendar.setOnClickListener(this)
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
/*
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
            }*/
    }

    override fun onClick(p0: View?) {
        /*when(p0) {
            btnCalendar -> {
                val calendarIntent = Intent(this, Calendar::class.java)
                startActivityForResult(calendarIntent,0)
            }
        }*/
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.nav_student_calendar -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_student_container,
                    TeacherCalendarFragment()).commit()
            }
            R.id.nav_student_myclass -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_student_container,
                    TeacherStudentsFragment()).commit()
            }
            R.id.nav_student_settings -> {
                Toast.makeText(this, "Settings",Toast.LENGTH_LONG).show()
            }
            R.id.nav_student_exit -> {
                Toast.makeText(this, "Log Out",Toast.LENGTH_LONG).show()
            }
        }

        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}