package com.ganargatul.submthree.ui.searchevent

import android.util.Log
import android.util.Log.d
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ganargatul.submthree.connection.RetroConfig
import com.ganargatul.submthree.model.PastEventItems
import com.ganargatul.submthree.model.PastEventReponse
import com.ganargatul.submthree.model.SearchEventResponse
import retrofit2.Call
import retrofit2.Response

class SearchEventViewModel : ViewModel() {
    private  var listMovie : MutableLiveData<List<PastEventItems>> = MutableLiveData()

    fun loadData(idEvent: String){
        d("viewmodel", "viewmodel")
        RetroConfig().services.searchEvents(idEvent).enqueue(object: retrofit2.Callback<SearchEventResponse>{
            override fun onFailure(call: Call<SearchEventResponse>, t: Throwable) {
                Log.d("onFailure", t.message)
            }

            override fun onResponse(
                call: Call<SearchEventResponse>,
                response: Response<SearchEventResponse>
            ) {
                d("viewmodel", response.code().toString())

                if (response.code() == 200){
                    response.body()?.event.let {
                        listMovie.postValue(it)
                    }
                    d("responsebody",response.body().toString())
                }
            }

        })
    }
    val listData: LiveData<List<PastEventItems>> = listMovie
}