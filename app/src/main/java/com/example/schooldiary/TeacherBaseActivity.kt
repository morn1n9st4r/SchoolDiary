package com.example.schooldiary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_drawer_teacher_main.*

class TeacherBaseActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener  {

    private lateinit var auth: FirebaseAuth
    private lateinit var drawer: DrawerLayout
    private val db = Firebase.firestore
    private var addedByEmail: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        setContentView(R.layout.activity_drawer_teacher_main)
        setSupportActionBar(toolbar)
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
        /*tvTeacher.text = "TeacherID = ${auth.currentUser!!.uid} " +
                "\n ${auth.currentUser!!.email}"*/
        addedByEmail = auth.currentUser!!.email as String
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
                auth.signOut()
                finish()
            }
        }

        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}