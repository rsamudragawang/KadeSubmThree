package com.ganargatul.submthree.ui.nextevent

import android.util.Log
import android.util.Log.d
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ganargatul.submthree.connection.RetroConfig

import com.ganargatul.submthree.model.NextEventResponse
import com.ganargatul.submthree.model.NextEventsItems
import retrofit2.Call
import retrofit2.Response

class NextEventViewModel : ViewModel() {
    private  var listMovie :  MutableLiveData<List<NextEventsItems>> = MutableLiveData()

    fun loadData(idEvent: String){
        d("viewmodel","viewmodel")
        RetroConfig().services.getNextEvent(idEvent).enqueue(object: retrofit2.Callback<NextEventResponse>{
            override fun onFailure(call: Call<NextEventResponse>, t: Throwable) {
                Log.d("onFailure", t.message)
            }

            override fun onResponse(
                call: Call<NextEventResponse>,
                response: Response<NextEventResponse>
            ) {
                d("viewmodel",response.code().toString())

                if (response.code() == 200){
                    response.body()?.events.let {
                        listMovie.postValue(it)
                    }
                }
            }

        })
    }
    val listData: LiveData<List<NextEventsItems>> = listMovie

}