package com.example.TrainFoodOrder.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.TrainFoodOrder.R
import com.example.TrainFoodOrder.data.model.Trains
import com.example.TrainFoodOrder.model.OrderViewModel

class TrainItemAdapter(private val context: Context, private val dataset:List<Trains>,private val sharedViewModel: OrderViewModel):
    RecyclerView.Adapter<TrainItemAdapter.ItemViewHolder>() {


    class ItemViewHolder(private val view: View?): RecyclerView.ViewHolder(view!!){
        val trainName:TextView?=view?.findViewById(R.id.train_name)
        val trainRoute:TextView?=view?.findViewById(R.id.train_route)
        val trainImage:ImageView?=view?.findViewById(R.id.train_image)
        val nextButton: Button? =view?.findViewById(R.id.button)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout=LayoutInflater.from(parent.context).inflate(R.layout.train_list_item,parent,false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.trainName?.text = context.resources.getString(item.name)
        holder.trainRoute?.text=context.resources.getString(item.route)
        holder.trainImage?.setImageResource(item.imageResourceId)

       // holder.nextButton?.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_trainListFragment_to_carriageSeatFragment))
        holder.nextButton?.setOnClickListener {
                View -> View.findNavController().navigate(R.id.action_trainListFragment_to_carriageSeatFragment)
            sharedViewModel.setUserTrainName( context.resources.getString(item.name))

            //  View -> viewModel?.setItemName(context.resources.getString(item.OrderName))

        }

    }

    override fun getItemCount(): Int {
            return dataset.size
    }
}