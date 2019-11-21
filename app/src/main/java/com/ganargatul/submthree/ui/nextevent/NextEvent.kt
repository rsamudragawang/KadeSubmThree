package com.ganargatul.submthree.ui.nextevent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ganargatul.submthree.R
import com.ganargatul.submthree.adapter.NextEventAdapter
import com.ganargatul.submthree.model.LeagueItems
import com.ganargatul.submthree.model.NextEventsItems
import com.ganargatul.submthree.ui.detailnextevent.NextEventDetail
import org.jetbrains.anko.startActivity

class NextEvent : AppCompatActivity() {
    lateinit var recyclerview: RecyclerView
    lateinit var adapter: NextEventAdapter
    lateinit var nextEventViewModel: NextEventViewModel
    lateinit var pb : ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next_event)
        pb= findViewById(R.id.pb_next_event)
        pb.visibility = View.VISIBLE
        recyclerview= findViewById(R.id.rv_list_next_event)
        recyclerview.layoutManager= LinearLayoutManager(baseContext)
        val items= intent.getParcelableExtra<LeagueItems>("Data")
        nextEventViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(NextEventViewModel::class.java)
        nextEventViewModel.loadData(items.id.toString())
        nextEventViewModel.listData.observe(this, Observer { eventItems ->

            d("listdataobeserve",eventItems.toString())

            if (eventItems != null) {
                pb.visibility = View.GONE
                showItems(eventItems)
            }
        })
    }

    private fun showItems(it1: List<NextEventsItems>) {
        adapter= NextEventAdapter(baseContext,it1){
            var nowItems = NextEventsItems(it.idEvent,it.strEvent,it.dateEvent,it.strTime,it.idHomeTeam,it.idAwayTeam)
            startActivity<NextEventDetail>("Data" to nowItems)
        }

        recyclerview.adapter = adapter
    }
}
