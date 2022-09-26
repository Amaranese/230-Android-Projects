package com.example.TrainFoodOrder

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.TrainFoodOrder.model.OrderViewModel
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class ViewModelTests {
    @get:Rule
    var instantTaskExecutorRule=InstantTaskExecutorRule()

    @Test
    fun quantity_twelve_cupcakes(){
        val viewModel= OrderViewModel()
        viewModel.setQuantity(12)
        assertEquals(12,viewModel.quantity.value)
    }
    @Test
    fun price_twelve_cupcakes(){
        val viewModel = OrderViewModel()
        viewModel.setQuantity(12)

        val price = viewModel.price
        viewModel.price.observeForever{}
        assertEquals("$27.00", viewModel.price.value)
    }



}