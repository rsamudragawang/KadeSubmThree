package com.ganargatul.submthree.ui.searchevent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ganargatul.submthree.R
import com.ganargatul.submthree.adapter.PastEventAdapter
import com.ganargatul.submthree.model.PastEventItems
import com.ganargatul.submthree.ui.detailpastevent.DetailPastEvent
import org.jetbrains.anko.startActivity

class SearchEvent : AppCompatActivity() {
    lateinit var recyclerview: RecyclerView
    lateinit var adapter: PastEventAdapter
    lateinit var items: PastEventItems
    lateinit var pb: ProgressBar
    private lateinit var searchEventViewModel: SearchEventViewModel
    private var listItems: MutableList<PastEventItems> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next_event)
        pb= findViewById(R.id.pb_next_event)
        pb.visibility = View.VISIBLE
        recyclerview= findViewById(R.id.rv_list_next_event)
        recyclerview.layoutManager= LinearLayoutManager(baseContext)
        val query  = intent.getStringExtra("query")
        searchEventViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            SearchEventViewModel::class.java)
        searchEventViewModel.loadData(query)
        searchEventViewModel.listData.observe(this, Observer { eventItems ->

//            Log.d("listdataobeserve", eventItems.toString())

            if (eventItems != null) {
                pb.visibility = View.GONE
                for (i in eventItems.indices){
                    d("listitems",eventItems[i].strEvent)
                    if (eventItems[i].strSport =="Soccer"){
                        d("soccer",eventItems[i].strEvent)
                        listItems.add(PastEventItems(
                            eventItems[i].idEvent,eventItems[i].strEvent,eventItems[i].dateEvent,eventItems[i].strTime,eventItems[i].idHomeTeam,eventItems[i].idAwayTeam,
                            eventItems[i].strThumb,eventItems[i].strHomeGoalDetails,eventItems[i].strHomeRedCards,eventItems[i].strHomeYellowCards,eventItems[i].strAwayRedCards,eventItems[i].strAwayYellowCards,
                            eventItems[i].strAwayGoalDetails,eventItems[i].intHomeScore,eventItems[i].intAwayScore,eventItems[i].strSport))
                    }
                    d("listitems",listItems.toString())

                }
                if (listItems.size > 0){
                    showItems(listItems)

                }else{
                    Toast.makeText(this@SearchEvent,R.string.notfound,Toast.LENGTH_SHORT).show()
                }
//                d("listitems",listItems.toString())
            }
        })
    }
    private fun showItems(it1: List<PastEventItems>) {
        adapter= PastEventAdapter(baseContext,it1){
            var nowItems = PastEventItems(it.idEvent,it.strEvent,it.dateEvent,it.strTime,it.idHomeTeam,it.idAwayTeam,
                it.strThumb,it.strHomeGoalDetails,it.strHomeRedCards,it.strHomeYellowCards,it.strAwayRedCards,it.strAwayYellowCards,
                it.strAwayGoalDetails,it.intHomeScore,it.intAwayScore,it.strSport)
            startActivity<DetailPastEvent>("Data" to nowItems)
        }

        recyclerview.adapter = adapter
    }
}
