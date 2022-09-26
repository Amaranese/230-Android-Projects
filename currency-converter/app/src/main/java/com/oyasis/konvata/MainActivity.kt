package com.oyasis.konvata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.oyasis.konvata.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val countries = listOf<String>(
        "KENYA",
        "UGANDA",
        "TANZANIA",
        "ETHIOPIA",
        "SOMALIA"
    )

    val rates = arrayOf<DoubleArray>(
        doubleArrayOf(1.0,31.84, 20.64, 0.42, 5.25),
        doubleArrayOf(0.303, 1.0, 0.65, 0.0013, 0.16),
        doubleArrayOf(0.048, 1.54, 1.0, 0.021, 0.25),
        doubleArrayOf(2.36, 75.20, 48.74, 1.0, 12.40),
        doubleArrayOf(0.19, 6.06, 3.93, 0.081, 1.0)
    )

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onResume() {
        super.onResume()
        populateSpinners()
        initialiseConverter()
    }

    private fun initialiseConverter() {
        binding.convertBtn.setOnClickListener {
            val fromPos = binding.spinnerCtrlFrom.selectedItemPosition
            val toPos = binding.spinnerCtrlTo.selectedItemPosition

            val amountToConvert = binding.editTextCurrency.text.toString().toDouble()
            val rate = rates[fromPos][toPos]
            val converted = rate * amountToConvert

            binding.txtConverted.text = "$converted"
            binding.txtToCurrency.text = countries[toPos].substring(0,2)
            binding.txtFromCurrency.text = countries[fromPos].substring(0,2)
        }
    }

    private fun populateSpinners() {
        val arrayAdapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item,
            countries
        )
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCtrlFrom.adapter = arrayAdapter
        binding.spinnerCtrlTo.adapter = arrayAdapter

    }

}