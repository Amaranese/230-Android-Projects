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

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.TrainFoodOrder.databinding.FragmentStartBinding
import com.example.TrainFoodOrder.model.OrderViewModel



class StartFragment : Fragment() {

    // Binding object instance corresponding to the fragment_start.xml layout
    // This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
    // when the view hierarchy is attached to the fragment.
    private var binding:FragmentStartBinding? = null

    // Use the 'by activityViewModels()' Kotlin property delegate from the fragment-ktx artifact
    private val sharedViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentStartBinding.inflate(inflater, container, false)
        binding = fragmentBinding


        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.startFragment = this

        val pref =activity?.getPreferences(Context.MODE_PRIVATE)
        binding?.nameEditText?.setText(pref?.getString("Usname",""))
        binding?.phoneEditText?.setText(pref?.getString("Usphone",""))
        binding?.addressTextField?.setText(pref?.getString("Usaddress",""))



//        sharedViewModel.setUserInfo(
//            pref?.getString("Usname", "").toString(),
//            pref?.getString("Usphone", "").toString(),
//            pref?.getString("Usaddress", "").toString()
////        )
//        val preferences = this.requireActivity()!!
//            .getSharedPreferences("pref", Context.MODE_PRIVATE)
//
//        if (pref?.getString("Usaddress", "") == "" || pref?.getString("Usname", "") == "" || pref?.getString("Usphone", "") == ""){
//            Toast.makeText(context, "Enter Your Details", Toast.LENGTH_SHORT).show()
//
//        }
//        else{
//            sharedViewModel.setUserInfo(binding?.nameEditText?.text.toString(),binding?.phoneEditText?.text.toString(),binding?.addressTextField?.text.toString())
//            findNavController().navigate(R.id.action_startFragment_to_trainListFragment)
//        }


    }


//    fun orderCupcake(quantity: Int) {
//        // Update the view model with the quantity
//        sharedViewModel.setQuantity(quantity)
//
//        // If no flavor is set in the view model yet, select vanilla as default flavor
//        if (sharedViewModel.hasNoFlavorSet()) {
//            sharedViewModel.setFlavor(getString(R.string.vanilla))
//        }
//
//        // Navigate to the next destination to select the flavor of the cupcakes
//        findNavController().navigate(R.id.action_startFragment_to_flavorFragment)
//    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
    fun goToNextScreen() {
        findNavController().navigate(R.id.action_startFragment_to_trainListFragment)

        sharedViewModel.setUserInfo(binding?.nameEditText?.text.toString(),binding?.phoneEditText?.text.toString(),binding?.addressTextField?.text.toString())

        //setting the values of nameEditText,phoneEditText,addressTextField
        val pref =activity?.getPreferences(Context.MODE_PRIVATE)
        val editor=pref?.edit()
        editor?.putString("Usname",binding?.nameEditText?.text.toString())
        editor?.putString("Usphone",binding?.phoneEditText?.text.toString())
        editor?.putString("Usaddress",binding?.addressTextField?.text.toString())
        editor?.apply()
        Toast.makeText(context, "Data saved successfully", Toast.LENGTH_SHORT).show()

    }
}