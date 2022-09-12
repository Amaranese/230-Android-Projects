package com.alejandro.maranes.timetablemanager.taskScheduler

import java.util.*

class Task(var name:String,var start: String, end:String, date:String)
{
    var taskName=name
    var startTime=start
    var endTime=end
    var dateToDo=date
    var startTimeInMinutes=timeInMinutes(startTime)
    var week=getDayOfWeek(startTime,dateToDo)
    private fun timeInMinutes(start:String):Int
    {
        val x=start.split(":")
        val min=(x[0].toInt()*60)+x[1].toInt()
        return min
    }
    private fun getDayOfWeek(start:String,date:String): String
    {
        val x=start.split(":")
        val minute =x[1].toInt()
        val hour = x[0].toInt()

        val y=date.split("/")
        val day = y[2].toInt()
        val month = y[1].toInt()
        val year = y[0].toInt()

        val calendar = Calendar.getInstance()
        calendar.set(year, month, day, hour, minute)
        val i= calendar.get(Calendar.DAY_OF_WEEK)
        if(i==1)return "Sunday"
        if(i==2)return "Monday"
        if(i==3)return "Tuesday"
        if(i==4)return "Wednesday"
        if(i==5)return "Thursday"
        if(i==6)return "Friday"
        if(i==7)return "Saturday"
        return "Invalid Day"

    }

}