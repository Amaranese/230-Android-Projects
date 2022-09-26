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
import com.example.TrainFoodOrder.data.model.Orders
import com.example.TrainFoodOrder.model.OrderViewModel

class OrderItemAdapter(private val context: Context, private val dataset: List<Orders>,private val sharedViewModel: OrderViewModel):
    RecyclerView.Adapter<OrderItemAdapter.ItemViewHolder>() {



   // val viewModel: OrderViewModel= OrderViewModel()
   // val viewModel :ViewModel = OrderViewModel()
    class ItemViewHolder(private val view: View?): RecyclerView.ViewHolder(view!!){
        val orderName: TextView?=view?.findViewById(R.id.order_name)
        val orderQuantity: TextView?=view?.findViewById(R.id.order_quantity)
        val orderPrice: TextView?=view?.findViewById(R.id.order_price)
        val orderImage: ImageView?=view?.findViewById(R.id.order_image)
        val orderButton:Button?=view?.findViewById(R.id.button)



    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout=
            LayoutInflater.from(parent.context).inflate(R.layout.order_list_item,parent,false)

//        viewModel?.setItemName("my order")
        return ItemViewHolder(adapterLayout)



    }

    override fun onBindViewHolder(holder: OrderItemAdapter.ItemViewHolder, position: Int) {



        val item = dataset[position]
        holder.orderName?.text = context.resources.getString(item.OrderName)
        holder.orderQuantity?.text=context.resources.getString(item.OrderQuantity)
        holder.orderPrice?.text=context.resources.getString(item.OrderPrice)
        holder.orderImage?.setImageResource(item.orderImageResourceid)

        //holder.orderButton?.setOnClickListener({viewModel?.setItemPrice(context.resources.getInteger(item.OrderPrice))})

       // holder.orderButton?.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_orderListFragment_to_pickupFragment))

//            OrderViewModel().setItemPrice((context.resources.getString(item.OrderPrice)).substring(1).toInt())
//        }
        holder.orderButton?.setOnClickListener {
            View -> View.findNavController().navigate(R.id.action_orderListFragment_to_pickupFragment)
            sharedViewModel.setItemName(context.resources.getString(item.OrderName))
            sharedViewModel.setItemPrice(context.resources.getString(item.OrderPrice).substring(1).toDouble())
            sharedViewModel.setItemQuantity(context.resources.getString(item.OrderQuantity))

          //  View -> viewModel?.setItemName(context.resources.getString(item.OrderName))
             
        }


            //Toast.makeText(context, "${context.resources.getString(item.OrderName)}", Toast.LENGTH_SHORT).show()




    }
    override fun getItemCount(): Int {


        return dataset.size
    }





//    fun setItemNameAdapter(dishName:String){
//        viewModel?.setItemName(dishName)
//    }


}