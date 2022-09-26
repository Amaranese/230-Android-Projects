package com.example.tuchatapplication.adapters

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tuchatapplication.R
import com.example.tuchatapplication.models.Chat
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso

open class ChatAdapter(var context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var lst: ArrayList<Chat> = ArrayList()
    var sharedPreferences = context.getSharedPreferences(context.getString(R.string.User), MODE_PRIVATE)
    var userId = sharedPreferences!!.getString(context.getString(R.string.id), "")
    private val SEND_TYPE = userId!!.toInt()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == SEND_TYPE){
            return SendViewModel(LayoutInflater.from(parent.context).inflate(R.layout.send_item, parent, false))
        }
        else{
            return ReceiveViewModel(LayoutInflater.from(parent.context).inflate(R.layout.receive_item, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var chat: Chat = lst[position]
        if (chat.userId!!.toInt() == SEND_TYPE){
            var sendViewModel: SendViewModel = holder as SendViewModel
            sendViewModel.name.text = chat.userId.toString()
            sendViewModel.content.text = chat.message
            sendViewModel.time.text = chat.time
            sendViewModel.img.visibility = View.GONE
            if (chat.img != ""){
                sendViewModel.img.visibility = View.VISIBLE
                var picasso: Picasso.Builder = Picasso.Builder(context)
                picasso.downloader(OkHttp3Downloader(context))
                picasso.build().load(chat.img).into(sendViewModel.img)
            }
        }
        else{
            var receiveViewModel: ReceiveViewModel = holder as ReceiveViewModel
            receiveViewModel.recName.text = chat.userId.toString()
            receiveViewModel.recContent.text = chat.message
            receiveViewModel.recTime.text = chat.time
            receiveViewModel.img.visibility = View.GONE
            if (chat.img != ""){
                receiveViewModel.img.visibility = View.VISIBLE
                var picasso: Picasso.Builder = Picasso.Builder(context)
                picasso.downloader(OkHttp3Downloader(context))
                picasso.build().load(chat.img).into(receiveViewModel.img)
            }
        }
    }

    override fun getItemCount(): Int {
        return lst.size
    }

    override fun getItemViewType(position: Int): Int {
        return lst[position].userId!!.toInt()

    }

    protected class SendViewModel(view: View): RecyclerView.ViewHolder(view){
        var name: TextView = view.findViewById(R.id.nmChat)
        var content: TextView = view.findViewById(R.id.chatContent)
        var time: TextView = view.findViewById(R.id.chatTime)
        var img: ImageView = view.findViewById(R.id.imgChatMessage)
    }

    protected class ReceiveViewModel(view: View): RecyclerView.ViewHolder(view){
        var recName: TextView = view.findViewById(R.id.recName)
        var recContent: TextView = view.findViewById(R.id.recContent)
        var recTime: TextView = view.findViewById(R.id.recTime)
        var img: ImageView = view.findViewById(R.id.imgReceiveMessage)
    }

    fun getData(arr: ArrayList<Chat>){
        for (i in arr){
            lst.add(i)
            notifyDataSetChanged()
        }
    }

    fun addNewData(chat: Chat, pos: Int){
        lst.add(chat)
        notifyItemInserted(pos)
    }

}