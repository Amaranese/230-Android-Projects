package com.example.timetablemanager.taskViewer.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.timetablemanager.R
import com.example.timetablemanager.databaseHandler.DatabaseOperations
import com.example.timetablemanager.taskScheduler.Task
import com.example.timetablemanager.taskScheduler.TaskEditorActivity

class TaskAdapter(private val taskList: ArrayList<Task>, val activity: FragmentActivity?):
    RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    class ViewHolder(val constraintLayout: ConstraintLayout): RecyclerView.ViewHolder(constraintLayout)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val constraintLayout = LayoutInflater.from(parent.context).inflate(
                R.layout.task_layout, parent, false) as ConstraintLayout

        constraintLayout.setOnClickListener(View.OnClickListener {
            val nameTextView = constraintLayout.getChildAt(0) as TextView
            val dateTextView=constraintLayout.getChildAt(1) as TextView
            val startTimeView = constraintLayout.getChildAt(2) as TextView
            val weekTextView = constraintLayout.getChildAt(3) as TextView
            val nameText = nameTextView.text
            val date=dateTextView.text
            val runningTime=startTimeView.text
            val start=runningTime.split("-")[0]
            val end=runningTime.split("-")[1]
            val week=weekTextView.text

            val intent: Intent = Intent(activity, TaskEditorActivity::class.java)
            intent.putExtra("ITEM_NAME", nameText)
            intent.putExtra("ITEM_START", start)
            intent.putExtra("ITEM_END", end)
            intent.putExtra("ITEM_DATE", taskList[0].dateToDo)
            activity?.startActivity(intent)
            activity?.finish()
        })

        constraintLayout.setOnLongClickListener(View.OnLongClickListener {
            val position: Int = parent.indexOfChild(it)

            val todoItemToRemove = taskList[position]
            val dbo = DatabaseOperations(parent.context)
            dbo.deleteItem(dbo, todoItemToRemove)

            taskList.removeAt(position)
            notifyItemRemoved(position)
            true
        })

        return ViewHolder(constraintLayout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val constraintLayout = holder.constraintLayout
        val nameTextView = constraintLayout.getChildAt(0) as TextView
        val date=constraintLayout.getChildAt(1) as TextView
        val startTimeView = constraintLayout.getChildAt(2) as TextView
        val weekTextView = constraintLayout.getChildAt(3) as TextView
        nameTextView.text = taskList[position].name
        val dateToDo=addMonthOne(taskList[position].dateToDo)
        date.text=dateToDo
        startTimeView.text="${taskList[position].startTime}-${taskList[position].endTime}"
        weekTextView.text=taskList[position].week

    }

    private fun addMonthOne(dateToDo: String):String {
        val list=dateToDo.split("/")
        val year=list[0]
        val month=(list[1].toInt()+1).toString()
        val day=list[2]
        return "${day}/${month}/${year}"
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

}