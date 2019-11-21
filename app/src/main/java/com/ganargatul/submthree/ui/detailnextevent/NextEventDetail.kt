package com.ganargatul.submthree.ui.detailnextevent

import android.database.sqlite.SQLiteConstraintException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.ganargatul.submthree.R
import com.ganargatul.submthree.connection.RetroConfig
import com.ganargatul.submthree.db.FavoriteItems
import com.ganargatul.submthree.db.database
import com.ganargatul.submthree.model.DetailTeamsItems
import com.ganargatul.submthree.model.DetailTeamsResponse
import com.ganargatul.submthree.model.NextEventsItems
import com.squareup.picasso.Picasso
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import retrofit2.Call
import retrofit2.Response

class NextEventDetail : AppCompatActivity() {
    lateinit var eventName: TextView
    lateinit var eventDate: TextView
    lateinit var homeBadge: ImageView
    lateinit var awayBadge: ImageView
    lateinit var eventTime: TextView
    lateinit var progressbar: ProgressBar
    lateinit var items: NextEventsItems
    private  var menuItem: Menu? = null
    private  var isFav : Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_next_event)

        progressbar = findViewById(R.id.detail_league_prog)
        progressbar.visibility = View.VISIBLE
        items= intent.getParcelableExtra("Data")
        eventName = findViewById(R.id.title_detail)
        eventDate = findViewById(R.id.date_detail)
        homeBadge = findViewById(R.id.homeBadge)
        awayBadge = findViewById(R.id.awayBadge)
        eventTime = findViewById(R.id.time_detail)
        RetroConfig().services.getDetailTeam(items.idHomeTeam.toString()).enqueue(object : retrofit2.Callback<DetailTeamsResponse>{
            override fun onFailure(call: Call<DetailTeamsResponse>, t: Throwable) {
                Log.d("failure", t.message)
            }

            override fun onResponse(
                call: Call<DetailTeamsResponse>,
                response: Response<DetailTeamsResponse>
            ) {
                if (response.code() == 200){
                    response.body()?.events.let {
                        it?.let { it1 -> getAwayBadge(it1) }
                    }
                }
            }

        })
    }
    private fun favState() {
        database.use {
            val result = select(FavoriteItems.TABLE_FAVORITE)
                .whereArgs("(EVENT_ID = {id})",
                    "id" to items.idEvent.toString())
            val favorite = result.parseList(classParser<FavoriteItems>())
            d("favorite",favorite.toString())
            if (favorite.isNotEmpty()) {
                d("menuitems",menuItem.toString())
                isFav = true
                menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this@NextEventDetail, R.drawable.ic_favorite_black_24dp)
            }else{
                isFav = false
                menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this@NextEventDetail, R.drawable.ic_favorite_border_black_24dp)

            }
        }
    }

    private fun getAwayBadge(it1: List<DetailTeamsItems>) {
        RetroConfig().services.getDetailTeam(items.idAwayTeam.toString()).enqueue(object : retrofit2.Callback<DetailTeamsResponse>{
            override fun onFailure(call: Call<DetailTeamsResponse>, t: Throwable) {
                d("failure", t.message)
            }

            override fun onResponse(
                call: Call<DetailTeamsResponse>,
                response: Response<DetailTeamsResponse>
            ) {
                progressbar.visibility = View.GONE
                eventName.text=items.strEvent
                eventDate.text = items.dateEvent
                eventTime.text = items.strTime
                response.body()?.events?.get(0)?.strTeamBadge.let { Picasso.get().load(it).into(awayBadge) }
                it1[0]?.strTeamBadge.let {  Picasso.get().load(it).into(homeBadge) }
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        return super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        favState()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return super.onOptionsItemSelected(item)
        return when(item.itemId){
            R.id.add_to_favorite -> {
               if(!isFav) addtofav() else deletefav()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun deletefav() {
        try {
            database.use {
                delete(FavoriteItems.TABLE_FAVORITE, "(EVENT_ID = {id})",
                    "id" to items.idEvent.toString())
            }
            Toast.makeText(this@NextEventDetail,getString(R.string.success),Toast.LENGTH_SHORT).show()
            finish()

        } catch (e: SQLiteConstraintException){
//            snackbar(e.localizedMessage).show()
            Toast.makeText(this@NextEventDetail,getString(R.string.error),Toast.LENGTH_SHORT).show()

        }
    }

    private fun addtofav() {
        try {
            database.use {
                insert(FavoriteItems.TABLE_FAVORITE,
                    FavoriteItems.EVENT_ID to items.idEvent,
                    FavoriteItems.TEAM_HOME_ID to items.idHomeTeam,
                    FavoriteItems.TEAM_AWAY_ID to items.idAwayTeam,
                    FavoriteItems.EVENT_NAME to items.strEvent
                )
            }
            Toast.makeText(this@NextEventDetail,getString(R.string.success),Toast.LENGTH_SHORT).show()
            finish()
        }catch (e: SQLiteConstraintException){
//            swipeRefresh.snackbar(e.localizedMessage).show()
            Toast.makeText(this@NextEventDetail,getString(R.string.error),Toast.LENGTH_SHORT).show()

            e.printStackTrace()
        }

    }
}
