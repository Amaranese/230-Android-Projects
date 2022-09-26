package com.example.timetablemanager.taskScheduler

import android.app.*
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import com.example.timetablemanager.*
import com.example.timetablemanager.notification.Notification
import com.example.timetablemanager.databaseHandler.DatabaseOperations
import com.example.timetablemanager.notification.channelID
import com.example.timetablemanager.notification.messageExtra
import com.example.timetablemanager.notification.notificationID
import com.example.timetablemanager.notification.titleExtra
import java.util.*

class TaskEditorActivity : AppCompatActivity() {
    private lateinit var taskName:EditText
    private lateinit var startTime:TimePicker
    private lateinit var endTime:TimePicker
    private lateinit var date:DatePicker
    private lateinit var saveButton:Button
    private lateinit var cancelButton:Button
    private lateinit var routinezed:SwitchCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_editor)
        taskName=findViewById(R.id.task_name)
        startTime=findViewById(R.id.start_time_picker)
        endTime=findViewById(R.id.end_time_picker)
        date=findViewById(R.id.date_picker)
        saveButton=findViewById(R.id.save_button)
        cancelButton=findViewById(R.id.cancel_button)
        routinezed=findViewById(R.id.routinized)

        var editingItem=false;
        val itemName = intent.getStringExtra("ITEM_NAME")
        val itemStart = intent.getStringExtra("ITEM_START")
        val itemEnd = intent.getStringExtra("ITEM_END")
        val itemDate = intent.getStringExtra("ITEM_DATE")
        if(itemName!=null) {
            taskName.setText(itemName)
            editingItem=true;
        }
        if(itemStart!=null) {
            val list=itemStart.split(":")
            startTime.hour=list[0].toInt()
            startTime.minute=list[1].toInt()

        }
        if(itemEnd!=null) {
            val list=itemEnd.split(":")
            endTime.hour=list[0].toInt()
            endTime.minute=list[1].toInt()
        }
        Log.d("testing","$itemName $itemStart $itemEnd $itemDate")

        lateinit var oldTask: Task
        if(editingItem)
            oldTask= Task(itemName!!,itemStart!!,itemEnd!!,itemDate!!)


        saveButton.setOnClickListener {

            val item=taskName.text.toString()
            val start="${startTime.hour}:${startTime.minute}"
            val end="${endTime.hour}:${endTime.minute}"
            val dateToDo="${date.year}/${date.month}/${date.dayOfMonth}"
            val task= Task(item,start,end,dateToDo)

            var invalidName=false
            if(item.isEmpty())
                invalidName=true

            var invalidEndtime=false
            if(startTime.hour>endTime.hour)
                invalidEndtime=true
            if(startTime.hour==endTime.hour && startTime.minute>=endTime.minute)
                invalidEndtime=true

            if(invalidEndtime)
            {
                show("Pick a valid Time")
            }
            else if(invalidName)
            {
                show("Please fill in a task name")
            }
            else {
                val dbo = DatabaseOperations(this)
                if (!editingItem) {
                    dbo.addItem(dbo, task)

                    if(routinezed.isChecked)
                    {
                        for(i in 1..10)
                        {
                            val dateTodoAfter="${date.year}/${date.month+(i*7)}/${date.dayOfMonth}"
                            val afterTask=Task(item,start,end,dateTodoAfter)
                            dbo.addItem(dbo, afterTask)

                        }
                    }
                } else {
                    dbo.updateItem(dbo, oldTask, task)
                }
                if(GlobalVariables.notif) {
                    createNotificationChannel()
                    scheduleNotification()
                }
                else
                {
                    show("task saved")
                }
                //            val intent: Intent = Intent(this, MainActivity::class.java)
                //            startActivity(intent)
                //            finish()
            }
        }

        cancelButton.setOnClickListener{
            val intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun scheduleNotification()
    {
        val intent = Intent(applicationContext, Notification::class.java)
        val title = "task alert"
        val message = taskName.text.toString()
        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, message)

        val pendingIntent = PendingIntent.getBroadcast(
                applicationContext,
                notificationID,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = getTime()
        alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                time,
                pendingIntent
        )
        showAlert(time, title, message)
    }

    private fun showAlert(time: Long, title: String, message: String)
    {
        val alertDate = Date(time)
        val dateFormat = android.text.format.DateFormat.getLongDateFormat(applicationContext)
        val timeFormat = android.text.format.DateFormat.getTimeFormat(applicationContext)

        AlertDialog.Builder(this)
                .setTitle("A notification is scheduled")
                .setMessage(
                        "Title: " + title +
                                "\nMessage: " + message +
                                "\nAt: " + dateFormat.format(alertDate) + " " + timeFormat.format(alertDate))
                .setPositiveButton("Okay"){_,_ ->}
                .show()
    }

    private fun getTime(): Long
    {
        val minute = startTime.minute
        val hour = startTime.hour
        val day = date.dayOfMonth
        val month = date.month
        val year = date.year

        val calendar = Calendar.getInstance()
        calendar.set(year, month, day, hour, minute)
        return calendar.timeInMillis
    }

    private fun createNotificationChannel()
    {
        val name = "Notif Channel"
        val desc = "A Description of the Channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelID, name, importance)
        channel.description = desc
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
    private fun show(message:String) {
        Toast.makeText(
                this.baseContext, message, Toast.LENGTH_SHORT).show()

    }
}