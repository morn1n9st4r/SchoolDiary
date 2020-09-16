package com.example.schooldiary

import java.sql.Time

data class Lesson(
    //CREATE ENUMS
    val name: String,
    val auditoryNumber: Int,
    //MAKE AS TIME
    val time: String,
    val teacherName: String,
    val isActive: Boolean
)