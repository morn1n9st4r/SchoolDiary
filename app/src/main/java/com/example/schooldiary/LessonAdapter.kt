package com.example.schooldiary

import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.sql.Time

class LessonAdapter(private val lessonDataSet: List<Lesson>, val callback: Callback): RecyclerView.Adapter<LessonAdapter.LessonHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonHolder
            = LessonHolder(LayoutInflater.from(parent.context).inflate(R.layout.lesson_item, parent, false))

    override fun onBindViewHolder(holder: LessonHolder, position: Int) {
        holder.bind(lessonDataSet[position])
    }

    override fun getItemCount(): Int {
        return lessonDataSet.size
    }

    inner class LessonHolder(lessonView: View): RecyclerView.ViewHolder(lessonView) {
        private val lessonName = lessonView.findViewById<TextView>(R.id.tvItemLessonName)
        private val teacherName = lessonView.findViewById<TextView>(R.id.tvItemTeacher)
        private val lessonTime = lessonView.findViewById<TextView>(R.id.tvItemTime)
        private val auditoryNumber = lessonView.findViewById<TextView>(R.id.tvItemAuditory)


        fun bind(item: Lesson) {
            lessonName.text = item.name
            teacherName.text = item.teacherName
            lessonTime.text = item.time
            auditoryNumber.text = item.auditoryNumber.toString()

            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) callback.onItemClicked(lessonDataSet[adapterPosition])
            }
        }
    }

    interface Callback {
        fun onItemClicked(item: Lesson)
    }

}