package com.alejandro.maranes.TrainFoodOrder.data.model

import androidx.annotation.DrawableRes

data class Orders (
    @DrawableRes val orderImageResourceid :Int,
    val OrderName:Int,
    val OrderQuantity:Int,
    val OrderPrice:Int
        )



