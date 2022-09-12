package com.alejandro.maranes.timetablemanager.taskViewer

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alejandro.maranes.timetablemanager.R
import com.alejandro.maranes.timetablemanager.databaseHandler.DatabaseInfo
import com.alejandro.maranes.timetablemanager.databaseHandler.DatabaseOperations
import com.alejandro.maranes.timetablemanager.taskScheduler.Task
import com.alejandro.maranes.timetablemanager.taskViewer.adapters.TaskAdapter
import java.util.*


class WeekViewFragment : Fragment() {

    var todoItemsListSun = ArrayList<Task>()
    var todoItemsListMon= ArrayList<Task>()
    var todoItemsListTues= ArrayList<Task>()
    var todoItemsListWeds= ArrayList<Task>()
    var todoItemsListThurs= ArrayList<Task>()
    var todoItemsListFri= ArrayList<Task>()
    var todoItemsListSat= ArrayList<Task>()

    private lateinit var taskRecyclerView: RecyclerView
    private lateinit var recyclerAdapter: TaskAdapter
    private lateinit var recyclerLayoutManager: RecyclerView.LayoutManager

    private lateinit var sundayButton:Button
    private lateinit var mondayButton: Button
    private lateinit var tuesdayButton:Button
    private lateinit var wednesdayButton: Button
    private lateinit var thursdayButton:Button
    private lateinit var fridayButton:Button
    private lateinit var saturdayButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_week_view, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sundayButton=view.findViewById(R.id.sun_button)
        mondayButton=view.findViewById(R.id.mon_button)
        tuesdayButton=view.findViewById(R.id.tues_button)
        wednesdayButton=view.findViewById(R.id.wed_button)
        thursdayButton=view.findViewById(R.id.thurs_button)
        fridayButton=view.findViewById(R.id.frid_button)
        saturdayButton=view.findViewById(R.id.sat_button)


        val date=Calendar.getInstance()
        while(date.get(Calendar.DAY_OF_WEEK)!=1)
        {
            date.add(Calendar.DATE,-1)
        }

        for(i in 0..6)
        {
            val stringDate=getDateAsString(date)


            val dbo = DatabaseOperations(requireActivity())
            val cursor = dbo.getItems(dbo,stringDate)
            with(cursor) {
                while(moveToNext()) {
                    val itemName = getString(getColumnIndex(DatabaseInfo.TableInfo.COLUMN_ITEM_NAME))
                    val itemStart = getString(getColumnIndex(DatabaseInfo.TableInfo.COLUMN_ITEM_START))
                    val itemEnd = getString(getColumnIndex(DatabaseInfo.TableInfo.COLUMN_ITEM_END))
                    val itemDate = getString(getColumnIndex(DatabaseInfo.TableInfo.COLUMN_DATE))
                    val task = Task(itemName, itemStart,itemEnd,itemDate)

                    if(i==0)todoItemsListSun.add(task)
                    if(i==1)todoItemsListMon.add(task)
                    if(i==2)todoItemsListTues.add(task)
                    if(i==3)todoItemsListWeds.add(task)
                    if(i==4)todoItemsListThurs.add(task)
                    if(i==5)todoItemsListFri.add(task)
                    if(i==6)todoItemsListSat.add(task)

                    Log.d("testing","$i $itemName $itemStart $itemEnd $itemDate")
                }
            }
            date.add(Calendar.DATE,1)
        }

        todoItemsListSat.sortBy { it.startTimeInMinutes }
        todoItemsListSun.sortBy { it.startTimeInMinutes }
        todoItemsListMon.sortBy { it.startTimeInMinutes }
        todoItemsListTues.sortBy { it.startTimeInMinutes }
        todoItemsListWeds.sortBy { it.startTimeInMinutes }
        todoItemsListThurs.sortBy { it.startTimeInMinutes }
        todoItemsListFri.sortBy { it.startTimeInMinutes }


