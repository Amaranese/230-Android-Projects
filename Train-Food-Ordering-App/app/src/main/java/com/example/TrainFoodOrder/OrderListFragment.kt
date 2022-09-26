package com.example.TrainFoodOrder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.TrainFoodOrder.adapter.OrderItemAdapter
import com.example.TrainFoodOrder.data.OrdersDataSource
import com.example.TrainFoodOrder.databinding.FragmentOrderListBinding
import com.example.TrainFoodOrder.model.OrderViewModel

class OrderListFragment: Fragment() {

    private var _binding: FragmentOrderListBinding? = null
    private lateinit var recyclerView: RecyclerView
    private val sharedViewModel: OrderViewModel by activityViewModels()
    private val binding get() = _binding!!




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentOrderListBinding.inflate(inflater,container,false)
        val view = binding.root
        return view


//        val myDataset = TrainsDatasource().loadTrains()
//        recyclerView.adapter=TrainItemAdapter(context=TrainListFragment,myDataset)
//
//        binding.trainRecyclerView.setHasFixedSize(true)
//
//        // Inflate the layout for this fragment
//
//        return inflater.inflate(R.layout.fragment_train_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
        val myDataset = OrdersDataSource().loadOrders()
        recyclerView = binding.orderRecyclerView

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = OrderItemAdapter(requireContext(),myDataset,sharedViewModel)

        binding?.apply {
            // Specify the fragment as the lifecycle owner


            // Assign the view model to a property in the binding class

            // Assign the fragment

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setHasOptionsMenu(true)

    }
}