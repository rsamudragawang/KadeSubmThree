package com.ganargatul.submthree

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_favorite
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    //add to db
//    private fun addToFavorite(teams : Teams){
//        try {
//            database.use {
//                insert(
//                    FavoriteItems.TABLE_FAVORITE,
//                    FavoriteItems.EVENT_ID to teams.teamId,
//                    FavoriteItems.TEAM_HOME_ID to teams.teamName,
//                    FavoriteItems.TEAM_AWAY_ID to teams.teamBadge)
//            }
////            swipeRefresh.snackbar("Added to favorite").show()
//        } catch (e: SQLiteConstraintException){
//
//        }
//    }

    //remove from db
//    private fun removeFromFavorite(){
//        try {
//            database.use {
//                delete(Favorite.TABLE_FAVORITE, "(TEAM_ID = {id})",
//                    "id" to id)
//            }
//            swipeRefresh.snackbar("Removed to favorite").show()
//        } catch (e: SQLiteConstraintException){
//            snackbar(e.localizedMessage).show()
//        }
//    }

    //check data
//    private fun favoriteState(){
//        database.use {
//            val result = select(Favorite.TABLE_FAVORITE)
//                .whereArgs("(TEAM_ID = {id})",
//                    "id" to id)
//            val favorite = result.parseList(classParser<Favorite>())
//            if (!favorite.isEmpty()) isFavorite = true
//        }
//    }
}
