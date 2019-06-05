package com.javeriana.ruben4181.javeriana.models

data class Subject (var nombre : String, var periodo : String, var codigo : String, var strm : String,
            var notp : String, var inasistencia : String, var grupo : String, var crseId : String,
            var horario : List<Schedule>, var qualifications : List<Qualification>, var exams : List<String>)