        taskRecyclerView=view.findViewById(R.id.task_recycler_view_week)
        val activity= activity
        recyclerLayoutManager = LinearLayoutManager(activity)


        resetButtonColors()
        sundayButton.setOnClickListener{
            resetButtonColors()
            sundayButton.setBackgroundColor(resources.getColor(R.color.purple_700))
            recyclerAdapter = TaskAdapter(todoItemsListSun,activity)
            taskRecyclerView.apply {
                setHasFixedSize(true)
                layoutManager = recyclerLayoutManager
                adapter = recyclerAdapter
            }
        }

        mondayButton.setOnClickListener {
            resetButtonColors()
            mondayButton.setBackgroundColor(resources.getColor(R.color.purple_700))
            recyclerAdapter = TaskAdapter(todoItemsListMon,activity)
            taskRecyclerView.apply {
                setHasFixedSize(true)
                layoutManager = recyclerLayoutManager
                adapter = recyclerAdapter
            }
        }
        tuesdayButton.setOnClickListener {
            resetButtonColors()
            tuesdayButton.setBackgroundColor(resources.getColor(R.color.purple_700))
            recyclerAdapter = TaskAdapter(todoItemsListTues,activity)
            taskRecyclerView.apply {
                setHasFixedSize(true)
                layoutManager = recyclerLayoutManager
                adapter = recyclerAdapter
            }
        }
        wednesdayButton.setOnClickListener {
            resetButtonColors()
            wednesdayButton.setBackgroundColor(resources.getColor(R.color.purple_700))
            recyclerAdapter = TaskAdapter(todoItemsListWeds,activity)
            taskRecyclerView.apply {
                setHasFixedSize(true)
                layoutManager = recyclerLayoutManager
                adapter = recyclerAdapter
            }
        }
        thursdayButton.setOnClickListener {
            resetButtonColors()
            thursdayButton.setBackgroundColor(resources.getColor(R.color.purple_700))
            recyclerAdapter = TaskAdapter(todoItemsListThurs,activity)
            taskRecyclerView.apply {
                setHasFixedSize(true)
                layoutManager = recyclerLayoutManager
                adapter = recyclerAdapter
            }
        }
        fridayButton.setOnClickListener {
            resetButtonColors()
            fridayButton.setBackgroundColor(resources.getColor(R.color.purple_700))
            recyclerAdapter = TaskAdapter(todoItemsListFri,activity)
            taskRecyclerView.apply {
                setHasFixedSize(true)
                layoutManager = recyclerLayoutManager
                adapter = recyclerAdapter
            }
        }
        saturdayButton.setOnClickListener {
            resetButtonColors()
            saturdayButton.setBackgroundColor(resources.getColor(R.color.purple_700))
            recyclerAdapter = TaskAdapter(todoItemsListSat,activity)
            taskRecyclerView.apply {
                setHasFixedSize(true)
                layoutManager = recyclerLayoutManager
                adapter = recyclerAdapter
            }
        }


    }
    private fun getDateAsString(date:Calendar): String {
        val year = date.get(Calendar.YEAR).toString()
        val month = (date.get(Calendar.MONTH)).toString()
        val day = date.get(Calendar.DAY_OF_MONTH).toString()
        Log.d("today's date","$year/$month/$day")
        return "$year/$month/$day"
    }
    private fun resetButtonColors()
    {
        sundayButton.setBackgroundColor(resources.getColor(R.color.purple_200))
        mondayButton.setBackgroundColor(resources.getColor(R.color.purple_200))
        tuesdayButton.setBackgroundColor(resources.getColor(R.color.purple_200))
        wednesdayButton.setBackgroundColor(resources.getColor(R.color.purple_200))
        thursdayButton.setBackgroundColor(resources.getColor(R.color.purple_200))
        fridayButton.setBackgroundColor(resources.getColor(R.color.purple_200))
        saturdayButton.setBackgroundColor(resources.getColor(R.color.purple_200))

    }


}