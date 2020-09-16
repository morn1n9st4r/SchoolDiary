package com.example.schooldiary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.sql.Time

class TeacherCalendarFragment: Fragment() {

    private lateinit var rvTeacherLessons: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_teacher_calendar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val lessonData = listOf(
            Lesson("Biology", 3, "11:15:02", "Ivanova", true),
            Lesson("Chemistry", 3, "12:17:52", "Petrova", true),
            Lesson("Physics", 3, "13:19:12", "Sidorova", false),
            Lesson("Math", 3, "14:22:42", "Artemova", true),
            )
       /* val rvLessonAdapter = LessonAdapter(lessonData, object: LessonAdapter.Callback {
            override fun onItemClicked(item: Lesson) {
                Toast.makeText(context, "Clicked", Toast.LENGTH_LONG).show();
            }
        })
        rvTeacherLessons.adapter = rvLessonAdapter*/
        viewManager = LinearLayoutManager(context)
        viewAdapter = LessonAdapter(lessonData, object: LessonAdapter.Callback {
            override fun onItemClicked(item: Lesson) {
                Toast.makeText(context, "Clicked", Toast.LENGTH_LONG).show();
            }
        })

        rvTeacherLessons = getView()!!.findViewById<RecyclerView>(R.id.rvTeacherDailyLessons).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

    }
}