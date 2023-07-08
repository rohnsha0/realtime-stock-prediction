package com.rohnsha.stocksense

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object pred_object {
    val retrofit= Retrofit.Builder()
        .baseUrl("https://45halapf2lg7zd42f33g6da7ci0kbjzo.lambda-url.ap-south-1.on.aws/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val predAPIservice = retrofit.create(prediction_api_interface::class.java)
}