package com.ganargatul.submthree.ui.pastevent

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ganargatul.submthree.connection.RetroConfig
import com.ganargatul.submthree.model.NextEventResponse
import com.ganargatul.submthree.model.NextEventsItems
import com.ganargatul.submthree.model.PastEventItems
import com.ganargatul.submthree.model.PastEventReponse
import retrofit2.Call
import retrofit2.Response

class PastEventViewModel : ViewModel() {
    private  var listMovie : MutableLiveData<List<PastEventItems>> = MutableLiveData()

    fun loadData(idEvent: String){
        Log.d("viewmodel", "viewmodel")
        RetroConfig().services.getPastEvent(idEvent).enqueue(object: retrofit2.Callback<PastEventReponse>{
            override fun onFailure(call: Call<PastEventReponse>, t: Throwable) {
                Log.d("onFailure", t.message)
            }

            override fun onResponse(
                call: Call<PastEventReponse>,
                response: Response<PastEventReponse>
            ) {
                Log.d("viewmodel", response.code().toString())

                if (response.code() == 200){
                    response.body()?.events.let {
                        listMovie.postValue(it)
                    }
                }
            }

        })
    }
    val listData: LiveData<List<PastEventItems>> = listMovie

}