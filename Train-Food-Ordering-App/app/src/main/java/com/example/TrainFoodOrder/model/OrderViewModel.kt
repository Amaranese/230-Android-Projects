/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.TrainFoodOrder.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


private const val PRICE_PER_CUPCAKE = 2.00


private const val PRICE_FOR_SAME_DAY_PICKUP = 150.00


class OrderViewModel : ViewModel() {

    //Item name
    private val _itemName = MutableLiveData<String>()
    val itemName: LiveData<String> = _itemName

    private val _itemQuantity = MutableLiveData<String>()
    val itemQuantity: LiveData<String> = _itemQuantity






    // Quantity of cupcakes in this order
    private val _quantity = MutableLiveData<Int>()
    val quantity: LiveData<Int> = _quantity

    private val _flavor = MutableLiveData<String>()
    val flavor: LiveData<String> = _flavor


    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    private val _userPhone = MutableLiveData<String>()
    val userPhone: LiveData<String> = _userPhone

    private val _userAddress = MutableLiveData<String>()
    val userAddress: LiveData<String> = _userAddress

    private val _userTrain = MutableLiveData<String>()
    val userTrain: LiveData<String> = _userTrain

    private val _userCarriage = MutableLiveData<String>()
    val userCarriage: LiveData<String> = _userCarriage

    private val _userSeat = MutableLiveData<String>()
    val userSeat: LiveData<String> = _userSeat



    // price for this order
    private val _itemPrice = MutableLiveData<Double>()
    val itemPrice: LiveData<Double> = _itemPrice

    // Possible time options
    val dateOptions: List<String> = getPickupOptions()

    // Pickup date
    private val _date = MutableLiveData<String>()
    val date: LiveData<String> = _date

    private val _priceFormatter =MutableLiveData<Double>()
    val priceFormatter : LiveData<String> = Transformations.map(_itemPrice){
        NumberFormat.getCurrencyInstance().format(it)

    }

    // Price of the order so far
    private val _price = MutableLiveData<Double>()
    val price: LiveData<String> = Transformations.map(_price) {
        // Format the price into the local currency and return this as LiveData<String>
        NumberFormat.getCurrencyInstance().format(it)
    }

    init {
        // Set initial values for the order
        resetOrder()
    }

    /**
     * Set the quantity of cupcakes for this order.
     *
     * @param numberCupcakes to order
     */
    fun setUserInfo(Name:String, Phone:String, Address:String){
        _userName.value=Name
        _userPhone.value=Phone
        _userAddress.value=Address
    }

    fun setUserTrainName(name:String){
        _userTrain.value=name
    }

    fun setUserCarriageSeat(carriage:String,seat:String){
        _userCarriage.value=carriage
        _userSeat.value=seat
    }



    fun setItemName(dishName: String) {
        _itemName.value = dishName
        updatePrice()

    }

    fun setItemQuantity(dishQuantity: String) {
        _itemQuantity.value = dishQuantity

    }


    fun setItemPrice(dishPrice: Double) {
        _itemPrice.value = dishPrice
        updatePrice()


    }


    fun setDate(pickupDate: String) {
        _date.value = pickupDate
        updatePrice()
    }


    fun hasNoFlavorSet(): Boolean {
        return _flavor.value.isNullOrEmpty()
    }


    fun resetOrder() {
        _itemQuantity.value  = ""
        _itemName.value  = ""
        _date.value = dateOptions[0]
        _itemPrice.value = 0.0

        _userPhone.value = ""
        _userName.value = ""
        _userAddress.value = ""

    }


    private fun updatePrice() {
        var calculatedPrice = itemPrice.value!!
        // If the user selected the first option (today) for pickup, add the surcharge
        if (dateOptions[0] == _date.value) {
            calculatedPrice += PRICE_FOR_SAME_DAY_PICKUP
        }
        _price.value = calculatedPrice
    }


    private fun getPickupOptions(): List<String> {
        val options = mutableListOf<String>()
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.HOUR, 1)
//        val formatter1 = SimpleDateFormat("h:00 a", Locale.getDefault())
//        val formatter2 =SimpleDateFormat("h:00 a", Locale.getDefault())
//        var s          = "${formatter1} - ${formatter2}"
        repeat(4) {
            val formatter1 = SimpleDateFormat("h:00 a", Locale.getDefault())
            val time1 =formatter1.format(calendar.time)
            calendar.add(Calendar.HOUR, 1)
            val formatter2 =SimpleDateFormat("h:00 a", Locale.getDefault())
            val time2 =formatter2.format(calendar.time)
            var s          = "${time1} - ${time2}"
            options.add(s)
            calendar.add(Calendar.HOUR, 1)


        }
        return options
    }
}