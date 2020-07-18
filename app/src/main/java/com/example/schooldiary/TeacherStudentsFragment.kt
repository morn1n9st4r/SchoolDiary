package com.example.schooldiary

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_teacher_students.*

class TeacherStudentsFragment: Fragment(), View.OnClickListener {

    val INTENT_REQUEST_ADD_STUDENTS = 10
    val INTENT_RESULT_ADD_STUDENT_SUCCESS = 11
    val INTENT_RESULT_ADD_STUDENT_FAILURE_ON_WRITE_DOCUMENT = 12
    val INTENT_RESULT_ADD_STUDENT_FAILURE_ON_AUTHENTICATION = 13


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_teacher_students, container, false)
    }

    override fun onStart() {
        super.onStart()
        fabTeacherAddStudentsStartActivity.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0) {
            fabTeacherAddStudentsStartActivity ->{
                var addStudentIntent = Intent(context, AddStudentActivity::class.java)
                startActivityForResult(addStudentIntent, INTENT_REQUEST_ADD_STUDENTS)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            INTENT_REQUEST_ADD_STUDENTS -> {

            }
        }

        when (resultCode) {
            INTENT_RESULT_ADD_STUDENT_SUCCESS -> {
                Snackbar.make(view!!, "Success!", Snackbar.LENGTH_LONG).show()
            }
            INTENT_RESULT_ADD_STUDENT_FAILURE_ON_WRITE_DOCUMENT -> {
                Snackbar.make(view!!, "Failed to write document to database!", Snackbar.LENGTH_LONG).show()
            }
            INTENT_RESULT_ADD_STUDENT_FAILURE_ON_AUTHENTICATION -> {
                Snackbar.make(view!!, "Failed to authenticate new user!", Snackbar.LENGTH_LONG).show()
            }
        }
    }
}