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
package com.example.TrainFoodOrder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.TrainFoodOrder.databinding.FragmentSummaryBinding
import com.example.TrainFoodOrder.model.OrderViewModel
import android.content.Intent
import android.net.Uri

class SummaryFragment : Fragment() {

    // Binding object instance corresponding to the fragment_summary.xml layout
    // This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
    // when the view hierarchy is attached to the fragment.
    private var binding: FragmentSummaryBinding? = null

    // Use the 'by activityViewModels()' Kotlin property delegate from the fragment-ktx artifact
    private val sharedViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentSummaryBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val permissionCheck = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.SEND_SMS)
//        if (ContextCompat.checkSelfPermission(context, Manifest.permission) == PackageManager.PERMISSION_DENIED) {
//            ActivityCompat.requestPermissions(context, new String[] { permission }, requestCode);
//        }
//        else {
//            Toast.makeText(MainActivity.this, "Permission already granted", Toast.LENGTH_SHORT).show();
//        }




        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
            summaryFragment = this@SummaryFragment
        }
    }


    fun sendOrder() {
        //val numberOfCupcakes = sharedViewModel.quantity.value ?: 0
        val orderSummary = getString(
            R.string.order_details,sharedViewModel.itemName.value.toString(),sharedViewModel.itemPrice.value.toString(),
            sharedViewModel.itemQuantity.value.toString(),sharedViewModel.date.value.toString(),
            sharedViewModel.userTrain.value.toString(),sharedViewModel.userCarriage.value.toString(),
            sharedViewModel.userSeat.value.toString(),sharedViewModel.userName.value.toString(),
            sharedViewModel.userPhone.value.toString(),sharedViewModel.userAddress.value.toString(),
            sharedViewModel.price.value.toString(),
            )



//        val permissionCheck = ContextCompat?.checkSelfPermission(context, Manifest.permission.SEND_SMS)
//
//        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
//            val smsManager: SmsManager = SmsManager.getDefault()
//            smsManager.sendTextMessage("+918306808320", null, orderSummary, null, null)
//            Toast.makeText(context, "Message Sent", Toast.LENGTH_SHORT).show()
//        } else {
//            requestPermissions(context, arrayOf(Manifest.permission.SEND_SMS))
//        }
        val uri = Uri.parse("smsto:8306808320")
        val intent = Intent(Intent.ACTION_SENDTO, uri)
        intent.putExtra(Intent.EXTRA_TEXT, orderSummary)
        startActivity(intent)


    //    val smsManager= SmsManager.getDefault() as SmsManager
            //   smsManager.sendTextMessage("+918306808320", null, orderSummary, null, null)

//val smsManager = SmsManager.getDefault() as SmsManager
  //      smsManager.sendTextMessage("+918306808320", null, orderSummary, null, null)
//        val intent = Intent(Intent.ACTION_SEND)
//            .setType("text/plain")
//            .putExtra(Intent.EXTRA_SUBJECT, getString(R.string.new_food_order))
//            .putExtra(Intent.EXTRA_TEXT, orderSummary)

//        if (activity?.packageManager?.resolveActivity(intent, 0) != null) {
//            startActivity(intent)
//        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
    fun cancelOrder() {
        sharedViewModel.resetOrder()
        findNavController().navigate(R.id.action_summaryFragment_to_startFragment)

    }
}