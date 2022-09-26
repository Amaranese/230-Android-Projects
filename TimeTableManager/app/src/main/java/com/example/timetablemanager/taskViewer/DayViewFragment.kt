package com.example.timetablemanager.taskViewer

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.timetablemanager.R
import com.example.timetablemanager.databaseHandler.DatabaseInfo
import com.example.timetablemanager.databaseHandler.DatabaseOperations
import com.example.timetablemanager.taskScheduler.Task
import com.example.timetablemanager.taskViewer.adapters.TaskAdapter
import java.util.*


class DayViewFragment : Fragment() {

    var todoItemsList = ArrayList<Task>()


    private lateinit var taskRecyclerView: RecyclerView
    private lateinit var recyclerAdapter: TaskAdapter
    private lateinit var recyclerLayoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_day_view, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dbo = DatabaseOperations(requireActivity())
        val cursor = dbo.getItems(dbo,getDateAsString())
        with(cursor) {
            while(moveToNext()) {
                val itemName = getString(getColumnIndex(DatabaseInfo.TableInfo.COLUMN_ITEM_NAME))
                val itemStart = getString(getColumnIndex(DatabaseInfo.TableInfo.COLUMN_ITEM_START))
                val itemEnd = getString(getColumnIndex(DatabaseInfo.TableInfo.COLUMN_ITEM_END))
                val itemDate = getString(getColumnIndex(DatabaseInfo.TableInfo.COLUMN_DATE))

                val task = Task(itemName, itemStart,itemEnd,itemDate)
                todoItemsList.add(task)
                Log.d("task name","${task.name} ${task.dateToDo}")
            }
        }
        todoItemsList.sortBy { it.startTimeInMinutes }


        taskRecyclerView=view.findViewById(R.id.task_recycler_view)
        val activity= activity
        recyclerLayoutManager = LinearLayoutManager(activity)
        recyclerAdapter = TaskAdapter(todoItemsList,activity)
        taskRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = recyclerLayoutManager
            adapter = recyclerAdapter
        }

        for(item in todoItemsList) {
            Log.d("iteminfo name", item.name)
            Log.d("iteminfo starttime", item.startTime)
            Log.d("iteminfoendtime", item.endTime)
            Log.d("iteminfodate",item.dateToDo)

        }
    }
    private fun getDateAsString(): String {
        val date = Calendar.getInstance()
        val year = date.get(Calendar.YEAR).toString()
        val month = (date.get(Calendar.MONTH)).toString()
        val day = date.get(Calendar.DAY_OF_MONTH).toString()
        Log.d("today's date","$year/$month/$day")
        return "$year/$month/$day"
    }


}