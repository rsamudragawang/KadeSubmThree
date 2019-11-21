package com.ganargatul.submthree.ui.pastevent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ganargatul.submthree.adapter.PastEventAdapter
import com.ganargatul.submthree.R
import com.ganargatul.submthree.model.LeagueItems
import com.ganargatul.submthree.model.PastEventItems
import com.ganargatul.submthree.ui.detailpastevent.DetailPastEvent
import org.jetbrains.anko.startActivity

class PastEvent : AppCompatActivity() {
    lateinit var recyclerview: RecyclerView
    lateinit var adapter: PastEventAdapter
    lateinit var items: PastEventItems
    lateinit var pb: ProgressBar
    lateinit var pastEventViewModel: PastEventViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next_event)
        pb= findViewById(R.id.pb_next_event)
        pb.visibility = View.VISIBLE
        recyclerview= findViewById(R.id.rv_list_next_event)
        recyclerview.layoutManager= LinearLayoutManager(baseContext)
        val items= intent.getParcelableExtra<LeagueItems>("Data")
        pastEventViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            PastEventViewModel::class.java)
        pastEventViewModel.loadData(items.id.toString())
        pastEventViewModel.listData.observe(this, Observer { eventItems ->

            Log.d("listdataobeserve", eventItems.toString())

            if (eventItems != null) {
                pb.visibility = View.GONE
                showItems(eventItems)
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
