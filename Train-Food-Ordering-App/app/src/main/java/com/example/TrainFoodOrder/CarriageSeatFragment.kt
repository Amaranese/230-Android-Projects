package com.example.TrainFoodOrder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.TrainFoodOrder.databinding.FragmentCarriageSeatBinding
import com.example.TrainFoodOrder.model.OrderViewModel

class CarriageSeatFragment : Fragment() {
    private var binding:FragmentCarriageSeatBinding?= null
    private val sharedViewModel: OrderViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentCarriageSeatBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
//        val carriages = resources.getStringArray(R.array.carriage_numbers)
//        val seats =resources.getStringArray(R.array.seat_numbers)
//
//        val arrayAdapter = ArrayAdapter(requireContext(),R.layout.dropdown_item,carriages)
//        val arrayAdapter2 = ArrayAdapter(requireContext(),R.layout.dropdown_item,seats)
//
//        val autocompleteTV = view?.findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
//        val autocompleteTV2 = view?.findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView2)
//
//        autocompleteTV?.setAdapter(arrayAdapter)
//        autocompleteTV2?.setAdapter(arrayAdapter2)

        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_carriage_seat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.carriageSeatFragment = this
        val carriages = resources.getStringArray(R.array.carriage_numbers)
        val seats =resources.getStringArray(R.array.seat_numbers)

        val arrayAdapter = ArrayAdapter(requireContext(),R.layout.dropdown_item,carriages)
        val arrayAdapter2 = ArrayAdapter(requireContext(),R.layout.dropdown_item,seats)

        val autocompleteTV = view?.findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
        val autocompleteTV2 = view?.findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView2)

        autocompleteTV?.setAdapter(arrayAdapter)
        autocompleteTV2?.setAdapter(arrayAdapter2)
    }

    fun goToNextScreen() {
        findNavController().navigate(R.id.action_carriageSeatFragment_to_orderListFragment)
        sharedViewModel.setUserCarriageSeat(binding?.textInputLayout?.editText?.text.toString(),binding?.textInputLayout2?.editText?.text.toString())
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
    fun cancelOrder() {
        sharedViewModel.resetOrder()
        findNavController().navigate(R.id.action_carriageSeatFragment_to_startFragment)
    }


}