package com.example.TrainFoodOrder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.TrainFoodOrder.adapter.TrainItemAdapter
import com.example.TrainFoodOrder.data.TrainsDatasource
import com.example.TrainFoodOrder.databinding.FragmentTrainListBinding
import com.example.TrainFoodOrder.model.OrderViewModel


class TrainListFragment : Fragment() {
    private var _binding: FragmentTrainListBinding? = null
    private lateinit var recyclerView: RecyclerView
    private val sharedViewModel: OrderViewModel by activityViewModels()
    private val binding get() = _binding!!




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentTrainListBinding.inflate(inflater,container,false)
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
        val myDataset = TrainsDatasource().loadTrains()
        recyclerView = binding.trainRecyclerView

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = TrainItemAdapter(requireContext(),myDataset, sharedViewModel)


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setHasOptionsMenu(true)

    }
 fun goToNextScreen() {
        findNavController().navigate(R.id.action_trainListFragment_to_carriageSeatFragment)
    }



}