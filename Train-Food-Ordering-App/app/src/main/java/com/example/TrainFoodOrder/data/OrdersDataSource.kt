package com.example.TrainFoodOrder.data

import com.example.TrainFoodOrder.R
import com.example.TrainFoodOrder.data.model.Orders

class OrdersDataSource {

    fun loadOrders():List<Orders> {
        return listOf<Orders>(
            Orders(R.drawable.chicken_nuggets,R.string.order_name_1,R.string.order_quantity_1,R.string.order_price_1),
            Orders(R.drawable.chicken_wings,R.string.order_name_2,R.string.order_quantity_2,R.string.order_price_2),
            Orders(R.drawable.pop_nuggets_combo,R.string.order_name_3,R.string.order_quantity_3,R.string.order_price_3),
            Orders(R.drawable.famous_bowl,R.string.order_name_4,R.string.order_quantity_4,R.string.order_price_4),
            Orders(R.drawable.tenders,R.string.order_name_5,R.string.order_quantity_5,R.string.order_price_5),
            Orders(R.drawable.chicken_sandwich_box,R.string.order_name_6,R.string.order_quantity_6,R.string.order_price_6),
            Orders(R.drawable.chicken_bucket_ccexpress,R.string.order_name_7,R.string.order_quantity_7,R.string.order_price_7),
            Orders(R.drawable.fantasticfour_ccexpress,R.string.order_name_8,R.string.order_quantity_8,R.string.order_price_8),
            Orders(R.drawable.fries,R.string.order_name_9,R.string.order_quantity_9,R.string.order_price_9),
            Orders(R.drawable.chicken_burger,R.string.order_name_10,R.string.order_quantity_10,R.string.order_price_10)


        )


    }
}