package com.example.TrainFoodOrder.data
import com.example.TrainFoodOrder.R
import com.example.TrainFoodOrder.data.model.Trains

class TrainsDatasource {
    fun loadTrains():List<Trains> {
        return listOf<Trains>(
            Trains(R.drawable.palaceonwheels,R.string.train_1_name,R.string.train_1_route),
            Trains(R.drawable.goldenchariot,R.string.train_2_name,R.string.train_2_route),
            Trains(R.drawable.maharajasexpress,R.string.train_3_name,R.string.train_3_route),
            Trains(R.drawable.deccan,R.string.train_4_name,R.string.train_4_route),
            Trains(R.drawable.maitree,R.string.train_5_name,R.string.train_5_route),
            Trains(R.drawable.howrahjaisalmer,R.string.train_6_name,R.string.train_6_route),
            Trains(R.drawable.vandebharat,R.string.train_7_name,R.string.train_7_route),
            Trains(R.drawable.toytrain,R.string.train_8_name,R.string.train_8_route)

        )
    }
}

