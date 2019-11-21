package com.ganargatul.submthree.ui.detailfavorite

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteConstraintException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
import com.ganargatul.submthree.model.PastEventItems
import com.ganargatul.submthree.model.PastEventReponse
import com.squareup.picasso.Picasso
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import retrofit2.Call
import retrofit2.Response

class DetailFavorite : AppCompatActivity() {
    lateinit var score: TextView
    lateinit var goalHome: TextView
    lateinit var redHome: TextView
    lateinit var yellowHome: TextView
    lateinit var goalAway: TextView
    lateinit var redAway: TextView
    lateinit var yellowAway: TextView
    lateinit var strEvent: TextView
    lateinit var items: FavoriteItems
    lateinit var imageView: ImageView
    lateinit var progressbar: ProgressBar
    private  var menuItem: Menu? = null
    private  var isFav : Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_event)
        items= intent.getParcelableExtra("Data")
        progressbar = findViewById(R.id.detail_league_prog)
        progressbar.visibility = View.VISIBLE
        score = findViewById(R.id.score_detail)
        goalHome = findViewById(R.id.goalHome_detail)
        redHome = findViewById(R.id.redHome_detail)
        yellowHome = findViewById(R.id.yellowHome_detail)
        goalAway = findViewById(R.id.goalAway_detail)
        redAway = findViewById(R.id.redAway_detail)
        yellowAway = findViewById(R.id.yellowAway_detail)
        strEvent = findViewById(R.id.title_detail)
        imageView = findViewById(R.id.image_detail)
        RetroConfig().services.getDetailEvent(items.EVENT_ID_.toString()).enqueue(object : retrofit2.Callback<PastEventReponse>{
            override fun onFailure(call: Call<PastEventReponse>, t: Throwable) {
                Log.d("onFailure", t.message)
            }

            override fun onResponse(
                call: Call<PastEventReponse>,
                response: Response<PastEventReponse>
            ) {
                if (response.code() == 200){
                    response.body()?.events.let {
                        it?.let { it1 -> showItems(it1) }
                    }
                }
            }

        })
    }
    @SuppressLint("SetTextI18n")
    private fun showItems(it1: List<PastEventItems>) {
        progressbar.visibility = View.GONE
        it1[0].strThumb.let {  Picasso.get().load(it).into(imageView) }
        score.text = it1[0].intHomeScore.toString() +"  " + it1[0].intAwayScore.toString()
        goalHome.text = it1[0].strHomeGoalDetails
        redHome.text = it1[0].strHomeRedCards
        yellowHome.text = it1[0].strHomeYellowCards
        goalAway.text = it1[0].strAwayGoalDetails
        redAway.text = it1[0].strAwayRedCards
        yellowAway.text = it1[0].strAwayYellowCards
        strEvent.text = it1[0].strEvent

    }
    private fun favState() {
        database.use {
            val result = select(FavoriteItems.TABLE_FAVORITE)
                .whereArgs("(EVENT_ID = {id})",
                    "id" to items.EVENT_ID_.toString())
            val favorite = result.parseList(classParser<FavoriteItems>())
            Log.d("favorite", favorite.toString())
            if (favorite.isNotEmpty()) {
                isFav = true
                Log.d("menuitems", menuItem.toString())
                menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this@DetailFavorite, R.drawable.ic_favorite_black_24dp)
            }else{
                Log.d("menuitems", menuItem.toString())
                isFav = false
                menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this@DetailFavorite, R.drawable.ic_favorite_border_black_24dp)

            }
        }
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
                    "id" to  items.EVENT_ID_.toString())
            }
            Toast.makeText(this@DetailFavorite,getString(R.string.success), Toast.LENGTH_SHORT).show()
            finish()

        } catch (e: SQLiteConstraintException){
//            snackbar(e.localizedMessage).show()
            Toast.makeText(this@DetailFavorite,getString(R.string.error), Toast.LENGTH_SHORT).show()

        }
    }

    private fun addtofav() {
        try {
            database.use {
                insert(FavoriteItems.TABLE_FAVORITE,
                    FavoriteItems.EVENT_ID to items.EVENT_ID_,
                    FavoriteItems.TEAM_HOME_ID to items.TEAM_HOME_,
                    FavoriteItems.TEAM_AWAY_ID to items.TEAM_AWAY_,
                    FavoriteItems.EVENT_NAME to items.EVENT_NAME_
                )
                Toast.makeText(this@DetailFavorite,getString(R.string.success), Toast.LENGTH_SHORT).show()
                finish()

            }
        }catch (e: SQLiteConstraintException){
//            swipeRefresh.snackbar(e.localizedMessage).show()
            Toast.makeText(this@DetailFavorite,getString(R.string.error), Toast.LENGTH_SHORT).show()

            e.printStackTrace()
        }

    }
}
